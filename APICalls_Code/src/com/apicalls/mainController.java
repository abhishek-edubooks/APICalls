package com.apicalls;

import java.io.File;
import java.sql.Connection;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.apicalls.util.LoggerMain;

import static com.apicalls.mws.GetCompetitivePricingForASIN.GetReport.getCompetitivePricingForASIN;
import static com.apicalls.mws.GetCompetitivePricingForASIN.xmlToSql.setGetCompetitivePricingForASIN;
import static com.apicalls.mws.GetLowestOfferListingsForASIN.GetReport.getLowestOfferListingsForASIN;
import static com.apicalls.mws.GetLowestOfferListingsForASIN.xmlToSql.setGetLowestOfferListingsForASIN;
import static com.apicalls.util.CommonMethod.*;
import static com.apicalls.util.DBOperations.*;

public class mainController {
    private static String filePath = new File("").getAbsolutePath();
    private static Logger logger = null;

    private static void checkThrottleSleep(Connection connection, int requestCount) throws InterruptedException {
        long currentTimestamp = System.currentTimeMillis() / 1000;
        String updateTrackerQuery;
        if (requestCount >= 36000){

            Map<String, String> Times = readTracker(connection, "SELECT EndTime FROM `DataEngine`.`Tracker_MWSReports` " +
                    "WHERE ReportType='GetCompetitivePricingForASIN'");
            if (Times != null) {

                if (currentTimestamp < Long.parseLong(Times.get("EndTime"))){
                    long diff = Long.parseLong(Times.get("EndTime")) - currentTimestamp;
                    diff /= 60;
                    diff += 1;
                    logger.info("Going for Sleep for " + diff + " Minutes.");

                    // Update MWSReportsCallTracker.
                    updateTrackerQuery = "UPDATE `DataEngine`.`Tracker_MWSReports` SET CurrentHealth = 'Sleep for " + diff + " Minutes.' " +
                            "WHERE ReportType IN ('GetCompetitivePricingForASIN', 'GetLowestOfferListingsForASIN')";
                    updateTracker(connection, updateTrackerQuery);

                    TimeUnit.MINUTES.sleep(diff);
                    currentTimestamp = System.currentTimeMillis() / 1000;

                    // Update MWSReportsCallTracker.
                    updateTrackerQuery = "UPDATE `DataEngine`.`Tracker_MWSReports` SET CurrentHealth = 'ok.', " +
                            "StartTime = " + currentTimestamp + ", EndTime = " + (currentTimestamp + 3600) +
                            " WHERE ReportType IN ('GetCompetitivePricingForASIN', 'GetLowestOfferListingsForASIN')";
                    updateTracker(connection, updateTrackerQuery);
                }
            } else {
                logger.info("Going for Sleep for 60 Minutes.");
                TimeUnit.MINUTES.sleep(60);
            }
        }
    }

    private static void runCalls() {
        System.out.println("runCalls");
        long currentTimestamp = System.currentTimeMillis() / 1000;

        Map<String, String> fileConfig = ReadConfigFile();
        Connection connection = getDataEngineDBConnection(fileConfig);
        if (connection == null)
            sqlConnectionError();

        try {
            updateTracker(connection, "UPDATE `DataEngine`.`Tracker_MWSReports` SET EmergencyExit='no', " +
                    "StartTime = " + currentTimestamp + ", EndTime = " + (currentTimestamp + 3600) +
                    ", CurrentHealth = 'ok.', CurrentSignal = 'green', CmdReturn = '_RUNNING_', CallStartDate = '" +
                    new SimpleDateFormat("YYYY-MM-dd").format(new Date()) + "' WHERE " +
                    "ReportType IN ('GetCompetitivePricingForASIN', 'GetLowestOfferListingsForASIN')");

            Map<String, String> DBConfigGetCompetitivePricingForASIN = getConfig(connection, fileConfig, "GetCompetitivePricingForASIN");
            Map<String, String> DBConfigGetLowestOfferListingsForASIN = getConfig(connection, fileConfig, "GetLowestOfferListingsForASIN");

            int requestCount = 0;
            while (checkEmergencyExit(connection)) {
                List<String> asinList = getASIN(connection);
                if (asinList != null) {

                    checkThrottleSleep(connection, requestCount);
                    if (requestCount >= 36000)
                        requestCount = 0;

                    Map<String, String> checkRun = readTracker(connection, "SELECT `CallBatchNumber` FROM " +
                            "`DataEngine`.`Tracker_MWSReports` WHERE ReportType='GetCompetitivePricingForASIN'");

                    assert checkRun != null;
                    int callBatchNumber = Integer.parseInt(checkRun.get("CallBatchNumber"));

                    String msgGetCompetitivePricingForASIN = callGetCompetitivePricingForASIN(DBConfigGetCompetitivePricingForASIN, asinList, callBatchNumber);
                    String msgGetLowestOfferListingsForASIN = callGetLowestOfferListingsForASIN(DBConfigGetLowestOfferListingsForASIN, asinList, callBatchNumber);
                    requestCount++;

                    errorUpdate(connection, msgGetCompetitivePricingForASIN, "GetCompetitivePricingForASIN");
                    errorUpdate(connection, msgGetLowestOfferListingsForASIN, "GetLowestOfferListingsForASIN");

                    updateTracker(connection, "UPDATE `DataEngine`.`Tracker_MWSReports` SET CallBatchNumber=CallBatchNumber + 1 " +
                            "WHERE ReportType IN ('GetCompetitivePricingForASIN', 'GetLowestOfferListingsForASIN')");
                    updateLastCalled(connection, asinList);
                }
            }
            System.out.println("IN_SUCCESS");
            logger.info("IN_SUCCESS");

        } catch (Exception e) {
            logger.severe("Failed. Error Message: " + e);
            e.printStackTrace();
            logger.severe("IN_FAILED");
        }
    }

    private static void runSQL() {
        System.out.println("runSQL");
        Map<String, String> fileConfig = ReadConfigFile();
        Connection connection = getDataEngineDBConnection(fileConfig);
        if (connection == null)
            sqlConnectionError();

        try {
            TimeUnit.SECONDS.sleep(10); // Wait for file to come in.
            while (checkEmergencyExit(connection)) {

                Map<String, String> checkRun = readTracker(connection, "SELECT `ProcessBatchNumber`, `CallBatchNumber` FROM " +
                        "`DataEngine`.`Tracker_MWSReports` WHERE ReportType='GetCompetitivePricingForASIN'");

                assert checkRun != null;
                int ProcessBatchNumber = Integer.parseInt(checkRun.get("ProcessBatchNumber"));
                int CallBatchNumber = Integer.parseInt(checkRun.get("CallBatchNumber"));
                int batchId = Integer.parseInt(new SimpleDateFormat("YYMMddHHmm").format(new Date()));

                if (CallBatchNumber > ProcessBatchNumber) {
                    sqlGetCompetitivePricingForASIN(connection, batchId, ProcessBatchNumber);
                    sqlGetLowestOfferListingsForASIN(connection, batchId, ProcessBatchNumber);

                    updateTracker(connection, "UPDATE `DataEngine`.`Tracker_MWSReports` SET ProcessBatchNumber=ProcessBatchNumber + 1 " +
                            "WHERE ReportType IN ('GetCompetitivePricingForASIN', 'GetLowestOfferListingsForASIN')");
                } else
                    TimeUnit.SECONDS.sleep(2); // Wait for file to come in.
            }
            System.out.println("IN_SUCCESS");
            logger.info("IN_SUCCESS");

        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed. Error Message: " + e);
            logger.severe("IN_FAILED");
        }
    }

    private static String callGetCompetitivePricingForASIN(Map<String, String> DBConfig, List<String> asinList, int CallBatchNumber) {
        String message = getCompetitivePricingForASIN(DBConfig, logger,
                filePath + "/report_files/GetCompetitivePricingForASIN_" + CallBatchNumber + ".xml", asinList);
        if (message != null)
            logger.severe("Failed GetCompetitivePricingForASIN for asin list: " + asinList);

        return message;
    }

    private static String callGetLowestOfferListingsForASIN(Map<String, String> DBConfig, List<String> asinList, int CallBatchNumber) {
        String message = getLowestOfferListingsForASIN(DBConfig, logger,
                filePath + "/report_files/GetLowestOfferListingsForASIN_" + CallBatchNumber + ".xml", asinList);
        if (message != null)
            logger.severe("Failed GetLowestOfferListingsForASIN for asin list: " + asinList);

        return message;
    }

    private static void sqlGetCompetitivePricingForASIN(Connection connection, int batchId, int ProcessBatchNumber) {
        String filename = filePath + "/report_files/GetCompetitivePricingForASIN_" + ProcessBatchNumber + ".xml";

        File f = new File(filename);
        if(f.exists() && f.length() > 0) {
            setGetCompetitivePricingForASIN(connection, logger, filename, batchId);
            if (!f.renameTo(new File("/Web/Crons/GoogleDrive/US_MWS_GetCompetitivePricingForASIN/GetCompetitivePricingForASIN_" + ProcessBatchNumber + ".xml")))
                logger.severe("Failed to move file.");
        } else
            logger.severe("File doesn't exist or File is empty: " + filename);

    }

    private static void sqlGetLowestOfferListingsForASIN(Connection connection, int batchId, int ProcessBatchNumber) {
        String filename = filePath + "/report_files/GetLowestOfferListingsForASIN_" + ProcessBatchNumber + ".xml";

        File f = new File(filename);
        if(f.exists() && f.length() > 0) {
            setGetLowestOfferListingsForASIN(connection, logger, filename, batchId);
            if (!f.renameTo(new File("/Web/Crons/GoogleDrive/US_MWS_GetLowestOfferListingsForASIN/GetLowestOfferListingsForASIN_" + ProcessBatchNumber + ".xml")))
                logger.severe("Failed to move file.");
        } else
            logger.severe("File doesn't exist or File is empty: " + filename);
    }

    private static void errorUpdate(Connection connection, String errorMessage, String report) {
        if (errorMessage != null){
            // Update MWSReportsCallTracker.
            String updateTrackerQuery = "UPDATE `DataEngine`.`Tracker_MWSReports` SET CurrentSignal = 'red', " +
                    "EndTime = " + (System.currentTimeMillis() / 1000) + ", CmdReturn = 'IN_FAILED', " +
                    "CurrentHealth = '" + errorMessage + "' WHERE ReportType = '" + report + "'";
            updateTracker(connection, updateTrackerQuery);

            System.out.println("IN_FAILED");
            logger.severe(errorMessage);
            logger.severe("IN_FAILED");
        }
    }

    private static void invalidArgumentError(String message){
        System.out.println(message);
        System.out.println("    Try these:");
        System.out.println("        Calls");
        System.out.println("        Sql");

        logger.severe(message);
        logger.severe("IN_FAILED");

        System.exit(0);
    }

    private static void sqlConnectionError(){
        System.out.println("SQL Connection Failed.");
        logger.severe("SQL Connection Failed.");
        System.out.println("IN_FAILED");
        logger.severe("IN_FAILED");
        System.exit(0);
    }


    public static void main(String[] args) {
        String dt = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String logfile = filePath + "/logFiles/" + dt + "_" + "ApiCalls.log";
        logger = LoggerMain.getLogger(logfile, false);

        if(args.length == 0)
            invalidArgumentError("Invalid Number of Arguments. Need one argument.");

        if (args[0].equalsIgnoreCase("Calls"))
            runCalls();
        else if (args[0].equalsIgnoreCase("Sql"))
            runSQL();

        else
            invalidArgumentError("Invalid Argument value.");
    }

}

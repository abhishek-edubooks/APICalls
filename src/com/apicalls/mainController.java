package com.apicalls;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.apicalls.util.LoggerMain;

import static com.apicalls.mws.GetCompetitivePricingForASIN.GetReport.getCompetitivePricingForASIN;
import static com.apicalls.util.CommonMethod.ReadConfigFile;
import static com.apicalls.util.DBOperations.*;

public class mainController {
    private static Logger logger = null;
    private static String filePath = new File("").getAbsolutePath();

    private static void checkThrottleSleep(Connection connection, int requestCount, String report)
            throws InterruptedException {

        String updateTrackerQuery;
        if (requestCount >= 36000){
            String selectQuery = "SELECT CurrentHourEnds FROM `DataEngine`.`Tracker_APICalls` " +
                    "WHERE APICallName = '" + report +"'";

            Map<String, String> Times = readTracker(connection, selectQuery);
            if (Times != null) {

                if ((System.currentTimeMillis() / 1000) < Long.parseLong(Times.get("CurrentHourEnds"))){
                    long diff = Long.parseLong(Times.get("CurrentHourEnds")) - (System.currentTimeMillis() / 1000);
                    diff /= 60;
                    diff += 1;
                    logger.info("Going for Sleep for " + diff + " Minutes.");

                    // Update MWSReportsCallTracker.
                    updateTrackerQuery = "UPDATE `DataEngine`.`Tracker_APICalls` SET " +
                            "CurrentHealth = 'Sleep for " + diff + " Minutes.' " +
                            "WHERE APICallName = '" + report + "'";
                    updateTracker(connection, updateTrackerQuery);

                    TimeUnit.MINUTES.sleep(diff);

                    // Update MWSReportsCallTracker.
                    updateTrackerQuery = "UPDATE `DataEngine`.`Tracker_APICalls` SET CurrentHealth = 'ok.', " +
                            "CurrentHourEnds = " + ((System.currentTimeMillis() / 1000) + 3600) +
                            " WHERE APICallName = '" + report + "'";
                    updateTracker(connection, updateTrackerQuery);
                }
            } else {
                logger.info("Going for Sleep for 60 Minutes.");
                TimeUnit.MINUTES.sleep(60);
            }
        }
    }


    private static void runGetCompetitivePricingForASIN() throws SQLException {
        Map<String, String> configuration = ReadConfigFile();
        Connection connection = getDataEngineDBConnection(configuration);
        if (connection == null)
            sqlConnectionError();

        String updateTrackerQuery;
        String errorMessage = null;
        String report = "US_MWS_GetCompetitivePricingForASIN";
        long currentTimestamp = System.currentTimeMillis() / 1000;

        try {
            List<String> asinList = getASIN(connection);
            if (asinList != null) {

                String selectQuery = "SELECT PublicKey, PrivateKey, AssociateTag, Region " +
                        "FROM `DataEngine`.`Tracker_APICalls` WHERE APICallName = '" + report + "'";
                Map<String, String> DBConfiguration = readTracker(connection, selectQuery);

                if (DBConfiguration != null) {
                    DBConfiguration.putAll(configuration);
                    int requestCount = 0;

                    // Update MWSReportsCallTracker.
                    updateTrackerQuery = "UPDATE `DataEngine`.`Tracker_APICalls` SET CurrentHourStarts = " + currentTimestamp +
                            ", CurrentHourEnds = " + (currentTimestamp + 3600) + ", CurrentHealth = 'ok.', " +
                            "CurrentSignal = 'green', Status = '_RUNNING_', CurrentHourRequests = 0," +
                            " EmergencyExit = 'no' WHERE APICallName = '" + report + "'";
                    updateTracker(connection, updateTrackerQuery);

                    String selectTrackerQuery = "SELECT `EmergencyExit` FROM `DataEngine`.`Tracker_APICalls`" +
                            " WHERE APICallName = '" + report + "'";

                    for (String asin : asinList) {
                        Map<String, String> checkRun = readTracker(connection, selectTrackerQuery);
                        assert checkRun != null;
                        if ((checkRun.get("EmergencyExit")).equalsIgnoreCase("yes"))
                            break;

                        checkThrottleSleep(connection, requestCount, report);
                        if (requestCount >= 36000)
                            requestCount = 0;

                        List<String> asinL = new ArrayList<>();
                        asinL.add("0674030575");
                        asinL.add("0736072535");
                        asinL.add("1337586161");

                        boolean statusNewReport = getCompetitivePricingForASIN(DBConfiguration, connection, logger, asinL);
                        TimeUnit.SECONDS.sleep(10);
                        requestCount += 2;

                        if (statusNewReport){
                            String updateSKUListQuery = "UPDATE `US_Inventory_Sku_List` SET haveCalled = true" +
                                    " WHERE SKU = '" + asin + "'";
                            insertData(connection, updateSKUListQuery);
                        }
                        if (!statusNewReport)
                            logger.severe("Failed GetCompetitivePricingForASIN for new SKU: " + asin);
                    }

                    // Update MWSReportsCallTracker.
                    updateTrackerQuery = "UPDATE `DataEngine`.`Tracker_APICalls` SET CurrentHourEnds = " +
                            (System.currentTimeMillis() / 1000) + ", CurrentHealth = 'ok.', CurrentSignal = 'yellow', " +
                            "Status = 'IN_SUCCESS' WHERE APICallName = '" + report + "'";
                    updateTracker(connection, updateTrackerQuery);

                } else
                    errorMessage = "Failed to read configuration in Database.";
            } else
                logger.info("NO SKU Found in the table.");

            System.out.println("IN_SUCCESS");
            logger.info("IN_SUCCESS");
        }
        catch (Exception e) {
            errorMessage = "Failed. Error Message: " + e;
        }

        errorUpdate(connection, errorMessage, report);
        File f = new File(filePath + "/report_files/Report_" + report + ".xml");
        if (!f.delete())
            logger.severe("Failed to Delete the report xml file.");

        assert connection != null;
        connection.close();
    }

    private static void errorUpdate(Connection connection, String errorMessage, String report) {
        String updateTrackerQuery;
        if (errorMessage != null){
            // Update MWSReportsCallTracker.
            updateTrackerQuery = "UPDATE `DataEngine`.`Tracker_APICalls` SET CurrentSignal = 'red', " +
                    "EndTime = " + (System.currentTimeMillis() / 1000) + ", CmdReturn = 'IN_FAILED', " +
                    "CurrentHealth = '" + errorMessage + "' WHERE APICallName = '" + report + "'";
            updateTracker(connection, updateTrackerQuery);

            System.out.println("IN_FAILED");
            logger.severe(errorMessage);
            logger.severe("IN_FAILED");
        }
    }

    private static void invalidArgumentError(String message){
        System.out.println(message);
        System.out.println("    Try these:");
        System.out.println("        GetCompetitivePricingForASIN");
        System.out.println("        SubscriptionDataFromSQS");
        System.out.println("        createMWSSubscription");
        System.out.println("        ManageFBAInventoryReport");
        System.out.println("        queueXmlTOSql");

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


    public static void main(String[] args) throws SQLException {
        String dt = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String filePath = new File("").getAbsolutePath();
        String logfile = filePath + "/logFiles/" + dt + "_" + "ApiCalls.log";
        logger = LoggerMain.getLogger(logfile, false);

        if(args.length == 0)
            invalidArgumentError("Invalid Number of Arguments. Need one argument.");

        if (args[0].equalsIgnoreCase("GetCompetitivePricingForASIN"))
            runGetCompetitivePricingForASIN();

        else
            invalidArgumentError("Invalid Argument value.");
    }

}

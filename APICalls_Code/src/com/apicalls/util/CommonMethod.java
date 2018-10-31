package com.apicalls.util;

import java.io.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

import static com.apicalls.util.DBOperations.readTracker;
import static com.apicalls.util.DBOperations.updateTracker;


public class CommonMethod {
	private static Map<String, String> Configuration = new LinkedHashMap<>();
    private static Logger logger = null;

    public static Map<String, String> ReadConfigFile() {
		try {
            String dt = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String filePath = new File("").getAbsolutePath();
            String logfile = filePath + "/logFiles/" + dt + "_" + "mws_config.log";
            logger = LoggerMain.getLogger(logfile, false);

			String pref_file = (new File("").getAbsolutePath()) + "/config/Config.cfg";
			File f = new File(pref_file);

			if (!f.exists()) {
				logger.info("Preference cfg File doesn't exists. Please check the path");
				return Configuration;
			}

			String FileLine;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pref_file)));

			while ((FileLine = br.readLine()) != null) {
				if (!FileLine.isEmpty() && FileLine.indexOf("=") > 0)
					Configuration.put(FileLine.substring(0, FileLine.indexOf("=") - 1).trim(), FileLine.substring(FileLine.indexOf("=") + 1).trim());
			}
			br.close();
			return Configuration;
		}
		catch (Exception e) {
			logger.info("Error in ReadConfigFile method. Error message: " + e);
			return Configuration;
		}
	}

    public static Map<String, String> getConfig(Connection connection, Map<String, String> fileConfig, String report) {
        Map<String, String> DBConfiguration = readTracker(connection,
                "SELECT MWSAccessKey, MWSSecretKey, SellerId, MarketplaceId, MWSServiceURL " +
                        "FROM `DataEngine`.`Tracker_MWSReports` WHERE ReportType = '" + report + "'");
        assert DBConfiguration != null;
        DBConfiguration.putAll(fileConfig);

        return DBConfiguration;
    }

    public static boolean checkEmergencyExit(Connection connection){
        Map<String, String> checkGetCompetitivePricingForASIN = readTracker(connection,
                "SELECT `EmergencyExit` FROM `DataEngine`.`Tracker_MWSReports`" +
                        " WHERE ReportType = 'GetCompetitivePricingForASIN'");

        Map<String, String> checkGetLowestOfferListingsForASIN = readTracker(connection,
                "SELECT `EmergencyExit` FROM `DataEngine`.`Tracker_MWSReports`" +
                        " WHERE ReportType = 'GetLowestOfferListingsForASIN'");

        assert checkGetLowestOfferListingsForASIN != null;
        assert checkGetCompetitivePricingForASIN != null;

        if ((checkGetCompetitivePricingForASIN.get("EmergencyExit")).equalsIgnoreCase("yes") ||
                (checkGetLowestOfferListingsForASIN.get("EmergencyExit")).equalsIgnoreCase("yes")) {

            updateTracker(connection, "UPDATE `DataEngine`.`Tracker_MWSReports` SET EmergencyExit = 'yes', CurrentSignal = 'red' " +
                    "WHERE ReportType IN ('GetCompetitivePricingForASIN', 'GetLowestOfferListingsForASIN')");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
    }
}

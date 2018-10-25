package com.apicalls.util;

import java.io.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

import static com.apicalls.mws.GetCompetitivePricingForASIN.xmlToSql.setGetCompetitivePricingForASIN;
import static com.apicalls.mws.GetLowestOfferListingsForASIN.xmlToSql.setGetLowestOfferListingsForASIN;
import static com.apicalls.util.DBOperations.getDataEngineDBConnection;


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

    public static void main(String[] args) {
        Map<String, String> configuration = ReadConfigFile();
//
//        String report = "GetLowestOfferListingsForASIN";
//
//        String filePath = new File("").getAbsolutePath();
//        String dt = new SimpleDateFormat("yyyyMMdd").format(new Date());
//        String logfile = filePath + "/logFiles/" + dt + "_" + "GetLowestOfferListingsForASIN.log";
//        Logger logger = LoggerMain.getLogger(logfile, false);

//        try {
//            JAXBContext context = JAXBContext.newInstance(GetCompetitivePricingForASINResponse.class);
//            Unmarshaller um = context.createUnmarshaller();
//            GetCompetitivePricingForASINResponse res = (GetCompetitivePricingForASINResponse) um.unmarshal(new FileReader("report_files/GetCompetitivePricingForASIN_0.xml"));
//            for (GetCompetitivePricingForASINResult getCompetitivePricingForASINResult: res.getGetCompetitivePricingForASINResult()) {
//                System.out.println(getCompetitivePricingForASINResult.getProduct().getIdentifiers().getMarketplaceASIN().getASIN());
//            }
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }

//        SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
//        String createDate = sdf.format(new Date());
//        System.out.println(createDate);


        String dt = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String filePath = new File("").getAbsolutePath();
        String logfile = filePath + "/logFiles/" + dt + "_GetCompetitivePricingForASIN_SQL.log";
        Logger logger = LoggerMain.getLogger(logfile, false);

        Connection connection = getDataEngineDBConnection(configuration);
        setGetCompetitivePricingForASIN(logger,"report_files/GetCompetitivePricingForASIN_0.xml", connection, 101);
        setGetLowestOfferListingsForASIN(logger,"report_files/GetLowestOfferListingsForASIN_1.xml", connection, 101);
    }
}

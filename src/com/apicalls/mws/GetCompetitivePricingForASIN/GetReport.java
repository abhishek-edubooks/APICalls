package com.apicalls.mws.GetCompetitivePricingForASIN;

import com.amazonservices.mws.products.MarketplaceWebServiceProducts;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsClient;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsConfig;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsException;
import com.amazonservices.mws.products.model.GetCompetitivePricingForASINResponse;
import com.amazonservices.mws.products.model.ResponseHeaderMetadata;
import com.amazonservices.mws.products.model.GetCompetitivePricingForASINRequest;
import com.amazonservices.mws.products.model.ASINListType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.apicalls.util.DBOperations.readTracker;
import static com.apicalls.util.DBOperations.updateTracker;


public class GetReport {
    private static String filePath = new File("").getAbsolutePath();

    private static boolean invokeGetCompetitivePricingForASIN(MarketplaceWebServiceProducts client,
                                                              GetCompetitivePricingForASINRequest request,
                                                              Connection connection,
                                                              Logger logger) {
        String updateTrackerQuery, errorMessage;
        String report = "GetCompetitivePricingForASIN";
        try {
            // Call the service.
            GetCompetitivePricingForASINResponse response = client.getCompetitivePricingForASIN(request);

            String ignoreString = "xmlns=\"http://mws.amazonservices.com/schema/Products/2011-10-01\"";
            String responseXml = (response.toXML()).replace(ignoreString, "");

            String selectTrackerQuery = "SELECT `CallBatchNumber` FROM `DataEngine`.`Tracker_MWSReports` " +
                                        "WHERE ReportType = '" + report + "'";
            Map<String, String> checkRun = readTracker(connection, selectTrackerQuery);

            assert checkRun != null;
            int CallBatchNumber = Integer.parseInt(checkRun.get("CallBatchNumber"));

            String FileName = filePath + "/report_files/GetCompetitivePricingForASIN_" + CallBatchNumber + ".xml";
            FileWriter fw = new FileWriter(FileName);
            fw.write(responseXml);
            fw.close();

            // Update MWSReportsCallTracker.
            updateTrackerQuery = "UPDATE `DataEngine`.`Tracker_MWSReports` SET CallBatchNumber = CallBatchNumber + 1 " +
                                 "WHERE ReportType = '" + report + "'";
            updateTracker(connection, updateTrackerQuery);
            return true;

        } catch (MarketplaceWebServiceProductsException ex) {
            logger.severe("Service Exception:");

            ResponseHeaderMetadata rhmd = ex.getResponseHeaderMetadata();
            if(rhmd != null) {
                logger.severe("RequestId: "+rhmd.getRequestId());
                logger.severe("Timestamp: "+rhmd.getTimestamp());
            }
            logger.severe("Message: "+ex.getMessage());
            logger.severe("StatusCode: "+ex.getStatusCode());
            logger.severe("ErrorCode: "+ex.getErrorCode());
            logger.severe("ErrorType: "+ex.getErrorType());

            errorMessage = "Failed. " + ex.getErrorCode() + " : " + ex.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            errorMessage = "Failed. " + e.getMessage();
        }

        // Update MWSReportsCallTracker.
        updateTrackerQuery = "UPDATE `DataEngine`.`Tracker_MWSReports` SET CurrentHealth = '" + errorMessage +
                             "' WHERE ReportType = '" + report + "'";
        updateTracker(connection, updateTrackerQuery);
        logger.severe(errorMessage);

        return false;
    }

    public static boolean getCompetitivePricingForASIN(Map<String, String> configuration,
                                                       Connection connection,
                                                       Logger logger,
                                                       List<String> asin) {

        // Get a client connection.
        final String accessKey = configuration.get("MWSAccessKey");
        final String secretKey = configuration.get("MWSSecretKey");
        final String appName = configuration.get("appName");
        final String appVersion = configuration.get("appVersion");
        final String serviceURL = configuration.get("MWSServiceURL");

        MarketplaceWebServiceProductsConfig config = new MarketplaceWebServiceProductsConfig();
        config.setServiceURL(serviceURL);

        MarketplaceWebServiceProductsClient client = new MarketplaceWebServiceProductsClient(accessKey, secretKey, appName, appVersion, config);

        // Create a request.
        GetCompetitivePricingForASINRequest request = new GetCompetitivePricingForASINRequest();
        request.setSellerId(configuration.get("SellerId"));
        request.setMarketplaceId(configuration.get("MarketplaceId"));

        ASINListType asinList = new ASINListType();
        asinList.setASIN(asin);
        request.setASINList(asinList);

        // Make the call.
        return GetReport.invokeGetCompetitivePricingForASIN(client, request, connection, logger);
    }

}

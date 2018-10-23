package com.apicalls.mws.GetLowestOfferListingsForASIN;

import com.amazonservices.mws.products.*;
import com.amazonservices.mws.products.model.*;

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

    private static boolean invokeGetLowestOfferListingsForASIN(MarketplaceWebServiceProducts client,
                                                               GetLowestOfferListingsForASINRequest request,
                                                               Connection connection,
                                                               Logger logger) {
        String updateTrackerQuery, errorMessage;
        String report = "GetLowestOfferListingsForASIN";
        try {
            // Call the service.
            GetLowestOfferListingsForASINResponse response = client.getLowestOfferListingsForASIN(request);

            String selectTrackerQuery = "SELECT `CallBatchNumber` FROM `DataEngine`.`Tracker_MWSReports` " +
                                        "WHERE ReportType = '" + report + "'";
            Map<String, String> checkRun = readTracker(connection, selectTrackerQuery);

            assert checkRun != null;
            int CallBatchNumber = Integer.parseInt(checkRun.get("CallBatchNumber"));

            String FileName = filePath + "/report_files/GetLowestOfferListingsForASIN_" + CallBatchNumber + ".xml";
            FileWriter fw = new FileWriter(FileName);
            fw.write(response.toXML());
            fw.close();

            GetLowestOfferListingsForASINResponse response1 = new GetLowestOfferListingsForASINResponse();

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

    public static boolean getLowestOfferListingsForASIN(Map<String, String> configuration,
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
        GetLowestOfferListingsForASINRequest request = new GetLowestOfferListingsForASINRequest();
        request.setSellerId(configuration.get("SellerId"));
        request.setMarketplaceId(configuration.get("MarketplaceId"));
        request.setExcludeMe(true);

        ASINListType asinList = new ASINListType();
        asinList.setASIN(asin);
        request.setASINList(asinList);


        // Make the call.
        return invokeGetLowestOfferListingsForASIN(client, request, connection, logger);
    }

}
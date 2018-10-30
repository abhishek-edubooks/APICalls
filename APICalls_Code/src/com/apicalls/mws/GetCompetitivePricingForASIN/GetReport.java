package com.apicalls.mws.GetCompetitivePricingForASIN;

import com.amazonservices.mws.products.MarketplaceWebServiceProducts;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsClient;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsConfig;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsException;
import com.amazonservices.mws.products.model.ResponseHeaderMetadata;
import com.amazonservices.mws.products.model.ASINListType;
import com.amazonservices.mws.products.model.GetCompetitivePricingForASINRequest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class GetReport {

    private static String invokeGetCompetitivePricingForASIN( MarketplaceWebServiceProducts client,
                                                              GetCompetitivePricingForASINRequest request,
                                                              Logger logger,
                                                              String fileName) {
        String errorMessage;
        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write((client.getCompetitivePricingForASIN(request).toXML())
                    .replace("xmlns=\"http://mws.amazonservices.com/schema/Products/2011-10-01\"", ""));
            fw.close();
            return null;

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
        return errorMessage;
    }

    public static String getCompetitivePricingForASIN( Map<String, String> configuration,
                                                       Logger logger,
                                                       String fileName,
                                                       List<String> asin) {

        MarketplaceWebServiceProductsConfig config = new MarketplaceWebServiceProductsConfig();
        config.setServiceURL(configuration.get("MWSServiceURL"));

        MarketplaceWebServiceProductsClient client = new MarketplaceWebServiceProductsClient(
                configuration.get("MWSAccessKey"),
                configuration.get("MWSSecretKey"),
                configuration.get("appName"),
                configuration.get("appVersion"), config);

        // Create a request.
        GetCompetitivePricingForASINRequest request = new GetCompetitivePricingForASINRequest();
        request.setSellerId(configuration.get("SellerId"));
        request.setMarketplaceId(configuration.get("MarketplaceId"));

        ASINListType asinList = new ASINListType();
        asinList.setASIN(asin);
        request.setASINList(asinList);

        // Make the call.
        return GetReport.invokeGetCompetitivePricingForASIN(client, request, logger, fileName);
    }

}

package com.apicalls.mws.GetLowestOfferListingsForASIN;

import com.apicalls.mws.model.GetCompetitivePricingForASINResponse;
import com.apicalls.util.LoggerMain;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import static com.apicalls.util.DBOperations.insertData;

public class xmlToSql {
    private static String dt = new SimpleDateFormat("yyyyMMdd").format(new Date());
    private static String filePath = new File("").getAbsolutePath();
    private static String logfile = filePath + "/logFiles/" + dt + "_SQL.log";
    private static Logger logger = LoggerMain.getLogger(logfile, false);

    public static boolean setGetCompetitivePricingForASIN(String fileName, Connection connection){
        try {
            String colNames = "batchId, CreateDate, ASIN, New_condition, New_subcondition, New_belongsToRequester, " +
                    "ListingPrice_CurrencyCode_New, ListingPrice_Amount_New, Shipping_CurrencyCode_New, " +
                    "Shipping_Amount_New, LandedPrice_CurrencyCode_New, LandedPrice_Amount_New, Used_condition, " +
                    "Used_subcondition, Used_belongsToRequester, ListingPrice_CurrencyCode_Used, " +
                    "ListingPrice_Amount_Used, Shipping_CurrencyCode_Used, Shipping_Amount_Used, " +
                    "LandedPrice_CurrencyCode_Used, LandedPrice_Amount_Used, New_OfferListingCount, " +
                    "Used_OfferListingCount, Any_OfferListingCount, Collectible_OfferListingCount, " +
                    "SalesRank_ProductCategoryId_0, SalesRank_Rank_0, SalesRank_ProductCategoryId_1, " +
                    "SalesRank_Rank_1, SalesRank_ProductCategoryId_2, SalesRank_Rank_2, " +
                    "SalesRank_ProductCategoryId_3, SalesRank_Rank_3, SalesRank_ProductCategoryId_4, " +
                    "SalesRank_Rank_4";

            JAXBContext context = JAXBContext.newInstance(GetCompetitivePricingForASINResponse.class);
            Unmarshaller um = context.createUnmarshaller();
            GetCompetitivePricingForASINResponse result = (GetCompetitivePricingForASINResponse) um.unmarshal(new FileReader(fileName));

            String mainTableQuery = "INSERT INTO `DataEngine`.`US_MWS_Subscriptions_API` (" + colNames + ") VALUES ()";
            if (insertData(connection, mainTableQuery))
                return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}

package com.apicalls.mws.GetCompetitivePricingForASIN;

import com.apicalls.mws.model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import static com.apicalls.util.DBOperations.updateTracker;


public class xmlToSql {

    public static void setGetCompetitivePricingForASIN(Logger logger, String fileName, Connection connection, int batchId){
        try {
            String createDate = new SimpleDateFormat("YYYYMMdd").format(new Date());

            String insertQuery = "INSERT INTO `DataEngine`.`US_MWS_GetCompetitivePricingForASIN` ( " +
                    "batchId, CreateDate, ASIN, New_condition, New_subcondition, New_belongsToRequester, " +
                    "ListingPrice_CurrencyCode_New, ListingPrice_Amount_New, Shipping_CurrencyCode_New, " +
                    "Shipping_Amount_New, LandedPrice_CurrencyCode_New, LandedPrice_Amount_New, Used_condition, " +
                    "Used_subcondition, Used_belongsToRequester, ListingPrice_CurrencyCode_Used, " +
                    "ListingPrice_Amount_Used, Shipping_CurrencyCode_Used, Shipping_Amount_Used, " +
                    "LandedPrice_CurrencyCode_Used, LandedPrice_Amount_Used, New_OfferListingCount, " +
                    "Used_OfferListingCount, Any_OfferListingCount, Collectible_OfferListingCount, " +
                    "SalesRank_ProductCategoryId_0, SalesRank_Rank_0, SalesRank_ProductCategoryId_1, " +
                    "SalesRank_Rank_1, SalesRank_ProductCategoryId_2, SalesRank_Rank_2, " +
                    "SalesRank_ProductCategoryId_3, SalesRank_Rank_3, SalesRank_ProductCategoryId_4, " +
                    "SalesRank_Rank_4) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            JAXBContext context = JAXBContext.newInstance(GetCompetitivePricingForASINResponse.class);
            Unmarshaller um = context.createUnmarshaller();
            GetCompetitivePricingForASINResponse result = (GetCompetitivePricingForASINResponse) um.unmarshal(new FileReader(fileName));

            for (GetCompetitivePricingForASINResult getCompetitivePricingForASINResult: result.getGetCompetitivePricingForASINResult()) {
                String asin;
                String newCondition = null;
                String newSubCondition = null;
                String usedCondition = null;
                String usedSubCondition = null;
                String newListingPriceCurrencyCode = null;
                String newShippingCurrencyCode = null;
                String newLandedPriceCurrencyCode = null;
                String usedListingPriceCurrencyCode = null;
                String usedShippingCurrencyCode = null;
                String usedLandedPriceCurrencyCode = null;
                String salesRankProductCategoryId_0 = null;
                String salesRankProductCategoryId_1 = null;
                String salesRankProductCategoryId_2 = null;
                String salesRankProductCategoryId_3 = null;
                String salesRankProductCategoryId_4 = null;

                BigDecimal newListingPriceAmount = null;
                BigDecimal newShippingAmount = null;
                BigDecimal newLandedPriceAmount = null;
                BigDecimal usedListingPriceAmount = null;
                BigDecimal usedShippingAmount = null;
                BigDecimal usedLandedPriceAmount = null;

                boolean newBelongsToRequester = false;
                boolean usedBelongsToRequester = false;

                int newOfferListingCount = 0;
                int usedOfferListingCount = 0;
                int anyOfferListingCount = 0;
                int collectibleOfferListingCount = 0;
                int salesRank_0 = 0;
                int salesRank_1 = 0;
                int salesRank_2 = 0;
                int salesRank_3 = 0;
                int salesRank_4 = 0;

                String responseStatus = getCompetitivePricingForASINResult.getStatus();
                if (responseStatus.equalsIgnoreCase("Success")){
                    Product product = getCompetitivePricingForASINResult.getProduct();
                    asin = product.getIdentifiers().getMarketplaceASIN().getASIN();

                    CompetitivePricingType competitivePricingType = product.getCompetitivePricing();
                    for (CompetitivePriceType competitivePriceType: competitivePricingType.getCompetitivePrices().getCompetitivePrice()) {
                        if (competitivePriceType.getCondition().equalsIgnoreCase("New")){
                            newCondition = competitivePriceType.getCondition();
                            newSubCondition = competitivePriceType.getSubcondition();
                            newBelongsToRequester = competitivePriceType.getBelongsToRequester();

                            PriceType priceType = competitivePriceType.getPrice();
                            newListingPriceCurrencyCode = priceType.getListingPrice().getCurrencyCode();
                            newListingPriceAmount = priceType.getListingPrice().getAmount();
                            newShippingCurrencyCode = priceType.getShipping().getCurrencyCode();
                            newShippingAmount = priceType.getShipping().getAmount();
                            newLandedPriceCurrencyCode = priceType.getLandedPrice().getCurrencyCode();
                            newLandedPriceAmount = priceType.getLandedPrice().getAmount();
                        }
                        else if (competitivePriceType.getCondition().equalsIgnoreCase("Used")){
                            usedCondition = competitivePriceType.getCondition();
                            usedSubCondition = competitivePriceType.getSubcondition();
                            usedBelongsToRequester = competitivePriceType.getBelongsToRequester();

                            PriceType priceType = competitivePriceType.getPrice();
                            usedListingPriceCurrencyCode = priceType.getListingPrice().getCurrencyCode();
                            usedListingPriceAmount = priceType.getListingPrice().getAmount();
                            usedShippingCurrencyCode = priceType.getShipping().getCurrencyCode();
                            usedShippingAmount = priceType.getShipping().getAmount();
                            usedLandedPriceCurrencyCode = priceType.getLandedPrice().getCurrencyCode();
                            usedLandedPriceAmount = priceType.getLandedPrice().getAmount();
                        }
                    }

                    for (OfferListingCountType offerListingCountType: competitivePricingType.getNumberOfOfferListings().getOfferListingCount()) {
                        if (offerListingCountType.getCondition().equalsIgnoreCase("New"))
                            newOfferListingCount = offerListingCountType.getValue();
                        else if (offerListingCountType.getCondition().equalsIgnoreCase("Used"))
                            usedOfferListingCount = offerListingCountType.getValue();
                        else if (offerListingCountType.getCondition().equalsIgnoreCase("Any"))
                            anyOfferListingCount = offerListingCountType.getValue();
                        else if (offerListingCountType.getCondition().equalsIgnoreCase("Collectible"))
                            collectibleOfferListingCount = offerListingCountType.getValue();
                    }

                    int i =0;
                    for (SalesRankType salesRankType: product.getSalesRankings().getSalesRank()) {
                        if (i == 0){
                            salesRankProductCategoryId_0 = salesRankType.getProductCategoryId();
                            salesRank_0 = salesRankType.getRank();
                        }
                        else if (i == 1){
                            salesRankProductCategoryId_1 = salesRankType.getProductCategoryId();
                            salesRank_1 = salesRankType.getRank();
                        }
                        else if (i == 2){
                            salesRankProductCategoryId_2 = salesRankType.getProductCategoryId();
                            salesRank_2 = salesRankType.getRank();
                        }
                        else if (i == 3){
                            salesRankProductCategoryId_3 = salesRankType.getProductCategoryId();
                            salesRank_3 = salesRankType.getRank();
                        }
                        else if (i == 4){
                            salesRankProductCategoryId_4 = salesRankType.getProductCategoryId();
                            salesRank_4 = salesRankType.getRank();
                        }
                        i ++;
                    }

                    try {
                        assert connection != null;
                        PreparedStatement stmt = connection.prepareStatement(insertQuery);

                        stmt.setInt(1, batchId);
                        stmt.setString(2, createDate);
                        stmt.setString(3, asin);
                        stmt.setString(4, newCondition);
                        stmt.setString(5, newSubCondition);
                        stmt.setBoolean(6, newBelongsToRequester);
                        stmt.setString(7, newListingPriceCurrencyCode);
                        stmt.setBigDecimal(8, newListingPriceAmount);
                        stmt.setString(9, newShippingCurrencyCode);
                        stmt.setBigDecimal(10,newShippingAmount);
                        stmt.setString(11, newLandedPriceCurrencyCode);
                        stmt.setBigDecimal(12, newLandedPriceAmount);
                        stmt.setString(13, usedCondition);
                        stmt.setString(14, usedSubCondition);
                        stmt.setBoolean(15, usedBelongsToRequester);
                        stmt.setString(16, usedListingPriceCurrencyCode);
                        stmt.setBigDecimal(17, usedListingPriceAmount);
                        stmt.setString(18, usedShippingCurrencyCode);
                        stmt.setBigDecimal(19, usedShippingAmount);
                        stmt.setString(20, usedLandedPriceCurrencyCode);
                        stmt.setBigDecimal(21, usedLandedPriceAmount);
                        stmt.setInt(22, newOfferListingCount);
                        stmt.setInt(23, usedOfferListingCount);
                        stmt.setInt(24, anyOfferListingCount);
                        stmt.setInt(25, collectibleOfferListingCount);
                        stmt.setString(26, salesRankProductCategoryId_0);
                        stmt.setInt(27, salesRank_0);
                        stmt.setString(28, salesRankProductCategoryId_1);
                        stmt.setInt(29, salesRank_1);
                        stmt.setString(30, salesRankProductCategoryId_2);
                        stmt.setInt(31, salesRank_2);
                        stmt.setString(32, salesRankProductCategoryId_3);
                        stmt.setInt(33, salesRank_3);
                        stmt.setString(34, salesRankProductCategoryId_4);
                        stmt.setInt(35, salesRank_4);

                        stmt.executeUpdate();
                        connection.commit();
                        stmt.close();

                        // Update MWSReportsCallTracker.
                        updateTracker(connection, "UPDATE `DataEngine`.`Tracker_MWSReports` SET DataPointsCollected = DataPointsCollected + 1, " +
                                "DataPointsQueue = TotalDataPoints - DataPointsCollected WHERE ReportType = 'GetCompetitivePricingForASIN'");

                        // Update AllIsbn.
                        updateTracker(connection, "UPDATE `DataEngine`.`AllIsbn` SET GetCompetitivePricingForASIN=true WHERE ASIN='" + asin + "'");

                    } catch (Exception e){
                        logger.severe("Failed to insert data for ASIN: " + asin + " | batchId: " + batchId);
                        logger.severe("Error in insertData operation. Error message: " + e);
                    }

                } else
                    logger.severe("Status: " + responseStatus + " | ASIN: " + getCompetitivePricingForASINResult.getASIN() + " | batchId: " + batchId);
            }

        } catch (Exception e){
            e.printStackTrace();
            logger.severe("Failed to insert data for Filename: " + fileName);
        }
    }

}

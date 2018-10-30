package com.apicalls.mws.GetLowestOfferListingsForASIN;

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

    public static void setGetLowestOfferListingsForASIN(Connection connection, Logger logger, String fileName, int batchId){
        try {
            String createDate = new SimpleDateFormat("YYYYMMdd").format(new Date());

            String insertQuery = "INSERT INTO `DataEngine`.`US_MWS_GetLowestOfferListingsForASIN` ( " +
                    "batchId, CreateDate, ASIN, AllOfferListingsConsidered, ItemCondition, ItemSubcondition, " +
                    "FulfillmentChannel, ShipsDomestically, ShippingTime_Max, SellerPositiveFeedbackRating, " +
                    "NumberOfOfferListingsConsidered, SellerFeedbackCount, LandedPrice_CurrencyCode, " +
                    "LandedPrice_Amount, ListingPrice_CurrencyCode, ListingPrice_Amount, Shipping_CurrencyCode, " +
                    "Shipping_Amount, MultipleOffersAtLowestPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            String insertLandedPriceQuery = "INSERT INTO `DataEngine`.`US_MWS_GetLowestOfferListingsForASIN_LandedPrice` ( " +
                    "batchId, CreateDate, ASIN, New_Merchant, Used_Merchant, New_Amazon, Used_Amazon) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            JAXBContext context = JAXBContext.newInstance(GetLowestOfferListingsForASINResponse.class);
            Unmarshaller um = context.createUnmarshaller();
            GetLowestOfferListingsForASINResponse result = (GetLowestOfferListingsForASINResponse) um.unmarshal(new FileReader(fileName));

            for (GetLowestOfferListingsForASINResult getLowestOfferListingsForASINResult: result.getGetLowestOfferListingsForASINResult()) {
                String asin;
                String itemCondition;
                String itemSubcondition;
                String fulfillmentChannel;
                String shippingTimeMax;
                String shipsDomestically;
                String sellerPositiveFeedbackRating;
                String multipleOffersAtLowestPrice;
                String listingPriceCurrencyCode;
                String shippingCurrencyCode;
                String landedPriceCurrencyCode;

                BigDecimal listingPriceAmount;
                BigDecimal shippingAmount;
                BigDecimal landedPriceAmount;
                BigDecimal newMerchant = BigDecimal.valueOf(0);
                BigDecimal usedMerchant = BigDecimal.valueOf(0);
                BigDecimal newAmazon = BigDecimal.valueOf(0);
                BigDecimal usedAmazon = BigDecimal.valueOf(0);

                boolean allOfferListingsConsidered;
                int numberOfOfferListingsConsidered;
                int sellerFeedbackCount;

                String responseStatus = getLowestOfferListingsForASINResult.getStatus();
                if (responseStatus.equalsIgnoreCase("Success")){
                    allOfferListingsConsidered = getLowestOfferListingsForASINResult.getAllOfferListingsConsidered();

                    Product product = getLowestOfferListingsForASINResult.getProduct();
                    asin = product.getIdentifiers().getMarketplaceASIN().getASIN();
                    LowestOfferListingList lowestOfferListingList = product.getLowestOfferListings();

                    for (LowestOfferListingType lowestOfferListingType: lowestOfferListingList.getLowestOfferListing()) {
                        numberOfOfferListingsConsidered = lowestOfferListingType.getNumberOfOfferListingsConsidered();
                        sellerFeedbackCount = lowestOfferListingType.getSellerFeedbackCount();
                        multipleOffersAtLowestPrice = lowestOfferListingType.getMultipleOffersAtLowestPrice();

                        QualifiersType qualifiersType = lowestOfferListingType.getQualifiers();
                        itemCondition = qualifiersType.getItemCondition();
                        itemSubcondition = qualifiersType.getItemSubcondition();
                        fulfillmentChannel = qualifiersType.getFulfillmentChannel();
                        shipsDomestically = qualifiersType.getShipsDomestically();
                        shippingTimeMax = qualifiersType.getShippingTime().getMax();
                        sellerPositiveFeedbackRating = qualifiersType.getSellerPositiveFeedbackRating();

                        PriceType priceType = lowestOfferListingType.getPrice();
                        landedPriceCurrencyCode = priceType.getLandedPrice().getCurrencyCode();
                        landedPriceAmount = priceType.getLandedPrice().getAmount();
                        listingPriceCurrencyCode = priceType.getListingPrice().getCurrencyCode();
                        listingPriceAmount = priceType.getListingPrice().getAmount();
                        shippingCurrencyCode = priceType.getShipping().getCurrencyCode();
                        shippingAmount = priceType.getShipping().getAmount();

                        if (fulfillmentChannel.equalsIgnoreCase("Merchant") && itemCondition.equalsIgnoreCase("New")) {
                            if (newMerchant.compareTo(BigDecimal.ZERO) == 0)
                                newMerchant = landedPriceAmount;
                            else if (landedPriceAmount.compareTo(newMerchant) < 0)
                                newMerchant = landedPriceAmount;
                        }
                        if (fulfillmentChannel.equalsIgnoreCase("Merchant") && itemCondition.equalsIgnoreCase("Used")) {
                            if (usedMerchant.compareTo(BigDecimal.ZERO) == 0)
                                usedMerchant = landedPriceAmount;
                            else if (landedPriceAmount.compareTo(usedMerchant) < 0)
                                usedMerchant = landedPriceAmount;
                        }
                        if (fulfillmentChannel.equalsIgnoreCase("Amazon") && itemCondition.equalsIgnoreCase("New")) {
                            if (newAmazon.compareTo(BigDecimal.ZERO) == 0)
                                newAmazon = landedPriceAmount;
                            else if (landedPriceAmount.compareTo(newAmazon) < 0)
                                newAmazon = landedPriceAmount;
                        }
                        if (fulfillmentChannel.equalsIgnoreCase("Amazon") && itemCondition.equalsIgnoreCase("Used")) {
                            if (usedAmazon.compareTo(BigDecimal.ZERO) == 0)
                                usedAmazon = landedPriceAmount;
                            else if (landedPriceAmount.compareTo(usedAmazon) < 0)
                                usedAmazon = landedPriceAmount;
                        }

                        try {
                            assert connection != null;
                            PreparedStatement stmt = connection.prepareStatement(insertQuery);

                            stmt.setInt(1, batchId);
                            stmt.setString(2, createDate);
                            stmt.setString(3, asin);
                            stmt.setBoolean(4, allOfferListingsConsidered);
                            stmt.setString(5, itemCondition);
                            stmt.setString(6, itemSubcondition);
                            stmt.setString(7, fulfillmentChannel);
                            stmt.setString(8, shipsDomestically);
                            stmt.setString(9, shippingTimeMax);
                            stmt.setString(10, sellerPositiveFeedbackRating);
                            stmt.setInt(11, numberOfOfferListingsConsidered);
                            stmt.setInt(12, sellerFeedbackCount);
                            stmt.setString(13, landedPriceCurrencyCode);
                            stmt.setBigDecimal(14, landedPriceAmount);
                            stmt.setString(15, listingPriceCurrencyCode);
                            stmt.setBigDecimal(16, listingPriceAmount);
                            stmt.setString(17, shippingCurrencyCode);
                            stmt.setBigDecimal(18, shippingAmount);
                            stmt.setString(19, multipleOffersAtLowestPrice);

                            stmt.executeUpdate();
                            connection.commit();
                            stmt.close();

                        } catch (Exception e){
                            logger.severe("Failed to insert data for ASIN: " + asin + " | batchId: " + batchId);
                            logger.severe("Error in insertData operation. Error message: " + e);
                        }
                    }

                    try {
                        assert connection != null;
                        PreparedStatement stmt = connection.prepareStatement(insertLandedPriceQuery);

                        stmt.setInt(1, batchId);
                        stmt.setString(2, createDate);
                        stmt.setString(3, asin);
                        stmt.setBigDecimal(4, newMerchant);
                        stmt.setBigDecimal(5, usedMerchant);
                        stmt.setBigDecimal(6, newAmazon);
                        stmt.setBigDecimal(7, usedAmazon);

                        stmt.executeUpdate();
                        connection.commit();
                        stmt.close();

                    } catch (Exception e){
                        logger.severe("Failed to insert data for ASIN: " + asin + " | batchId: " + batchId);
                        logger.severe("Error in insertData operation. Error message: " + e);
                    }

                    // Update MWSReportsCallTracker.
                    updateTracker(connection, "UPDATE `DataEngine`.`Tracker_MWSReports` SET DataPointsCollected=DataPointsCollected + 1, " +
                            "DataPointsQueue=TotalDataPoints - DataPointsCollected WHERE ReportType= 'GetLowestOfferListingsForASIN'");

                    // Update AllIsbn.
                    updateTracker(connection, "UPDATE `DataEngine`.`AllIsbn` SET GetLowestOfferListingsForASIN = true," +
                            " trials_GetLowestOfferListingsForASIN = 0 WHERE ASIN='" + asin + "'");

                } else {
                    String asinMain = getLowestOfferListingsForASINResult.getASIN();
                    logger.severe("Status: " + responseStatus + " | ASIN: " + asinMain + " | batchId: " + batchId);

                    // Update AllIsbn.
                    updateTracker(connection, "UPDATE `DataEngine`.`AllIsbn` SET GetLowestOfferListingsForASIN = false," +
                            " trials_GetLowestOfferListingsForASIN = trials_GetLowestOfferListingsForASIN + 1 WHERE ASIN='" + asinMain + "'");
                }
            }

        } catch (Exception e){
            e.printStackTrace();
            logger.severe("Failed to insert data for Filename: " + fileName);
        }
    }

}

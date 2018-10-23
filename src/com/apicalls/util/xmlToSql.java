package com.apicalls.util;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static com.apicalls.util.DBOperations.insertData;

public class xmlToSql {
    private static String dt = new SimpleDateFormat("yyyyMMdd").format(new Date());
    private static String filePath = new File("").getAbsolutePath();
    private static String logfile = filePath + "/logFiles/" + dt + "_" + "aws_sqs.log";
    private static Logger logger = LoggerMain.getLogger(logfile, false);

    public static boolean setSubscriptionData(String fileName, Connection connection){
        boolean status = false;
        try {
            String colNames = "UniqueId, PublishTime, ASIN, ItemCondition, TimeOfOfferChange, " +
                    "NumberOfOffers_OfferCount_new_Amazon, NumberOfOffers_OfferCount_new_Merchant, " +
                    "NumberOfOffers_OfferCount_used_Amazon, NumberOfOffers_OfferCount_used_Merchant, " +
                    "LowestPrice_new_Amazon_LandedPrice_Amount, LowestPrice_new_Amazon_LandedPrice_CurrencyCode, " +
                    "LowestPrice_new_Amazon_ListingPrice_Amount, LowestPrice_new_Amazon_ListingPrice_CurrencyCode, " +
                    "LowestPrice_new_Amazon_Shipping_Amount, LowestPrice_new_Amazon_Shipping_CurrencyCode, " +
                    "LowestPrice_new_Merchant_LandedPrice_Amount, LowestPrice_new_Merchant_LandedPrice_CurrencyCode, " +
                    "LowestPrice_new_Merchant_ListingPrice_Amount, LowestPrice_new_Merchant_ListingPrice_CurrencyCode, " +
                    "LowestPrice_new_Merchant_Shipping_Amount, LowestPrice_new_Merchant_Shipping_CurrencyCode, " +
                    "LowestPrice_used_Merchant_LandedPrice_Amount, LowestPrice_used_Merchant_LandedPrice_CurrencyCode, " +
                    "LowestPrice_used_Merchant_ListingPrice_Amount, LowestPrice_used_Merchant_ListingPrice_CurrencyCode, " +
                    "LowestPrice_used_Merchant_Shipping_Amount, LowestPrice_used_Merchant_Shipping_CurrencyCode, " +
                    "LowestPrice_used_Amazon_LandedPrice_Amount, LowestPrice_used_Amazon_LandedPrice_CurrencyCode, " +
                    "LowestPrice_used_Amazon_ListingPrice_Amount, LowestPrice_used_Amazon_ListingPrice_CurrencyCode, " +
                    "LowestPrice_used_Amazon_Shipping_Amount, LowestPrice_used_Amazon_Shipping_CurrencyCode, " +
                    "BuyBoxPrice_new_LandedPrice_Amount, BuyBoxPrice_new_LandedPrice_CurrencyCode, " +
                    "BuyBoxPrice_new_ListingPrice_Amount, BuyBoxPrice_new_ListingPrice_CurrencyCode, " +
                    "BuyBoxPrice_new_Shipping_Amount, BuyBoxPrice_new_Shipping_CurrencyCode, " +
                    "BuyBoxPrice_Used_LandedPrice_Amount, BuyBoxPrice_Used_LandedPrice_CurrencyCode, " +
                    "BuyBoxPrice_Used_ListingPrice_Amount, BuyBoxPrice_Used_ListingPrice_CurrencyCode, " +
                    "BuyBoxPrice_Used_Shipping_Amount, BuyBoxPrice_Used_Shipping_CurrencyCode, " +
                    "SuggestedLowerPricePlusShipping_Amount, SuggestedLowerPricePlusShipping_CurrencyCode, " +
                    "ListPrice_Amount, ListPrice_CurrencyCode, BuyBoxEligibleOffers_OfferCount_new_Amazon, " +
                    "BuyBoxEligibleOffers_OfferCount_new_Merchant, BuyBoxEligibleOffers_OfferCount_used_Amazon, " +
                    "BuyBoxEligibleOffers_OfferCount_used_Merchant";

            String offerColName = "UniqueId, SellerId, SubCondition, SellerPositiveFeedbackRating, FeedbackCount, " +
                    "ShippingTime_availabilityType, ShippingTime_maximumHours, ShippingTime_minimumHours, " +
                    "ListingPrice_Amount, ListingPrice_CurrencyCode, Shipping_Amount, Shipping_CurrencyCode, " +
                    "ShipsFrom_Country, ShipsFrom_State, IsFulfilledByAmazon, IsBuyBoxWinner, IsFeaturedMerchant, ShipsDomestically";

            String salesRankingColName = "UniqueId, SalesRank_ProductCategoryId, SalesRank_Rank";

            File file = new File(fileName);
            SAXReader reader = new SAXReader();
            Document xmlDoc = reader.read(file);

            Node Notification = xmlDoc.selectSingleNode("Notification");
            Node NotificationMetaData = Notification.selectSingleNode("NotificationMetaData");
            Node AnyOfferChangedNotification = Notification.selectSingleNode("NotificationPayload/AnyOfferChangedNotification");

            Node OfferChangeTrigger = AnyOfferChangedNotification.selectSingleNode("OfferChangeTrigger");
            Node Summary = AnyOfferChangedNotification.selectSingleNode("Summary");

            String UniqueId = "'" + NotificationMetaData.selectSingleNode("UniqueId").getText() + "', ";
            StringBuilder colData = new StringBuilder( UniqueId +
                    "'" + NotificationMetaData.selectSingleNode("PublishTime").getText() + "', " +
                    "'" + OfferChangeTrigger.selectSingleNode("ASIN").getText() + "', " +
                    "'" + OfferChangeTrigger.selectSingleNode("ItemCondition").getText() + "', " +
                    "'" + OfferChangeTrigger.selectSingleNode("TimeOfOfferChange").getText() + "', ");

            String NumberOfOffers_OfferCount_new_Amazon = null, NumberOfOffers_OfferCount_new_Merchant = null;
            String NumberOfOffers_OfferCount_used_Amazon = null, NumberOfOffers_OfferCount_used_Merchant = null;

            Node NumberOfOffers = Summary.selectSingleNode("NumberOfOffers");
            List<Node> OfferCounts = NumberOfOffers.selectNodes("OfferCount");
            for (Node OfferCount : OfferCounts) {
                if (OfferCount.valueOf("@condition").equalsIgnoreCase("new") &&
                        OfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Amazon"))
                    NumberOfOffers_OfferCount_new_Amazon = OfferCount.getText();

                if (OfferCount.valueOf("@condition").equalsIgnoreCase("new") &&
                        OfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Merchant"))
                    NumberOfOffers_OfferCount_new_Merchant = OfferCount.getText();

                if (OfferCount.valueOf("@condition").equalsIgnoreCase("used") &&
                        OfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Amazon"))
                    NumberOfOffers_OfferCount_used_Amazon = OfferCount.getText();

                if (OfferCount.valueOf("@condition").equalsIgnoreCase("used") &&
                        OfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Merchant"))
                    NumberOfOffers_OfferCount_used_Merchant = OfferCount.getText() ;
            }
            colData.append(NumberOfOffers_OfferCount_new_Amazon).append(", ").append(NumberOfOffers_OfferCount_new_Merchant).append(", ");
            colData.append(NumberOfOffers_OfferCount_used_Amazon).append(", ").append(NumberOfOffers_OfferCount_used_Merchant).append(", ");

            String LowestPrice_new_Amazon_LandedPrice_Amount = null, LowestPrice_new_Amazon_LandedPrice_CurrencyCode = null;
            String LowestPrice_new_Amazon_ListingPrice_Amount = null, LowestPrice_new_Amazon_ListingPrice_CurrencyCode = null;
            String LowestPrice_new_Amazon_Shipping_Amount = null, LowestPrice_new_Amazon_Shipping_CurrencyCode = null;
            String LowestPrice_new_Merchant_LandedPrice_Amount = null, LowestPrice_new_Merchant_LandedPrice_CurrencyCode = null;
            String LowestPrice_new_Merchant_ListingPrice_Amount = null, LowestPrice_new_Merchant_ListingPrice_CurrencyCode = null;
            String LowestPrice_new_Merchant_Shipping_Amount = null, LowestPrice_new_Merchant_Shipping_CurrencyCode = null;
            String LowestPrice_used_Merchant_LandedPrice_Amount = null, LowestPrice_used_Merchant_LandedPrice_CurrencyCode = null;
            String LowestPrice_used_Merchant_ListingPrice_Amount = null, LowestPrice_used_Merchant_ListingPrice_CurrencyCode = null;
            String LowestPrice_used_Merchant_Shipping_Amount = null, LowestPrice_used_Merchant_Shipping_CurrencyCode = null;
            String LowestPrice_used_Amazon_LandedPrice_Amount = null, LowestPrice_used_Amazon_LandedPrice_CurrencyCode = null;
            String LowestPrice_used_Amazon_ListingPrice_Amount = null, LowestPrice_used_Amazon_ListingPrice_CurrencyCode = null;
            String LowestPrice_used_Amazon_Shipping_Amount = null, LowestPrice_used_Amazon_Shipping_CurrencyCode = null;

            Node LowestPrices = Summary.selectSingleNode("LowestPrices");
            List <Node> LowestPrice = LowestPrices.selectNodes("LowestPrice");
            for (Node lowestPrice : LowestPrice) {
                if (lowestPrice.valueOf("@condition").equalsIgnoreCase("new") &&
                        lowestPrice.valueOf("@fulfillmentChannel").equalsIgnoreCase("Amazon")) {
                    LowestPrice_new_Amazon_LandedPrice_Amount = lowestPrice.selectSingleNode("LandedPrice/Amount").getText();
                    LowestPrice_new_Amazon_LandedPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("LandedPrice/CurrencyCode").getText() + "'";
                    LowestPrice_new_Amazon_ListingPrice_Amount = lowestPrice.selectSingleNode("ListingPrice/Amount").getText();
                    LowestPrice_new_Amazon_ListingPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("ListingPrice/CurrencyCode").getText() + "'";
                    LowestPrice_new_Amazon_Shipping_Amount = lowestPrice.selectSingleNode("Shipping/Amount").getText();
                    LowestPrice_new_Amazon_Shipping_CurrencyCode = "'" + lowestPrice.selectSingleNode("Shipping/CurrencyCode").getText() + "'";
                }

                if (lowestPrice.valueOf("@condition").equalsIgnoreCase("new") &&
                        lowestPrice.valueOf("@fulfillmentChannel").equalsIgnoreCase("Merchant")) {
                    LowestPrice_new_Merchant_LandedPrice_Amount = lowestPrice.selectSingleNode("LandedPrice/Amount").getText();
                    LowestPrice_new_Merchant_LandedPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("LandedPrice/CurrencyCode").getText() + "'";
                    LowestPrice_new_Merchant_ListingPrice_Amount = lowestPrice.selectSingleNode("ListingPrice/Amount").getText();
                    LowestPrice_new_Merchant_ListingPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("ListingPrice/CurrencyCode").getText() + "'";
                    LowestPrice_new_Merchant_Shipping_Amount = lowestPrice.selectSingleNode("Shipping/Amount").getText();
                    LowestPrice_new_Merchant_Shipping_CurrencyCode = "'" + lowestPrice.selectSingleNode("Shipping/CurrencyCode").getText() + "'";
                }

                if (lowestPrice.valueOf("@condition").equalsIgnoreCase("used") &&
                        lowestPrice.valueOf("@fulfillmentChannel").equalsIgnoreCase("Amazon")) {
                    LowestPrice_used_Amazon_LandedPrice_Amount = lowestPrice.selectSingleNode("LandedPrice/Amount").getText();
                    LowestPrice_used_Amazon_LandedPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("LandedPrice/CurrencyCode").getText() + "'";
                    LowestPrice_used_Amazon_ListingPrice_Amount = lowestPrice.selectSingleNode("ListingPrice/Amount").getText();
                    LowestPrice_used_Amazon_ListingPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("ListingPrice/CurrencyCode").getText() + "'";
                    LowestPrice_used_Amazon_Shipping_Amount = lowestPrice.selectSingleNode("Shipping/Amount").getText();
                    LowestPrice_used_Amazon_Shipping_CurrencyCode = "'" + lowestPrice.selectSingleNode("Shipping/CurrencyCode").getText() + "'";
                }

                if (lowestPrice.valueOf("@condition").equalsIgnoreCase("used") &&
                        lowestPrice.valueOf("@fulfillmentChannel").equalsIgnoreCase("Merchant")) {
                    LowestPrice_used_Merchant_LandedPrice_Amount = lowestPrice.selectSingleNode("LandedPrice/Amount").getText();
                    LowestPrice_used_Merchant_LandedPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("LandedPrice/CurrencyCode").getText() + "'";
                    LowestPrice_used_Merchant_ListingPrice_Amount = lowestPrice.selectSingleNode("ListingPrice/Amount").getText();
                    LowestPrice_used_Merchant_ListingPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("ListingPrice/CurrencyCode").getText() + "'";
                    LowestPrice_used_Merchant_Shipping_Amount = lowestPrice.selectSingleNode("Shipping/Amount").getText();
                    LowestPrice_used_Merchant_Shipping_CurrencyCode = "'" + lowestPrice.selectSingleNode("Shipping/CurrencyCode").getText() + "'";
                }
            }
            colData.append(LowestPrice_new_Amazon_LandedPrice_Amount).append(", ").append(LowestPrice_new_Amazon_LandedPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_new_Amazon_ListingPrice_Amount).append(", ").append(LowestPrice_new_Amazon_ListingPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_new_Amazon_Shipping_Amount).append(", ").append(LowestPrice_new_Amazon_Shipping_CurrencyCode).append(", ");

            colData.append(LowestPrice_new_Merchant_LandedPrice_Amount).append(", ").append(LowestPrice_new_Merchant_LandedPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_new_Merchant_ListingPrice_Amount).append(", ").append(LowestPrice_new_Merchant_ListingPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_new_Merchant_Shipping_Amount).append(", ").append(LowestPrice_new_Merchant_Shipping_CurrencyCode).append(", ");

            colData.append(LowestPrice_used_Merchant_LandedPrice_Amount).append(", ").append(LowestPrice_used_Merchant_LandedPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_used_Merchant_ListingPrice_Amount).append(", ").append(LowestPrice_used_Merchant_ListingPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_used_Merchant_Shipping_Amount).append(", ").append(LowestPrice_used_Merchant_Shipping_CurrencyCode).append(", ");

            colData.append(LowestPrice_used_Amazon_LandedPrice_Amount).append(", ").append(LowestPrice_used_Amazon_LandedPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_used_Amazon_ListingPrice_Amount).append(", ").append(LowestPrice_used_Amazon_ListingPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_used_Amazon_Shipping_Amount).append(", ").append(LowestPrice_used_Amazon_Shipping_CurrencyCode).append(", ");

            String BuyBoxPrice_new_LandedPrice_Amount = null, BuyBoxPrice_new_LandedPrice_CurrencyCode = null;
            String BuyBoxPrice_new_ListingPrice_Amount = null, BuyBoxPrice_new_ListingPrice_CurrencyCode = null;
            String BuyBoxPrice_new_Shipping_Amount = null, BuyBoxPrice_new_Shipping_CurrencyCode = null;
            String BuyBoxPrice_Used_LandedPrice_Amount = null, BuyBoxPrice_Used_LandedPrice_CurrencyCode = null;
            String BuyBoxPrice_Used_ListingPrice_Amount = null, BuyBoxPrice_Used_ListingPrice_CurrencyCode = null;
            String BuyBoxPrice_Used_Shipping_Amount = null, BuyBoxPrice_Used_Shipping_CurrencyCode = null;

            Node BuyBoxPrices = Summary.selectSingleNode("BuyBoxPrices");
            List <Node> BuyBoxPrice = BuyBoxPrices.selectNodes("BuyBoxPrice");
            for (Node buyBoxPrice : BuyBoxPrice) {
                if (buyBoxPrice.valueOf("@condition").equalsIgnoreCase("new")) {
                    BuyBoxPrice_new_LandedPrice_Amount = buyBoxPrice.selectSingleNode("LandedPrice/Amount").getText();
                    BuyBoxPrice_new_LandedPrice_CurrencyCode = "'" + buyBoxPrice.selectSingleNode("LandedPrice/CurrencyCode").getText() + "'";
                    BuyBoxPrice_new_ListingPrice_Amount = buyBoxPrice.selectSingleNode("ListingPrice/Amount").getText();
                    BuyBoxPrice_new_ListingPrice_CurrencyCode = "'" + buyBoxPrice.selectSingleNode("ListingPrice/CurrencyCode").getText() + "'";
                    BuyBoxPrice_new_Shipping_Amount = buyBoxPrice.selectSingleNode("Shipping/Amount").getText();
                    BuyBoxPrice_new_Shipping_CurrencyCode = "'" + buyBoxPrice.selectSingleNode("Shipping/CurrencyCode").getText() + "'";
                }

                if (buyBoxPrice.valueOf("@condition").equalsIgnoreCase("Used")) {
                    BuyBoxPrice_Used_LandedPrice_Amount = buyBoxPrice.selectSingleNode("LandedPrice/Amount").getText();
                    BuyBoxPrice_Used_LandedPrice_CurrencyCode = "'" + buyBoxPrice.selectSingleNode("LandedPrice/CurrencyCode").getText() + "'";
                    BuyBoxPrice_Used_ListingPrice_Amount = buyBoxPrice.selectSingleNode("ListingPrice/Amount").getText();
                    BuyBoxPrice_Used_ListingPrice_CurrencyCode = "'" + buyBoxPrice.selectSingleNode("ListingPrice/CurrencyCode").getText() + "'";
                    BuyBoxPrice_Used_Shipping_Amount = buyBoxPrice.selectSingleNode("Shipping/Amount").getText();
                    BuyBoxPrice_Used_Shipping_CurrencyCode = "'" + buyBoxPrice.selectSingleNode("Shipping/CurrencyCode").getText() + "'";
                }
            }
            colData.append(BuyBoxPrice_new_LandedPrice_Amount).append(", ").append(BuyBoxPrice_new_LandedPrice_CurrencyCode).append(", ");
            colData.append(BuyBoxPrice_new_ListingPrice_Amount).append(", ").append(BuyBoxPrice_new_ListingPrice_CurrencyCode).append(", ");
            colData.append(BuyBoxPrice_new_Shipping_Amount).append(", ").append(BuyBoxPrice_new_Shipping_CurrencyCode).append(", ");

            colData.append(BuyBoxPrice_Used_LandedPrice_Amount).append(", ").append(BuyBoxPrice_Used_LandedPrice_CurrencyCode).append(", ");
            colData.append(BuyBoxPrice_Used_ListingPrice_Amount).append(", ").append(BuyBoxPrice_Used_ListingPrice_CurrencyCode).append(", ");
            colData.append(BuyBoxPrice_Used_Shipping_Amount).append(", ").append(BuyBoxPrice_Used_Shipping_CurrencyCode).append(", ");

            String SuggestedLowerPricePlusShipping_Amount = null, SuggestedLowerPricePlusShipping_CurrencyCode = null;
            if (Summary.selectSingleNode("SuggestedLowerPricePlusShipping") != null) {
                SuggestedLowerPricePlusShipping_Amount = Summary.selectSingleNode("SuggestedLowerPricePlusShipping/Amount").getText();
                SuggestedLowerPricePlusShipping_CurrencyCode = "'" + Summary.selectSingleNode("SuggestedLowerPricePlusShipping/CurrencyCode").getText() + "'";
            }
            colData.append(SuggestedLowerPricePlusShipping_Amount).append(", ").append(SuggestedLowerPricePlusShipping_CurrencyCode).append(", ");

            String ListPrice_Amount = null, ListPrice_CurrencyCode = null;
            if (Summary.selectSingleNode("ListPrice") != null) {
                ListPrice_Amount = Summary.selectSingleNode("ListPrice/Amount").getText();
                ListPrice_CurrencyCode = "'" + Summary.selectSingleNode("ListPrice/CurrencyCode").getText() + "'";
            }
            colData.append(ListPrice_Amount).append(", ").append(ListPrice_CurrencyCode).append(", ");

            String BuyBoxEligibleOffers_OfferCount_new_Amazon = null, BuyBoxEligibleOffers_OfferCount_new_Merchant = null;
            String BuyBoxEligibleOffers_OfferCount_used_Amazon = null, BuyBoxEligibleOffers_OfferCount_used_Merchant = null;

            Node BuyBoxEligibleOffers = Summary.selectSingleNode("BuyBoxEligibleOffers");
            List <Node> BBOfferCounts = BuyBoxEligibleOffers.selectNodes("OfferCount");
            for (Node BBOfferCount : BBOfferCounts) {
                if (BBOfferCount.valueOf("@condition").equalsIgnoreCase("new") &&
                        BBOfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Amazon"))
                    BuyBoxEligibleOffers_OfferCount_new_Amazon = BBOfferCount.getText();

                if (BBOfferCount.valueOf("@condition").equalsIgnoreCase("new") &&
                        BBOfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Merchant"))
                    BuyBoxEligibleOffers_OfferCount_new_Merchant = BBOfferCount.getText();

                if (BBOfferCount.valueOf("@condition").equalsIgnoreCase("used") &&
                        BBOfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Amazon"))
                    BuyBoxEligibleOffers_OfferCount_used_Amazon = BBOfferCount.getText();

                if (BBOfferCount.valueOf("@condition").equalsIgnoreCase("used") &&
                        BBOfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Merchant"))
                    BuyBoxEligibleOffers_OfferCount_used_Merchant = BBOfferCount.getText();
            }
            colData.append(BuyBoxEligibleOffers_OfferCount_new_Amazon).append(", ").append(BuyBoxEligibleOffers_OfferCount_new_Merchant).append(", ");
            colData.append(BuyBoxEligibleOffers_OfferCount_used_Amazon).append(", ").append(BuyBoxEligibleOffers_OfferCount_used_Merchant);

            String mainTableQuery = "INSERT INTO `DataEngine`.`US_MWS_Subscriptions_API` (" + colNames + ") VALUES (" + colData + ")";
            boolean statusQuery = insertData(connection, mainTableQuery);

            if (statusQuery) {
                if (Summary.selectSingleNode("SalesRankings") != null) {
                    Node SalesRankings = Summary.selectSingleNode("SalesRankings");
                    List<Node> SalesRank = SalesRankings.selectNodes("SalesRank");

                    for (Node salesRank : SalesRank) {
                        String SalesRank_ProductCategoryId = null, SalesRank_Rank = null;

                        if (salesRank.selectSingleNode("ProductCategoryId") != null)
                            SalesRank_ProductCategoryId = "'" + salesRank.selectSingleNode("ProductCategoryId").getText() + "'";
                        if (salesRank.selectSingleNode("Rank") != null)
                            SalesRank_Rank = salesRank.selectSingleNode("Rank").getText();

                        String salesRankingQuery = "INSERT INTO `DataEngine`.`US_MWS_Subscriptions_SalesRankings` " +
                                                    "(" + salesRankingColName + ") VALUES " +
                                                    "(" + UniqueId + SalesRank_ProductCategoryId + ", " + SalesRank_Rank + ")";

                        statusQuery = insertData(connection, salesRankingQuery);
                        if (!statusQuery)
                            logger.severe("Failed to insert. salesRankingQuery");
                    }
                }

                Node Offers = AnyOfferChangedNotification.selectSingleNode("Offers");
                List<Node> Offer = Offers.selectNodes("Offer");
                for (Node offer : Offer) {
                    StringBuilder offerColData = new StringBuilder(UniqueId);

                    String SellerId = null, SubCondition = null;
                    String SellerPositiveFeedbackRating = null, FeedbackCount = null;
                    String minimumHours = null, maximumHours = null, availabilityType = null;
                    String ListingPrice_Amount = null, ListingPrice_CurrencyCode = null;
                    String Shipping_Amount = null, Shipping_CurrencyCode = null;
                    String ShipsFrom_Country = null, ShipsFrom_State = null;
                    String IsFulfilledByAmazon = null, IsBuyBoxWinner = null;
                    String IsFeaturedMerchant = null, ShipsDomestically = null;

                    if (offer.selectSingleNode("SellerId") != null)
                        SellerId = "'" + offer.selectSingleNode("SellerId").getText() + "'";
                    if (offer.selectSingleNode("SubCondition") != null)
                        SubCondition = "'" + offer.selectSingleNode("SubCondition").getText() + "'";
                    offerColData.append(SellerId).append(", ").append(SubCondition).append(", ");

                    if (offer.selectSingleNode("SellerFeedbackRating") != null) {
                        Node SellerFeedbackRating = offer.selectSingleNode("SellerFeedbackRating");
                        SellerPositiveFeedbackRating = SellerFeedbackRating.selectSingleNode("SellerPositiveFeedbackRating").getText();
                        FeedbackCount = SellerFeedbackRating.selectSingleNode("FeedbackCount").getText();
                    }
                    offerColData.append(SellerPositiveFeedbackRating).append(", ").append(FeedbackCount).append(", ");

                    if (offer.selectSingleNode("ShippingTime") != null) {
                        Node ShippingTime = offer.selectSingleNode("ShippingTime");
                        availabilityType = "'" + ShippingTime.valueOf("@availabilityType") + "'";
                        maximumHours = ShippingTime.valueOf("@maximumHours");
                        minimumHours = ShippingTime.valueOf("@minimumHours");
                    }
                    offerColData.append(availabilityType).append(", ");
                    offerColData.append(maximumHours).append(", ").append(minimumHours).append(", ");

                    if (offer.selectSingleNode("ListingPrice") != null) {
                        Node ListingPrice = offer.selectSingleNode("ListingPrice");
                        ListingPrice_Amount = ListingPrice.selectSingleNode("Amount").getText();
                        ListingPrice_CurrencyCode = "'" + ListingPrice.selectSingleNode("CurrencyCode").getText() + "'";
                    }
                    offerColData.append(ListingPrice_Amount).append(", ").append(ListingPrice_CurrencyCode).append(", ");

                    if (offer.selectSingleNode("Shipping") != null) {
                        Node Shipping = offer.selectSingleNode("Shipping");
                        Shipping_Amount = Shipping.selectSingleNode("Amount").getText();
                        Shipping_CurrencyCode = "'" + Shipping.selectSingleNode("CurrencyCode").getText() + "'";
                    }
                    offerColData.append(Shipping_Amount).append(", ").append(Shipping_CurrencyCode).append(", ");

                    if (offer.selectSingleNode("ShipsFrom") != null) {
                        Node ShipsFrom = offer.selectSingleNode("ShipsFrom");
                        ShipsFrom_Country = "'" + ShipsFrom.selectSingleNode("Country").getText() + "'";
                        ShipsFrom_State = "'" + ShipsFrom.selectSingleNode("State").getText() + "'";
                    }
                    offerColData.append(ShipsFrom_Country).append(", ").append(ShipsFrom_State).append(", ");

                    if (offer.selectSingleNode("IsFulfilledByAmazon") != null)
                        IsFulfilledByAmazon = offer.selectSingleNode("IsFulfilledByAmazon").getText();
                    if (offer.selectSingleNode("IsBuyBoxWinner") != null)
                        IsBuyBoxWinner = offer.selectSingleNode("IsBuyBoxWinner").getText();
                    if (offer.selectSingleNode("IsFeaturedMerchant") != null)
                        IsFeaturedMerchant = offer.selectSingleNode("IsFeaturedMerchant").getText();
                    if (offer.selectSingleNode("ShipsDomestically") != null)
                        ShipsDomestically = offer.selectSingleNode("ShipsDomestically").getText();

                    offerColData.append(IsFulfilledByAmazon).append(", ");
                    offerColData.append(IsBuyBoxWinner).append(", ");
                    offerColData.append(IsFeaturedMerchant).append(", ").append(ShipsDomestically);

                    String offersQuery = "INSERT INTO `DataEngine`.`US_MWS_Subscriptions_Offers` (" + offerColName + ") " +
                            "VALUES (" + offerColData + ");";

                    statusQuery = insertData(connection, offersQuery);
                    if (!statusQuery)
                        logger.severe("Failed to insert offersQuery.");
                }
                status = true;
            }

            return status;
        }
        catch (Exception e){
            e.printStackTrace();
            return status;
        }
    }

    public static boolean setGetCompetitivePricingForASINData(String fileName, Connection connection){
        boolean status = false;
        try {
            String colNames = "SKU, ItemCondition, TimeOfOfferChange, TotalOfferCount, NumberOfOffers_OfferCount_new_Amazon, " +
                    "NumberOfOffers_OfferCount_new_Merchant, NumberOfOffers_OfferCount_used_Amazon, " +
                    "NumberOfOffers_OfferCount_used_Merchant, LowestPrice_new_Amazon_LandedPrice_Amount, " +
                    "LowestPrice_new_Amazon_LandedPrice_CurrencyCode, LowestPrice_new_Amazon_ListingPrice_Amount, " +
                    "LowestPrice_new_Amazon_ListingPrice_CurrencyCode, LowestPrice_new_Amazon_Shipping_Amount, " +
                    "LowestPrice_new_Amazon_Shipping_CurrencyCode, LowestPrice_new_Merchant_LandedPrice_Amount, " +
                    "LowestPrice_new_Merchant_LandedPrice_CurrencyCode, LowestPrice_new_Merchant_ListingPrice_Amount, " +
                    "LowestPrice_new_Merchant_ListingPrice_CurrencyCode, LowestPrice_new_Merchant_Shipping_Amount, " +
                    "LowestPrice_new_Merchant_Shipping_CurrencyCode, LowestPrice_used_Merchant_LandedPrice_Amount, " +
                    "LowestPrice_used_Merchant_LandedPrice_CurrencyCode, LowestPrice_used_Merchant_ListingPrice_Amount, " +
                    "LowestPrice_used_Merchant_ListingPrice_CurrencyCode, LowestPrice_used_Merchant_Shipping_Amount, " +
                    "LowestPrice_used_Merchant_Shipping_CurrencyCode, LowestPrice_used_Amazon_LandedPrice_Amount, " +
                    "LowestPrice_used_Amazon_LandedPrice_CurrencyCode, LowestPrice_used_Amazon_ListingPrice_Amount, " +
                    "LowestPrice_used_Amazon_ListingPrice_CurrencyCode, LowestPrice_used_Amazon_Shipping_Amount, " +
                    "LowestPrice_used_Amazon_Shipping_CurrencyCode, BuyBoxPrice_new_LandedPrice_Amount, " +
                    "BuyBoxPrice_new_LandedPrice_CurrencyCode, BuyBoxPrice_new_ListingPrice_Amount, " +
                    "BuyBoxPrice_new_ListingPrice_CurrencyCode, BuyBoxPrice_new_Shipping_Amount, " +
                    "BuyBoxPrice_new_Shipping_CurrencyCode, BuyBoxPrice_Used_LandedPrice_Amount, " +
                    "BuyBoxPrice_Used_LandedPrice_CurrencyCode, BuyBoxPrice_Used_ListingPrice_Amount, " +
                    "BuyBoxPrice_Used_ListingPrice_CurrencyCode, BuyBoxPrice_Used_Shipping_Amount, " +
                    "BuyBoxPrice_Used_Shipping_CurrencyCode, SuggestedLowerPricePlusShipping_Amount, " +
                    "SuggestedLowerPricePlusShipping_CurrencyCode, ListPrice_Amount, ListPrice_CurrencyCode, " +
                    "BuyBoxEligibleOffers_OfferCount_new_Amazon, BuyBoxEligibleOffers_OfferCount_new_Merchant, " +
                    "BuyBoxEligibleOffers_OfferCount_used_Amazon, BuyBoxEligibleOffers_OfferCount_used_Merchant";

            String offerColName = "SKU, MyOffer, SubCondition, SellerPositiveFeedbackRating, FeedbackCount, " +
                    "ShippingTime_availabilityType, ShippingTime_maximumHours, ShippingTime_minimumHours, " +
                    "ListingPrice_Amount, ListingPrice_CurrencyCode, Shipping_Amount, Shipping_CurrencyCode, " +
                    "ShipsFrom_Country, ShipsFrom_State, IsFulfilledByAmazon, IsBuyBoxWinner, IsFeaturedMerchant";


            File file = new File(fileName);
            SAXReader reader = new SAXReader();
            Document xmlDoc = reader.read(file);

            Node GetLowestPricedOffersForSKUResponse = xmlDoc.selectSingleNode("GetLowestPricedOffersForSKUResponse");
            Node GetLowestPricedOffersForSKUResult = GetLowestPricedOffersForSKUResponse.selectSingleNode("GetLowestPricedOffersForSKUResult");
            Node Identifier = GetLowestPricedOffersForSKUResult.selectSingleNode("Identifier");
            Node Summary = GetLowestPricedOffersForSKUResult.selectSingleNode("Summary");

            String SellerSKU = "'" + Identifier.selectSingleNode("SellerSKU").getText() + "', ";
            StringBuilder colData = new StringBuilder( SellerSKU +
                    "'" + Identifier.selectSingleNode("ItemCondition").getText() + "', " +
                    "'" + Identifier.selectSingleNode("TimeOfOfferChange").getText() + "', " +
                    Summary.selectSingleNode("TotalOfferCount").getText() + ", ");

            String NumberOfOffers_OfferCount_new_Amazon = null, NumberOfOffers_OfferCount_new_Merchant = null;
            String NumberOfOffers_OfferCount_used_Amazon = null, NumberOfOffers_OfferCount_used_Merchant = null;

            Node NumberOfOffers = Summary.selectSingleNode("NumberOfOffers");
            List<Node> OfferCounts = NumberOfOffers.selectNodes("OfferCount");
            for (Node OfferCount : OfferCounts) {
                if (OfferCount.valueOf("@condition").equalsIgnoreCase("new") &&
                        OfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Amazon"))
                    NumberOfOffers_OfferCount_new_Amazon = OfferCount.getText();

                if (OfferCount.valueOf("@condition").equalsIgnoreCase("new") &&
                        OfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Merchant"))
                    NumberOfOffers_OfferCount_new_Merchant = OfferCount.getText();

                if (OfferCount.valueOf("@condition").equalsIgnoreCase("used") &&
                        OfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Amazon"))
                    NumberOfOffers_OfferCount_used_Amazon = OfferCount.getText();

                if (OfferCount.valueOf("@condition").equalsIgnoreCase("used") &&
                        OfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Merchant"))
                    NumberOfOffers_OfferCount_used_Merchant = OfferCount.getText() ;
            }
            colData.append(NumberOfOffers_OfferCount_new_Amazon).append(", ").append(NumberOfOffers_OfferCount_new_Merchant).append(", ");
            colData.append(NumberOfOffers_OfferCount_used_Amazon).append(", ").append(NumberOfOffers_OfferCount_used_Merchant).append(", ");

            String LowestPrice_new_Amazon_LandedPrice_Amount = null, LowestPrice_new_Amazon_LandedPrice_CurrencyCode = null;
            String LowestPrice_new_Amazon_ListingPrice_Amount = null, LowestPrice_new_Amazon_ListingPrice_CurrencyCode = null;
            String LowestPrice_new_Amazon_Shipping_Amount = null, LowestPrice_new_Amazon_Shipping_CurrencyCode = null;
            String LowestPrice_new_Merchant_LandedPrice_Amount = null, LowestPrice_new_Merchant_LandedPrice_CurrencyCode = null;
            String LowestPrice_new_Merchant_ListingPrice_Amount = null, LowestPrice_new_Merchant_ListingPrice_CurrencyCode = null;
            String LowestPrice_new_Merchant_Shipping_Amount = null, LowestPrice_new_Merchant_Shipping_CurrencyCode = null;
            String LowestPrice_used_Merchant_LandedPrice_Amount = null, LowestPrice_used_Merchant_LandedPrice_CurrencyCode = null;
            String LowestPrice_used_Merchant_ListingPrice_Amount = null, LowestPrice_used_Merchant_ListingPrice_CurrencyCode = null;
            String LowestPrice_used_Merchant_Shipping_Amount = null, LowestPrice_used_Merchant_Shipping_CurrencyCode = null;
            String LowestPrice_used_Amazon_LandedPrice_Amount = null, LowestPrice_used_Amazon_LandedPrice_CurrencyCode = null;
            String LowestPrice_used_Amazon_ListingPrice_Amount = null, LowestPrice_used_Amazon_ListingPrice_CurrencyCode = null;
            String LowestPrice_used_Amazon_Shipping_Amount = null, LowestPrice_used_Amazon_Shipping_CurrencyCode = null;

            Node LowestPrices = Summary.selectSingleNode("LowestPrices");
            List <Node> LowestPrice = LowestPrices.selectNodes("LowestPrice");
            for (Node lowestPrice : LowestPrice) {
                if (lowestPrice.valueOf("@condition").equalsIgnoreCase("new") &&
                        lowestPrice.valueOf("@fulfillmentChannel").equalsIgnoreCase("Amazon")) {
                    LowestPrice_new_Amazon_LandedPrice_Amount = lowestPrice.selectSingleNode("LandedPrice/Amount").getText();
                    LowestPrice_new_Amazon_LandedPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("LandedPrice/CurrencyCode").getText() + "'";
                    LowestPrice_new_Amazon_ListingPrice_Amount = lowestPrice.selectSingleNode("ListingPrice/Amount").getText();
                    LowestPrice_new_Amazon_ListingPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("ListingPrice/CurrencyCode").getText() + "'";
                    LowestPrice_new_Amazon_Shipping_Amount = lowestPrice.selectSingleNode("Shipping/Amount").getText();
                    LowestPrice_new_Amazon_Shipping_CurrencyCode = "'" + lowestPrice.selectSingleNode("Shipping/CurrencyCode").getText() + "'";
                }

                if (lowestPrice.valueOf("@condition").equalsIgnoreCase("new") &&
                        lowestPrice.valueOf("@fulfillmentChannel").equalsIgnoreCase("Merchant")) {
                    LowestPrice_new_Merchant_LandedPrice_Amount = lowestPrice.selectSingleNode("LandedPrice/Amount").getText();
                    LowestPrice_new_Merchant_LandedPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("LandedPrice/CurrencyCode").getText() + "'";
                    LowestPrice_new_Merchant_ListingPrice_Amount = lowestPrice.selectSingleNode("ListingPrice/Amount").getText();
                    LowestPrice_new_Merchant_ListingPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("ListingPrice/CurrencyCode").getText() + "'";
                    LowestPrice_new_Merchant_Shipping_Amount = lowestPrice.selectSingleNode("Shipping/Amount").getText();
                    LowestPrice_new_Merchant_Shipping_CurrencyCode = "'" + lowestPrice.selectSingleNode("Shipping/CurrencyCode").getText() + "'";
                }

                if (lowestPrice.valueOf("@condition").equalsIgnoreCase("used") &&
                        lowestPrice.valueOf("@fulfillmentChannel").equalsIgnoreCase("Amazon")) {
                    LowestPrice_used_Amazon_LandedPrice_Amount = lowestPrice.selectSingleNode("LandedPrice/Amount").getText();
                    LowestPrice_used_Amazon_LandedPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("LandedPrice/CurrencyCode").getText() + "'";
                    LowestPrice_used_Amazon_ListingPrice_Amount = lowestPrice.selectSingleNode("ListingPrice/Amount").getText();
                    LowestPrice_used_Amazon_ListingPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("ListingPrice/CurrencyCode").getText() + "'";
                    LowestPrice_used_Amazon_Shipping_Amount = lowestPrice.selectSingleNode("Shipping/Amount").getText();
                    LowestPrice_used_Amazon_Shipping_CurrencyCode = "'" + lowestPrice.selectSingleNode("Shipping/CurrencyCode").getText() + "'";
                }

                if (lowestPrice.valueOf("@condition").equalsIgnoreCase("used") &&
                        lowestPrice.valueOf("@fulfillmentChannel").equalsIgnoreCase("Merchant")) {
                    LowestPrice_used_Merchant_LandedPrice_Amount = lowestPrice.selectSingleNode("LandedPrice/Amount").getText();
                    LowestPrice_used_Merchant_LandedPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("LandedPrice/CurrencyCode").getText() + "'";
                    LowestPrice_used_Merchant_ListingPrice_Amount = lowestPrice.selectSingleNode("ListingPrice/Amount").getText();
                    LowestPrice_used_Merchant_ListingPrice_CurrencyCode = "'" + lowestPrice.selectSingleNode("ListingPrice/CurrencyCode").getText() + "'";
                    LowestPrice_used_Merchant_Shipping_Amount = lowestPrice.selectSingleNode("Shipping/Amount").getText();
                    LowestPrice_used_Merchant_Shipping_CurrencyCode = "'" + lowestPrice.selectSingleNode("Shipping/CurrencyCode").getText() + "'";
                }
            }
            colData.append(LowestPrice_new_Amazon_LandedPrice_Amount).append(", ").append(LowestPrice_new_Amazon_LandedPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_new_Amazon_ListingPrice_Amount).append(", ").append(LowestPrice_new_Amazon_ListingPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_new_Amazon_Shipping_Amount).append(", ").append(LowestPrice_new_Amazon_Shipping_CurrencyCode).append(", ");

            colData.append(LowestPrice_new_Merchant_LandedPrice_Amount).append(", ").append(LowestPrice_new_Merchant_LandedPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_new_Merchant_ListingPrice_Amount).append(", ").append(LowestPrice_new_Merchant_ListingPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_new_Merchant_Shipping_Amount).append(", ").append(LowestPrice_new_Merchant_Shipping_CurrencyCode).append(", ");

            colData.append(LowestPrice_used_Merchant_LandedPrice_Amount).append(", ").append(LowestPrice_used_Merchant_LandedPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_used_Merchant_ListingPrice_Amount).append(", ").append(LowestPrice_used_Merchant_ListingPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_used_Merchant_Shipping_Amount).append(", ").append(LowestPrice_used_Merchant_Shipping_CurrencyCode).append(", ");

            colData.append(LowestPrice_used_Amazon_LandedPrice_Amount).append(", ").append(LowestPrice_used_Amazon_LandedPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_used_Amazon_ListingPrice_Amount).append(", ").append(LowestPrice_used_Amazon_ListingPrice_CurrencyCode).append(", ");
            colData.append(LowestPrice_used_Amazon_Shipping_Amount).append(", ").append(LowestPrice_used_Amazon_Shipping_CurrencyCode).append(", ");

            String BuyBoxPrice_new_LandedPrice_Amount = null, BuyBoxPrice_new_LandedPrice_CurrencyCode = null;
            String BuyBoxPrice_new_ListingPrice_Amount = null, BuyBoxPrice_new_ListingPrice_CurrencyCode = null;
            String BuyBoxPrice_new_Shipping_Amount = null, BuyBoxPrice_new_Shipping_CurrencyCode = null;
            String BuyBoxPrice_Used_LandedPrice_Amount = null, BuyBoxPrice_Used_LandedPrice_CurrencyCode = null;
            String BuyBoxPrice_Used_ListingPrice_Amount = null, BuyBoxPrice_Used_ListingPrice_CurrencyCode = null;
            String BuyBoxPrice_Used_Shipping_Amount = null, BuyBoxPrice_Used_Shipping_CurrencyCode = null;

            if (Summary.selectSingleNode("BuyBoxPrices") != null) {
                Node BuyBoxPrices = Summary.selectSingleNode("BuyBoxPrices");
                List<Node> BuyBoxPrice = BuyBoxPrices.selectNodes("BuyBoxPrice");
                for (Node buyBoxPrice : BuyBoxPrice) {
                    if (buyBoxPrice.valueOf("@condition").equalsIgnoreCase("new")) {
                        BuyBoxPrice_new_LandedPrice_Amount = buyBoxPrice.selectSingleNode("LandedPrice/Amount").getText();
                        BuyBoxPrice_new_LandedPrice_CurrencyCode = "'" + buyBoxPrice.selectSingleNode("LandedPrice/CurrencyCode").getText() + "'";
                        BuyBoxPrice_new_ListingPrice_Amount = buyBoxPrice.selectSingleNode("ListingPrice/Amount").getText();
                        BuyBoxPrice_new_ListingPrice_CurrencyCode = "'" + buyBoxPrice.selectSingleNode("ListingPrice/CurrencyCode").getText() + "'";
                        BuyBoxPrice_new_Shipping_Amount = buyBoxPrice.selectSingleNode("Shipping/Amount").getText();
                        BuyBoxPrice_new_Shipping_CurrencyCode = "'" + buyBoxPrice.selectSingleNode("Shipping/CurrencyCode").getText() + "'";
                    }

                    if (buyBoxPrice.valueOf("@condition").equalsIgnoreCase("Used")) {
                        BuyBoxPrice_Used_LandedPrice_Amount = buyBoxPrice.selectSingleNode("LandedPrice/Amount").getText();
                        BuyBoxPrice_Used_LandedPrice_CurrencyCode = "'" + buyBoxPrice.selectSingleNode("LandedPrice/CurrencyCode").getText() + "'";
                        BuyBoxPrice_Used_ListingPrice_Amount = buyBoxPrice.selectSingleNode("ListingPrice/Amount").getText();
                        BuyBoxPrice_Used_ListingPrice_CurrencyCode = "'" + buyBoxPrice.selectSingleNode("ListingPrice/CurrencyCode").getText() + "'";
                        BuyBoxPrice_Used_Shipping_Amount = buyBoxPrice.selectSingleNode("Shipping/Amount").getText();
                        BuyBoxPrice_Used_Shipping_CurrencyCode = "'" + buyBoxPrice.selectSingleNode("Shipping/CurrencyCode").getText() + "'";
                    }
                }
            }
            colData.append(BuyBoxPrice_new_LandedPrice_Amount).append(", ").append(BuyBoxPrice_new_LandedPrice_CurrencyCode).append(", ");
            colData.append(BuyBoxPrice_new_ListingPrice_Amount).append(", ").append(BuyBoxPrice_new_ListingPrice_CurrencyCode).append(", ");
            colData.append(BuyBoxPrice_new_Shipping_Amount).append(", ").append(BuyBoxPrice_new_Shipping_CurrencyCode).append(", ");

            colData.append(BuyBoxPrice_Used_LandedPrice_Amount).append(", ").append(BuyBoxPrice_Used_LandedPrice_CurrencyCode).append(", ");
            colData.append(BuyBoxPrice_Used_ListingPrice_Amount).append(", ").append(BuyBoxPrice_Used_ListingPrice_CurrencyCode).append(", ");
            colData.append(BuyBoxPrice_Used_Shipping_Amount).append(", ").append(BuyBoxPrice_Used_Shipping_CurrencyCode).append(", ");

            String SuggestedLowerPricePlusShipping_Amount = null, SuggestedLowerPricePlusShipping_CurrencyCode = null;
            if (Summary.selectSingleNode("SuggestedLowerPricePlusShipping") != null) {
                SuggestedLowerPricePlusShipping_Amount = Summary.selectSingleNode("SuggestedLowerPricePlusShipping/Amount").getText();
                SuggestedLowerPricePlusShipping_CurrencyCode = "'" + Summary.selectSingleNode("SuggestedLowerPricePlusShipping/CurrencyCode").getText() + "'";
            }
            colData.append(SuggestedLowerPricePlusShipping_Amount).append(", ").append(SuggestedLowerPricePlusShipping_CurrencyCode).append(", ");

            String ListPrice_Amount = null, ListPrice_CurrencyCode = null;
            if (Summary.selectSingleNode("ListPrice") != null) {
                ListPrice_Amount = Summary.selectSingleNode("ListPrice/Amount").getText();
                ListPrice_CurrencyCode = "'" + Summary.selectSingleNode("ListPrice/CurrencyCode").getText() + "'";
            }
            colData.append(ListPrice_Amount).append(", ").append(ListPrice_CurrencyCode).append(", ");

            String BuyBoxEligibleOffers_OfferCount_new_Amazon = null, BuyBoxEligibleOffers_OfferCount_new_Merchant = null;
            String BuyBoxEligibleOffers_OfferCount_used_Amazon = null, BuyBoxEligibleOffers_OfferCount_used_Merchant = null;

            Node BuyBoxEligibleOffers = Summary.selectSingleNode("BuyBoxEligibleOffers");
            List <Node> BBOfferCounts = BuyBoxEligibleOffers.selectNodes("OfferCount");
            for (Node BBOfferCount : BBOfferCounts) {
                if (BBOfferCount.valueOf("@condition").equalsIgnoreCase("new") &&
                        BBOfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Amazon"))
                    BuyBoxEligibleOffers_OfferCount_new_Amazon = BBOfferCount.getText();

                if (BBOfferCount.valueOf("@condition").equalsIgnoreCase("new") &&
                        BBOfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Merchant"))
                    BuyBoxEligibleOffers_OfferCount_new_Merchant = BBOfferCount.getText();

                if (BBOfferCount.valueOf("@condition").equalsIgnoreCase("used") &&
                        BBOfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Amazon"))
                    BuyBoxEligibleOffers_OfferCount_used_Amazon = BBOfferCount.getText();

                if (BBOfferCount.valueOf("@condition").equalsIgnoreCase("used") &&
                        BBOfferCount.valueOf("@fulfillmentChannel").equalsIgnoreCase("Merchant"))
                    BuyBoxEligibleOffers_OfferCount_used_Merchant = BBOfferCount.getText();
            }
            colData.append(BuyBoxEligibleOffers_OfferCount_new_Amazon).append(", ").append(BuyBoxEligibleOffers_OfferCount_new_Merchant).append(", ");
            colData.append(BuyBoxEligibleOffers_OfferCount_used_Amazon).append(", ").append(BuyBoxEligibleOffers_OfferCount_used_Merchant);

            String mainTableQuery = "INSERT INTO `DataEngine`.`US_MWS_GetLowestPricedOffersForSKU` (" + colNames + ") VALUES (" + colData + ")";
            boolean statusQuery = insertData(connection, mainTableQuery);

            if (statusQuery) {
                Node Offers = GetLowestPricedOffersForSKUResult.selectSingleNode("Offers");
                List<Node> Offer = Offers.selectNodes("Offer");
                for (Node offer : Offer) {
                    StringBuilder offerColData = new StringBuilder(SellerSKU);

                    String MyOffer = null, SubCondition = null;
                    String SellerPositiveFeedbackRating = null, FeedbackCount = null;
                    String minimumHours = null, maximumHours = null, availabilityType = null;
                    String ListingPrice_Amount = null, ListingPrice_CurrencyCode = null;
                    String Shipping_Amount = null, Shipping_CurrencyCode = null;
                    String ShipsFrom_Country = null, ShipsFrom_State = null;
                    String IsFulfilledByAmazon = null, IsBuyBoxWinner = null;
                    String IsFeaturedMerchant = null;

                    if (offer.selectSingleNode("MyOffer") != null)
                        MyOffer = offer.selectSingleNode("MyOffer").getText();
                    if (offer.selectSingleNode("SubCondition") != null)
                        SubCondition = "'" + offer.selectSingleNode("SubCondition").getText() + "'";
                    offerColData.append(MyOffer).append(", ").append(SubCondition).append(", ");

                    if (offer.selectSingleNode("SellerFeedbackRating") != null) {
                        Node SellerFeedbackRating = offer.selectSingleNode("SellerFeedbackRating");
                        SellerPositiveFeedbackRating = SellerFeedbackRating.selectSingleNode("SellerPositiveFeedbackRating").getText();
                        FeedbackCount = SellerFeedbackRating.selectSingleNode("FeedbackCount").getText();
                    }
                    offerColData.append(SellerPositiveFeedbackRating).append(", ").append(FeedbackCount).append(", ");

                    if (offer.selectSingleNode("ShippingTime") != null) {
                        Node ShippingTime = offer.selectSingleNode("ShippingTime");
                        availabilityType = "'" + ShippingTime.valueOf("@availabilityType") + "'";
                        maximumHours = ShippingTime.valueOf("@maximumHours");
                        minimumHours = ShippingTime.valueOf("@minimumHours");
                    }
                    offerColData.append(availabilityType).append(", ");
                    offerColData.append(maximumHours).append(", ").append(minimumHours).append(", ");

                    if (offer.selectSingleNode("ListingPrice") != null) {
                        Node ListingPrice = offer.selectSingleNode("ListingPrice");
                        ListingPrice_Amount = ListingPrice.selectSingleNode("Amount").getText();
                        ListingPrice_CurrencyCode = "'" + ListingPrice.selectSingleNode("CurrencyCode").getText() + "'";
                    }
                    offerColData.append(ListingPrice_Amount).append(", ").append(ListingPrice_CurrencyCode).append(", ");

                    if (offer.selectSingleNode("Shipping") != null) {
                        Node Shipping = offer.selectSingleNode("Shipping");
                        Shipping_Amount = Shipping.selectSingleNode("Amount").getText();
                        Shipping_CurrencyCode = "'" + Shipping.selectSingleNode("CurrencyCode").getText() + "'";
                    }
                    offerColData.append(Shipping_Amount).append(", ").append(Shipping_CurrencyCode).append(", ");

                    if (offer.selectSingleNode("ShipsFrom") != null) {
                        Node ShipsFrom = offer.selectSingleNode("ShipsFrom");
                        if (ShipsFrom.selectSingleNode("Country") != null)
                            ShipsFrom_Country = "'" + ShipsFrom.selectSingleNode("Country").getText() + "'";
                        if (ShipsFrom.selectSingleNode("State") != null)
                            ShipsFrom_State = "'" + ShipsFrom.selectSingleNode("State").getText() + "'";
                    }
                    offerColData.append(ShipsFrom_Country).append(", ").append(ShipsFrom_State).append(", ");

                    if (offer.selectSingleNode("IsFulfilledByAmazon") != null)
                        IsFulfilledByAmazon = offer.selectSingleNode("IsFulfilledByAmazon").getText();
                    if (offer.selectSingleNode("IsBuyBoxWinner") != null)
                        IsBuyBoxWinner = offer.selectSingleNode("IsBuyBoxWinner").getText();
                    if (offer.selectSingleNode("IsFeaturedMerchant") != null)
                        IsFeaturedMerchant = offer.selectSingleNode("IsFeaturedMerchant").getText();

                    offerColData.append(IsFulfilledByAmazon).append(", ");
                    offerColData.append(IsBuyBoxWinner).append(", ");
                    offerColData.append(IsFeaturedMerchant);

                    String offersQuery = "INSERT INTO `DataEngine`.`US_MWS_GetLowestPricedOffersForSKU_Offers` (" + offerColName + ") " +
                            "VALUES (" + offerColData + ");";

                    statusQuery = insertData(connection, offersQuery);
                    if (!statusQuery)
                        logger.severe("Failed to insert offersQuery.");
                }
                status = true;
            }
            return status;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}

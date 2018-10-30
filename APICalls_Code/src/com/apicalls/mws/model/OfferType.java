package com.apicalls.mws.model;

import com.amazonservices.mws.client.AbstractMwsObject;
import com.amazonservices.mws.client.MwsReader;
import com.amazonservices.mws.client.MwsWriter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "OfferType",
        propOrder = {"buyingPrice", "regularPrice", "fulfillmentChannel", "itemCondition", "itemSubCondition", "sellerId", "sellerSKU"}
)
@XmlRootElement(
        name = "OfferType"
)
public class OfferType extends AbstractMwsObject {
    @XmlElement(
            name = "BuyingPrice",
            required = true
    )
    private PriceType buyingPrice;
    @XmlElement(
            name = "RegularPrice",
            required = true
    )
    private MoneyType regularPrice;
    @XmlElement(
            name = "FulfillmentChannel",
            required = true
    )
    private String fulfillmentChannel;
    @XmlElement(
            name = "ItemCondition",
            required = true
    )
    private String itemCondition;
    @XmlElement(
            name = "ItemSubCondition",
            required = true
    )
    private String itemSubCondition;
    @XmlElement(
            name = "SellerId",
            required = true
    )
    private String sellerId;
    @XmlElement(
            name = "SellerSKU",
            required = true
    )
    private String sellerSKU;

    public PriceType getBuyingPrice() {
        return this.buyingPrice;
    }

    public void setBuyingPrice(PriceType buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public boolean isSetBuyingPrice() {
        return this.buyingPrice != null;
    }

    public OfferType withBuyingPrice(PriceType buyingPrice) {
        this.buyingPrice = buyingPrice;
        return this;
    }

    public MoneyType getRegularPrice() {
        return this.regularPrice;
    }

    public void setRegularPrice(MoneyType regularPrice) {
        this.regularPrice = regularPrice;
    }

    public boolean isSetRegularPrice() {
        return this.regularPrice != null;
    }

    public OfferType withRegularPrice(MoneyType regularPrice) {
        this.regularPrice = regularPrice;
        return this;
    }

    public String getFulfillmentChannel() {
        return this.fulfillmentChannel;
    }

    public void setFulfillmentChannel(String fulfillmentChannel) {
        this.fulfillmentChannel = fulfillmentChannel;
    }

    public boolean isSetFulfillmentChannel() {
        return this.fulfillmentChannel != null;
    }

    public OfferType withFulfillmentChannel(String fulfillmentChannel) {
        this.fulfillmentChannel = fulfillmentChannel;
        return this;
    }

    public String getItemCondition() {
        return this.itemCondition;
    }

    public void setItemCondition(String itemCondition) {
        this.itemCondition = itemCondition;
    }

    public boolean isSetItemCondition() {
        return this.itemCondition != null;
    }

    public OfferType withItemCondition(String itemCondition) {
        this.itemCondition = itemCondition;
        return this;
    }

    public String getItemSubCondition() {
        return this.itemSubCondition;
    }

    public void setItemSubCondition(String itemSubCondition) {
        this.itemSubCondition = itemSubCondition;
    }

    public boolean isSetItemSubCondition() {
        return this.itemSubCondition != null;
    }

    public OfferType withItemSubCondition(String itemSubCondition) {
        this.itemSubCondition = itemSubCondition;
        return this;
    }

    public String getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public boolean isSetSellerId() {
        return this.sellerId != null;
    }

    public OfferType withSellerId(String sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public String getSellerSKU() {
        return this.sellerSKU;
    }

    public void setSellerSKU(String sellerSKU) {
        this.sellerSKU = sellerSKU;
    }

    public boolean isSetSellerSKU() {
        return this.sellerSKU != null;
    }

    public OfferType withSellerSKU(String sellerSKU) {
        this.sellerSKU = sellerSKU;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.buyingPrice = (PriceType)r.read("BuyingPrice", PriceType.class);
        this.regularPrice = (MoneyType)r.read("RegularPrice", MoneyType.class);
        this.fulfillmentChannel = (String)r.read("FulfillmentChannel", String.class);
        this.itemCondition = (String)r.read("ItemCondition", String.class);
        this.itemSubCondition = (String)r.read("ItemSubCondition", String.class);
        this.sellerId = (String)r.read("SellerId", String.class);
        this.sellerSKU = (String)r.read("SellerSKU", String.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.write("BuyingPrice", this.buyingPrice);
        w.write("RegularPrice", this.regularPrice);
        w.write("FulfillmentChannel", this.fulfillmentChannel);
        w.write("ItemCondition", this.itemCondition);
        w.write("ItemSubCondition", this.itemSubCondition);
        w.write("SellerId", this.sellerId);
        w.write("SellerSKU", this.sellerSKU);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "OfferType", this);
    }

    public OfferType(PriceType buyingPrice, MoneyType regularPrice, String fulfillmentChannel, String itemCondition, String itemSubCondition, String sellerId, String sellerSKU) {
        this.buyingPrice = buyingPrice;
        this.regularPrice = regularPrice;
        this.fulfillmentChannel = fulfillmentChannel;
        this.itemCondition = itemCondition;
        this.itemSubCondition = itemSubCondition;
        this.sellerId = sellerId;
        this.sellerSKU = sellerSKU;
    }

    public OfferType() {
    }
}

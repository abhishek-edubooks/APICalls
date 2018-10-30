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
        name = "QualifiersType",
        propOrder = {"itemCondition", "itemSubcondition", "fulfillmentChannel", "shipsDomestically", "shippingTime", "sellerPositiveFeedbackRating"}
)
@XmlRootElement(
        name = "QualifiersType"
)
public class QualifiersType extends AbstractMwsObject {
    @XmlElement(
            name = "ItemCondition",
            required = true
    )
    private String itemCondition;
    @XmlElement(
            name = "ItemSubcondition",
            required = true
    )
    private String itemSubcondition;
    @XmlElement(
            name = "FulfillmentChannel",
            required = true
    )
    private String fulfillmentChannel;
    @XmlElement(
            name = "ShipsDomestically",
            required = true
    )
    private String shipsDomestically;
    @XmlElement(
            name = "ShippingTime",
            required = true
    )
    private ShippingTimeType shippingTime;
    @XmlElement(
            name = "SellerPositiveFeedbackRating",
            required = true
    )
    private String sellerPositiveFeedbackRating;

    public String getItemCondition() {
        return this.itemCondition;
    }

    public void setItemCondition(String itemCondition) {
        this.itemCondition = itemCondition;
    }

    public boolean isSetItemCondition() {
        return this.itemCondition != null;
    }

    public QualifiersType withItemCondition(String itemCondition) {
        this.itemCondition = itemCondition;
        return this;
    }

    public String getItemSubcondition() {
        return this.itemSubcondition;
    }

    public void setItemSubcondition(String itemSubcondition) {
        this.itemSubcondition = itemSubcondition;
    }

    public boolean isSetItemSubcondition() {
        return this.itemSubcondition != null;
    }

    public QualifiersType withItemSubcondition(String itemSubcondition) {
        this.itemSubcondition = itemSubcondition;
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

    public QualifiersType withFulfillmentChannel(String fulfillmentChannel) {
        this.fulfillmentChannel = fulfillmentChannel;
        return this;
    }

    public String getShipsDomestically() {
        return this.shipsDomestically;
    }

    public void setShipsDomestically(String shipsDomestically) {
        this.shipsDomestically = shipsDomestically;
    }

    public boolean isSetShipsDomestically() {
        return this.shipsDomestically != null;
    }

    public QualifiersType withShipsDomestically(String shipsDomestically) {
        this.shipsDomestically = shipsDomestically;
        return this;
    }

    public ShippingTimeType getShippingTime() {
        return this.shippingTime;
    }

    public void setShippingTime(ShippingTimeType shippingTime) {
        this.shippingTime = shippingTime;
    }

    public boolean isSetShippingTime() {
        return this.shippingTime != null;
    }

    public QualifiersType withShippingTime(ShippingTimeType shippingTime) {
        this.shippingTime = shippingTime;
        return this;
    }

    public String getSellerPositiveFeedbackRating() {
        return this.sellerPositiveFeedbackRating;
    }

    public void setSellerPositiveFeedbackRating(String sellerPositiveFeedbackRating) {
        this.sellerPositiveFeedbackRating = sellerPositiveFeedbackRating;
    }

    public boolean isSetSellerPositiveFeedbackRating() {
        return this.sellerPositiveFeedbackRating != null;
    }

    public QualifiersType withSellerPositiveFeedbackRating(String sellerPositiveFeedbackRating) {
        this.sellerPositiveFeedbackRating = sellerPositiveFeedbackRating;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.itemCondition = (String)r.read("ItemCondition", String.class);
        this.itemSubcondition = (String)r.read("ItemSubcondition", String.class);
        this.fulfillmentChannel = (String)r.read("FulfillmentChannel", String.class);
        this.shipsDomestically = (String)r.read("ShipsDomestically", String.class);
        this.shippingTime = (ShippingTimeType)r.read("ShippingTime", ShippingTimeType.class);
        this.sellerPositiveFeedbackRating = (String)r.read("SellerPositiveFeedbackRating", String.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.write("ItemCondition", this.itemCondition);
        w.write("ItemSubcondition", this.itemSubcondition);
        w.write("FulfillmentChannel", this.fulfillmentChannel);
        w.write("ShipsDomestically", this.shipsDomestically);
        w.write("ShippingTime", this.shippingTime);
        w.write("SellerPositiveFeedbackRating", this.sellerPositiveFeedbackRating);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "QualifiersType", this);
    }

    public QualifiersType(String itemCondition, String itemSubcondition, String fulfillmentChannel, String shipsDomestically, ShippingTimeType shippingTime, String sellerPositiveFeedbackRating) {
        this.itemCondition = itemCondition;
        this.itemSubcondition = itemSubcondition;
        this.fulfillmentChannel = fulfillmentChannel;
        this.shipsDomestically = shipsDomestically;
        this.shippingTime = shippingTime;
        this.sellerPositiveFeedbackRating = sellerPositiveFeedbackRating;
    }

    public QualifiersType() {
    }
}

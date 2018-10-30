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
        name = "LowestOfferListingType",
        propOrder = {"qualifiers", "numberOfOfferListingsConsidered", "sellerFeedbackCount", "price", "multipleOffersAtLowestPrice"}
)
@XmlRootElement(
        name = "LowestOfferListingType"
)
public class LowestOfferListingType extends AbstractMwsObject {
    @XmlElement(
            name = "Qualifiers",
            required = true
    )
    private QualifiersType qualifiers;
    @XmlElement(
            name = "NumberOfOfferListingsConsidered"
    )
    private Integer numberOfOfferListingsConsidered;
    @XmlElement(
            name = "SellerFeedbackCount",
            required = true
    )
    private int sellerFeedbackCount;
    @XmlElement(
            name = "Price",
            required = true
    )
    private PriceType price;
    @XmlElement(
            name = "MultipleOffersAtLowestPrice",
            required = true
    )
    private String multipleOffersAtLowestPrice;

    public QualifiersType getQualifiers() {
        return this.qualifiers;
    }

    public void setQualifiers(QualifiersType qualifiers) {
        this.qualifiers = qualifiers;
    }

    public boolean isSetQualifiers() {
        return this.qualifiers != null;
    }

    public LowestOfferListingType withQualifiers(QualifiersType qualifiers) {
        this.qualifiers = qualifiers;
        return this;
    }

    public Integer getNumberOfOfferListingsConsidered() {
        return this.numberOfOfferListingsConsidered;
    }

    public void setNumberOfOfferListingsConsidered(Integer numberOfOfferListingsConsidered) {
        this.numberOfOfferListingsConsidered = numberOfOfferListingsConsidered;
    }

    public boolean isSetNumberOfOfferListingsConsidered() {
        return this.numberOfOfferListingsConsidered != null;
    }

    public LowestOfferListingType withNumberOfOfferListingsConsidered(Integer numberOfOfferListingsConsidered) {
        this.numberOfOfferListingsConsidered = numberOfOfferListingsConsidered;
        return this;
    }

    public int getSellerFeedbackCount() {
        return this.sellerFeedbackCount;
    }

    public void setSellerFeedbackCount(int sellerFeedbackCount) {
        this.sellerFeedbackCount = sellerFeedbackCount;
    }

    public boolean isSetSellerFeedbackCount() {
        return true;
    }

    public LowestOfferListingType withSellerFeedbackCount(int sellerFeedbackCount) {
        this.sellerFeedbackCount = sellerFeedbackCount;
        return this;
    }

    public PriceType getPrice() {
        return this.price;
    }

    public void setPrice(PriceType price) {
        this.price = price;
    }

    public boolean isSetPrice() {
        return this.price != null;
    }

    public LowestOfferListingType withPrice(PriceType price) {
        this.price = price;
        return this;
    }

    public String getMultipleOffersAtLowestPrice() {
        return this.multipleOffersAtLowestPrice;
    }

    public void setMultipleOffersAtLowestPrice(String multipleOffersAtLowestPrice) {
        this.multipleOffersAtLowestPrice = multipleOffersAtLowestPrice;
    }

    public boolean isSetMultipleOffersAtLowestPrice() {
        return this.multipleOffersAtLowestPrice != null;
    }

    public LowestOfferListingType withMultipleOffersAtLowestPrice(String multipleOffersAtLowestPrice) {
        this.multipleOffersAtLowestPrice = multipleOffersAtLowestPrice;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.qualifiers = (QualifiersType)r.read("Qualifiers", QualifiersType.class);
        this.numberOfOfferListingsConsidered = (Integer)r.read("NumberOfOfferListingsConsidered", Integer.class);
        this.sellerFeedbackCount = (Integer)r.read("SellerFeedbackCount", Integer.TYPE);
        this.price = (PriceType)r.read("Price", PriceType.class);
        this.multipleOffersAtLowestPrice = (String)r.read("MultipleOffersAtLowestPrice", String.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.write("Qualifiers", this.qualifiers);
        w.write("NumberOfOfferListingsConsidered", this.numberOfOfferListingsConsidered);
        w.write("SellerFeedbackCount", this.sellerFeedbackCount);
        w.write("Price", this.price);
        w.write("MultipleOffersAtLowestPrice", this.multipleOffersAtLowestPrice);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "LowestOfferListingType", this);
    }

    public LowestOfferListingType(QualifiersType qualifiers, int sellerFeedbackCount, PriceType price, String multipleOffersAtLowestPrice) {
        this.qualifiers = qualifiers;
        this.sellerFeedbackCount = sellerFeedbackCount;
        this.price = price;
        this.multipleOffersAtLowestPrice = multipleOffersAtLowestPrice;
    }

    public LowestOfferListingType() {
    }
}

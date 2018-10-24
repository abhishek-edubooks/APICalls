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
        name = "PriceType",
        propOrder = {"landedPrice", "listingPrice", "shipping", "points"}
)
@XmlRootElement(
        name = "PriceType"
)
public class PriceType extends AbstractMwsObject {
    @XmlElement(
            name = "LandedPrice"
    )
    private MoneyType landedPrice;
    @XmlElement(
            name = "ListingPrice",
            required = true
    )
    private MoneyType listingPrice;
    @XmlElement(
            name = "Shipping"
    )
    private MoneyType shipping;
    @XmlElement(
            name = "Points"
    )
    private Points points;

    public MoneyType getLandedPrice() {
        return this.landedPrice;
    }

    public void setLandedPrice(MoneyType landedPrice) {
        this.landedPrice = landedPrice;
    }

    public boolean isSetLandedPrice() {
        return this.landedPrice != null;
    }

    public PriceType withLandedPrice(MoneyType landedPrice) {
        this.landedPrice = landedPrice;
        return this;
    }

    public MoneyType getListingPrice() {
        return this.listingPrice;
    }

    public void setListingPrice(MoneyType listingPrice) {
        this.listingPrice = listingPrice;
    }

    public boolean isSetListingPrice() {
        return this.listingPrice != null;
    }

    public PriceType withListingPrice(MoneyType listingPrice) {
        this.listingPrice = listingPrice;
        return this;
    }

    public MoneyType getShipping() {
        return this.shipping;
    }

    public void setShipping(MoneyType shipping) {
        this.shipping = shipping;
    }

    public boolean isSetShipping() {
        return this.shipping != null;
    }

    public PriceType withShipping(MoneyType shipping) {
        this.shipping = shipping;
        return this;
    }

    public Points getPoints() {
        return this.points;
    }

    public void setPoints(Points points) {
        this.points = points;
    }

    public boolean isSetPoints() {
        return this.points != null;
    }

    public PriceType withPoints(Points points) {
        this.points = points;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.landedPrice = (MoneyType)r.read("LandedPrice", MoneyType.class);
        this.listingPrice = (MoneyType)r.read("ListingPrice", MoneyType.class);
        this.shipping = (MoneyType)r.read("Shipping", MoneyType.class);
        this.points = (Points)r.read("Points", Points.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.write("LandedPrice", this.landedPrice);
        w.write("ListingPrice", this.listingPrice);
        w.write("Shipping", this.shipping);
        w.write("Points", this.points);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "PriceType", this);
    }

    public PriceType(MoneyType listingPrice) {
        this.listingPrice = listingPrice;
    }

    public PriceType() {
    }
}

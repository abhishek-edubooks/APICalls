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
        name = "IdentifierType",
        propOrder = {"marketplaceASIN", "skuIdentifier"}
)
@XmlRootElement(
        name = "IdentifierType"
)
public class IdentifierType extends AbstractMwsObject {
    @XmlElement(
            name = "MarketplaceASIN",
            required = true
    )
    private ASINIdentifier marketplaceASIN;
    @XmlElement(
            name = "SKUIdentifier"
    )
    private SellerSKUIdentifier skuIdentifier;

    public ASINIdentifier getMarketplaceASIN() {
        return this.marketplaceASIN;
    }

    public void setMarketplaceASIN(ASINIdentifier marketplaceASIN) {
        this.marketplaceASIN = marketplaceASIN;
    }

    public boolean isSetMarketplaceASIN() {
        return this.marketplaceASIN != null;
    }

    public IdentifierType withMarketplaceASIN(ASINIdentifier marketplaceASIN) {
        this.marketplaceASIN = marketplaceASIN;
        return this;
    }

    public SellerSKUIdentifier getSKUIdentifier() {
        return this.skuIdentifier;
    }

    public void setSKUIdentifier(SellerSKUIdentifier skuIdentifier) {
        this.skuIdentifier = skuIdentifier;
    }

    public boolean isSetSKUIdentifier() {
        return this.skuIdentifier != null;
    }

    public IdentifierType withSKUIdentifier(SellerSKUIdentifier skuIdentifier) {
        this.skuIdentifier = skuIdentifier;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.marketplaceASIN = (ASINIdentifier)r.read("MarketplaceASIN", ASINIdentifier.class);
        this.skuIdentifier = (SellerSKUIdentifier)r.read("SKUIdentifier", SellerSKUIdentifier.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.write("MarketplaceASIN", this.marketplaceASIN);
        w.write("SKUIdentifier", this.skuIdentifier);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "IdentifierType", this);
    }

    public IdentifierType(ASINIdentifier marketplaceASIN) {
        this.marketplaceASIN = marketplaceASIN;
    }

    public IdentifierType() {
    }
}

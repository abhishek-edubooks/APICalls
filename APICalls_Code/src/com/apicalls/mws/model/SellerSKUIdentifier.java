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
        name = "SellerSKUIdentifier",
        propOrder = {"marketplaceId", "sellerId", "sellerSKU"}
)
@XmlRootElement(
        name = "SellerSKUIdentifier"
)
public class SellerSKUIdentifier extends AbstractMwsObject {
    @XmlElement(
            name = "MarketplaceId",
            required = true
    )
    private String marketplaceId;
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

    public String getMarketplaceId() {
        return this.marketplaceId;
    }

    public void setMarketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    public boolean isSetMarketplaceId() {
        return this.marketplaceId != null;
    }

    public SellerSKUIdentifier withMarketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
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

    public SellerSKUIdentifier withSellerId(String sellerId) {
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

    public SellerSKUIdentifier withSellerSKU(String sellerSKU) {
        this.sellerSKU = sellerSKU;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.marketplaceId = (String)r.read("MarketplaceId", String.class);
        this.sellerId = (String)r.read("SellerId", String.class);
        this.sellerSKU = (String)r.read("SellerSKU", String.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.write("MarketplaceId", this.marketplaceId);
        w.write("SellerId", this.sellerId);
        w.write("SellerSKU", this.sellerSKU);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "SellerSKUIdentifier", this);
    }

    public SellerSKUIdentifier(String marketplaceId, String sellerId, String sellerSKU) {
        this.marketplaceId = marketplaceId;
        this.sellerId = sellerId;
        this.sellerSKU = sellerSKU;
    }

    public SellerSKUIdentifier() {
    }
}

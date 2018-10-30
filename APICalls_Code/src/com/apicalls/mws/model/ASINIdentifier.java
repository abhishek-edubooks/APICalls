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
        name = "ASINIdentifier",
        propOrder = {"marketplaceId", "asin"}
)
@XmlRootElement(
        name = "ASINIdentifier"
)
public class ASINIdentifier extends AbstractMwsObject {
    @XmlElement(
            name = "MarketplaceId",
            required = true
    )
    private String marketplaceId;
    @XmlElement(
            name = "ASIN",
            required = true
    )
    private String asin;

    public String getMarketplaceId() {
        return this.marketplaceId;
    }

    public void setMarketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    public boolean isSetMarketplaceId() {
        return this.marketplaceId != null;
    }

    public ASINIdentifier withMarketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
        return this;
    }

    public String getASIN() {
        return this.asin;
    }

    public void setASIN(String asin) {
        this.asin = asin;
    }

    public boolean isSetASIN() {
        return this.asin != null;
    }

    public ASINIdentifier withASIN(String asin) {
        this.asin = asin;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.marketplaceId = (String)r.read("MarketplaceId", String.class);
        this.asin = (String)r.read("ASIN", String.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.write("MarketplaceId", this.marketplaceId);
        w.write("ASIN", this.asin);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "ASINIdentifier", this);
    }

    public ASINIdentifier(String marketplaceId, String asin) {
        this.marketplaceId = marketplaceId;
        this.asin = asin;
    }

    public ASINIdentifier() {
    }
}

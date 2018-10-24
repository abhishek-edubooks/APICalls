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
        name = "CompetitivePricingType",
        propOrder = {"competitivePrices", "numberOfOfferListings", "tradeInValue"}
)
@XmlRootElement(
        name = "CompetitivePricingType"
)
public class CompetitivePricingType extends AbstractMwsObject {
    @XmlElement(
            name = "CompetitivePrices",
            required = true
    )
    private CompetitivePriceList competitivePrices;
    @XmlElement(
            name = "NumberOfOfferListings",
            required = true
    )
    private NumberOfOfferListingsList numberOfOfferListings;
    @XmlElement(
            name = "TradeInValue"
    )
    private MoneyType tradeInValue;

    public CompetitivePriceList getCompetitivePrices() {
        return this.competitivePrices;
    }

    public void setCompetitivePrices(CompetitivePriceList competitivePrices) {
        this.competitivePrices = competitivePrices;
    }

    public boolean isSetCompetitivePrices() {
        return this.competitivePrices != null;
    }

    public CompetitivePricingType withCompetitivePrices(CompetitivePriceList competitivePrices) {
        this.competitivePrices = competitivePrices;
        return this;
    }

    public NumberOfOfferListingsList getNumberOfOfferListings() {
        return this.numberOfOfferListings;
    }

    public void setNumberOfOfferListings(NumberOfOfferListingsList numberOfOfferListings) {
        this.numberOfOfferListings = numberOfOfferListings;
    }

    public boolean isSetNumberOfOfferListings() {
        return this.numberOfOfferListings != null;
    }

    public CompetitivePricingType withNumberOfOfferListings(NumberOfOfferListingsList numberOfOfferListings) {
        this.numberOfOfferListings = numberOfOfferListings;
        return this;
    }

    public MoneyType getTradeInValue() {
        return this.tradeInValue;
    }

    public void setTradeInValue(MoneyType tradeInValue) {
        this.tradeInValue = tradeInValue;
    }

    public boolean isSetTradeInValue() {
        return this.tradeInValue != null;
    }

    public CompetitivePricingType withTradeInValue(MoneyType tradeInValue) {
        this.tradeInValue = tradeInValue;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.competitivePrices = (CompetitivePriceList)r.read("CompetitivePrices", CompetitivePriceList.class);
        this.numberOfOfferListings = (NumberOfOfferListingsList)r.read("NumberOfOfferListings", NumberOfOfferListingsList.class);
        this.tradeInValue = (MoneyType)r.read("TradeInValue", MoneyType.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.write("CompetitivePrices", this.competitivePrices);
        w.write("NumberOfOfferListings", this.numberOfOfferListings);
        w.write("TradeInValue", this.tradeInValue);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "CompetitivePricingType", this);
    }

    public CompetitivePricingType(CompetitivePriceList competitivePrices, NumberOfOfferListingsList numberOfOfferListings) {
        this.competitivePrices = competitivePrices;
        this.numberOfOfferListings = numberOfOfferListings;
    }

    public CompetitivePricingType() {
    }
}

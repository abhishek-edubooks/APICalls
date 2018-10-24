package com.apicalls.mws.model;

import com.amazonservices.mws.client.MwsReader;
import com.amazonservices.mws.client.MwsWriter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "OfferListingCountType",
        propOrder = {"value"}
)
@XmlRootElement(
        name = "OfferListingCountType"
)
public class OfferListingCountType {
    @XmlValue
    private int value;
    @XmlAttribute(
            required = true
    )
    private String condition;

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isSetValue() {
        return true;
    }

    public OfferListingCountType withValue(int value) {
        this.value = value;
        return this;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isSetCondition() {
        return this.condition != null;
    }

    public OfferListingCountType withCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.condition = (String)r.readAttribute("condition", String.class);
        this.value = (Integer)r.readValue(Integer.TYPE);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.writeAttribute("condition", this.condition);
        w.writeValue(this.value);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "OfferListingCountType");
    }

    public OfferListingCountType(int value, String condition) {
        this.value = value;
        this.condition = condition;
    }

    public OfferListingCountType() {
    }
}

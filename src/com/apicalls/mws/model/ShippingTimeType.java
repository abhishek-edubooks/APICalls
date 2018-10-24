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
        name = "ShippingTimeType",
        propOrder = {"max"}
)
@XmlRootElement(
        name = "ShippingTimeType"
)
public class ShippingTimeType extends AbstractMwsObject {
    @XmlElement(
            name = "Max"
    )
    private String max;

    public String getMax() {
        return this.max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public boolean isSetMax() {
        return this.max != null;
    }

    public ShippingTimeType withMax(String max) {
        this.max = max;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.max = (String)r.read("Max", String.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.write("Max", this.max);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "ShippingTimeType", this);
    }

    public ShippingTimeType() {
    }
}

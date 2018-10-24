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
        name = "Points",
        propOrder = {"pointsNumber", "pointsMonetaryValue"}
)
@XmlRootElement(
        name = "Points"
)
public class Points extends AbstractMwsObject {
    @XmlElement(
            name = "PointsNumber"
    )
    private Integer pointsNumber;
    @XmlElement(
            name = "PointsMonetaryValue"
    )
    private MoneyType pointsMonetaryValue;

    public Integer getPointsNumber() {
        return this.pointsNumber;
    }

    public void setPointsNumber(Integer pointsNumber) {
        this.pointsNumber = pointsNumber;
    }

    public boolean isSetPointsNumber() {
        return this.pointsNumber != null;
    }

    public Points withPointsNumber(Integer pointsNumber) {
        this.pointsNumber = pointsNumber;
        return this;
    }

    public MoneyType getPointsMonetaryValue() {
        return this.pointsMonetaryValue;
    }

    public void setPointsMonetaryValue(MoneyType pointsMonetaryValue) {
        this.pointsMonetaryValue = pointsMonetaryValue;
    }

    public boolean isSetPointsMonetaryValue() {
        return this.pointsMonetaryValue != null;
    }

    public Points withPointsMonetaryValue(MoneyType pointsMonetaryValue) {
        this.pointsMonetaryValue = pointsMonetaryValue;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.pointsNumber = (Integer)r.read("PointsNumber", Integer.class);
        this.pointsMonetaryValue = (MoneyType)r.read("PointsMonetaryValue", MoneyType.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.write("PointsNumber", this.pointsNumber);
        w.write("PointsMonetaryValue", this.pointsMonetaryValue);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "Points", this);
    }

    public Points() {
    }
}

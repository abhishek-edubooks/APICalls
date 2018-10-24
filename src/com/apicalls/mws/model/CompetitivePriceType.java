package com.apicalls.mws.model;

import com.amazonservices.mws.client.AbstractMwsObject;
import com.amazonservices.mws.client.MwsReader;
import com.amazonservices.mws.client.MwsWriter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "CompetitivePriceType",
        propOrder = {"competitivePriceId", "price"}
)
@XmlRootElement(
        name = "CompetitivePriceType"
)
public class CompetitivePriceType extends AbstractMwsObject {
    @XmlElement(
            name = "CompetitivePriceId",
            required = true
    )
    private String competitivePriceId;
    @XmlElement(
            name = "Price",
            required = true
    )
    private PriceType price;
    @XmlAttribute
    private String condition;
    @XmlAttribute
    private String subcondition;
    @XmlAttribute
    private Boolean belongsToRequester;

    public String getCompetitivePriceId() {
        return this.competitivePriceId;
    }

    public void setCompetitivePriceId(String competitivePriceId) {
        this.competitivePriceId = competitivePriceId;
    }

    public boolean isSetCompetitivePriceId() {
        return this.competitivePriceId != null;
    }

    public CompetitivePriceType withCompetitivePriceId(String competitivePriceId) {
        this.competitivePriceId = competitivePriceId;
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

    public CompetitivePriceType withPrice(PriceType price) {
        this.price = price;
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

    public CompetitivePriceType withCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public String getSubcondition() {
        return this.subcondition;
    }

    public void setSubcondition(String subcondition) {
        this.subcondition = subcondition;
    }

    public boolean isSetSubcondition() {
        return this.subcondition != null;
    }

    public CompetitivePriceType withSubcondition(String subcondition) {
        this.subcondition = subcondition;
        return this;
    }

    public boolean isBelongsToRequester() {
        return this.belongsToRequester != null && this.belongsToRequester;
    }

    public Boolean getBelongsToRequester() {
        return this.belongsToRequester;
    }

    public void setBelongsToRequester(Boolean belongsToRequester) {
        this.belongsToRequester = belongsToRequester;
    }

    public boolean isSetBelongsToRequester() {
        return this.belongsToRequester != null;
    }

    public void setBelongsToRequester(boolean value) {
        this.belongsToRequester = value;
    }

    public void unsetBelongsToRequester() {
        this.belongsToRequester = null;
    }

    public CompetitivePriceType withBelongsToRequester(boolean value) {
        this.belongsToRequester = value;
        return this;
    }

    public CompetitivePriceType withBelongsToRequester(Boolean belongsToRequester) {
        this.belongsToRequester = belongsToRequester;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.condition = (String)r.readAttribute("condition", String.class);
        this.subcondition = (String)r.readAttribute("subcondition", String.class);
        this.belongsToRequester = (Boolean)r.readAttribute("belongsToRequester", Boolean.class);
        this.competitivePriceId = (String)r.read("CompetitivePriceId", String.class);
        this.price = (PriceType)r.read("Price", PriceType.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.writeAttribute("condition", this.condition);
        w.writeAttribute("subcondition", this.subcondition);
        w.writeAttribute("belongsToRequester", this.belongsToRequester);
        w.write("CompetitivePriceId", this.competitivePriceId);
        w.write("Price", this.price);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "CompetitivePriceType", this);
    }

    public CompetitivePriceType(String competitivePriceId, PriceType price) {
        this.competitivePriceId = competitivePriceId;
        this.price = price;
    }

    public CompetitivePriceType() {
    }
}

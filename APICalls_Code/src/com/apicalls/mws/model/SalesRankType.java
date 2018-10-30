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
        name = "SalesRankType",
        propOrder = {"productCategoryId", "rank"}
)
@XmlRootElement(
        name = "SalesRankType"
)
public class SalesRankType extends AbstractMwsObject {
    @XmlElement(
            name = "ProductCategoryId",
            required = true
    )
    private String productCategoryId;
    @XmlElement(
            name = "Rank",
            required = true
    )
    private int rank;

    public String getProductCategoryId() {
        return this.productCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public boolean isSetProductCategoryId() {
        return this.productCategoryId != null;
    }

    public SalesRankType withProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
        return this;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isSetRank() {
        return true;
    }

    public SalesRankType withRank(int rank) {
        this.rank = rank;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.productCategoryId = (String)r.read("ProductCategoryId", String.class);
        this.rank = (Integer)r.read("Rank", Integer.TYPE);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.write("ProductCategoryId", this.productCategoryId);
        w.write("Rank", this.rank);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "SalesRankType", this);
    }

    public SalesRankType(String productCategoryId, int rank) {
        this.productCategoryId = productCategoryId;
        this.rank = rank;
    }

    public SalesRankType() {
    }
}

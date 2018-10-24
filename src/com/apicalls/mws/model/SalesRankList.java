package com.apicalls.mws.model;

import com.amazonservices.mws.client.AbstractMwsObject;
import com.amazonservices.mws.client.MwsReader;
import com.amazonservices.mws.client.MwsWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "SalesRankList",
        propOrder = {"salesRank"}
)
@XmlRootElement(
        name = "SalesRankList"
)
public class SalesRankList extends AbstractMwsObject {
    @XmlElement(
            name = "SalesRank"
    )
    private List<SalesRankType> salesRank;

    public List<SalesRankType> getSalesRank() {
        if (this.salesRank == null) {
            this.salesRank = new ArrayList();
        }

        return this.salesRank;
    }

    public void setSalesRank(List<SalesRankType> salesRank) {
        this.salesRank = salesRank;
    }

    public void unsetSalesRank() {
        this.salesRank = null;
    }

    public boolean isSetSalesRank() {
        return this.salesRank != null && !this.salesRank.isEmpty();
    }

    public SalesRankList withSalesRank(SalesRankType... values) {
        List<SalesRankType> list = this.getSalesRank();
        SalesRankType[] arr$ = values;
        int len$ = values.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            SalesRankType value = arr$[i$];
            list.add(value);
        }

        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.salesRank = r.readList("SalesRank", SalesRankType.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.writeList("SalesRank", this.salesRank);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "SalesRankList", this);
    }

    public SalesRankList() {
    }
}

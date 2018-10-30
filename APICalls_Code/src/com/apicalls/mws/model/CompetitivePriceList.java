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
        name = "CompetitivePriceList",
        propOrder = {"competitivePrice"}
)
@XmlRootElement(
        name = "CompetitivePriceList"
)
public class CompetitivePriceList extends AbstractMwsObject {
    @XmlElement(
            name = "CompetitivePrice"
    )
    private List<CompetitivePriceType> competitivePrice;

    public List<CompetitivePriceType> getCompetitivePrice() {
        if (this.competitivePrice == null) {
            this.competitivePrice = new ArrayList();
        }

        return this.competitivePrice;
    }

    public void setCompetitivePrice(List<CompetitivePriceType> competitivePrice) {
        this.competitivePrice = competitivePrice;
    }

    public void unsetCompetitivePrice() {
        this.competitivePrice = null;
    }

    public boolean isSetCompetitivePrice() {
        return this.competitivePrice != null && !this.competitivePrice.isEmpty();
    }

    public CompetitivePriceList withCompetitivePrice(CompetitivePriceType... values) {
        List<CompetitivePriceType> list = this.getCompetitivePrice();
        CompetitivePriceType[] arr$ = values;
        int len$ = values.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            CompetitivePriceType value = arr$[i$];
            list.add(value);
        }

        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.competitivePrice = r.readList("CompetitivePrice", CompetitivePriceType.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.writeList("CompetitivePrice", this.competitivePrice);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "CompetitivePriceList", this);
    }

    public CompetitivePriceList() {
    }
}

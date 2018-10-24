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
        name = "NumberOfOfferListingsList",
        propOrder = {"offerListingCount"}
)
@XmlRootElement(
        name = "NumberOfOfferListingsList"
)
public class NumberOfOfferListingsList extends AbstractMwsObject {
    @XmlElement(
            name = "OfferListingCount"
    )
    private List<OfferListingCountType> offerListingCount;

    public List<OfferListingCountType> getOfferListingCount() {
        if (this.offerListingCount == null) {
            this.offerListingCount = new ArrayList();
        }

        return this.offerListingCount;
    }

    public void setOfferListingCount(List<OfferListingCountType> offerListingCount) {
        this.offerListingCount = offerListingCount;
    }

    public void unsetOfferListingCount() {
        this.offerListingCount = null;
    }

    public boolean isSetOfferListingCount() {
        return this.offerListingCount != null && !this.offerListingCount.isEmpty();
    }

    public NumberOfOfferListingsList withOfferListingCount(OfferListingCountType... values) {
        List<OfferListingCountType> list = this.getOfferListingCount();
        OfferListingCountType[] arr$ = values;
        int len$ = values.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            OfferListingCountType value = arr$[i$];
            list.add(value);
        }

        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.offerListingCount = r.readList("OfferListingCount", OfferListingCountType.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.writeList("OfferListingCount", this.offerListingCount);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "NumberOfOfferListingsList", this);
    }

    public NumberOfOfferListingsList() {
    }
}

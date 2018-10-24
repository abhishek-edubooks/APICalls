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
        name = "LowestOfferListingList",
        propOrder = {"lowestOfferListing"}
)
@XmlRootElement(
        name = "LowestOfferListingList"
)
public class LowestOfferListingList extends AbstractMwsObject {
    @XmlElement(
            name = "LowestOfferListing"
    )
    private List<LowestOfferListingType> lowestOfferListing;

    public List<LowestOfferListingType> getLowestOfferListing() {
        if (this.lowestOfferListing == null) {
            this.lowestOfferListing = new ArrayList();
        }

        return this.lowestOfferListing;
    }

    public void setLowestOfferListing(List<LowestOfferListingType> lowestOfferListing) {
        this.lowestOfferListing = lowestOfferListing;
    }

    public void unsetLowestOfferListing() {
        this.lowestOfferListing = null;
    }

    public boolean isSetLowestOfferListing() {
        return this.lowestOfferListing != null && !this.lowestOfferListing.isEmpty();
    }

    public LowestOfferListingList withLowestOfferListing(LowestOfferListingType... values) {
        List<LowestOfferListingType> list = this.getLowestOfferListing();
        LowestOfferListingType[] arr$ = values;
        int len$ = values.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            LowestOfferListingType value = arr$[i$];
            list.add(value);
        }

        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.lowestOfferListing = r.readList("LowestOfferListing", LowestOfferListingType.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.writeList("LowestOfferListing", this.lowestOfferListing);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "LowestOfferListingList", this);
    }

    public LowestOfferListingList() {
    }
}

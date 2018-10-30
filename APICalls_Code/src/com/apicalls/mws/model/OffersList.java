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
        name = "OffersList",
        propOrder = {"offer"}
)
@XmlRootElement(
        name = "OffersList"
)
public class OffersList extends AbstractMwsObject {
    @XmlElement(
            name = "Offer"
    )
    private List<OfferType> offer;

    public List<OfferType> getOffer() {
        if (this.offer == null) {
            this.offer = new ArrayList();
        }

        return this.offer;
    }

    public void setOffer(List<OfferType> offer) {
        this.offer = offer;
    }

    public void unsetOffer() {
        this.offer = null;
    }

    public boolean isSetOffer() {
        return this.offer != null && !this.offer.isEmpty();
    }

    public OffersList withOffer(OfferType... values) {
        List<OfferType> list = this.getOffer();
        OfferType[] arr$ = values;
        int len$ = values.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            OfferType value = arr$[i$];
            list.add(value);
        }

        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.offer = r.readList("Offer", OfferType.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.writeList("Offer", this.offer);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "OffersList", this);
    }

    public OffersList() {
    }
}

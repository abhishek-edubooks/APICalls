package com.apicalls.mws.model;

import com.amazonservices.mws.client.MwsReader;
import com.amazonservices.mws.client.MwsWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "GetLowestOfferListingsForASINResponse",
        propOrder = {"getLowestOfferListingsForASINResult", "responseMetadata"}
)
@XmlRootElement(
        name = "GetLowestOfferListingsForASINResponse"
)
public class GetLowestOfferListingsForASINResponse {
    @XmlElement(
            name = "GetLowestOfferListingsForASINResult"
    )
    private List<GetLowestOfferListingsForASINResult> getLowestOfferListingsForASINResult;
    @XmlElement(
            name = "ResponseMetadata"
    )
    private ResponseMetadata responseMetadata;
    @XmlTransient
    private ResponseHeaderMetadata responseHeaderMetadata;

    public List<GetLowestOfferListingsForASINResult> getGetLowestOfferListingsForASINResult() {
        if (this.getLowestOfferListingsForASINResult == null) {
            this.getLowestOfferListingsForASINResult = new ArrayList();
        }

        return this.getLowestOfferListingsForASINResult;
    }

    public void setGetLowestOfferListingsForASINResult(List<GetLowestOfferListingsForASINResult> getLowestOfferListingsForASINResult) {
        this.getLowestOfferListingsForASINResult = getLowestOfferListingsForASINResult;
    }

    public void unsetGetLowestOfferListingsForASINResult() {
        this.getLowestOfferListingsForASINResult = null;
    }

    public boolean isSetGetLowestOfferListingsForASINResult() {
        return this.getLowestOfferListingsForASINResult != null && !this.getLowestOfferListingsForASINResult.isEmpty();
    }

    public GetLowestOfferListingsForASINResponse withGetLowestOfferListingsForASINResult(GetLowestOfferListingsForASINResult... values) {
        List<GetLowestOfferListingsForASINResult> list = this.getGetLowestOfferListingsForASINResult();
        GetLowestOfferListingsForASINResult[] arr$ = values;
        int len$ = values.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            GetLowestOfferListingsForASINResult value = arr$[i$];
            list.add(value);
        }

        return this;
    }

    public ResponseMetadata getResponseMetadata() {
        return this.responseMetadata;
    }

    public void setResponseMetadata(ResponseMetadata responseMetadata) {
        this.responseMetadata = responseMetadata;
    }

    public boolean isSetResponseMetadata() {
        return this.responseMetadata != null;
    }

    public GetLowestOfferListingsForASINResponse withResponseMetadata(ResponseMetadata responseMetadata) {
        this.responseMetadata = responseMetadata;
        return this;
    }

    public ResponseHeaderMetadata getResponseHeaderMetadata() {
        return this.responseHeaderMetadata;
    }

    public void setResponseHeaderMetadata(ResponseHeaderMetadata responseHeaderMetadata) {
        this.responseHeaderMetadata = responseHeaderMetadata;
    }

    public boolean isSetResponseHeaderMetadata() {
        return this.responseHeaderMetadata != null;
    }

    public GetLowestOfferListingsForASINResponse withResponseHeaderMetadata(ResponseHeaderMetadata responseHeaderMetadata) {
        this.responseHeaderMetadata = responseHeaderMetadata;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.getLowestOfferListingsForASINResult = r.readList("GetLowestOfferListingsForASINResult", GetLowestOfferListingsForASINResult.class);
        this.responseMetadata = (ResponseMetadata)r.read("ResponseMetadata", ResponseMetadata.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.writeList("GetLowestOfferListingsForASINResult", this.getLowestOfferListingsForASINResult);
        w.write("ResponseMetadata", this.responseMetadata);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "GetLowestOfferListingsForASINResponse");
    }

    public GetLowestOfferListingsForASINResponse() {
    }
}

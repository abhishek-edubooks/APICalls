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
        name = "GetCompetitivePricingForASINResponse",
        propOrder = {"getCompetitivePricingForASINResult", "responseMetadata"}
)
@XmlRootElement(
        name = "GetCompetitivePricingForASINResponse"
)
public class GetCompetitivePricingForASINResponse {
    @XmlElement(
            name = "GetCompetitivePricingForASINResult"
    )
    private List<GetCompetitivePricingForASINResult> getCompetitivePricingForASINResult;
    @XmlElement(
            name = "ResponseMetadata"
    )
    private ResponseMetadata responseMetadata;
    @XmlTransient
    private ResponseHeaderMetadata responseHeaderMetadata;

    public List<GetCompetitivePricingForASINResult> getGetCompetitivePricingForASINResult() {
        if (this.getCompetitivePricingForASINResult == null) {
            this.getCompetitivePricingForASINResult = new ArrayList();
        }

        return this.getCompetitivePricingForASINResult;
    }

    public void setGetCompetitivePricingForASINResult(List<GetCompetitivePricingForASINResult> getCompetitivePricingForASINResult) {
        this.getCompetitivePricingForASINResult = getCompetitivePricingForASINResult;
    }

    public void unsetGetCompetitivePricingForASINResult() {
        this.getCompetitivePricingForASINResult = null;
    }

    public boolean isSetGetCompetitivePricingForASINResult() {
        return this.getCompetitivePricingForASINResult != null && !this.getCompetitivePricingForASINResult.isEmpty();
    }

    public GetCompetitivePricingForASINResponse withGetCompetitivePricingForASINResult(GetCompetitivePricingForASINResult... values) {
        List<GetCompetitivePricingForASINResult> list = this.getGetCompetitivePricingForASINResult();
        GetCompetitivePricingForASINResult[] arr$ = values;
        int len$ = values.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            GetCompetitivePricingForASINResult value = arr$[i$];
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

    public GetCompetitivePricingForASINResponse withResponseMetadata(ResponseMetadata responseMetadata) {
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

    public GetCompetitivePricingForASINResponse withResponseHeaderMetadata(ResponseHeaderMetadata responseHeaderMetadata) {
        this.responseHeaderMetadata = responseHeaderMetadata;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.getCompetitivePricingForASINResult = r.readList("GetCompetitivePricingForASINResult", GetCompetitivePricingForASINResult.class);
        this.responseMetadata = (ResponseMetadata)r.read("ResponseMetadata", ResponseMetadata.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.writeList("GetCompetitivePricingForASINResult", this.getCompetitivePricingForASINResult);
        w.write("ResponseMetadata", this.responseMetadata);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "GetCompetitivePricingForASINResponse");
    }

    public GetCompetitivePricingForASINResponse() {
    }
}


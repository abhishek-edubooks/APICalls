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
        name = "ResponseMetadata",
        propOrder = {"requestId"}
)
@XmlRootElement(
        name = "ResponseMetadata"
)
public class ResponseMetadata extends AbstractMwsObject {
    @XmlElement(
            name = "RequestId"
    )
    private String requestId;

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isSetRequestId() {
        return this.requestId != null;
    }

    public ResponseMetadata withRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.requestId = (String)r.read("RequestId", String.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.write("RequestId", this.requestId);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "ResponseMetadata", this);
    }

    public ResponseMetadata() {
    }
}

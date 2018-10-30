package com.apicalls.mws.model;

import com.amazonservices.mws.client.MwsResponseHeaderMetadata;
import java.util.Date;
import java.util.List;

public class ResponseHeaderMetadata extends MwsResponseHeaderMetadata {
    public ResponseHeaderMetadata(String requestId, List<String> responseContext, String timestamp, Double quotaMax, Double quotaRemaining, Date quotaResetsAt) {
        super(requestId, responseContext, timestamp, quotaMax, quotaRemaining, quotaResetsAt);
    }

    public ResponseHeaderMetadata() {
        super((String)null, (List)null, (String)null, (Double)null, (Double)null, (Date)null);
    }

    public ResponseHeaderMetadata(MwsResponseHeaderMetadata rhmd) {
        super(rhmd);
    }
}

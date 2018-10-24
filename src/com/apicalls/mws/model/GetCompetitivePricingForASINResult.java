package com.apicalls.mws.model;

import com.amazonservices.mws.client.MwsReader;
import com.amazonservices.mws.client.MwsWriter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "GetCompetitivePricingForASINResult",
        propOrder = {"product", "error"}
)
@XmlRootElement(
        name = "GetCompetitivePricingForASINResult"
)
public class GetCompetitivePricingForASINResult {
    @XmlElement(
            name = "Product"
    )
    private Product product;
    @XmlElement(
            name = "Error"
    )
    private Error error;
    @XmlAttribute
    private String asin;
    @XmlAttribute(
            required = true
    )
    private String status;

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isSetProduct() {
        return this.product != null;
    }

    public GetCompetitivePricingForASINResult withProduct(Product product) {
        this.product = product;
        return this;
    }

    public Error getError() {
        return this.error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public boolean isSetError() {
        return this.error != null;
    }

    public GetCompetitivePricingForASINResult withError(Error error) {
        this.error = error;
        return this;
    }

    public String getASIN() {
        return this.asin;
    }

    public void setASIN(String asin) {
        this.asin = asin;
    }

    public boolean isSetASIN() {
        return this.asin != null;
    }

    public GetCompetitivePricingForASINResult withASIN(String asin) {
        this.asin = asin;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSetStatus() {
        return this.status != null;
    }

    public GetCompetitivePricingForASINResult withStatus(String status) {
        this.status = status;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.asin = (String)r.readAttribute("ASIN", String.class);
        this.status = (String)r.readAttribute("status", String.class);
        this.product = (Product)r.read("Product", Product.class);
        this.error = (Error)r.read("Error", Error.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.writeAttribute("ASIN", this.asin);
        w.writeAttribute("status", this.status);
        w.write("Product", this.product);
        w.write("Error", this.error);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "GetCompetitivePricingForASINResult");
    }

    public GetCompetitivePricingForASINResult(String status) {
        this.status = status;
    }

    public GetCompetitivePricingForASINResult() {
    }
}

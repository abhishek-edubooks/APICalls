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
        name = "GetLowestOfferListingsForASINResult",
        propOrder = {"allOfferListingsConsidered", "product", "error"}
)
@XmlRootElement(
        name = "GetLowestOfferListingsForASINResult"
)
public class GetLowestOfferListingsForASINResult {
    @XmlElement(
            name = "AllOfferListingsConsidered"
    )
    private Boolean allOfferListingsConsidered;
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

    public boolean isAllOfferListingsConsidered() {
        return this.allOfferListingsConsidered != null && this.allOfferListingsConsidered;
    }

    public Boolean getAllOfferListingsConsidered() {
        return this.allOfferListingsConsidered;
    }

    public void setAllOfferListingsConsidered(Boolean allOfferListingsConsidered) {
        this.allOfferListingsConsidered = allOfferListingsConsidered;
    }

    public boolean isSetAllOfferListingsConsidered() {
        return this.allOfferListingsConsidered != null;
    }

    public GetLowestOfferListingsForASINResult withAllOfferListingsConsidered(Boolean allOfferListingsConsidered) {
        this.allOfferListingsConsidered = allOfferListingsConsidered;
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isSetProduct() {
        return this.product != null;
    }

    public GetLowestOfferListingsForASINResult withProduct(Product product) {
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

    public GetLowestOfferListingsForASINResult withError(Error error) {
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

    public GetLowestOfferListingsForASINResult withASIN(String asin) {
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

    public GetLowestOfferListingsForASINResult withStatus(String status) {
        this.status = status;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.asin = (String)r.readAttribute("ASIN", String.class);
        this.status = (String)r.readAttribute("status", String.class);
        this.allOfferListingsConsidered = (Boolean)r.read("AllOfferListingsConsidered", Boolean.class);
        this.product = (Product)r.read("Product", Product.class);
        this.error = (Error)r.read("Error", Error.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.writeAttribute("ASIN", this.asin);
        w.writeAttribute("status", this.status);
        w.write("AllOfferListingsConsidered", this.allOfferListingsConsidered);
        w.write("Product", this.product);
        w.write("Error", this.error);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "GetLowestOfferListingsForASINResult");
    }

    public GetLowestOfferListingsForASINResult(String status) {
        this.status = status;
    }

    public GetLowestOfferListingsForASINResult() {
    }
}

package com.apicalls.mws.model;

import com.amazonservices.mws.client.AbstractMwsObject;
import com.amazonservices.mws.client.MwsReader;
import com.amazonservices.mws.client.MwsWriter;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "MoneyType",
        propOrder = {"currencyCode", "amount"}
)
@XmlRootElement(
        name = "MoneyType"
)
public class MoneyType extends AbstractMwsObject {
    @XmlElement(
            name = "CurrencyCode"
    )
    private String currencyCode;
    @XmlElement(
            name = "Amount"
    )
    private BigDecimal amount;

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public boolean isSetCurrencyCode() {
        return this.currencyCode != null;
    }

    public MoneyType withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isSetAmount() {
        return this.amount != null;
    }

    public MoneyType withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.currencyCode = (String)r.read("CurrencyCode", String.class);
        this.amount = (BigDecimal)r.read("Amount", BigDecimal.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.write("CurrencyCode", this.currencyCode);
        w.write("Amount", this.amount);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "MoneyType", this);
    }

    public MoneyType() {
    }
}

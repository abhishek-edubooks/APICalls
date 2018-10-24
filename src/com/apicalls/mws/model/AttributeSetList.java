package com.apicalls.mws.model;

import com.amazonservices.mws.client.MwsReader;
import com.amazonservices.mws.client.MwsWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "AttributeSetList",
        propOrder = {"any"}
)
@XmlRootElement(
        name = "AttributeSetList"
)
public class AttributeSetList {
    @XmlAnyElement(
            lax = true
    )
    private List<Object> any;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList();
        }

        return this.any;
    }

    public void setAny(List<Object> any) {
        this.any = any;
    }

    public void unsetAny() {
        this.any = null;
    }

    public boolean isSetAny() {
        return this.any != null && !this.any.isEmpty();
    }

    public AttributeSetList withAny(Object... values) {
        List<Object> list = this.getAny();
        Object[] arr$ = values;
        int len$ = values.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Object value = arr$[i$];
            list.add(value);
        }

        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.any = (List)r.readAny();
    }

    public void writeFragmentTo(MwsWriter w) {
        w.writeAny((List)this.any);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "AttributeSetList");
    }

    public AttributeSetList() {
    }
}

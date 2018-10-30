package com.apicalls.mws.model;

import com.amazonservices.mws.client.MwsReader;
import com.amazonservices.mws.client.MwsWriter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "Product",
        propOrder = {"identifiers", "attributeSets", "relationships", "competitivePricing", "salesRankings", "lowestOfferListings", "offers"}
)
@XmlRootElement(
        name = "Product"
)
public class Product {
    @XmlElement(
            name = "Identifiers",
            required = true
    )
    private IdentifierType identifiers;
    @XmlElement(
            name = "AttributeSets"
    )
    private AttributeSetList attributeSets;
    @XmlElement(
            name = "Relationships"
    )
    private RelationshipList relationships;
    @XmlElement(
            name = "CompetitivePricing"
    )
    private CompetitivePricingType competitivePricing;
    @XmlElement(
            name = "SalesRankings"
    )
    private SalesRankList salesRankings;
    @XmlElement(
            name = "LowestOfferListings"
    )
    private LowestOfferListingList lowestOfferListings;
    @XmlElement(
            name = "Offers"
    )
    private OffersList offers;

    public IdentifierType getIdentifiers() {
        return this.identifiers;
    }

    public void setIdentifiers(IdentifierType identifiers) {
        this.identifiers = identifiers;
    }

    public boolean isSetIdentifiers() {
        return this.identifiers != null;
    }

    public Product withIdentifiers(IdentifierType identifiers) {
        this.identifiers = identifiers;
        return this;
    }

    public AttributeSetList getAttributeSets() {
        return this.attributeSets;
    }

    public void setAttributeSets(AttributeSetList attributeSets) {
        this.attributeSets = attributeSets;
    }

    public boolean isSetAttributeSets() {
        return this.attributeSets != null;
    }

    public Product withAttributeSets(AttributeSetList attributeSets) {
        this.attributeSets = attributeSets;
        return this;
    }

    public RelationshipList getRelationships() {
        return this.relationships;
    }

    public void setRelationships(RelationshipList relationships) {
        this.relationships = relationships;
    }

    public boolean isSetRelationships() {
        return this.relationships != null;
    }

    public Product withRelationships(RelationshipList relationships) {
        this.relationships = relationships;
        return this;
    }

    public CompetitivePricingType getCompetitivePricing() {
        return this.competitivePricing;
    }

    public void setCompetitivePricing(CompetitivePricingType competitivePricing) {
        this.competitivePricing = competitivePricing;
    }

    public boolean isSetCompetitivePricing() {
        return this.competitivePricing != null;
    }

    public Product withCompetitivePricing(CompetitivePricingType competitivePricing) {
        this.competitivePricing = competitivePricing;
        return this;
    }

    public SalesRankList getSalesRankings() {
        return this.salesRankings;
    }

    public void setSalesRankings(SalesRankList salesRankings) {
        this.salesRankings = salesRankings;
    }

    public boolean isSetSalesRankings() {
        return this.salesRankings != null;
    }

    public Product withSalesRankings(SalesRankList salesRankings) {
        this.salesRankings = salesRankings;
        return this;
    }

    public LowestOfferListingList getLowestOfferListings() {
        return this.lowestOfferListings;
    }

    public void setLowestOfferListings(LowestOfferListingList lowestOfferListings) {
        this.lowestOfferListings = lowestOfferListings;
    }

    public boolean isSetLowestOfferListings() {
        return this.lowestOfferListings != null;
    }

    public Product withLowestOfferListings(LowestOfferListingList lowestOfferListings) {
        this.lowestOfferListings = lowestOfferListings;
        return this;
    }

    public OffersList getOffers() {
        return this.offers;
    }

    public void setOffers(OffersList offers) {
        this.offers = offers;
    }

    public boolean isSetOffers() {
        return this.offers != null;
    }

    public Product withOffers(OffersList offers) {
        this.offers = offers;
        return this;
    }

    public void readFragmentFrom(MwsReader r) {
        this.identifiers = (IdentifierType)r.read("Identifiers", IdentifierType.class);
        this.attributeSets = (AttributeSetList)r.read("AttributeSets", AttributeSetList.class);
        this.relationships = (RelationshipList)r.read("Relationships", RelationshipList.class);
        this.competitivePricing = (CompetitivePricingType)r.read("CompetitivePricing", CompetitivePricingType.class);
        this.salesRankings = (SalesRankList)r.read("SalesRankings", SalesRankList.class);
        this.lowestOfferListings = (LowestOfferListingList)r.read("LowestOfferListings", LowestOfferListingList.class);
        this.offers = (OffersList)r.read("Offers", OffersList.class);
    }

    public void writeFragmentTo(MwsWriter w) {
        w.write("Identifiers", this.identifiers);
        w.write("AttributeSets", this.attributeSets);
        w.write("Relationships", this.relationships);
        w.write("CompetitivePricing", this.competitivePricing);
        w.write("SalesRankings", this.salesRankings);
        w.write("LowestOfferListings", this.lowestOfferListings);
        w.write("Offers", this.offers);
    }

    public void writeTo(MwsWriter w) {
        w.write("http://mws.amazonservices.com/schema/Products/2011-10-01", "Product");
    }

    public Product(IdentifierType identifiers) {
        this.identifiers = identifiers;
    }

    public Product() {
    }
}

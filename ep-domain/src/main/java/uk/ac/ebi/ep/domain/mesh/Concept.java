//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.21 at 10:28:29 AM CET 
//


package uk.ac.ebi.ep.domain.mesh;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "conceptUI",
    "conceptName",
    "conceptUMLSUI",
    "casn1Name",
    "registryNumber",
    "scopeNote",
    "translatorsEnglishScopeNote",
    "translatorsScopeNote",
    "semanticTypeList",
    "relatedRegistryNumberList",
    "conceptRelationList",
    "termList"
})
@XmlRootElement(name = "Concept")
public class Concept {

    @XmlAttribute(name = "PreferredConceptYN", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String preferredConceptYN;
    @XmlElement(name = "ConceptUI", required = true)
    protected String conceptUI;
    @XmlElement(name = "ConceptName", required = true)
    protected ConceptName conceptName;
    @XmlElement(name = "ConceptUMLSUI")
    protected String conceptUMLSUI;
    @XmlElement(name = "CASN1Name")
    protected String casn1Name;
    @XmlElement(name = "RegistryNumber")
    protected String registryNumber;
    @XmlElement(name = "ScopeNote")
    protected String scopeNote;
    @XmlElement(name = "TranslatorsEnglishScopeNote")
    protected String translatorsEnglishScopeNote;
    @XmlElement(name = "TranslatorsScopeNote")
    protected String translatorsScopeNote;
    @XmlElement(name = "SemanticTypeList")
    protected SemanticTypeList semanticTypeList;
    @XmlElement(name = "RelatedRegistryNumberList")
    protected RelatedRegistryNumberList relatedRegistryNumberList;
    @XmlElement(name = "ConceptRelationList")
    protected ConceptRelationList conceptRelationList;
    @XmlElement(name = "TermList", required = true)
    protected TermList termList;

    /**
     * Gets the value of the preferredConceptYN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferredConceptYN() {
        return preferredConceptYN;
    }

    /**
     * Sets the value of the preferredConceptYN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferredConceptYN(String value) {
        this.preferredConceptYN = value;
    }

    /**
     * Gets the value of the conceptUI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConceptUI() {
        return conceptUI;
    }

    /**
     * Sets the value of the conceptUI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConceptUI(String value) {
        this.conceptUI = value;
    }

    /**
     * Gets the value of the conceptName property.
     * 
     * @return
     *     possible object is
     *     {@link ConceptName }
     *     
     */
    public ConceptName getConceptName() {
        return conceptName;
    }

    /**
     * Sets the value of the conceptName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConceptName }
     *     
     */
    public void setConceptName(ConceptName value) {
        this.conceptName = value;
    }

    /**
     * Gets the value of the conceptUMLSUI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConceptUMLSUI() {
        return conceptUMLSUI;
    }

    /**
     * Sets the value of the conceptUMLSUI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConceptUMLSUI(String value) {
        this.conceptUMLSUI = value;
    }

    /**
     * Gets the value of the casn1Name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCASN1Name() {
        return casn1Name;
    }

    /**
     * Sets the value of the casn1Name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCASN1Name(String value) {
        this.casn1Name = value;
    }

    /**
     * Gets the value of the registryNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistryNumber() {
        return registryNumber;
    }

    /**
     * Sets the value of the registryNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistryNumber(String value) {
        this.registryNumber = value;
    }

    /**
     * Gets the value of the scopeNote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScopeNote() {
        return scopeNote;
    }

    /**
     * Sets the value of the scopeNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScopeNote(String value) {
        this.scopeNote = value;
    }

    /**
     * Gets the value of the translatorsEnglishScopeNote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTranslatorsEnglishScopeNote() {
        return translatorsEnglishScopeNote;
    }

    /**
     * Sets the value of the translatorsEnglishScopeNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTranslatorsEnglishScopeNote(String value) {
        this.translatorsEnglishScopeNote = value;
    }

    /**
     * Gets the value of the translatorsScopeNote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTranslatorsScopeNote() {
        return translatorsScopeNote;
    }

    /**
     * Sets the value of the translatorsScopeNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTranslatorsScopeNote(String value) {
        this.translatorsScopeNote = value;
    }

    /**
     * Gets the value of the semanticTypeList property.
     * 
     * @return
     *     possible object is
     *     {@link SemanticTypeList }
     *     
     */
    public SemanticTypeList getSemanticTypeList() {
        return semanticTypeList;
    }

    /**
     * Sets the value of the semanticTypeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link SemanticTypeList }
     *     
     */
    public void setSemanticTypeList(SemanticTypeList value) {
        this.semanticTypeList = value;
    }

    /**
     * Gets the value of the relatedRegistryNumberList property.
     * 
     * @return
     *     possible object is
     *     {@link RelatedRegistryNumberList }
     *     
     */
    public RelatedRegistryNumberList getRelatedRegistryNumberList() {
        return relatedRegistryNumberList;
    }

    /**
     * Sets the value of the relatedRegistryNumberList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelatedRegistryNumberList }
     *     
     */
    public void setRelatedRegistryNumberList(RelatedRegistryNumberList value) {
        this.relatedRegistryNumberList = value;
    }

    /**
     * Gets the value of the conceptRelationList property.
     * 
     * @return
     *     possible object is
     *     {@link ConceptRelationList }
     *     
     */
    public ConceptRelationList getConceptRelationList() {
        return conceptRelationList;
    }

    /**
     * Sets the value of the conceptRelationList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConceptRelationList }
     *     
     */
    public void setConceptRelationList(ConceptRelationList value) {
        this.conceptRelationList = value;
    }

    /**
     * Gets the value of the termList property.
     * 
     * @return
     *     possible object is
     *     {@link TermList }
     *     
     */
    public TermList getTermList() {
        return termList;
    }

    /**
     * Sets the value of the termList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TermList }
     *     
     */
    public void setTermList(TermList value) {
        this.termList = value;
    }

}

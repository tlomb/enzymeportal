//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.21 at 10:28:29 AM CET 
//


package uk.ac.ebi.ep.domain.mesh;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "descriptorReferredTo",
    "qualifierReferredTo"
})
@XmlRootElement(name = "ECOUT")
public class ECOUT {

    @XmlElement(name = "DescriptorReferredTo", required = true)
    protected DescriptorReferredTo descriptorReferredTo;
    @XmlElement(name = "QualifierReferredTo")
    protected QualifierReferredTo qualifierReferredTo;

    /**
     * Gets the value of the descriptorReferredTo property.
     * 
     * @return
     *     possible object is
     *     {@link DescriptorReferredTo }
     *     
     */
    public DescriptorReferredTo getDescriptorReferredTo() {
        return descriptorReferredTo;
    }

    /**
     * Sets the value of the descriptorReferredTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DescriptorReferredTo }
     *     
     */
    public void setDescriptorReferredTo(DescriptorReferredTo value) {
        this.descriptorReferredTo = value;
    }

    /**
     * Gets the value of the qualifierReferredTo property.
     * 
     * @return
     *     possible object is
     *     {@link QualifierReferredTo }
     *     
     */
    public QualifierReferredTo getQualifierReferredTo() {
        return qualifierReferredTo;
    }

    /**
     * Sets the value of the qualifierReferredTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualifierReferredTo }
     *     
     */
    public void setQualifierReferredTo(QualifierReferredTo value) {
        this.qualifierReferredTo = value;
    }

}
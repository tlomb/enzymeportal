/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.ep.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author <a href="mailto:joseph@ebi.ac.uk">Joseph</a>
 */
@Entity
@Table(name = "INTENZ_ALT_NAMES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IntenzAltNames.findAll", query = "SELECT i FROM IntenzAltNames i"),
    @NamedQuery(name = "IntenzAltNames.findByInternalId", query = "SELECT i FROM IntenzAltNames i WHERE i.internalId = :internalId"),
    @NamedQuery(name = "IntenzAltNames.findByEcNumber", query = "SELECT i FROM IntenzAltNames i WHERE i.ecNumber = :ecNumber"),
    @NamedQuery(name = "IntenzAltNames.findByAltName", query = "SELECT i FROM IntenzAltNames i WHERE i.altName = :altName")})
public class IntenzAltNames implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "INTERNAL_ID")
    private BigDecimal internalId;
    @Size(max = 15)
    @Column(name = "EC_NUMBER")
    private String ecNumber;
    @Size(max = 4000)
    @Column(name = "ALT_NAME")
    private String altName;

    public IntenzAltNames() {
    }

    public IntenzAltNames(BigDecimal internalId) {
        this.internalId = internalId;
    }

    public BigDecimal getInternalId() {
        return internalId;
    }

    public void setInternalId(BigDecimal internalId) {
        this.internalId = internalId;
    }

    public String getEcNumber() {
        return ecNumber;
    }

    public void setEcNumber(String ecNumber) {
        this.ecNumber = ecNumber;
    }

    public String getAltName() {
        return altName;
    }

    public void setAltName(String altName) {
        this.altName = altName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (internalId != null ? internalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntenzAltNames)) {
            return false;
        }
        IntenzAltNames other = (IntenzAltNames) object;
        if ((this.internalId == null && other.internalId != null) || (this.internalId != null && !this.internalId.equals(other.internalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.ac.ebi.ep.ep.model.IntenzAltNames[ internalId=" + internalId + " ]";
    }
    
}

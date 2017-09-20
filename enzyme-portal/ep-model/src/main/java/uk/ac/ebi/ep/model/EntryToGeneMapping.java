
package uk.ac.ebi.ep.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ENTRY_TO_GENE_MAPPING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntryToGeneMapping.findAll", query = "SELECT e FROM EntryToGeneMapping e"),
    @NamedQuery(name = "EntryToGeneMapping.findByGeneInternalId", query = "SELECT e FROM EntryToGeneMapping e WHERE e.geneInternalId = :geneInternalId"),
    @NamedQuery(name = "EntryToGeneMapping.findByGeneName", query = "SELECT e FROM EntryToGeneMapping e WHERE e.geneName = :geneName"),
    @NamedQuery(name = "EntryToGeneMapping.findByUniprotGeneId", query = "SELECT e FROM EntryToGeneMapping e WHERE e.uniprotGeneId = :uniprotGeneId")})
public class EntryToGeneMapping implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "GENE_INTERNAL_ID")
    private BigDecimal geneInternalId;
    @Size(max = 60)
    @Column(name = "GENE_NAME")
    private String geneName;
    @Column(name = "UNIPROT_GENE_ID")
    private BigInteger uniprotGeneId;
    @JoinColumn(name = "UNIPROT_ACCESSION", referencedColumnName = "ACCESSION")
    @ManyToOne
    private UniprotEntry uniprotAccession;

    public EntryToGeneMapping() {
    }

    public EntryToGeneMapping(BigDecimal geneInternalId) {
        this.geneInternalId = geneInternalId;
    }

    public BigDecimal getGeneInternalId() {
        return geneInternalId;
    }

    public void setGeneInternalId(BigDecimal geneInternalId) {
        this.geneInternalId = geneInternalId;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public BigInteger getUniprotGeneId() {
        return uniprotGeneId;
    }

    public void setUniprotGeneId(BigInteger uniprotGeneId) {
        this.uniprotGeneId = uniprotGeneId;
    }

    public UniprotEntry getUniprotAccession() {
        return uniprotAccession;
    }

    public void setUniprotAccession(UniprotEntry uniprotAccession) {
        this.uniprotAccession = uniprotAccession;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geneInternalId != null ? geneInternalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntryToGeneMapping)) {
            return false;
        }
        EntryToGeneMapping other = (EntryToGeneMapping) object;
        if ((this.geneInternalId == null && other.geneInternalId != null) || (this.geneInternalId != null && !this.geneInternalId.equals(other.geneInternalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.ac.ebi.ep.ep.model.EntryToGeneMapping[ geneInternalId=" + geneInternalId + " ]";
    }
    
}

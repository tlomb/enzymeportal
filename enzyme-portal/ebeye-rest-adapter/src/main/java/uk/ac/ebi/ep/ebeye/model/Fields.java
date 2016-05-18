/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.ep.ebeye.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Joseph <joseph@ebi.ac.uk>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "acc",
    "protein_name",
    "common_name",
    "scientific_name",
    "compound_name",
    "disease_name",
    "enzyme_family"
})
public class Fields {

    @JsonProperty("id")
    private List<String> ec = new ArrayList<>();
    @JsonProperty("name")
    private List<String> name = new ArrayList<>();
    @JsonProperty("description")
    private List<String> description = new ArrayList<>();
    @JsonProperty("acc")
    private List<String> acc = new ArrayList<>();
    @JsonProperty("protein_name")
    private Set<String> proteinName = new HashSet<>();
    @JsonProperty("common_name")
    private List<String> commonName = new ArrayList<>();
    @JsonProperty("scientific_name")
    private List<String> scientificName = new ArrayList<>();
    @JsonProperty("compound_name")
    private List<String> compoundName = new ArrayList<>();
    @JsonProperty("disease_name")
    private List<String> diseaseName = new ArrayList<>();
    @JsonProperty("enzyme_family")
    private List<String> enzymeFamily = new ArrayList<>();
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();

    /**
     *
     * @return The ec
     */
    @JsonProperty("id")
    public List<String> getEc() {
        return ec;
    }

    /**
     *
     * @param ec The ec
     */
    @JsonProperty("id")
    public void setEc(List<String> ec) {
        this.ec = ec;
    }

    /**
     *
     * @return The name
     */
    @JsonProperty("name")
    public List<String> getName() {
        return name;
    }

    /**
     *
     * @param name The name
     */
    @JsonProperty("name")
    public void setName(List<String> name) {
        this.name = name;
    }

    /**
     *
     * @return The description
     */
    @JsonProperty("description")
    public List<String> getDescription() {
        return description;
    }

    /**
     *
     * @param description The description
     */
    @JsonProperty("description")
    public void setDescription(List<String> description) {
        this.description = description;
    }

    /**
     *
     * @return The accession
     */
    @JsonProperty("acc")
    public List<String> getAcc() {
        return acc;
    }

    /**
     *
     * @param acc The accession
     */
    @JsonProperty("acc")
    public void setAcc(List<String> acc) {
        this.acc = acc;
    }

    /**
     *
     * @return The proteinName
     */
    public Set<String> getProteinName() {
        return proteinName;
    }

    /**
     *
     * @param proteinName The protein_name
     */
    public void setProteinName(Set<String> proteinName) {
        this.proteinName = proteinName;
    }

    /**
     *
     * @return The commonName
     */
    @JsonProperty("common_name")
    public List<String> getCommonName() {
        return commonName;
    }

    /**
     *
     * @param commonName The common_name
     */
    @JsonProperty("common_name")
    public void setCommonName(List<String> commonName) {
        this.commonName = commonName;
    }

    /**
     *
     * @return The scientificName
     */
    @JsonProperty("scientific_name")
    public List<String> getScientificName() {
        return scientificName;
    }

    /**
     *
     * @param scientificName The scientific_name
     */
    @JsonProperty("scientific_name")
    public void setScientificName(List<String> scientificName) {
        this.scientificName = scientificName;
    }

    /**
     *
     * @return The compoundName
     */
    @JsonProperty("compound_name")
    public List<String> getCompoundName() {
        return compoundName;
    }

    /**
     *
     * @param compoundName The compound_name
     */
    @JsonProperty("compound_name")
    public void setCompoundName(List<String> compoundName) {
        this.compoundName = compoundName;
    }

    /**
     *
     * @return The diseaseName
     */
    @JsonProperty("disease_name")
    public List<String> getDiseaseName() {
        return diseaseName;
    }

    /**
     *
     * @param diseaseName The disease_name
     */
    @JsonProperty("disease_name")
    public void setDiseaseName(List<String> diseaseName) {
        this.diseaseName = diseaseName;
    }

    /**
     *
     * @return The enzymeFamily
     */
    @JsonProperty("enzyme_family")
    public List<String> getEnzymeFamily() {
        return enzymeFamily;
    }

    /**
     *
     * @param enzymeFamily The enzyme_family
     */
    @JsonProperty("enzyme_family")
    public void setEnzymeFamily(List<String> enzymeFamily) {
        this.enzymeFamily = enzymeFamily;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Fields{" + "id=" + ec + ", name=" + name + ", description=" + description + ", proteinName=" + proteinName + ", commonName=" + commonName + ", scientificName=" + scientificName + ", compoundName=" + compoundName + ", diseaseName=" + diseaseName + ", enzymeFamily=" + enzymeFamily + '}';
    }

}

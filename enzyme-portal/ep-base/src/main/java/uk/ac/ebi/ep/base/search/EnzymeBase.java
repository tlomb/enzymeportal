/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.ep.base.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import uk.ac.ebi.ep.data.domain.UniprotEntry;
import uk.ac.ebi.ep.data.service.EnzymePortalService;
import uk.ac.ebi.ep.ebeye.EbeyeRestService;

/**
 *
 * @author Joseph <joseph@ebi.ac.uk>
 */
public abstract class EnzymeBase {

    protected EnzymePortalService enzymePortalService;
    protected EbeyeRestService ebeyeRestService;

    private EnzymeBase() {

    }

    public EnzymeBase(EnzymePortalService service) {
        this.enzymePortalService = service;
    }

    public EnzymeBase(EbeyeRestService ebeyeRestService) {
        this.ebeyeRestService = ebeyeRestService;
    }

    public EnzymeBase(EnzymePortalService service, EbeyeRestService ebeyeRestService) {
        this.enzymePortalService = service;
        this.ebeyeRestService = ebeyeRestService;
    }

    /**
     * Retrieves the protein recommended name as well as any synonyms.
     *
     * @param namesColumn the column returned by the web enzymePortalService
     * @return a list of names, the first one of them being the recommended one.
     */
    protected List<String> parseNameSynonyms(String namesColumn) {
        List<String> nameSynonyms = new ArrayList<>();
        if (namesColumn != null) {
            final int sepIndex = namesColumn.indexOf(" (");

            if (sepIndex == -1) {
                // no synonyms, just recommended name:

                nameSynonyms.add(namesColumn);
            } else {
                // Recommended name:
                nameSynonyms.add(namesColumn.substring(0, sepIndex));
                // take out starting and ending parentheses
                String[] synonyms = namesColumn.substring(sepIndex + 2, namesColumn.length() - 1).split("\\) \\(");
                nameSynonyms.addAll(Arrays.asList(synonyms));
            }
            return nameSynonyms.stream().distinct().collect(Collectors.toList());
        }
        return nameSynonyms;
    }

    @Deprecated
    private List<String> computeSynonymNames(UniprotEntry uniprotEntry) {
        List<String> synonyms = new LinkedList<>();

        String namesColumn = uniprotEntry.getSynonymNames();

        if (namesColumn != null && namesColumn.contains(";")) {
            String[] syn = namesColumn.split(";");
            for (String x : syn) {

                synonyms.addAll(parseNameSynonyms(x));
            }
        }
        return synonyms.stream().sorted().distinct().collect(Collectors.toList());

    }

}

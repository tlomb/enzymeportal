/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.ep.parser.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.ep.centralservice.helper.Relationship;
import uk.ac.ebi.ep.data.entry.Summary;
import uk.ac.ebi.ep.data.repositories.EnzymePortalCompoundRepository;
import uk.ac.ebi.ep.data.repositories.EnzymePortalSummaryRepository;
import uk.ac.ebi.ep.data.search.model.Compound;
import uk.ac.ebi.ep.data.service.EnzymePortalParserService;

/**
 *
 * @author joseph
 */
public class Cofactors extends CompoundParser {
    
    private List<LiteCompound> compounds = null;
    private static final String COMMENT_TYPE = "COFACTORS";
    private static final String NAME = "Name=([^\\s]+)";
    private static final String XREF = "Xref=ChEBI:([^\\s]+)";
    private static final String NOTE = "Note=([^\\*]+)";
    
    public Cofactors(ChebiWebServiceClient chebiWsClient, EnzymePortalCompoundRepository compoundRepository, EnzymePortalSummaryRepository enzymeSummaryRepository, EnzymePortalParserService parserService) {
        super(chebiWsClient, compoundRepository, enzymeSummaryRepository, parserService);
        compounds = new ArrayList<>();
    }

    /**
     * parse cofactor comments from uniprot and validates compound names in
     * chebi before storing them at enzyme portal database
     */
    @Override
    public void loadCofactors() {
        List<Summary> enzymeSummary = enzymeSummaryRepository.findSummariesByCommentType(COMMENT_TYPE);
        
        LOGGER.info("Number of Regulation Text from EnzymeSummary Table to parse for cofactors " + enzymeSummary.size());
     
   
        parseCofactorText(enzymeSummary);
    }
    
    private void computeSpecialCases(String text, Summary summary, String note) {
        final Pattern xrefPattern = Pattern.compile(XREF);
        final Matcher xrefMatcher = xrefPattern.matcher(text);
        
        while (xrefMatcher.find()) {
            
            String xref = xrefMatcher.group(1).replaceAll(";", "");
            
            if (xref != null) {
                LiteCompound compound = null;
                try{
                   compound = searchCompoundInChEBI(xref);  
                }catch(Exception e){
                   LOGGER.error("Chebi webservice error while searching "+ xref, e);
                }
                
                if (compound != null) {
                    
                    String compoundId = compound.getCompoundId();
                    String compoundName = compound.getCompoundName();
                    String compoundSource = compound.getCompoundSource();
                    String relationship = Relationship.is_cofactor_of.name();
                    String compoundRole = Compound.Role.COFACTOR.name();
                    String url = compound.getUrl();
                    //String accession = summary.getUniprotAccession().getAccession();

                    //parserService.createCompound(compoundId, compoundName, compoundSource, relationship, accession, url, compoundRole, note);
                    compound.setCompoundId(compoundId);
                    compound.setCompoundName(compoundName);
                    compound.setCompoundSource(compoundSource);
                    compound.setRelationship(relationship);
                    compound.setUniprotAccession(summary.getAccession());
                    compound.setUrl(url);
                    compound.setCompoundRole(compoundRole);
                    compound.setNote(note);
                    compounds.add(compound);
                    LOGGER.info("added compound for special case " + compound.getCompoundId() + " <> " + compound.getCompoundName());
                    
                }
                
            }
            
        }
        
    }
    
    private void parseCofactorText(List<Summary> enzymeSummary) {
        
//        Stream<Summary> existingStream = enzymeSummary.stream();
//        Stream<List<Summary>> partitioned = partition(existingStream, 100, 1);
//        AtomicInteger count = new AtomicInteger(1);
//        partitioned.parallel().forEach((chunk) -> {
//            chunk.stream().forEach((summary) -> {
//                processCofactors(summary);
//            });
//        });

        enzymeSummary.forEach(summary ->{
         processCofactors(summary);
     
        });
        //save compounds
        LOGGER.warn("Writing to Enzyme Portal database... Number of cofactors to write : " + compounds.size());
        
        compounds.stream().filter((compound) -> (compound != null)).forEach((compound) -> {
            parserService.createCompound(compound.getCompoundId(), compound.getCompoundName(), compound.getCompoundSource(), compound.getRelationship(), compound.getUniprotAccession(), compound.getUrl(), compound.getCompoundRole(), compound.getNote());

        });
        LOGGER.warn("-------- Done populating the database with cofactors ---------------");
        compounds.clear();
        
    }
    
    private void processCofactors(Summary summary) {
        String cofactorText = summary.getCommentText();
        String note = "";
        final Pattern notePattern = Pattern.compile(NOTE);
        final Matcher noteMatcher = notePattern.matcher(cofactorText);
        
        while (noteMatcher.find()) {
            
            note = noteMatcher.group(1);
            
        }
        
        final Pattern namePattern = Pattern.compile(NAME);
        final Matcher nameMatcher = namePattern.matcher(cofactorText);
        
        while (nameMatcher.find()) {
            
            String cofactorName = nameMatcher.group(1).replaceAll(";", "");
            
            if (cofactorName != null) {
                LiteCompound compound = searchMoleculeInChEBI(cofactorName);
                
                if (compound != null) {
                    
                    String compoundId = compound.getCompoundId();
                    String compoundName = compound.getCompoundName();
                    String compoundSource = compound.getCompoundSource();
                    String relationship = Relationship.is_cofactor_of.name();
                    String compoundRole = Compound.Role.COFACTOR.name();
                    String url = compound.getUrl();
                    //String accession = summary.getUniprotAccession().getAccession();

                    //parserService.createCompound(compoundId, compoundName, compoundSource, relationship, accession, url, compoundRole, note);
                    //deprecate later
//                    compound.setRelationship(Relationship.is_cofactor_of.name());
//                    compound.setUniprotAccession(summary.getUniprotAccession());
//                    compound.setCompoundRole(Compound.Role.COFACTOR.name());
                    compound.setCompoundId(compoundId);
                    compound.setCompoundName(compoundName);
                    compound.setCompoundSource(compoundSource);
                    compound.setRelationship(relationship);
                    compound.setUniprotAccession(summary.getAccession());
                    compound.setUrl(url);
                    compound.setCompoundRole(compoundRole);
                    compound.setNote(note);
                    compounds.add(compound);
                    
                }
                if (compound == null) {
                    computeSpecialCases(cofactorText, summary, note);
                }
            }
            
        }
    }
    
}

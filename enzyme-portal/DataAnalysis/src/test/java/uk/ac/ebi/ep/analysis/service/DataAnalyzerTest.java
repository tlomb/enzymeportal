/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.ep.analysis.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StringUtils;
import uk.ac.ebi.ep.data.domain.SpEnzymeEvidence;

/**
 *
 * @author Joseph <joseph@ebi.ac.uk>
 */
public class DataAnalyzerTest {

    private final Logger logger = Logger.getLogger(DataAnalyzerTest.class);
    private DataAnalyzer instance = null;
    private final List<SpEnzymeEvidence> evidences = new ArrayList<>();

    public DataAnalyzerTest() {
    }

    @Before
    public void setUp() {
        instance = new DataAnalyzer();
        SpEnzymeEvidence evidence = new SpEnzymeEvidence(BigDecimal.ONE);
        evidence.setAccession("Acc123");
        evidence.setEvidenceLine("FUNCTION");
        SpEnzymeEvidence evidence1 = new SpEnzymeEvidence(BigDecimal.TEN);
        evidence1.setAccession("Acc456");
        evidence1.setEvidenceLine("COFACTOR");
        evidences.add(evidence);
        evidences.add(evidence1);
    }

    /**
     * Test of writeToFile method, of class DataAnalyzer.
     */
    @Test
    public void testWriteToFile() {
        logger.warn("Test writeToFile ..");
        String fileDir = null;
        String filename = "evidence.txt";
        Boolean deleteFile = false;

        instance.writeToFile(evidences, fileDir, filename, deleteFile);
        String userHome = System.getProperty("user.home");

        String filePath = fileDir + "/" + filename;
        if (StringUtils.isEmpty(fileDir)) {
            filePath = userHome + "/" + filename;
        }

        File file = new File(filePath);

        Assert.assertTrue(file.isFile());

        try {
            if (file.isFile()) {
                Path path = Paths.get(filePath);

                Files.deleteIfExists(path);
            }
        } catch (IOException ex) {
            logger.error(ex);
        }

    }

    /**
     * Test of combineString method, of class DataAnalyzer.
     */
    @Test
    public void testCombineString() {
        logger.warn("test combineString");
        List<String> part1 = new ArrayList<>();
        part1.add("Swiss");
        List<String> part2 = new ArrayList<>();
        part2.add("Prot");
        Boolean allowDuplicate = false;

        List<String> expResult = new ArrayList<>();
        expResult.add("Swiss");
        expResult.add("Prot");
        List<String> result = instance.combineString(part1, part2, allowDuplicate);

        assertEquals(expResult, result);
        Assert.assertSame(expResult.stream().findFirst().get(), result.stream().findFirst().get());

    }

    /**
     * Test of combine method, of class DataAnalyzer.
     */
    @Test
    public void testCombine() {
        logger.warn("test combine");

        SpEnzymeEvidence evidence = new SpEnzymeEvidence(BigDecimal.ONE);
        evidence.setAccession("Acc123");
        evidence.setEvidenceLine("FUNCTION");
        SpEnzymeEvidence evidence1 = new SpEnzymeEvidence(BigDecimal.TEN);
        evidence1.setAccession("Acc456");
        evidence1.setEvidenceLine("COFACTOR");

        List<SpEnzymeEvidence> part1 = new ArrayList<>();
        part1.add(evidence);
        List<SpEnzymeEvidence> part2 = new ArrayList<>();
        part2.add(evidence1);
        Boolean allowDuplicate = true;

        List<SpEnzymeEvidence> expResult = new ArrayList<>();
        expResult.add(evidence);
        expResult.add(evidence1);

        List<SpEnzymeEvidence> result = instance.combine(part1, part2, allowDuplicate);
        assertEquals(expResult, result);

    }

}

package uk.ac.ebi.ep.model.service;

import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.stream.Stream;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.ep.model.EnzymePortalEcNumbers;
import uk.ac.ebi.ep.model.IntenzEnzymes;
import uk.ac.ebi.ep.model.ProteinGroups;
import uk.ac.ebi.ep.model.QUniprotEntry;
import uk.ac.ebi.ep.model.UniprotEntry;
import uk.ac.ebi.ep.model.repositories.EnzymePortalEcNumbersRepository;
import uk.ac.ebi.ep.model.repositories.IntenzEnzymesRepository;
import uk.ac.ebi.ep.model.repositories.ProteinGroupsRepository;
import uk.ac.ebi.ep.model.repositories.UniprotEntryRepository;

/**
 *
 * @author joseph
 */
@Service
public class EnzymePortalXmlService {

    @Autowired
    private IntenzEnzymesRepository intenzEnzymesRepository;
    @Autowired
    private UniprotEntryRepository uniprotEntryRepository;

    @Autowired
    private ProteinGroupsRepository proteinGroupsRepository;

    @Autowired
    private EnzymePortalEcNumbersRepository ecNumbersRepository;

    private static Predicate swissprotEnzymesByEcNumber(String ecNumber) {
        QUniprotEntry enzyme = QUniprotEntry.uniprotEntry;

        return enzyme.entryType.eq(Integer.valueOf(0).shortValue()).and(enzyme.enzymePortalEcNumbersSet.any().ecNumber.equalsIgnoreCase(ecNumber));

    }

    private static Predicate enzymesByEcNumber(String ecNumber) {
        QUniprotEntry enzyme = QUniprotEntry.uniprotEntry;

        return enzyme.enzymePortalEcNumbersSet.any().ecNumber.equalsIgnoreCase(ecNumber);

    }

    /**
     * Note : This method should only used for Unit Test.
     *
     * @param enzyme the Intenz enzyme
     */
    public void addIntenzEnzyme(IntenzEnzymes enzyme) {
        intenzEnzymesRepository.save(enzyme);
    }

    /**
     * Note : This method should only used for Unit Test.
     *
     * @param enzymes the Intenz enzymes
     */
    public void addIntenzEnzymes(List<IntenzEnzymes> enzymes) {
        intenzEnzymesRepository.save(enzymes);
    }

    @Transactional(readOnly = true)
    public List<IntenzEnzymes> findAllIntenzEnzymes() {

        return intenzEnzymesRepository.findIntenzEnzymes();
    }

    @Transactional(readOnly = true)
    public Iterable<UniprotEntry> findSwissprotEnzymesByEcNumber(String ec) {

        return uniprotEntryRepository.findAll(swissprotEnzymesByEcNumber(ec));
    }

    @Transactional(readOnly = true)
    public Iterable<UniprotEntry> findEnzymesByEcNumber(String ec) {

        return uniprotEntryRepository.findAll(enzymesByEcNumber(ec));
    }

    @Transactional(readOnly = true)
    public List<UniprotEntry> findEnzymesByEcNumberNativeQuery(String ec) {

        return uniprotEntryRepository.findEnzymesByEc(ec);
    }
    
        @Transactional(readOnly = true)
    public Stream<UniprotEntry> streamEnzymesByEcNumber(String ec) {

        return uniprotEntryRepository.streamEnzymesByEc(ec);
    }

    @Transactional(readOnly = true)
    public List<UniprotEntry> findUniprotEntries() {

        return uniprotEntryRepository.findUniprotEntries();
    }

    @Transactional(readOnly = true)
    public Long countUniprotEntries() {
        return uniprotEntryRepository.countUniprotEntries();
    }

    @Transactional(readOnly = true)
    public Long countEnzymes() {

        return ecNumbersRepository.countEnzymes();
    }

    @Transactional(readOnly = true)
    public List<EnzymePortalEcNumbers> findEnzymes() {

        return ecNumbersRepository.findEnzymePortalEcNumbers();
    }
    
        @Transactional(readOnly = true)
    public List<EnzymePortalEcNumbers> findEnzymesByEcClass(Integer ecFamily) {

        return ecNumbersRepository.findEnzymesByEcClass(ecFamily);
    }
    
            @Transactional(readOnly = true)
    public List<EnzymePortalEcNumbers> findEnzymesByEcClass(Integer ecFamily,Integer limit) {

        return ecNumbersRepository.findEnzymesByEcClass(ecFamily, limit);
    }

    @Transactional(readOnly = true)
    public List<EnzymePortalEcNumbers> findEnzymes(Integer limit) {

        return ecNumbersRepository.findEnzymePortalEcNumbers(limit);
    }

    @Transactional(readOnly = true)
    public List<IntenzEnzymes> findNonTransferredIntenzEnzymesWithNoAcc(Integer limit) {

        return intenzEnzymesRepository.findNonTransferredEnzymesWithNoAcc(limit);
    }

    @Transactional(readOnly = true)
    public List<IntenzEnzymes> findNonTransferredIntenzEnzymesWithNoAcc() {

        return intenzEnzymesRepository.findNonTransferredEnzymesWithNoAcc();
    }

    //****** TODO ******
    @Transactional(readOnly = true)
    public List<UniprotEntry> findUniprotEntriesOrderedByEntryType() {

        return uniprotEntryRepository.findUniprotEntriesOrderedByEntryType();
    }

    @Transactional(readOnly = true)
    public List<UniprotEntry> findSwissprotEnzymesByEc(String ec) {

        return uniprotEntryRepository.findSwissprotEnzymesByEc(ec);
    }

    @Transactional(readOnly = true)
    public List<IntenzEnzymes> findNonTransferredEnzymes() {

        return intenzEnzymesRepository.findNonTransferredEnzymes();
    }

    @Transactional
    /**
     * example query -      <code>
     *   String query = "select e from IntenzEnzymes e where transferFlag='N'";
     * </code>
     */
    public Stream<IntenzEnzymes> streamIntenzEnzymesInBatch(SessionFactory sessionFactory, String query, int batchSize) {
        return intenzEnzymesRepository.streamingService(IntenzEnzymes.class, sessionFactory, query, batchSize);
    }

    @Transactional(readOnly = true)
    public Stream<UniprotEntry> streamUniprotEntriesInBatch(SessionFactory sessionFactory, String query, int batchSize) {
        return uniprotEntryRepository.streamingService(UniprotEntry.class, sessionFactory, query, batchSize);
    }

    @Transactional(readOnly = true)
    public Stream<ProteinGroups> streamProteinGroupsInBatch(SessionFactory sessionFactory, String query, int batchSize) {
        return proteinGroupsRepository.streamingService(ProteinGroups.class, sessionFactory, query, batchSize);
    }

    @Transactional(readOnly = true)
    public Stream<ProteinGroups> streamProteinGroupsInBatch(SessionFactory sessionFactory, String query, int batchSize, long limit) {
        return proteinGroupsRepository.streamingService(ProteinGroups.class, sessionFactory, query, batchSize, limit);
    }

    @Transactional(readOnly = true)
    public Stream<String> streamProteinGroupsIDInBatch(SessionFactory sessionFactory, String query, int batchSize) {
        return proteinGroupsRepository.streamingService(String.class, sessionFactory, query, batchSize);
    }

    @Transactional(readOnly = true)
    public Long countProteinGroups() {
        return proteinGroupsRepository.countProteinGroups();
    }

    @Transactional(readOnly = true)
    public List<UniprotEntry> findEnzymesByProteinGroupId(String pgId) {

        return uniprotEntryRepository.findEnzymesByProteinGroupId(pgId);
    }

    @Transactional(readOnly = true)
    public List<ProteinGroups> findProteinGroups() {
        return proteinGroupsRepository.findProteinGroups();
    }
}

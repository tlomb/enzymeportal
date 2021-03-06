package uk.ac.ebi.ep.xml.config;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.xml.StaxWriterCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import uk.ac.ebi.ep.data.domain.UniprotEntry;
import uk.ac.ebi.ep.data.service.EnzymePortalXmlService;
import uk.ac.ebi.ep.xml.generator.protein.ProteinXmlFooterCallback;
import uk.ac.ebi.ep.xml.generator.protein.ProteinXmlHeaderCallback;
import uk.ac.ebi.ep.xml.generator.protein.UniProtEntryToEntryConverter;
import uk.ac.ebi.ep.xml.model.Entry;
import uk.ac.ebi.ep.xml.util.LogChunkListener;
import uk.ac.ebi.ep.xml.util.LogJobListener;
import uk.ac.ebi.ep.xml.util.PrettyPrintStaxEventItemWriter;
import uk.ac.ebi.ep.xml.util.XmlFileUtils;

/**
 * Configures the protein centric XML generator to use Spring batch.
 */
@Configuration
@EnableBatchProcessing
@Import({EnzymePortalXmlService.class, XmlConfig.class})
@PropertySource(value = "classpath:ep-xml-config.properties", ignoreResourceNotFound = true)
public class ProteinBatchConfig extends DefaultBatchConfigurer {

    public static final String PROTEIN_CENTRIC_JOB = "PROTEIN_CENTRIC_JOB";
    public static final String PROTEIN_CENTRIC_DB_TO_XML_STEP = "readFromDbWriteToXMLStep";

    @Autowired
    private JobBuilderFactory jobBuilders;

    @Autowired
    private StepBuilderFactory stepBuilders;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EnzymePortalXmlService enzymePortalXmlService;

    @Autowired
    private XmlConfigParams xmlConfigParams;

    @Bean
    public Resource proteinCentricXmlDir() {
        XmlFileUtils file = null;
        return new FileSystemResource(xmlConfigParams.getProteinCentricXmlDir());
    }

    @Bean
    public JobLauncher jobLauncher() {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(this.getJobRepository());

        return jobLauncher;
    }

    @Bean
    public Job proteinCentricJob() {
        return jobBuilders.get(PROTEIN_CENTRIC_JOB)
                .start(readFromDbWriteToXMLStep())
                .listener(logJobListener(PROTEIN_CENTRIC_JOB))
                .build();
    }

    @Bean
    public Step readFromDbWriteToXMLStep() {
        return stepBuilders.get(PROTEIN_CENTRIC_DB_TO_XML_STEP)
                .<UniprotEntry, Entry>chunk(xmlConfigParams.getChunkSize())
                .<UniprotEntry>reader(uniProtEntryReader())
                .processor(uniProtEntryToEntryConverter())
                .writer(entryToXmlWriter())
                .listener(logChunkListener())
                .build();
    }

    @Bean
    public ItemReader<UniprotEntry> uniProtEntryReader() {
        JpaPagingItemReader<UniprotEntry> databaseReader = new JpaPagingItemReader<>();
        databaseReader.setEntityManagerFactory(entityManagerFactory);
        databaseReader.setQueryString("select u from UniprotEntry u");
        databaseReader.setPageSize(xmlConfigParams.getChunkSize());
        return databaseReader;
    }

    @Bean
    public ItemProcessor<UniprotEntry, Entry> uniProtEntryToEntryConverter() {
        return new UniProtEntryToEntryConverter(xmlConfigParams);
    }

    @Bean
    public ItemWriter<Entry> entryToXmlWriter() {
        PrettyPrintStaxEventItemWriter<Entry> xmlWriter = new PrettyPrintStaxEventItemWriter<>();
        XmlFileUtils.createDirectory(xmlConfigParams.getXmlDir());
        xmlWriter.setResource(proteinCentricXmlDir());
        xmlWriter.setRootTagName("database");
        xmlWriter.setMarshaller(entryMarshaller());
        xmlWriter.setHeaderCallback(xmlHeaderCallback());
        xmlWriter.setFooterCallback(new ProteinXmlFooterCallback());
        return xmlWriter;
    }

    private StaxWriterCallback xmlHeaderCallback() {
        return new ProteinXmlHeaderCallback(xmlConfigParams.getReleaseNumber(), enzymePortalXmlService);
    }

    private JobExecutionListener logJobListener(String jobName) {
        return new LogJobListener(jobName);
    }

    private ChunkListener logChunkListener() {
        return new LogChunkListener(xmlConfigParams.getChunkSize());
    }

    /**
     * Creates a job repository that uses an in memory map to register the job's
     * progress. This should be changed to use a real data source in the
     * following cases: - If you want to be able to resume a failed job - If you
     * want more than one of the same job (with the same parameters) to be
     * launched simultaneously - If you want to multi thread the job - If you
     * have a locally partitioned step.
     *
     * @return a JobRepository
     * @throws Exception if an error is encountered whilst creating the job repo
     */
    @Override
    protected JobRepository createJobRepository() throws Exception {
        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    private Marshaller entryMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Entry.class);

        Map<String, Object> jaxbProps = new HashMap<>();
        jaxbProps.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.setMarshallerProperties(jaxbProps);

        return marshaller;
    }
}
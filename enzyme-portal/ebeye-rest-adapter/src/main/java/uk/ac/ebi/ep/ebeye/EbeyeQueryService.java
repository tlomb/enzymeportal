package uk.ac.ebi.ep.ebeye;

import rx.Observable;
import uk.ac.ebi.ep.ebeye.protein.model.Entry;

/**
 * Service responsible for querying the EbeyeSearch REST webservice.
 *
 * The service decides on the best way to achieve the clients response in the shortest amount of time.
 *
 * @author Ricardo Antunes
 */
public interface EbeyeQueryService {

    /**
     * Submits the {@param query} to the EbeyeSearch REST service. The result will be an {@link Observable}
     * containing instances of {@link Entry} that match the query.
     *
     * @param query the query to search for
     * @return an Observable with the resulting Entry objects
     */
    Observable<Entry> executeQuery(String query);
}
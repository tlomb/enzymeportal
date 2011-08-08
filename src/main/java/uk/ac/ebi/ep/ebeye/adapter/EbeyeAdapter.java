package uk.ac.ebi.ep.ebeye.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.log4j.Logger;
import uk.ac.ebi.ebeye.param.ParamOfGetResults;
import uk.ac.ebi.ebeye.util.Transformer;
import uk.ac.ebi.ep.ebeye.adapter.EbeyeCallable.GetEntriesCallable;
import uk.ac.ebi.ep.ebeye.adapter.EbeyeCallable.GetResultsCallable;
import uk.ac.ebi.ep.ebeye.adapter.EbeyeCallable.NumberOfResultsCaller;
import uk.ac.ebi.ep.ebeye.result.Result;
import uk.ac.ebi.ep.search.exception.MultiThreadingException;
import uk.ac.ebi.ep.search.result.Pagination;
import uk.ac.ebi.webservices.ebeye.ArrayOfArrayOfString;
import uk.ac.ebi.webservices.ebeye.ArrayOfString;

/**
 *
 * @since   1.0
 * @version $LastChangedRevision$ <br/>
 *          $LastChangedDate$ <br/>
 *          $Author$
 * @author  $Author$
 */
public class EbeyeAdapter implements IEbeyeAdapter {

//********************************* VARIABLES ********************************//
//private static Logger log = Logger.getLogger(EbeyeAdapter.class);
  private static Logger log = Logger.getLogger(EbeyeAdapter.class);

//******************************** CONSTRUCTORS ******************************//


//****************************** GETTER & SETTER *****************************//


//********************************** METHODS *********************************//

    public List<Result> getResults(ParamOfGetResults param, boolean convertResultToUniprot)
            throws MultiThreadingException {
        List<String> domainFields = param.getFields();
        String domain  = param.getDomain();

        //Limited uniprot results to 100
        /*
        if (domain.equals(IEbeyeAdapter.Domains.uniprot.name())) {
            if (param.getTotalFound()> IEbeyeAdapter.EBEYE_RESULT_LIMIT) {
                param.setTotalFound(IEbeyeAdapter.EBEYE_RESULT_LIMIT);
            }
        }
        */
        List<Callable<ArrayOfArrayOfString>> callableList
                 = prepareCallableCollection(param);


         //List<ArrayOfArrayOfString> rawResults =
            //      submitAll(paramOfGetResultsList);
         //List<ArrayOfArrayOfString> rawResults = submitAll(callableList);
         List<ArrayOfArrayOfString> rawResults = executeCallables(callableList);
            List<List<String>> transformedResults
                    = Transformer.transformToList(rawResults);

            Collection<String> accessionList = Transformer.transformFieldValueToList(rawResults, true);
            //Save the content to an object
            ResultFactory resultFactory = new ResultFactory(
                    domain, domainFields);
            List<Result> resultList = resultFactory.getResults(transformedResults
                    , convertResultToUniprot);
         /*
        List<List<String>> transformedResults
                = Transformer.transformToList(rawResults);
        //Save the content to an object

        ResultFactory resultFactory = new ResultFactory(
                domain, domainFields);
        List<Result> resultList = resultFactory.getResults(transformedResults);
          *
          */
         return resultList;

    }

    public List<Callable<ArrayOfArrayOfString>> prepareCallableCollection (
            List<ParamOfGetResults> paramList){
        List<Callable<ArrayOfArrayOfString>> callableList
                = new ArrayList<Callable<ArrayOfArrayOfString>>();
        if (paramList.size() > 0) {
            Iterator it = paramList.iterator();
            while (it.hasNext()) {
                ParamOfGetResults param = (ParamOfGetResults)it.next();
                List<Callable<ArrayOfArrayOfString>> callables =
                                                prepareCallableCollection(param);
                if (callables.size()>0) {
                    callableList.addAll(callables);
                }

            }
        }
        return callableList;
    }
    
    public List<Callable<ArrayOfArrayOfString>> prepareCallableCollection (
            ParamOfGetResults param){
        List<Callable<ArrayOfArrayOfString>> callableList
                = new ArrayList<Callable<ArrayOfArrayOfString>>();
                int totalFound = param.getTotalFound();
                int size = IEbeyeAdapter.EBEYE_RESULT_LIMIT;
                //Work around to solve big result set issue
                Pagination pagination = new Pagination(totalFound, IEbeyeAdapter.EBEYE_RESULT_LIMIT);
                int nrOfQueries = pagination.calTotalPages();
                int start = 0;
                //TODO
                for (int i = 0; i < nrOfQueries; i++) {
                    if (i == nrOfQueries - 1 && (totalFound % IEbeyeAdapter.EBEYE_RESULT_LIMIT) > 0) {
                        size =pagination.getLastPageResults();
                    }

                    Callable<ArrayOfArrayOfString> callable =
                            new GetResultsCallable(param, start, size);
                    callableList.add(callable);
                    //TODO check
                    start = start+ size;
                }
        return callableList;
    }

    public List<ArrayOfArrayOfString> executeCallables(
            List<Callable<ArrayOfArrayOfString>> callables) throws MultiThreadingException {
        List<ArrayOfArrayOfString> ebeyeResultList = new ArrayList<ArrayOfArrayOfString>();
           ExecutorService pool = Executors.newCachedThreadPool();
        int counter = 0;
        try {
            for (Callable<ArrayOfArrayOfString> callable:callables) {
                Future<ArrayOfArrayOfString> future  = pool.submit(callable);
                ArrayOfArrayOfString rawResults = null;
                try {
                    rawResults = (ArrayOfArrayOfString) future
                            .get(IEbeyeAdapter.EBEYE_ONLINE_REQUEST_TIMEOUT, TimeUnit.SECONDS);
                } catch (InterruptedException ex) {
                    throw  new MultiThreadingException(ex.getMessage(), ex);
                } catch (ExecutionException ex) {
                    throw  new MultiThreadingException(ex.getMessage(), ex);
                } catch (TimeoutException ex) {
                    throw  new MultiThreadingException(ex.getMessage(), ex);
                }
                ebeyeResultList.add(rawResults);
                counter++;
                if (counter >  IEbeyeAdapter.EP_THREADS_LIMIT)
                    break;
            }
        }
        finally {
            pool.shutdown();
        }

        return ebeyeResultList;


    }

    public List<ParamOfGetResults> getNumberOfResults(
            List<ParamOfGetResults> paramOfGetResults) throws MultiThreadingException {
        List<ParamOfGetResults> params = new ArrayList<ParamOfGetResults>();
        ExecutorService pool = Executors.newCachedThreadPool();
        try {
            for (ParamOfGetResults param:paramOfGetResults) {
                Callable<Integer> callable = new NumberOfResultsCaller(param);
                Future<Integer> future = pool.submit(callable);
                try {
                    int totalFound = future.get(IEbeyeAdapter.EBEYE_ONLINE_REQUEST_TIMEOUT, TimeUnit.SECONDS);
                    if (totalFound > 0) {
                        param.setTotalFound(totalFound);
                        params.add(param);
                    }
                    
                } catch (InterruptedException ex) {
                    throw  new MultiThreadingException(ex.getMessage(), ex);
                } catch (ExecutionException ex) {
                    throw  new MultiThreadingException(ex.getMessage(), ex);
                } catch (TimeoutException ex) {
                    throw  new MultiThreadingException(ex.getMessage(), ex);
                }

            }
        }
        finally {
            pool.shutdown();
        }
        return params;
    }
/*
    public Map<String,String> getReferencedIds(List<String> ids, String targetDomain) {
        ArrayOfString uniprotIds = Transformer.transformToArrayOfString(ids);
        ArrayOfString fields = Transformer.transformToArrayOfString(
                IEbeyeAdapter.FieldsOfGetResults.id.name());
        String uniprotDomain = IEbeyeAdapter.Domains.uniprot.name();

        GetReferencedEntriesSet caller = new EbeyeCallable.GetReferencedEntriesSet(
                uniprotDomain, uniprotIds, targetDomain, fields);
        ArrayOfEntryReferences rawResults = caller.callGetReferencedEntriesSet();
        Map<String,String> results = Transformer.transformToMap(rawResults);
        return results;
    }


    public Map<String,String> getReferencedIds(String sourceDomain, List<String>sourceIds, String targetDomain) {
         ArrayOfString ids = Transformer.transformToArrayOfString(sourceIds);
        ArrayOfString fields = Transformer.transformToArrayOfString(
                IEbeyeAdapter.FieldsOfGetResults.id.name());
        //String uniprotDomain = IEbeyeAdapter.Domains.uniprot.name();

        GetReferencedEntriesSet caller = new EbeyeCallable.GetReferencedEntriesSet(
                sourceDomain, ids, targetDomain, fields);
        ArrayOfEntryReferences rawResults = caller.callGetReferencedEntriesSet();
        Map<String,String> results = Transformer.transformToMap(rawResults);
        return results;
    }
*/
      public Map<String, String> getNameMapByAccessions(String domain, Collection<String> accessions) {
        Domains domains = IEbeyeAdapter.Domains.valueOf(domain);
        List<String> configFields = null;
        switch (domains) {
            case chebi:
                configFields = IEbeyeAdapter.FieldsOfChebiNameMap.getFields();
                break;
           default: configFields = IEbeyeAdapter.FieldsOfUniprotNameMap.getFields();

        }
        ArrayOfString fields = Transformer
                .transformToArrayOfString(configFields);
        
        //TODO limited array size
        ArrayOfString idsArray = Transformer
                .transformToArrayOfString(accessions);

        GetEntriesCallable caller = new EbeyeCallable
                .GetEntriesCallable(domain, idsArray, fields);

        ArrayOfArrayOfString results = caller.callGetEntries();
        Map<String,String> resultMap = Transformer.transformToMap(results);
        return resultMap;
    }

/*
    public Collection<String> getUniprotXrefAccessions(List<ParamOfGetResults> params) throws MultiThreadingException {

        List<Callable<ArrayOfArrayOfString>> callableList
                 = prepareCallableCollection(params);


         //List<ArrayOfArrayOfString> rawResults =
            //      submitAll(paramOfGetResultsList);
         //List<ArrayOfArrayOfString> rawResults = submitAll(callableList);
         List<ArrayOfArrayOfString> rawResults = executeCallables(callableList);
         Collection<String> transformedResults
                    = Transformer.transformFieldValueToList(rawResults, true);
            //Save the content to an object

         return transformedResults;
    }
 * 
 */

    public ParamOfGetResults getNumberOfResults(
            ParamOfGetResults param){
        NumberOfResultsCaller callable = new NumberOfResultsCaller(param);
        int totalFound = callable.getNumberOfResults();
        param.setTotalFound(totalFound);
        return param;
    }

    public Collection<String> getRelatedUniprotAccessionSet(ParamOfGetResults param)
            throws MultiThreadingException {
        List<Callable<ArrayOfArrayOfString>> callableList
                 = prepareCallableCollection(param);         
         Collection<String> accessionList = getFieldValue(callableList, true);
         return accessionList;
    }

    public LinkedHashSet<String> getFieldValue(List<Callable<ArrayOfArrayOfString>> callableList, boolean isUNIPROTfield) throws MultiThreadingException {
         List<ArrayOfArrayOfString> rawResults = executeCallables(callableList);
         LinkedHashSet<String> accessionList = Transformer.transformFieldValueToList(rawResults, isUNIPROTfield);
         return accessionList;

    }

    public Set<String> getValueOfFields(List<ParamOfGetResults> paramOfGetResults) throws MultiThreadingException {
            List<Callable<ArrayOfArrayOfString>> callableList
                     = prepareCallableCollection(paramOfGetResults);
             List<ArrayOfArrayOfString> rawResults = executeCallables(callableList);
             Set<String> NameList = Transformer.transformFieldValueToList(rawResults, false);
             return NameList;
    }

        public Map<String, String> getMapOfFieldAndValue(List<ParamOfGetResults> params) throws MultiThreadingException{
        Map<String,String> fieldValueMap = new HashMap<String, String>();
        ExecutorService pool = Executors.newCachedThreadPool();
        try {
            for (ParamOfGetResults param: params) {
                Callable callable = new GetResultsCallable(param, 0,1);
                Future<ArrayOfArrayOfString> future  = pool.submit(callable);
                ArrayOfArrayOfString rawResults;
                try {
                    rawResults = (ArrayOfArrayOfString) future
                                .get(IEbeyeAdapter.EBEYE_ONLINE_REQUEST_TIMEOUT, TimeUnit.SECONDS);
                } catch (InterruptedException ex) {
                    throw  new MultiThreadingException(ex.getMessage(), ex);
                } catch (ExecutionException ex) {
                    throw  new MultiThreadingException(ex.getMessage(), ex);
                } catch (TimeoutException ex) {
                    throw  new MultiThreadingException(ex.getMessage(), ex);
                }
                 List<String> valueList =
                         new ArrayList<String>(Transformer.transformFieldValueToList(rawResults, false));
                 if (valueList.size() > 0) {
                     fieldValueMap.put(param.getQuery(), valueList.get(0));
                 }
            }
        }
        finally {
            pool.shutdown();
        }
        return fieldValueMap;
    }


    public Set<String> getRelatedUniprotAccessionSet(List<ParamOfGetResults> paramOfGetResults) throws MultiThreadingException {
        Set<String> accessionSet = new LinkedHashSet<String>();
        for (ParamOfGetResults param:paramOfGetResults )  {
            Collection<String> resultsPerDomain = getRelatedUniprotAccessionSet(param);
            if (resultsPerDomain.size() > 0) {
                accessionSet.addAll(resultsPerDomain);
            }
        }
        return accessionSet;
    }

    public Set<String> getValueOfFields(ParamOfGetResults paramOfGetResults) throws MultiThreadingException {
        List<Callable<ArrayOfArrayOfString>> callableList
                 = prepareCallableCollection(paramOfGetResults);
         List<ArrayOfArrayOfString> rawResults = executeCallables(callableList);
         Set<String> NameList = Transformer.transformFieldValueToList(rawResults, false);
         return NameList;
    }

    public Map<String,List<String>> getUniprotRefAccesionsMap(ParamOfGetResults paramOfGetResults) throws MultiThreadingException {
        List<Callable<ArrayOfArrayOfString>> callableList
                 = prepareCallableCollection(paramOfGetResults);
         List<ArrayOfArrayOfString> rawResults = executeCallables(callableList);
         Map<String,List<String>> uniprotRefAccesionsMap = Transformer.transformChebiResults(rawResults, true);
         return uniprotRefAccesionsMap;
    }

}

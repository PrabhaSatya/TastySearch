package tastysearch;
/**
 * com.tastysearch.QueryServlet class contains code to read and output results
 */

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentIndex {
    private Map<Integer,Document> documents;
    private Set<String> stopWords;
    private Map<String,List<Integer>> index;
    private static final Integer K = 20;
    private Logger logger = Logger.getLogger(DocumentIndex.class.getSimpleName());

    public List<Document> getTopKScores(String queryString) {
        long startTime = System.currentTimeMillis();
        logger.log(Level.INFO,"Query:" + queryString);
        String[] queryWords = queryString.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        // TODO: write code to clean queryString
        logger.log(Level.INFO,"Query Words:" + Arrays.toString(queryWords));
        Integer numQueryWords = queryWords.length;
        List<List<Integer>> queryTokenDocumentBuckets = new ArrayList<>(numQueryWords);
        for (String queryWord : queryWords) {
            if (null != index.get(queryWord)) {
                queryTokenDocumentBuckets.add(index.get(queryWord));
            }
        }
        Query query = new Query();
        ArrayList<Document> topDocuments = query.getTopKDocuments(queryTokenDocumentBuckets,documents,
                                                                                numQueryWords,K);
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        logger.log(Level.INFO,"Time taken for querying in Milli Seconds:" + totalTime);
        if(topDocuments.size() > K){
            return topDocuments.subList(0,K-1);
        }else{
            return topDocuments;
        }
    }

    public void createIndex(String inputFile, String stopWordsFile) throws IOException{
        Parse parse = new Parse();
        logger.log(Level.INFO,"***************Index creation starts.....*************");
        documents = parse.getDocuments(inputFile,0.2);
        logger.log(Level.INFO,"Total documents:"+documents.size());
        stopWords = parse.getStopWords(stopWordsFile);
        logger.log(Level.INFO,"Total stopwords:"+stopWords.size());
        index = parse.getIndex(documents,stopWords);
        logger.log(Level.INFO,"Unique words in the input sample(without stopwords):"+index.size());
        logger.log(Level.INFO,"***************Done with indexing*************");
    }
}

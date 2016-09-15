package tastysearch;
/**
 * com.tastysearch.TastySearch class contains code to read and output results
 */

import java.io.IOException;
import java.util.*;
class TastySearch {
     private void TastySearchInterface() throws IOException {
        Integer K = 20;
        Parse parse = new Parse();
        Map<Integer,Document> documents = parse.getDocuments("inputfiles/finefoods.txt",0.2);
        System.out.println("Total documents:"+documents.size());
        Set<String> stopWords = parse.getStopWords("inputfiles/stopwords");
        System.out.println("Total stopwords:"+stopWords.size());
        Map<String,List<Integer>> index = parse.getIndex(documents,stopWords);
        System.out.println("Unique words in the input sample(without stopwords):"+index.size());
        System.out.println("***************Done with indexing*************");
        long startTime = System.currentTimeMillis();
        String queryString = "Vitality canned dog food products";
        String[] queryWords = queryString.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
       // TODO: write code to clean queryString
        System.out.println("Query Words:" + Arrays.toString(queryWords));
        Integer numQueryWords = queryWords.length;
        List<List<Integer>> queryTokenDocumentBuckets = new ArrayList<>(numQueryWords);
        for (String queryWord : queryWords) {
            if (null != index.get(queryWord)) {
                queryTokenDocumentBuckets.add(index.get(queryWord));
            }
        }
        Query query = new Query();
        ArrayList<Document> topKDocuments = query.getTopKDocuments(queryTokenDocumentBuckets,documents,
                                                                                numQueryWords,K);
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Time taken for querying in Milli Seconds:" + totalTime);
        for(int i = 0; i < K; i++){
            printDocument(topKDocuments.get(i));
        }

    }

    private static void printDocument(Document document) {
        System.out.println("Matching Score:"+document.matchingScore);
        System.out.println("Summary:"+ document.summary);
        System.out.println("Text:"+ document.text);
        System.out.println("Static Review Score:"+ document.reviewScore);
    }
}

package com.tastysearch;
/**
 * com.tastysearch.Main class contains code to read and output results
 */

import javafx.util.Pair;

import java.io.IOException;
import java.util.*;
class Main {
    public static void main(String args[]) throws IOException {
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
        ArrayList<Pair<Double,Document>> topKDocuments = query.getTopKDocuments(queryTokenDocumentBuckets,documents,numQueryWords,K);
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Time taken for querying in Milli Seconds:" + totalTime);

        for(int i = 0; i < K; i++){
            printDocument(topKDocuments.get(i));
        }

    }

    private static void printDocument(Pair<Double,Document> documentAndScore) {
        System.out.println("Matching Score:"+documentAndScore.getKey());
        Document document = documentAndScore.getValue();
        System.out.println("Summary:"+ document.summary);
        System.out.println("Text:"+ document.text);
        System.out.println("Static Review Score:"+ document.reviewScore);
    }
}

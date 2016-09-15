package tastysearch;

import java.util.*;

/**
 * This class contains functions to find the topK reviews given a query and index of input dataset.
 */
class Query {
    ArrayList<Document> getTopKDocuments(List<List<Integer>> queryTokenDocumentLists,
                                                                Map<Integer,Document> documents,
                                                                Integer numQueryWords,
                                                                Integer K){
        Integer numQueryTokensInIndex = queryTokenDocumentLists.size();
        Map<Integer,Integer> documentCounts = getDocumentCounts(queryTokenDocumentLists);
        Map<Integer,ArrayList<Integer>> countBuckets = getBuckets(documentCounts);
        ArrayList<Document> topKDocuments = new ArrayList<>();
        for(Integer count = numQueryTokensInIndex; count >= 0; count--){
            ArrayList<Integer> bucket = countBuckets.get(count);
            if(null != bucket){
                ArrayList<Document> sortedBucket = sortBucketAsPerScore(bucket,documents);
                Double matchScore = (1.0 * count)/numQueryWords;
                for(Document document: sortedBucket){
                    document.matchingScore = matchScore;
                    topKDocuments.add(document);
                }
                if(topKDocuments.size() >= K){
                    break;
                }
            }
        }
        return topKDocuments;
    }

    private ArrayList<Document> sortBucketAsPerScore(ArrayList<Integer> bucket,Map<Integer,Document> documents) {
        ArrayList<Document> sortedBucket = new ArrayList<>();
        for (Integer documentId : bucket) {
            sortedBucket.add(documents.get(documentId));
        }
        Collections.sort(sortedBucket,new ScoreCompartor());
        return sortedBucket;
    }

    private Map<Integer,ArrayList<Integer>> getBuckets(Map<Integer, Integer> documentCounts) {
        Map<Integer,ArrayList<Integer>> countBuckets = new HashMap<>();
        for(Map.Entry<Integer,Integer> entry : documentCounts.entrySet()){
            Integer documentId = entry.getKey();
            Integer count = entry.getValue();
            ArrayList<Integer> bucket = countBuckets.get(count);
            if(null == bucket){
                bucket = new ArrayList<>();
                countBuckets.put(count,bucket);
            }
            bucket.add(documentId);
        }
        return countBuckets;
    }

    private Map<Integer,Integer> getDocumentCounts(List<List<Integer>> documentLists){
        Map<Integer,Integer> documentCounts = new HashMap<>();
        for (List<Integer> documentList : documentLists) {
            for (Integer documentId : documentList) {
                Integer count = documentCounts.get(documentId);
                if (null == count) {
                    documentCounts.put(documentId, 1);
                } else {
                    documentCounts.put(documentId, count + 1);
                }
            }
        }
        return documentCounts;
    }

    private class ScoreCompartor implements Comparator<Document>{
        @Override
        public int compare(Document doc1, Document doc2) {
            if(Double.parseDouble(doc1.reviewScore) < Double.parseDouble(doc2.reviewScore)){
                return 1;
            }else if (Double.parseDouble(doc1.reviewScore) > Double.parseDouble(doc2.reviewScore)){
                return -1;
            }else{
                return 0;
            }
        }
    }
}

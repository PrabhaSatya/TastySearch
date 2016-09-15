package tastysearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class contains function to sample and parse documents given a sample rate.
 * And other function to create an inverted index from a given list of documents.
 */
class Parse {
    Map<Integer,Document> getDocuments(String inputFile, double sampleRate) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        Map<Integer, Document> documents = new HashMap<>();
        int skipLines = 4;
        String delimiter = ":";
        String line;
        Integer documentId = -1;
        Integer parsedDocuments = -1;
        while (null != (line = bufferedReader.readLine())) {
            if (line.length() == 0) {
                parsedDocuments++;
            }
            if (line.length() == 0 && parsedDocuments % (1 / sampleRate) == 0) {
                Document document = new Document();
                for (int i = 0; i < skipLines; i++) {
                    bufferedReader.readLine();
                }
                line = bufferedReader.readLine();
                if(null == line){
                    break;
                }
                documentId++;
                document.id = documentId;
                document.reviewScore = line.split(delimiter)[1];
                bufferedReader.readLine();
                line = bufferedReader.readLine();
                document.summary = line.split(delimiter)[1];
                line = bufferedReader.readLine();
                document.text = line.split(delimiter)[1];
                documents.put(documentId, document);
            }

        }
        return documents;

    }

     Set<String> getStopWords(String stopWordsFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(stopWordsFile));
        Set<String> stopWords = new HashSet<>();
        String line;
        while(null != (line = bufferedReader.readLine())){
            if(!stopWords.contains(line)){
                stopWords.add(line);
            }
        }
        return stopWords;
    }

    Map<String,List<Integer>> getIndex(Map<Integer, Document> documents, Set<String> stopWords) {
        Map<String,List<Integer>> index = new HashMap<>();
        for(Map.Entry<Integer, Document> document:documents.entrySet()){
            parseDocument(index, document,stopWords);
        }
        return index;
    }

    private void parseDocument(Map<String, List<Integer>> index, Map.Entry<Integer, Document> document,
                               Set<String> stopWords) {
        Integer documentId = document.getValue().id;
        String summary = document.getValue().summary;
        String text = document.getValue().text;
        String[] summaryWords = summary.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        insertWordsToIndex(index, documentId, summaryWords,stopWords);
        String[] textWords = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        insertWordsToIndex(index,documentId,textWords,stopWords);
    }

    private void insertWordsToIndex(Map<String, List<Integer>> index, Integer documentId, String[] summaryWords,
                                    Set<String> stopWords) {
        for(String word:summaryWords){
            if(!stopWords.contains(word)){
                List<Integer> documentList = index.get(word);
                if(null == documentList){
                    documentList = new ArrayList<>();
                    documentList.add(documentId);
                    index.put(word, documentList);
                }else{
                    if(!documentList.contains(documentId)){
                        documentList.add(documentId);
                    }
                }
            }
        }
    }
}

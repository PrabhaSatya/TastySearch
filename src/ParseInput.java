import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Prabha on 14/09/16.
 */
class ParseInput {

    Map<Integer,Document> getDocuments(String inputFile, double sampleRate) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        Map<Integer, Document> documents = new HashMap();
        int skipLines = 4;
        String delimiter = ":";
        String line;
        Integer documentId = 0;
        Integer parseDocuments = 0;
        while (null != (line = bufferedReader.readLine())) {
            if (line.length() == 0) {
                parseDocuments++;
            }
            if (line.length() == 0 && parseDocuments % (1 / sampleRate) == 0) {
                Document document = new Document();
                for (int i = 0; i < skipLines; i++) {
                    bufferedReader.readLine();
                }
                line = bufferedReader.readLine();
                document.id = documentId;
                document.score = line.split(delimiter)[1];
                bufferedReader.readLine();
                line = bufferedReader.readLine();
                document.summary = line.split(delimiter)[1];
                line = bufferedReader.readLine();
                document.text = line.split(delimiter)[1];
                documents.put(documentId, document);
                documentId++;
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

    private void parseDocument(Map<String, List<Integer>> index, Map.Entry<Integer, Document> document,Set<String> stopWords) {
        Integer documentId = document.getValue().id;
        String summary = document.getValue().summary;
        String text = document.getValue().text;
        String[] summaryWords = summary.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        insertWordsToIndex(index, documentId, summaryWords,stopWords);
        String[] textWords = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        insertWordsToIndex(index,documentId,textWords,stopWords);
    }

    private void insertWordsToIndex(Map<String, List<Integer>> index, Integer documentId, String[] summaryWords,Set<String> stopWords) {
        for(String word:summaryWords){
            if(!stopWords.contains(word)){
                if(null == index.get(word)){
                    List<Integer> documentList = new ArrayList<>();
                    documentList.add(documentId);
                    index.put(word, documentList);
                }else{
                    if(!index.get(word).contains(documentId)){
                        index.get(word).add(documentId);
                    }
                }
            }
        }
    }
}

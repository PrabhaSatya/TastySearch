/**
 * Created by Prabha on 14/09/16.
 */

import java.io.IOException;
import java.util.*;
public class Main {
    public static void main(String args[]) throws IOException {
        ParseInput parseInput = new ParseInput();
        Map<Integer,Document> documents = parseInput.getDocuments("inputfiles/finefoods.txt",0.2);
        System.out.println("Total documents:"+documents.size());
        Set<String> stopWords = parseInput.getStopWords("inputfiles/stopwords");
        System.out.println("Total stopwords:"+stopWords.size());
        Map<String,List<Integer>> index = parseInput.getIndex(documents,stopWords);
        System.out.println("Unique words in the input sample(without stopwords):"+index.size());
    }
}

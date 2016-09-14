/**
 * Created by Prabha on 14/09/16.
 */

import java.io.IOException;
import java.util.*;
public class Main {
    public static void main(String args[]) throws IOException {
        ParseInput parseInput = new ParseInput();
        Map<Integer,Document> documents = parseInput.getDocuments("inputfiles/finefoods.txt",0.2);
    }

}

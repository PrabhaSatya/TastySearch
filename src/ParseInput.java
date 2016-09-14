import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Prabha on 14/09/16.
 */
class ParseInput {

    Map<Integer, Document> getDocuments(String inputFile, double sampleRate) throws IOException {

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
}

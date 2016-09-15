package tastysearch;

/**
 * Model class representing each document in the given data set.
 */
public class Document {
    Integer id;
    String summary;
    String text;
    String reviewScore;
    Double matchingScore;

    @Override
    public String toString() {
        return "Document{" +
                "matchingScore=" + matchingScore + "\n"+
                "id=" + id + "\n" +
                "summary='" + summary + "\n" +
                "text='" + text + "\n" +
                "reviewScore='" + reviewScore + "\n" +
                "}\n\n\n";
    }
}

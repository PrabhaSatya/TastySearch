package Launch;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import servlet.QueryServlet;
import tastysearch.DocumentIndex;

public class Main {

    public static void main(String[] args) throws Exception {
        String inputFile = args[0];
        String stopWordsFile = args[1];
        DocumentIndex documentIndex = new DocumentIndex();
        documentIndex.createIndex(inputFile,stopWordsFile);
        Tomcat tomcat = new Tomcat();
        String webPort = "8080";
        tomcat.setPort(Integer.valueOf(webPort));
        Context context = tomcat.addContext("/", new File(".").getAbsolutePath());
        Tomcat.addServlet(context, "tastysearch", new QueryServlet(documentIndex));
        context.addServletMapping("/tastysearch/*", "tastysearch");
        tomcat.start();
        tomcat.getServer().await();
    }
}

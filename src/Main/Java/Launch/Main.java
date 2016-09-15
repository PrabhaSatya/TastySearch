package Launch;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import servlet.TastySearch;

public class Main {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        String webPort = "8080";
        tomcat.setPort(Integer.valueOf(webPort));
        Context context = tomcat.addContext("/", new File(".").getAbsolutePath());
        Tomcat.addServlet(context, "tastysearch", new TastySearch());
        context.addServletMapping("/tastysearch/*", "tastysearch");
        tomcat.start();
        tomcat.getServer().await();
    }
}

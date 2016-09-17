package servlet;

import tastysearch.Document;
import tastysearch.DocumentIndex;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QueryServlet extends HttpServlet {
    private DocumentIndex documentIndex;

    public QueryServlet(DocumentIndex documentIndex){
        this.documentIndex = documentIndex;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String queryString = req.getParameter("query");
        List<Document> results = documentIndex.getTopKScores(queryString);
        Writer writer = resp.getWriter();
        if(0 == results.size()){
            writer.write("No matches found");
        }else{
            for(Document document:results){
                writer.write(document.toString());
            }
        }
        writer.flush();
    }

}
package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String pageTitle = "Search Interface";
    	request.setAttribute("title", pageTitle);
    	String query = request.getParameter("query");
    	if(query != null) {
    		final int numberOfItemsPerPage = 20;
    		request.setAttribute("isQuery", true);
    		request.setAttribute("query", request.getParameter("query"));
    		int page;
    		if(request.getParameter("page") != null) {
    			page = Integer.parseInt(request.getParameter("page"));
    		}
    		else {
    			page = 1;
    		}
    		SearchResult[] result = AuctionSearch.basicSearch(query, numberOfItemsPerPage*(page - 1), numberOfItemsPerPage);
    		request.setAttribute("searchResult", result);
    		request.setAttribute("page", page);
    	}
    	else {
    		request.setAttribute("isQuery", false);
    	}
    	request.getRequestDispatcher("/searchServlet.jsp").forward(request, response);
    }
}

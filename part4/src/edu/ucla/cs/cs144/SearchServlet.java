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
    		boolean isPageOne = false;
    		boolean isInvalidPage = false;
    		if(request.getParameter("page") != null) {
    			page = Integer.parseInt(request.getParameter("page"));
    			if(page == 1) {
    				isPageOne = true;
    			}
    			if(page < 1) {
    				isInvalidPage = true;
    			}
    		}
    		else {
    			page = 1;
    			isPageOne = true;
    		}
    		SearchResult[] result = AuctionSearch.basicSearch(query, numberOfItemsPerPage*(page - 1), numberOfItemsPerPage);
    		SearchResult[] lastPageTest = AuctionSearch.basicSearch(query, numberOfItemsPerPage*(page), numberOfItemsPerPage);
    		if(lastPageTest.length == 0) {
    			request.setAttribute("isPageLast", true);
    		}
    		else {
    			request.setAttribute("isPageLast", false);
    		}
    		if(result.length == 0) {
    			request.setAttribute("isNoResults", true);
    		}
    		else {
    			request.setAttribute("isNoResults", false);
    		}
    		request.setAttribute("isInvalidPage", isInvalidPage);
    		request.setAttribute("searchResult", result);
    		request.setAttribute("page", page);
    		request.setAttribute("isPageOne", isPageOne);
    	}
    	else {
    		request.setAttribute("isQuery", false);
    	}
    	request.getRequestDispatcher("/searchServlet.jsp").forward(request, response);
    }
}

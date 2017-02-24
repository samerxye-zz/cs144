package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String pageTitle = "Item Interface";
    	request.setAttribute("title", pageTitle);
    	String id = request.getParameter("id");
    	if(id != null) {
    		String result = AuctionSearch.getXMLDataForItemId(id);
    		if(result != null) {
    			request.setAttribute("isValidID", true);
    			request.setAttribute("result", result);
    		}
    		else {
    			request.setAttribute("isValidID", false);
    		}
    	}
    	else {
    		request.setAttribute("isValidID", false);
    	}
    	request.getRequestDispatcher("/itemServlet.jsp").forward(request, response);
    }
}

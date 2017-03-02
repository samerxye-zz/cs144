package edu.ucla.cs.cs144;

import java.io.IOException;
import java.net.HttpURLConnection;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ProxyServlet extends HttpServlet implements Servlet {
       
    public ProxyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	// Receive query
        String query = request.getParameter("query");
        if (query == null) {
            // TODO: remove redundant code
            PrintWriter out = response.getWriter();
            out.println("Error, no query!");
            out.close();
        }

    	String url = "http://google.com/complete/search?output=toolbar&q=" + URLEncoder.encode(query, "UTF-8");;

        // Open connection to Google server
        URL url_obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) url_obj.openConnection();
        con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();

		if (responseCode != 200) {
            // TODO: remove redundant code
            PrintWriter out = response.getWriter();
            out.println("Url: " + url);
            out.println("Error: Response code received: " + responseCode);
            out.close();
		}

		// Get response from Google Server
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer resp = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			resp.append(inputLine);
		}
		in.close();
        
        // Send response back
        PrintWriter out = response.getWriter();
        out.println(resp.toString());
        out.close();
    }
}

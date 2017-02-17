package edu.ucla.cs.cs144;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import edu.ucla.cs.cs144.DbManager;
import edu.ucla.cs.cs144.SearchRegion;
import edu.ucla.cs.cs144.SearchResult;

import java.util.*;

public class AuctionSearch implements IAuctionSearch {

	/* 
     * You will probably have to use JDBC to access MySQL data
     * Lucene IndexSearcher class to lookup Lucene index.
     * Read the corresponding tutorial to learn about how to use these.
     *
	 * You may create helper functions or classes to simplify writing these
	 * methods. Make sure that your helper functions are not public,
     * so that they are not exposed to outside of this class.
     *
     * Any new classes that you create should be part of
     * edu.ucla.cs.cs144 package and their source files should be
     * placed at src/edu/ucla/cs/cs144.
     *
     */
	
	private IndexSearcher searcher = null;
    private QueryParser parser = null;

    public AuctionSearch() throws IOException {
        searcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(new File("/var/lib/lucene/index1"))));
        parser = new QueryParser("Content", new StandardAnalyzer());
    }

	public SearchResult[] basicSearch(String query, int numResultsToSkip, 
			int numResultsToReturn) throws IOException, ParseException {

        ArrayList<SearchResult> results = new ArrayList<SearchResult>();
        
        // Default query: keyword OR search on "Content" field
        Query q = parser.parse(query); 
        TopDocs topDocs = searcher.search(q, numResultsToSkip + numResultsToReturn);

        System.out.println("Results found: " + topDocs.totalHits);
        ScoreDoc[] hits = topDocs.scoreDocs;
        for (int i = 0; i < hits.length; i++) {
            // skip the first numResultsToSkip results
            if (i < numResultsToSkip) 
                continue;

            // aggregate SearchResults
            Document doc = searcher.doc(hits[i].doc);
            //System.out.println(doc.get("ItemID") + " " + doc.get("Name") + " (" + hits[i].score + ")");
            SearchResult sr = new SearchResult();
            sr.setItemId(doc.get("ItemID"));
            sr.setName(doc.get("Name"));
            results.add(sr);
        }

        return results.toArray(new SearchResult[results.size()]);
	}

	public SearchResult[] spatialSearch(String query, SearchRegion region,
			int numResultsToSkip, int numResultsToReturn) throws IOException, ParseException {

        HashSet<Integer> spatialResultSet = new HashSet<Integer>();

        try {

            Connection conn = DbManager.getConnection(true);

            int item_id;
            
            String polygon = "'POLYGON((" + region.getRx() + " " + region.getRy() + ", "
                + region.getRx() + " " + region.getLy() + ", "
                + region.getLx() + " " + region.getLy() + ", "
                + region.getLx() + " " + region.getRy() + ", "
                + region.getRx() + " " + region.getRy() + "))'";

            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT ItemID FROM Location WHERE MBRContains(GeomFromText(" 
                + polygon + "),Coordinates);");

            while (rs.next()) {
                item_id = rs.getInt("ItemID");
                spatialResultSet.add(item_id);
            }

            // close connections
            s.close();
            rs.close();
            conn.close();
        
		} catch (SQLException ex){
            System.out.println("SQLException caught");
            System.out.println("---");
            while ( ex != null ){
                System.out.println("Message   : " + ex.getMessage());
                System.out.println("SQLState  : " + ex.getSQLState());
                System.out.println("ErrorCode : " + ex.getErrorCode());
                System.out.println("---");
                ex = ex.getNextException();
            }
        }

        SearchResult[] basicSearchResults = basicSearch(query, 0, spatialResultSet.size());
        ArrayList<SearchResult> results = new ArrayList<SearchResult>();
        int i = 0;
        
        for (SearchResult r: basicSearchResults) {
            if (results.size() >= numResultsToReturn) 
                break;

            if(spatialResultSet.contains(Integer.parseInt(r.getItemId()))) {
                if (i < numResultsToSkip) {
                    i++;
                    continue;
                }
                results.add(r);
            }
        }

		return results.toArray(new SearchResult[results.size()]);
	}

	public String getXMLDataForItemId(String itemId) {
		// TODO: Your code here!
		return "";
	}
	
	public String echo(String message) {
		return message;
	}

}

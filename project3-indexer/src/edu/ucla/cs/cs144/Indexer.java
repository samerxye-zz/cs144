package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.StringReader;
import java.io.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {
    
    /** Creates a new instance of Indexer */
    public Indexer() {
    }

    public void rebuildIndexes() {

        Connection conn = null;

        // create a connection to the database to retrieve Items from MySQL
    	try {
    	    conn = DbManager.getConnection(true);
    	} catch (SQLException ex) {
    	    System.out.println(ex);
    	}

        try {
            int item_id;
            String description, category;

            PreparedStatement prepareQueryCategory = conn.prepareStatement("SELECT * FROM Category WHERE ItemID = ?");
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM Item");
            while (rs.next()) {

                item_id = rs.getInt("ItemID");
                description = rs.getString("Description");
                System.out.println(item_id);
                System.out.println(description);

                prepareQueryCategory.setInt(1, item_id);
                ResultSet rs_category = prepareQueryCategory.executeQuery();
                while (rs_category.next()) {
                    category = rs_category.getString("Category");
                    //System.out.println(category);
                }
                rs_category.close();

                // System.out.println("Indexing hotel: " + hotel);
                // IndexWriter writer = getIndexWriter(false);
                // Document doc = new Document();
                // doc.add(new StringField("id", hotel.getId(), Field.Store.YES));
                // doc.add(new StringField("name", hotel.getName(), Field.Store.YES));
                // doc.add(new StringField("city", hotel.getCity(), Field.Store.YES));
                // String fullSearchableText = hotel.getName() + " " + hotel.getCity() + " " + hotel.getDescription();
                // doc.add(new TextField("content", fullSearchableText, Field.Store.NO));
                // writer.addDocument(doc);
            }

            // close connections
            prepareQueryCategory.close();
            s.close();
            rs.close();
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

    	/*
    	 * Add your code here to retrieve Items using the connection
    	 * and add corresponding entries to your Lucene inverted indexes.
         *
         * You will have to use JDBC API to retrieve MySQL data from Java.
         * Read our tutorial on JDBC if you do not know how to use JDBC.
         *
         * You will also have to use Lucene IndexWriter and Document
         * classes to create an index and populate it with Items data.
         * Read our tutorial on Lucene as well if you don't know how.
         *
         * As part of this development, you may want to add 
         * new methods and create additional Java classes. 
         * If you create new classes, make sure that
         * the classes become part of "edu.ucla.cs.cs144" package
         * and place your class source files at src/edu/ucla/cs/cs144/.
    	 * 
    	 */


        // close the database connection
    	try {
    	    conn.close();
    	} catch (SQLException ex) {
    	    System.out.println(ex);
    	}
    }    

    public static void main(String args[]) {
        Indexer idx = new Indexer();
        idx.rebuildIndexes();
    }   
}

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

import java.util.HashMap;

public class Indexer {
    
    /** Creates a new instance of Indexer */
    public Indexer() {
    }

    private IndexWriter indexWriter = null;

    public IndexWriter getIndexWriter() throws IOException {
        if (indexWriter == null) {
            Directory indexDir = FSDirectory.open(new File("/var/lib/lucene/index1"));
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, new StandardAnalyzer());
            indexWriter = new IndexWriter(indexDir, config);
        }
        return indexWriter;
    }

    public void closeIndexWriter() throws IOException {
        if (indexWriter != null) {
            indexWriter.close();
        }
    }

    public void rebuildIndexes() throws IOException {

        IndexWriter writer = getIndexWriter();

        try {
            Connection conn = DbManager.getConnection(true);

            int item_id;
            String name, description, category, fullSearchableText;
            HashMap item_categories = new HashMap();

            // Query #1
            // map ItemID -> categories for efficient retrieval later
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM Category");
            while (rs.next()) {
                item_id = rs.getInt("ItemID");
                category = rs.getString("Category");
                if (!item_categories.containsKey(item_id))
                    item_categories.put(item_id, "");
                item_categories.put(item_id, item_categories.get(item_id) + " " + category);
            }

            // Query #2
            // store each item as a document in index
            rs = s.executeQuery("SELECT * FROM Item");
            while (rs.next()) {

                item_id = rs.getInt("ItemID");
                name = rs.getString("Name");
                description = rs.getString("Description");
                
                Document doc = new Document();
                doc.add(new StringField("ItemID", String.valueOf(item_id), Field.Store.YES));
                doc.add(new StringField("Name", name, Field.Store.YES));
                fullSearchableText = name + " " + item_categories.get(item_id) + " " + description;
                doc.add(new TextField("Content", fullSearchableText, Field.Store.NO));
                writer.addDocument(doc);
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

        closeIndexWriter();
    }    

    public static void main(String args[]) throws IOException {
        Indexer idx = new Indexer();
        idx.rebuildIndexes();
    }   
}

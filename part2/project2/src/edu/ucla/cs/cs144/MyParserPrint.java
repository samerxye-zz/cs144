/* CS144
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */

package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;
interface TableRow {
    abstract String[] getRowStringAsArray();
    abstract String getRowAsString(String columnDivider);
}
class Item implements TableRow {
    private long id;
    private String userId;
    private String name;
    private double currently;
    private double buy_price;
    private double first_bid;
    private int number_of_bids;
    private String location;
    private double latitude;
    private double longitude;
    private String country;
    private String starts;
    private String ends;
    private String description;
    void setId(long id) {
        this.id = id;
    }
    void setUserId(String userId) {
        this.userId = userId;
    }
    void setName(String name) {
        this.name = name;
    }
    void setCurrently(double currently) {
        this.currently = currently;
    }
    void setBuyPrice(double buy_price) {
        this.buy_price = buy_price;
    }
    void setFirstBid(double first_bid) {
        this.first_bid = first_bid;
    }
    void setNumberOfBids(int number_of_bids) {
        this.number_of_bids = number_of_bids;
    }
    void setLocation(String location) {
        this.location = location;
    }
    void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    void setCountry(String country) {
        this.country = country;
    }
    void setStarts(String starts) {
        this.starts = starts;
    }
    void setEnds(String ends) {
        this.ends = ends;
    }
    void setDescription(String description) {
        this.description = description;
    }
    long getId() {
        return id;
    }
    String getUserId() {
        return userId;
    }
    String getName() {
        return name;
    }
    double getCurrently() {
        return currently;
    }
    double getBuyPrice() {
        return buy_price;
    }
    double getFirstBid() {
        return first_bid;
    }
    int getNumberOfBids() {
        return number_of_bids;
    }
    String getLocation() {
        return location;
    }
    double getLatitude() {
        return latitude;
    }
    double getLongitude() {
        return longitude;
    }
    String getCountry() {
        return country;
    }
    String getStarts() {
        return starts;
    }
    String getEnds() {
        return ends;
    }
    String getDescription() {
        return description;
    }
    @Override
    String[] getRowStringAsArray(){
        return {String.valueOf(id), userId, name, 
            String.valueOf(currently), 
            String.valueOf(buy_price), 
            String.valueOf(first_bid),
            String.valueOf(number_of_bids),
            location,String.valueOf(latitude),
            String.valueOf(longitude),
            country, starts, ends, description
        };
    }
    @Override
    String getRowAsString(String columnDivider) {
        return String.valueOf(id)+columnDivider+
            userId+columnDivider+
            name+columnDivider+
            String.valueOf(currently)+columnDivider+
            String.valueOf(buy_price)+columnDivider+
            String.valueOf(first_bid)+columnDivider+
            String.valueOf(number_of_bids)+columnDivider+
            location+columnDivider+
            String.valueOf(latitude)+columnDivider+
            String.valueOf(longitude)+columnDivider+
            country+columnDivider+
            starts+columnDivider+
            ends,+columnDivider+description;
    }
}
class Bidder implements TableRow {
    private String userId;
    private int rating;
    private String location;
    private String country;
    void setUserId(String userId) {
        this.userId = userId;
    }
    void setRating(int rating) {
        this.rating = rating;
    }
    void setLocation(String location) {
        this.location = location;
    }
    void setCountry(String country) {
        this.country = country;
    }
    String getUserId() {
        return userId;
    }
    int getRating() {
        return rating;
    }
    String getLocation() {
        return location;
    }
    String getCountry() {
        return country;
    }
    @Override
    String[] getRowStringAsArray(){
        return {userId, String.valueOf(rating),
            location,country};
    }
    @Override
    String getRowAsString(String columnDivider) {
        return userId+columnDivider+
            String.valueOf(rating)+columnDivider+
            location+columnDivider+
            country;
    }
}
class Bid implements TableRow {
    private long itemId;
    private String userId;
    private String time;
    private double amount;
    void setItemId(long itemId) {
        this.itemId = itemId;
    }
    void setUserId(String userId) {
        this.userId = userId;
    }
    void setTime(String time) {
        this.time = time;
    }
    void setAmount(double amount) {
        this.amount = amount;
    }
    long getItemId() {
        return itemId;
    }
    String getUserId() {
        return userId;
    }
    String getTime() {
        return time;
    }
    double getAmount() {
        return amount;
    }
    @Override
    String[] getRowStringAsArray(){
        return {String.valueOf(itemId), userId, 
            time, String.valueOf(amount)};
    }
    @Override
    String getRowAsString(String columnDivider) {
        return String.valueOf(itemId)+columnDivider+
            userId+columnDivider+
            time+columnDivider+
            String.valueOf(amount);
    }
}
class Seller implements TableRow {
    private String userId;
    private int rating;
    void setUserId(String userId) {
        this.userId = userId;
    }
    void setRating(int rating) {
        this.rating = rating;
    }
    String getUserId() {
        return userId;
    }
    int getRating() {
        return rating;
    }
    @Override
    String[] getRowStringAsArray(){
        return {userId, String.valueOf(rating)};
    }
    @Override
    String getRowAsString(String columnDivider) {
        return userId+columnDivider+
            String.valueOf(rating);
    }
}
class Category implements TableRow {
    private String category;
    private long itemId;
    void setCategory(String category) {
        this.category = category;
    }
    void setItemId(long itemId) {
        this.itemId = itemId;
    }
    String getCategory() {
        return category;
    }
    long getItemId() {
        return itemId;
    }
    @Override
    String[] getRowStringAsArray(){
        return {category, String.valueOf(itemId)};
    }
    @Override
    String getRowAsString(String columnDivider) {
        return category+columnDivider+
            String.valueOf(itemId);
    }
}
class MyParserPrint {
    
    static final String columnSeparator = "|*|";
    static DocumentBuilder builder;

    static HashMap<String, Item> itemHash;
    static HashMap<String, Bidder> bidderHash;
    static HashMap<String, Bid> bidHash;
    static HashMap<String, Category> categoryHash;
    static HashMap<String, Seller> sellerHash;
    private static BufferedWriter itemFileWriter;
    private static BufferedWriter bidderFileWriter;
    private static BufferedWriter bidFileWriter;
    private static BufferedWriter categoryFileWriter;
    private static BufferedWriter sellerFileWriter;

    static final String[] typeName = {
	"none",
	"Element",
	"Attr",
	"Text",
	"CDATA",
	"EntityRef",
	"Entity",
	"ProcInstr",
	"Comment",
	"Document",
	"DocType",
	"DocFragment",
	"Notation",
    };
    
    static class MyErrorHandler implements ErrorHandler {
        
        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
        }
        
    }
    
    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }
    
    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }
    
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }
    
    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }
    
    /* Process one items-???.xml file.
     */
    static void processFile(File xmlFile) {
        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on file " + xmlFile);
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }
        
        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed - " + xmlFile);
        
        /* Fill in code here (you will probably need to write auxiliary
            methods). */
        itemHash = new HashMap<String, Item>();
        bidderHash = new HashMap<String, Bidder>();
        bidHash = new HashMap<String, Bid>();
        categoryHash = new HashMap<String, Category>();
        sellerHash = new HashMap<String, Seller>();
        Element elem = doc.getDocumentElement();
        System.out.println("DEBUG: "+doc.getDocumentElement().toString());
        Elements[] items = getElementsByTagNameNR(elem, "Item");
        for(int i=0; i<items.length; i++) {
            processItem(item[i]);
        }
        itemFileWriter = new BufferedWriter(new FileWriter("item.dat",true));
        bidderFileWriter = new BufferedWriter(new FileWriter("bidder.dat",true));
        bidFileWriter = new BufferedWriter(new FileWriter("bid.dat",true));
        categoryFileWriter = new BufferedWriter(new FileWriter("category.dat",true));
        sellerFileWriter = new BufferedWriter(new FileWriter("seller.dat",true));
        writeToFile(itemFileWriter, itemHash);
        writeToFile(bidderFileWriter, bidderHash);
        writeToFile(bidFileWriter, bidHash);
        writeToFile(categoryFileWriter, categoryHash);
        writeToFile(sellerFileWriter, sellerHash);
        /**************************************************************/
        
        //recursiveDescent(doc, 0);
    }

    static void processItem(Element e) {
        Item item = new Item();
        item.setId(e.getAttribute("ItemID"));
        Element seller = getElementByTagNameNR(e, "Seller");
        processSeller(seller);
        Element[] categories = getElementsByTagNameNR(e, "Category");
        for(int i=0; i<categories.length; i++) {
            processCategory(categories[i], item.getId());
        }
        item.setUserId(seller.getAttribute("UserID"));
        item.setName(getElementTextByTagNameNR(e, "Name"))
        item.setBuyPrice(strip(getElementTextByTagNameNR(e, "Buy_Price")));
        item.setFirstBid(strip(getElementTextByTagNameNR(e, "First_Bid")));
        item.setCurrently(strip(getElementTextByTagNameNR(e, "Currently")));
        Element bidsElem = getElementsByTagNameNR(e, "Bids");
        Element[] bids = getElementsByTagNameNR(bidsElem, "Bid");
        for(int i=0; i<bids.length; i++) {
            Element bidder = getElementByTagNameNR(bids[i], "Bidder");
            processBid(bids[i], item.getId(), bidder.getAttribute("UserID"));
            processBidder(bidder);
        }
        String itemStarted = getElementTextByTagNameNR(e, "Started");
        String itemEnds = getElementTextByTagNameNR(e, "Ends");
        String started = xmlTimeToSqlTime(itemStarted);
        String ends = xmlTimeToSqlTime(itemEnds);
        item.setStarts(started);
        item.setEnds(ends);
        String desc = getElementTextByTagNameNR(e, "Description");
        if(desc.length() > 4000)
                desc = desc.substring(0, 4000);
        item.setDescription(desc);
        itemHash.put(item.getItemId(), item);
    }

    static void processSeller(Element e) {
        Seller seller = new Seller();
        seller.setUserId(e.getAttribute("UserID"));
        seller.setRating(e.getAttribute("Rating"));
        sellerHash.put(seller.getUserId(), seller);
    }

    static void processCategory(Element e, Long itemId) {
        Category category = new Category();
        category.setCategory(getElementText(e));
        category.setItemId(itemId);
        categoryHash.put(category.getCategory()+itemId.toString(), category);
    }

    static void processBid(Element e, Long itemId, String userId) {
        Bid bid = new Bid();
        bid.setItemId(itemId);
        bid.setUserId(userId);
        bid.setTime(getElementTextByTagNameNR(e, "Time"));
        bid.setAmount(strip(getElementTextByTagNameNR(e, "Amount")));
        bidHash.put(itemId.toString()+bid.getUserId()+bid.getTime(), bid);
    }

    static void processBidder(Element e) {
        Bidder bidder = new Bidder();
        bidder.setUserId(e.getAttribute("UserID"));
        bidder.setRating(e.getAttribute("Rating"));
        bidder.setLocation(getElementTextByTagNameNR(e, "Location"));
        bidder.setCountry(getElementTextByTagNameNR(e, "Country"));
        bidderHash.put(userId, bidder);
    }

    static String xmlTimeToSqlTime(String xmlTime) {
        SimpleDateFormat xmlFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = xmlFormat.parse(xmlTime);    
        }
        catch(ParseException pe) {
            System.out.println("ERROR: Cannot parse \"" + xmlTime + "\"");
        }
        return sqlFormat.format(date);
    }

    static void writeToFile(BufferedWriter output, HashMap<String, TableRow> data,) throws IOException
    {
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            output.write(pair.getValue().getRowAsString(columnSeparator));
            output.newLine();
            it.remove();
        }
        output.close();
    }
    
    public static void recursiveDescent(Node n, int level) {
        // adjust indentation according to level
        for(int i=0; i<4*level; i++)
            System.out.print(" ");
        
        // dump out node name, type, and value  
        String ntype = typeName[n.getNodeType()];
        String nname = n.getNodeName();
        String nvalue = n.getNodeValue();
        
        System.out.println("Type = " + ntype + ", Name = " + nname + ", Value = " + nvalue);
        
        // dump out attributes if any
        org.w3c.dom.NamedNodeMap nattrib = n.getAttributes();
        if(nattrib != null && nattrib.getLength() > 0)
            for(int i=0; i<nattrib.getLength(); i++)
                recursiveDescent(nattrib.item(i),  level+1);
        
        // now walk through its children list
        org.w3c.dom.NodeList nlist = n.getChildNodes();
        
        for(int i=0; i<nlist.getLength(); i++)
            recursiveDescent(nlist.item(i), level+1);
    }  
    
    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java MyParser [file] [file] ...");
            System.exit(1);
        }
        
        /* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        } 
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }
        
        /* Process all files listed on command line. */
        for (int i = 0; i < args.length; i++) {
            File currentFile = new File(args[i]);
            processFile(currentFile);
        }
    }
}

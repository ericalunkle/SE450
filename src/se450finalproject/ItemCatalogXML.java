package se450finalproject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ItemCatalogXML {

  public static HashMap<String, Item> getItemCatalogXML() {
	    HashMap<String, Item> Items = new HashMap<String, Item>();
        try {
        	//File file = new File(url.toURI());
        	 String fileName = "ItemList.xml";

             DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
             DocumentBuilder db = dbf.newDocumentBuilder();

             File xml = new File(fileName);
             if (!xml.exists()) {
                 System.err.println("**** XML File '" + fileName+ "' cannot be found");
                 System.exit(-1);
             }

            Document doc = db.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList itemEntries = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < itemEntries.getLength(); i++) {
                if (itemEntries.item(i).getNodeType() == Node.TEXT_NODE) {
                    continue;
                }
                
                String entryName = itemEntries.item(i).getNodeName();
                if (!entryName.equals("Item")) {
                    System.err.println("Unexpected node found: " + entryName);
                    break;
                }
                
                // Get a node attribute
                NamedNodeMap aMap = itemEntries.item(i).getAttributes();
                String itemname = aMap.getNamedItem("id").getNodeValue();

                // Get a named nodes
                Element elem = (Element) itemEntries.item(i);
                String itemId = elem.getElementsByTagName("Id").item(0).getTextContent();
                //Don't need this one
                String price = elem.getElementsByTagName("Price").item(0).getTextContent();
                int itemPrice = Integer.parseInt(price);
                
                // Here I would create an Item object using the data I just loaded from the XML
                Item i1 = new Item(itemId, itemPrice);
                Items.put(itemId, i1); 	
                
            }

        } catch (ParserConfigurationException | SAXException | IOException | InvalidDataException | DOMException e) {
            e.printStackTrace();
        }
        return Items;
    }
	
}
    

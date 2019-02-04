package se450finalproject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

public class LoadFacilityNetworkandInventoryXML {
	
	public static HashMap<String, FacilityImpl> loadNetwork() {
		  
			HashMap<String, FacilityImpl> facilityNetwork = new HashMap<>();
	        try {
	            String fileName = "FacilityandNetwork.xml";

	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder db = dbf.newDocumentBuilder();

	            File xml = new File(fileName);
	            if (!xml.exists()) {
	                System.err.println("**** XML File '" + fileName + "' cannot be found");
	                System.exit(-1);
	            }

	            Document doc = db.parse(xml);
	            doc.getDocumentElement().normalize();

	            NodeList facilityEntries = doc.getDocumentElement().getChildNodes();

	            for (int i = 0; i < facilityEntries.getLength(); i++) {
	                if (facilityEntries.item(i).getNodeType() == Node.TEXT_NODE) {
	                    continue;
	                }
	                
	                String entryName = facilityEntries.item(i).getNodeName();
	                if (!entryName.equals("Facility")) {
	                    System.err.println("Unexpected node found: " + entryName);
	                    break;
	                }
	                
	                // Get a node attribute
	                NamedNodeMap aMap = facilityEntries.item(i).getAttributes();
	                String facilityId = aMap.getNamedItem("Id").getNodeValue();

	                // Get a named nodes
	                Element elem = (Element) facilityEntries.item(i);
	                String facilityName = elem.getElementsByTagName("Name").item(0).getTextContent();
	                String facilityRate1 = elem.getElementsByTagName("Rate").item(0).getTextContent();
	                String facilityCost1 = elem.getElementsByTagName("Cost").item(0).getTextContent();
	                int facilityRate = Integer.parseInt(facilityRate1);
	                int facilityCost = Integer.parseInt(facilityCost1);
	                HashMap<Integer, Integer> schedule = new HashMap<>();
	                
	                
	                
	                // Get all nodes named "Book" - there can be 0 or more
	                HashMap<String, Integer> Links = new HashMap<>();
	                NodeList linkList = elem.getElementsByTagName("Link");
	                for (int j = 0; j < linkList.getLength(); j++) {
	                    if (linkList.item(j).getNodeType() == Node.TEXT_NODE) {
	                        continue;
	                    }

	                    entryName = linkList.item(j).getNodeName();
	                    if (!entryName.equals("Link")) {
	                        System.err.println("Unexpected node found: " + entryName);
	                        break;
	                    }

	                    // Get some named nodes
	                    elem = (Element) linkList.item(j);
	                    String linkId = elem.getElementsByTagName("LinkId").item(0).getTextContent();
	                    String linkDistance1 = elem.getElementsByTagName("Distance").item(0).getTextContent();
	                    int linkDistance = Integer.parseInt(linkDistance1);
	                    Links.put(linkId, linkDistance);
	                }
	                Schedule schedule1 = new Schedule(schedule);
	                FacilityImpl f = FacilityFactory.createFacility(facilityId, facilityCost, facilityRate, Links, schedule1);
	                facilityNetwork.put(facilityId, f);
	            }
	            
	        }
            catch (ParserConfigurationException | SAXException | IOException | InvalidDataException | DOMException e ) {
	            e.printStackTrace();
	        }
	        
	        return facilityNetwork;
	    }
	
    public static HashMap<String, FacilityImpl> loadInventoryandNetwork() {
    	HashMap<String, FacilityImpl> facilityNetwork = new HashMap<>();
    	facilityNetwork = LoadFacilityNetworkandInventoryXML.loadNetwork();
    	//System.out.println("Start facility");
    	try {    
	      //Now INVENTORY ENTRY
	    	
	        String fileName = "FacilityInventory.xml";
	
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	
	        File xml = new File(fileName);
	        if (!xml.exists()) {
	            System.err.println("**** XML File '" + fileName + "' cannot be found");
	            System.exit(-1);
	        }
	
	        Document doc = db.parse(xml);
	        doc.getDocumentElement().normalize();
	
	        NodeList facilityEntries = doc.getDocumentElement().getChildNodes();
	
	        for (int i = 0; i < facilityEntries.getLength(); i++) {
	            if (facilityEntries.item(i).getNodeType() == Node.TEXT_NODE) {
	                continue;
	            }
	            
	            String entryName = facilityEntries.item(i).getNodeName();
	            if (!entryName.equals("Facility")) {
	                System.err.println("Unexpected node found: " + entryName);
	                break;
	            }
	            
	            // Get a node attribute
	            NamedNodeMap aMap = facilityEntries.item(i).getAttributes();
	            String facilityId2 = aMap.getNamedItem("id").getNodeValue();
	
	            // Get a named nodes
	            Element elem = (Element) facilityEntries.item(i);
	
	            // Get all nodes named "Book" - there can be 0 or more
	            HashMap<String, Integer> Items = new HashMap<>();
	            NodeList itemList = elem.getElementsByTagName("Item");
	            for (int j = 0; j < itemList.getLength(); j++) {
	                if (itemList.item(j).getNodeType() == Node.TEXT_NODE) {
	                    continue;
	                }
	
	                entryName = itemList.item(j).getNodeName();
	                if (!entryName.equals("Item")) {
	                    System.err.println("Unexpected node found: " + entryName);
	                    break;
	                }
	
	                // Get some named nodes
	                elem = (Element) itemList.item(j);
	                String itemId = elem.getElementsByTagName("Id").item(0).getTextContent();
	                String itemQuantity1 = elem.getElementsByTagName("Quantity").item(0).getTextContent();
	                int itemQuantity = Integer.parseInt(itemQuantity1);
	                Items.put(itemId, itemQuantity);
	            }
	            //System.out.println("Adding items to facility object " + i);
	            
	            Facility f = facilityNetwork.get(facilityId2);
	            Inventory inventory = new Inventory(Items);
	            f.setInventory(inventory);
	            //System.out.println("Facility " + f.getFacilityId() + " items = " + f.getItems());
	        }
	    }
	        
	        catch (ParserConfigurationException | SAXException | IOException | InvalidDataException | DOMException e) {
	            e.printStackTrace();
	        } 
	    return facilityNetwork;
    }
       
}

package se450finalproject;

import java.io.File;
import java.io.IOException;
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

public class OrderXML {
	public static HashMap<String, Order> loadOrder() {
		HashMap<String, Order> orders = new HashMap<String, Order>();
        try {
        	//File file = new File(url.toURI());
        	 String fileName = "OrderContent.xml";

             DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
             DocumentBuilder db = dbf.newDocumentBuilder();

             File xml = new File(fileName);
             if (!xml.exists()) {
                 System.err.println("**** XML File '" + fileName+ "' cannot be found");
                 System.exit(-1);
             }

            Document doc = db.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList orderEntries = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < orderEntries.getLength(); i++) {
                if (orderEntries.item(i).getNodeType() == Node.TEXT_NODE) {
                    continue;
                }
                
                String entryName = orderEntries.item(i).getNodeName();
                if (!entryName.equals("Order")) {
                    System.err.println("Unexpected node found: " + entryName);
                    break;
                }
                
                // Get a node attribute
                NamedNodeMap aMap = orderEntries.item(i).getAttributes();
                String orderId = aMap.getNamedItem("id").getNodeValue();

                // Get a named nodes
                Element elem = (Element) orderEntries.item(i);
                String time = elem.getElementsByTagName("Time").item(0).getTextContent();
                String destination = elem.getElementsByTagName("Destination").item(0).getTextContent();
           
                FacilityImpl destinationFacility = FacilityManager.getInstance().getFacility(destination);
                
                int time2 = Integer.parseInt(time);
                
                
                // Get all nodes named "item" - there can be 0 or more
                HashMap<String, Integer> orderDetail = new HashMap<>();
                NodeList orderItems = elem.getElementsByTagName("Item");
                for (int j = 0; j < orderItems.getLength(); j++) {
                    if (orderItems.item(j).getNodeType() == Node.TEXT_NODE) {
                        continue;
                    }

                    entryName = orderItems.item(j).getNodeName();
                    if (!entryName.equals("Item")) {
                        System.err.println("Unexpected node found: " + entryName);
                        break;
                    }

                    // Get some named nodes
                    elem = (Element) orderItems.item(j);
                    String itemId = elem.getElementsByTagName("Id").item(0).getTextContent();
                    String itemQuantity = elem.getElementsByTagName("Quantity").item(0).getTextContent();
                    int itemQuantity1 = Integer.parseInt(itemQuantity);
                    orderDetail.put(itemId, itemQuantity1);
                    
                    
                } 
                
                Order o1 = OrderFactory.createOrder(orderId, destinationFacility, time2, orderDetail);
                orders.put(orderId, o1);
            }

            //System.out.println(orders);
        } catch (ParserConfigurationException | SAXException | IOException | DOMException | InvalidDataException e) {
            e.printStackTrace();
        }
        return orders;
    }
	
}


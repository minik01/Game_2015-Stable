import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
//import com.sun.org.apache.xml.internal.serialize.OutputFormat;
//import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.lang.*;
import java.util.*;
import java.sql.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlShipsManagement {

	private DBManager2 dbm2;
	private static  File fXmlFile;
	private static  DocumentBuilderFactory docBuilderFactory;
	private static  DocumentBuilder docBuilder;
	private static  Document doc;
	private static  NodeList nodeList;
	private static String docName="ships_data.xml";
	private static String nodeName="ship";
	
	// always call it before using file
	private static void openXML() {
		try {
			// open file, prepare document and normalize it
			fXmlFile = new File(docName);
			if(!fXmlFile.exists()) {
				fXmlFile.createNewFile();
			} 
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(fXmlFile);
	
			doc.getDocumentElement().normalize();
			
			nodeList = doc.getElementsByTagName(nodeName); // ship for this case
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// constructor to prevent NullException
	public XmlShipsManagement(String fileName,String nodesName, DBManager2 db) {
		dbm2 = db;
		docName = fileName;
		nodeName = nodesName;
		//openXML();
	}
	
	// How many ships exist
	
	public int getAllShipsAmmount() {
		
		openXML();
		return nodeList.getLength();
	}
	
	// get ships table by sql and save to ships_data.xml
	
	public boolean copyToXML() {
		
		try {
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docBuilderFactory.newDocumentBuilder();
		    doc = docBuilder.newDocument();
	    
		
			
			ResultSet rs = dbm2.getDbm().select2("select * from ship");
			
		    ResultSetMetaData rsmd = rs.getMetaData();
		    int colCount = rsmd.getColumnCount();
		    
		    Element ships = doc.createElement("ships");
		    doc.appendChild(ships);
		    
		    while (rs.next()) {
		      Element ship = doc.createElement("ship");
		      ships.appendChild(ship);
		      for (int i = 1; i <= colCount; i++) {
		        String columnName = rsmd.getColumnName(i);
		        Object value = rs.getObject(i);
		        Element node = doc.createElement(columnName);
		        node.appendChild(doc.createTextNode(value.toString()));
		        ship.appendChild(node);
		      }
		   }
		   
		    
		    // write the content into xml file
		    
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(docName));
			transformer.transform(source, result); // execute to file
			
//			print in console
//			StreamResult result2 = new StreamResult(System.out);
//			
//			transformer.transform(source, result2); // execute to console
			 
			System.out.println("File "+docName+" saved!");
		    
		  }catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  }catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }catch(SQLException e){
			  e.printStackTrace();
		 }
	      return true;
	}
	
	// Remove all nodes from xml
	public static void removeAll(Node node, short nodeType, String name) {
	    if (node.getNodeType() == nodeType && (name == null || node.getNodeName().equals(name))) {
	      node.getParentNode().removeChild(node);
	    } else {
	      NodeList list = node.getChildNodes();
	      for (int i = 0; i < list.getLength(); i++) {
	        removeAll(list.item(i), nodeType, name);
	      }
	    }
	  }
	// Read value from ship parameter
	
	public String readNodeValue(int object_id, String param) {
		
		openXML();
		
		String id = Integer.toString(object_id);
		String value = null;
		
		// find object among the all of them
		for (int temp = 0; temp < nodeList.getLength(); temp++) {

			// node of current ship from list of all
			Node object = nodeList.item(temp);
			
			// parse to element to check attribute
			Element currObject = (Element)object;
			
			if(currObject.getAttribute("id").equals(id)) {
			
				// get all nodes from ship
				NodeList childNodes = object.getChildNodes();
				
				// find needed node in ship
				
				for (int i = 0; i != childNodes.getLength(); ++i)
				{
					Node child = childNodes.item(i);
					if (!(child instanceof Element))
						continue;
					
						if (child.getNodeName().equals(param))
							value = child.getFirstChild().getNodeValue() ;
				}
			}
		}
		
		return value;
	}
	// Write value to ship parameter
	
	public void writeNodeValue(int object_id, String param, String value) {
		
		try {
			openXML();
			
			String id = Integer.toString(object_id);
			
			// find object among the all of them
			for (int temp = 0; temp < nodeList.getLength(); temp++) {
	
				// node of current ship from list of all
				Node object = nodeList.item(temp);
				
				// parse to element to check attribute
				Element currObject = (Element)object;
				
				if(currObject.getAttribute("id").equals(id)) {
				
					// get all nodes from ship
					NodeList childNodes = object.getChildNodes();
					
					// find needed node in ship
					
					for (int i = 0; i != childNodes.getLength(); ++i)
					{
						Node child = childNodes.item(i);
						if (!(child instanceof Element))
							continue;
						
							if (child.getNodeName().equals(param))
								child.getFirstChild().setNodeValue(value) ;
					}
				}
			}
			
			// Write updated XML
			/*
			OutputFormat format = new OutputFormat(doc);
			format.setIndenting(true);
			String filename = docName;
			XMLSerializer serializer = new XMLSerializer(
				new FileOutputStream(new File(filename)), format);
			serializer.serialize(doc);*/
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(docName));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/*
	public static void main(String argv[]) {
 
		try {

			openXML();
			
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	
			System.out.println("----------------------------");
	
			for (int temp = 0; temp < nodeList.getLength(); temp++) {
	
				Node nNode = nodeList.item(temp);
	
				System.out.println("\nCurrent object :" + nNode.getNodeName());
	
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
					Element eElement = (Element) nNode;
	
					System.out.println("Ship id : " + eElement.getAttribute("id"));
					System.out.println("Owner id : " + eElement.getElementsByTagName("owner_id").item(0).getTextContent());
					System.out.println("Current x : " + eElement.getElementsByTagName("x").item(0).getTextContent());
					System.out.println("Current y : " + eElement.getElementsByTagName("y").item(0).getTextContent());
					System.out.println("Current sector : " + eElement.getElementsByTagName("sector").item(0).getTextContent());
	
				}
			}
			
			
			
			
			// Change value of owner id for choosen ship
			
			// get value
			System.out.println("---------------\nGive me object id:	");
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String id = bufferRead.readLine();
			System.out.println("---------------\nGive me param:	");
			BufferedReader bufferRead2 = new BufferedReader(new InputStreamReader(System.in));
			String param = bufferRead2.readLine();
			System.out.println("---------------\nGive me value:	");
			BufferedReader bufferRead3 = new BufferedReader(new InputStreamReader(System.in));
			String value = bufferRead3.readLine();
			
			// find object among the all of them
			for (int temp = 0; temp < nodeList.getLength(); temp++) {
	
				// node of current object from list of all
				Node object = nodeList.item(temp);
				
				// parse to element to check attribute
				Element currObject = (Element)object;
				
				
				
				if(currObject.getAttribute("id").equals(id)) {
				
					// get all nodes from object
					NodeList childNodes = object.getChildNodes();
					
					// find needed node in object
					
					for (int i = 0; i != childNodes.getLength(); ++i)
					{
						Node child = childNodes.item(i);
						if (!(child instanceof Element))
							continue;
						
							if (child.getNodeName().equals(param))
								child.getFirstChild().setNodeValue(value) ;
					}
				}
			}
			// Write updated XML
	
			OutputFormat format = new OutputFormat(doc);
			format.setIndenting(true);
			String filename = docName;
			XMLSerializer serializer = new XMLSerializer(
				new FileOutputStream(new File(filename)), format);
			serializer.serialize(doc);
			
			System.out.println("Value changed successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
		}*/
 
}
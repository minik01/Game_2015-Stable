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
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.lang.*;
import java.util.*;

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
	public XmlManagement(String fileName,String nodesName, DBManager2 db) {
		dbm2 = db;
		docName = filename;
		nodeName = nodesName;
		openXML();
	}
	
	// How many ships exist
	
	public int getAllShipsAmmount() {
		
		openXML();
		return nodeList.getLength();
	}
	
	// get ships table by sql and save to ships_data.xml
	
	public boolean copyToXML() {
		
		openXML();
		
		try {
			Connection con = dbm2.getConnection();
			
			ResultSet rs = con.createStatement().executeQuery("select * from ships");

		    ResultSetMetaData rsmd = rs.getMetaData();
		    int colCount = rsmd.getColumnCount();

		    while (rs.next()) {
		      Element row = doc.createElement("Row");
		      results.appendChild(row);
		      for (int i = 1; i <= colCount; i++) {
		        String columnName = rsmd.getColumnName(i);
		        Object value = rs.getObject(i);
		        Element node = doc.createElement(columnName);
		        node.appendChild(doc.createTextNode(value.toString()));
		        row.appendChild(node);
		      }
		      
		   // Write updated XML
				
			OutputFormat format = new OutputFormat(doc);
			format.setIndenting(true);
			String filename = docName;
			XMLSerializer serializer = new XMLSerializer(
				new FileOutputStream(new File(filename)), format);
			serializer.serialize(doc);
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
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
			
			OutputFormat format = new OutputFormat(doc);
			format.setIndenting(true);
			String filename = docName;
			XMLSerializer serializer = new XMLSerializer(
				new FileOutputStream(new File(filename)), format);
			serializer.serialize(doc);

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
		}
 
}*/
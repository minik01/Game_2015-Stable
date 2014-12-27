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

public class XmlManagement {

	private File fXmlFile;
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private NodeList nodeList;
	private static String docName="ships_data.xml";
	
	// always call it before using file
	private void openXML() {
		// open file, prepare document and normalize it
		fXmlFile = new File(docName);
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docBuilderFactory.newDocumentBuilder();
		doc = docBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();
		
		nodeList = doc.getElementsByTagName("ship"); // ship for this case

	}
	// constructor to prevent NullException
	public XmlMaanagement() {
		
		openXML();
	}
	
	// How many ships exist
	
	public int getAllShipsAmmount() {
		
		openXML();
		return nodeList.getLength();
	}
	
	// Read value from ship parameter
	
	public String readNodeValue(int object_id, String param) {
		
		openXML();
		
		String id = object_id.toString();
		String value;
		
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
			
			String id = object_id.toString();
			
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
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String param = bufferRead.readLine();
			System.out.println("---------------\nGive me value:	");
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String value = bufferRead.readLine();
			
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
			String filename = "staff"+System.currentTimeMillis()+".xml";
			XMLSerializer serializer = new XMLSerializer(
				new FileOutputStream(new File(filename)), format);
			serializer.serialize(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		}
 
}
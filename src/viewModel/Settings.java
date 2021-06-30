package viewModel;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Settings {
	String algType = "";
	String algLoc = "";
	String normal = "";
	int send = 0;
	int recive = 0;
	
	public Settings() {
		try {
	         File inputFile = new File("settings.xml");
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         NodeList nList = doc.getElementsByTagName("algorithem");
	         
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eElement = (Element) nNode;
	               algType = eElement
	                  .getElementsByTagName("type")
	                  .item(0)
	                  .getTextContent();
	               algLoc = eElement
	 	                  .getElementsByTagName("loc")
	 	                  .item(0)
	 	                  .getTextContent();
	            }
	         }
	         
	         nList = doc.getElementsByTagName("normal");
	         
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eElement = (Element) nNode;
	               normal = eElement
	                  .getElementsByTagName("file")
	                  .item(0)
	                  .getTextContent();
	            }
	         }
	         
	         nList = doc.getElementsByTagName("ports");
	         
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eElement = (Element) nNode;
	               send = Integer.parseInt( eElement
	                  .getElementsByTagName("send")
	                  .item(0)
	                  .getTextContent());
	               recive = Integer.parseInt( eElement
	 	                  .getElementsByTagName("recive")
	 	                  .item(0)
	 	                  .getTextContent());
	            }
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}

}

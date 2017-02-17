package com.niko.xml.digsig;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ExtractXml {
	/**
	 * XML 파일에서 값을 추출한다.
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public static void extractSigXml(String fileName) throws Exception {
		File xmlFile = new File(fileName);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		Node node = null;
		Node nd = null;
		NodeList nList = doc.getElementsByTagName("firstname");
		String str = null;
		for (int i = 0; i < nList.getLength(); i++) {
			node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				if (eElement.hasChildNodes()) {
					NodeList nl = node.getChildNodes();
					nd = nl.item(0);
					System.out.println(nd.getTextContent());
					str = nd.getTextContent();
				}
				System.out.println("");
			}
		}
		byte[] bytes = str.getBytes();
		byte[] dec = Base64.decodeBase64(bytes);

		FileIO.writeFile(fileName + "_ORIGINAL", dec);
	}

	public static void main(String fileName) throws Exception {
		extractSigXml(fileName);
	}
}

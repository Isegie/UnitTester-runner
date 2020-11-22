package com.ise.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Xml file service to extract and change data in xml
 * 
 * @author ise
 *
 */
public class XmlService {

	private static final String DEPENDENCY = "dependency";
	private static final String ARTIFACT_ID = "artifactId";
	private static final String DEPENDENCIES = "dependencies";

	public static Document constructDoocument(Path filePath)
			throws SAXException, IOException, ParserConfigurationException {

		Document document;
		DocumentBuilderFactory factory;
		DocumentBuilder documentBuilder;

		File xmlFile = filePath.toFile();

		factory = DocumentBuilderFactory.newInstance();
		documentBuilder = factory.newDocumentBuilder();
		document = documentBuilder.parse(xmlFile);
		document.getDocumentElement().normalize();

		return document;
	}

	public static boolean fetchDataFromPomXml(String fileName)
			throws SAXException, IOException, ParserConfigurationException {
		boolean checkArtifactId = false;
		Path filePath = Paths.get(fileName);
		Document doc;

		doc = constructDoocument(filePath);

		NodeList listOfDependencies = doc.getElementsByTagName(DEPENDENCIES);

		for (int i = 0; i < listOfDependencies.getLength(); i++) {
			Node dependencies = listOfDependencies.item(i);

			for (int k = 0; k < dependencies.getChildNodes().getLength(); k++) {
				Node dependency = dependencies.getChildNodes().item(k);

				if (dependency.getNodeName().equals(DEPENDENCY) && dependency.getNodeType() == Node.ELEMENT_NODE) {

					for (int j = 0; j < dependency.getChildNodes().getLength(); j++) {
						Node item = dependency.getChildNodes().item(j);
						if (item.getNodeName().equals(ARTIFACT_ID)
								&& item.getTextContent().equals("junit-platform-runner")) {
							checkArtifactId = true;
						}
					}
				}
			}
		}

		return checkArtifactId;
	}

}

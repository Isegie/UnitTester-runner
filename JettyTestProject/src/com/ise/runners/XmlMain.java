package com.ise.runners;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.ise.services.XmlService;

public class XmlMain {

	public static void main(String[] args) {

			try {
				boolean checkDependency = XmlService.fetchDataFromPomXml("pom.xml");

				if (checkDependency) {
					System.out.println("Dependency junit-platform-runner included");
				}else {
					System.out.println("Dependency junit-platform-runner not included");
				}

			} catch (SAXException | IOException | ParserConfigurationException e) {
				e.printStackTrace();
			}
		
	}

}

package com.ise.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.ByteArrayISO8859Writer;
import org.eclipse.jetty.util.resource.PathResource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import com.ise.entities.WebEntity;
import com.ise.services.XmlService;

/**
 * 
 * @author ise
 *
 */
class WebServiceTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		Path userDir = Paths.get(System.getProperty("user.dir"));
		PathResource pathResource = new PathResource(userDir);

		Server server = new Server(8090);

		ResourceHandler resourceHandler = new ResourceHandler();

		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setBaseResource(pathResource);

		server.setHandler(new ContentHandler());

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testPomXml() throws MalformedURLException {
		Path filePath = Paths.get("pom.xml");
		String content = "";
		try {
			content = Files.readString(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebEntity client = new WebEntity();
		String fetchedPomData = client.getContent(new URL("http://localhost:8090/pom.xml"));

		assertEquals(content, fetchedPomData);
	}

	@Test
	public void testArtifactId() {
		boolean checkDependency = false;
		try {
			checkDependency = XmlService.fetchDataFromPomXml("pom.xml");
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		assertEquals(true, checkDependency);
	}

	private static class ContentHandler extends AbstractHandler {
		@Override
		public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {

			OutputStream out = response.getOutputStream();
			@SuppressWarnings("resource")
			ByteArrayISO8859Writer writer = new ByteArrayISO8859Writer();
			writer.write("Jetty response");
			writer.flush();
			response.setIntHeader(String.valueOf(writer.size()), writer.size());
			writer.writeTo(out);
			out.flush();
		}
	}

}

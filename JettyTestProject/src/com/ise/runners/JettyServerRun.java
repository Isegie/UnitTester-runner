package com.ise.runners;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.PathResource;
import org.eclipse.jetty.util.resource.Resource;

public class JettyServerRun {

	public static void main(String[] args) {

		Server server;
		try {
			server = createServer(fetchUserDirectoryResource());
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Server createServer(Resource baseResource) throws Exception {

		Server server = new Server(8090);

		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setBaseResource(baseResource);

		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { resourceHandler, new DefaultHandler() });
		server.setHandler(handlers);

		return server;
	}

	public static PathResource fetchUserDirectoryResource() {
		Path userDirectory = Paths.get(System.getProperty("user.dir"));
		PathResource resource = new PathResource(userDirectory);
		return resource;

	}
}

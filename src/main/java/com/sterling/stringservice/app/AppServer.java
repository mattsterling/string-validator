package com.sterling.stringservice.app;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class AppServer {

    public static void main(final String[] args) {

        // Primitive check for port number.
        int port = 8080;
        if(args.length != 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch(NumberFormatException e) {
                // TODO logging;
            }
        }

        // Servlet holder for the resources
        ResourceConfig config = new ResourceConfig();
        config.packages("com.sterling.stringservice.web.resource");
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));
        // Make sure JSON object serialization is on.
        servlet.setInitParameter("jersey.config.server.provider.classnames",
                "org.glassfish.jersey.jackson.JacksonFeature");

        // Set up the server and the application root path
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(servlet,"/*");
        Server server = new Server(port);
        server.setHandler(context);

        // Clean shut down hook.
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            if(null != server && server.isRunning()) server.destroy();
            return;
        }));

        try {
            server.start();
            server.join(); // blocks until something kills it.
        } catch (Exception e) {
            e.printStackTrace();
            server.destroy();
        }

    }
}

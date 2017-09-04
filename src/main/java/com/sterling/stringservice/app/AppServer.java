package com.sterling.stringservice.app;


import com.sterling.stringservice.web.resource.StringServiceApplication;
import com.sterling.stringservice.web.resource.StringV1Resource;
import io.swagger.jaxrs.listing.ApiListingResource;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Main entry point for the string validator service.
 */
public class AppServer {

    private static final Logger LOG = LogManager.getLogger(AppServer.class);

    /**
     * Input args are option. The first argument will be the only parsed to determine the port.
     * If it is not a valid number it will be ignored and port 8080 will be used.
     * @param args args containing the port to run on.
     */
    public static void main(final String[] args) {
        // Setup primitive console logging.
        setupLogger();

        // Primitive check for port number.
        int port = 8080;
        if(args.length != 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch(NumberFormatException e) {
                LOG.warn("Unable to process command line argument for port. Defaulting to 8080");
            }
        }

        // Servlet holder for the resources
        ResourceConfig config = new StringServiceApplication();
        config.packages(StringV1Resource.class.getPackage().getName(),
                ApiListingResource.class.getPackage().getName());
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));
        // Make sure JSON object serialization
        servlet.setInitOrder(1);
        servlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                "org.glassfish.jersey.jackson.JacksonFeature");


        // Set up the server and the application root path
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(servlet,"/*");

        Server server = new Server(port);
        server.setHandler(context);

        // Clean shut down hook.
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            LOG.info("Encountered shut down signal.");
            if(null != server && server.isRunning()) server.destroy();
            return;
        }));

        try {
            LOG.info("Starting server...");
            server.start();
            server.join(); // blocks until something kills it.
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            server.destroy();
        }

    }

    public static void setupLogger() {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        AbstractConfiguration config = (AbstractConfiguration) ctx.getConfiguration();
        ConsoleAppender appender = ConsoleAppender.createDefaultAppenderForLayout(PatternLayout.createDefaultLayout());
        appender.start();
        config.addAppender(appender);
        AppenderRef[] refs = new AppenderRef[] { AppenderRef.createAppenderRef(appender.getName(), null, null) };
        LoggerConfig loggerConfig = LoggerConfig.createLogger(true,
                Level.ALL,
                LogManager.ROOT_LOGGER_NAME,
                "true",
                refs,
                null,
                config,
                null);
        loggerConfig.addAppender(appender, null, null);
        config.addLogger(LogManager.ROOT_LOGGER_NAME, loggerConfig);
        ctx.updateLoggers();
    }
}

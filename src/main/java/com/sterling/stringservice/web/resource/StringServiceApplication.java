package com.sterling.stringservice.web.resource;

import io.swagger.jaxrs.config.BeanConfig;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Custom Application class. For now it just loads Swagger Configuration.
 */
@ApplicationPath("/*")
public class StringServiceApplication extends ResourceConfig {

    public StringServiceApplication() {
        super();
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setTitle("String Service.");
        beanConfig.setVersion("1.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setBasePath("/");
        beanConfig.setPrettyPrint(true);
        beanConfig.setResourcePackage(StringV1Resource.class.getPackage().getName());
        beanConfig.setScan(true);
    }
}

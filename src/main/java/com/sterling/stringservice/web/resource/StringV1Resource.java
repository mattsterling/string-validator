package com.sterling.stringservice.web.resource;


import com.sterling.stringservice.model.ValidateResponse;
import com.sterling.stringservice.validator.StringValidator;
import com.sterling.stringservice.validator.StringValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * String validation resource. For this purpose the api takes in a string, and validation type.
 * This example has only one type of validation, panagram, and will fail if anything else is provided.
 */
@Path("/string-service/v1")
public class StringV1Resource {
    private static final Logger LOG = LogManager.getLogger(StringV1Resource.class);

    /**
     * GET method that takes in a String resource parameter and performs validation
     * based off the parameter type. Default validation type is PANAGRAM.
     * @param input String to validate.
     * @param validationType Type of validation to perform.
     * @return
     */
    @GET
    @Path("/validate/{input}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validate(@PathParam("input") final String input,
                             @QueryParam("type") String validationType) {
        // Check for the type of validation to perform.
        ValidateResponse.ValidationType type = null;
        LOG.debug(String.format("Request provided validation type %s", validationType));
        if(null != validationType){
            validationType = validationType.toUpperCase(); // Because of the enum syntax
            try {
                type = ValidateResponse.ValidationType.valueOf(validationType);
            } catch (IllegalArgumentException e) {
                LOG.error(String.format("Bad validation type provided %s", validationType));
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(e.getMessage())
                        .build();
            }
        } else {
            // Didn't provide a validation type so we start with the default.
            type = ValidateResponse.ValidationType.PANAGRAM;
        }
        LOG.debug(String.format("Using validation type %s", validationType));
        // In a production system creating a new object every request might not make sense.
        StringValidator validator = StringValidatorFactory.create(type);
        if (null == validator) {
            LOG.error(String.format("Validation type %s provided, with no know implementation", type));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Validation type not implemented")
                    .build();
        }
        ValidateResponse resp = validator.validate(input);
        return Response.ok(resp).build();

    }
}

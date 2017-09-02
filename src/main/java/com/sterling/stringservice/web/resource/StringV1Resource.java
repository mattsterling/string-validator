package com.sterling.stringservice.web.resource;


import com.sterling.stringservice.model.ValidateResponse;
import com.sterling.stringservice.validator.StringValidator;
import com.sterling.stringservice.validator.StringValidatorFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * String validation resource. For this purpose the api takes in a string, and validation type.
 * This example has only one type of validation, panagram, and will fail if anything else is provided.
 */
@Path("/string-service/v1")
public class StringV1Resource {

    @GET
    @Path("/validate/{input}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validate(@PathParam("input") final String validate,
                             @QueryParam("type") String validationType) {
        // Check for the type of validation to perform.
        ValidateResponse.ValidationType type = null;
        if(null != validationType){
            try {
                type = ValidateResponse.ValidationType.valueOf(validationType);
            } catch (IllegalArgumentException e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(e.getMessage())
                        .build();
            }
        } else {
            // Didn't provide a validation type so we start with the default.
            type = ValidateResponse.ValidationType.PANAGRAM;
        }

        // In a production system creating a new object every request might not make sense.
        StringValidator validator = StringValidatorFactory.create(type);
        if (null == validator) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Validation type not implemented")
                    .build();
        }
        ValidateResponse resp = validator.validate(validate);
        return Response.ok(resp).build();

    }
}

package com.tutorialapi.rest.resources;

import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/test")
public class HelloResource {

    @GET
    @Produces(TEXT_PLAIN)
    public String text() {
        return "Hello from test";
    }
}

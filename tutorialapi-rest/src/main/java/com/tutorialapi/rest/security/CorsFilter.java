package com.tutorialapi.rest.security;

import java.io.IOException;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {
        @Override
        public void filter(ContainerRequestContext containerRequestContext, 
                          ContainerResponseContext containerResponseContext) throws IOException {

                containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
                containerResponseContext.getHeaders().add(
                      "Access-Control-Allow-Methods", 
                      "DELETE, GET, HEAD, OPTIONS, PATCH, POST, PUT"
                );

        }
}

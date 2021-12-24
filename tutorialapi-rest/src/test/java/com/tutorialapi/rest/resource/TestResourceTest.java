package com.tutorialapi.rest.resource;

import java.util.logging.LogManager;

import com.tutorialapi.rest.ApiApplication;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestResourceTest extends JerseyTest {
        static {
                LogManager.getLogManager().reset();
        }
        @Override
        protected Application configure() {
                return new ApiApplication();
        }

        @Test
        public void test() {
            Response response =    target("/test").request().get();
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertEquals("Hello from test", response.readEntity(String.class));


            Assertions.assertEquals("*", response.getHeaderString("Access-Control-Allow-Origin"));
            Assertions.assertEquals("DELETE, GET, HEAD, OPTIONS, PATCH, POST, PUT",
                            response.getHeaderString("Access-Control-Allow-Methods"));
        }
}

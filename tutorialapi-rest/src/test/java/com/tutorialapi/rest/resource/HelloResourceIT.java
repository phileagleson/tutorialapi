package com.tutorialapi.rest.resource;

import com.tutorialapi.model.Subscription;
import com.tutorialapi.rest.ApiApplication;
import com.tutorialapi.rest.security.SecurityHeader;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import java.util.logging.LogManager;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloResourceIT extends JerseyTest {
    static {
        LogManager.getLogManager().reset();
    }

    @Override
    protected Application configure() {
        return new ApiApplication();
    }

    @Test
    public void testNoSecurityHeaders() {
        Response response = target("/test").request().get();
        Assertions.assertEquals(401, response.getStatus());
        Assertions.assertEquals("", response.readEntity(String.class));
    }

    @Test
    public void testOnlyProxySecretHeader() {
        Response response = target("/test")
            .request()
            .header(SecurityHeader.RAPID_API_PROXY_SECRET.getHeader(), "proxy-secret")
            .get();
        Assertions.assertEquals(401, response.getStatus());
        Assertions.assertEquals("", response.readEntity(String.class));
    }

    @Test
    public void testOnlyProxySecretAndUserHeader() {
        Response response = target("/test")
            .request()
            .header(SecurityHeader.RAPID_API_PROXY_SECRET.getHeader(), "proxy-secret")
            .header(SecurityHeader.RAPID_API_USER.getHeader(), "user")
            .get();
        Assertions.assertEquals(401, response.getStatus());
        Assertions.assertEquals("", response.readEntity(String.class));
    }

    @Test
    public void testOnlyInvalidSubscription() {
        Response response = target("/test")
            .request()
            .header(SecurityHeader.RAPID_API_PROXY_SECRET.getHeader(), "proxy-secret")
            .header(SecurityHeader.RAPID_API_USER.getHeader(), "user")
            .header(SecurityHeader.RAPID_API_SUBSCRIPTION.getHeader(), "invalid")
            .get();
        Assertions.assertEquals(401, response.getStatus());
        Assertions.assertEquals("", response.readEntity(String.class));
    }

    @Test
    public void testValidHeaders() {
        Response response = target("/test")
            .request()
            .header(SecurityHeader.RAPID_API_PROXY_SECRET.getHeader(), "proxy-secret")
            .header(SecurityHeader.RAPID_API_USER.getHeader(), "user")
            .header(SecurityHeader.RAPID_API_SUBSCRIPTION.getHeader(), Subscription.BASIC.name())
            .get();
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals("Hello from test", response.readEntity(String.class));
    }
}

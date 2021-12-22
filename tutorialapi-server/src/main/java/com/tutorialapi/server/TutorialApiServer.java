package com.tutorialapi.server;
import static org.eclipse.jetty.http.HttpScheme.HTTPS;

import java.util.Optional;

import com.tutorialapi.rest.ApiApplication;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialApiServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TutorialApiServer.class);
    public static void main(String... args) throws Exception {
        int port = Optional.ofNullable(System.getProperty("port")).map(Integer::parseInt).orElse(8443);

        HttpConfiguration httpsConfiguration = new HttpConfiguration();
        httpsConfiguration.setSecureScheme(HTTPS.asString());
	httpsConfiguration.setSecurePort(port);
	httpsConfiguration.addCustomizer(new SecureRequestCustomizer());
	httpsConfiguration.setSendServerVersion(false);
        httpsConfiguration.setSendDateHeader(false);

	HttpConnectionFactory httpsConnectionFactory = new HttpConnectionFactory(httpsConfiguration);

	SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
	sslContextFactory.setKeyStorePath("/Users/phil/Source/tutorialapi/tutorialapi-server/src/main/resources/certs/tutorialapi.p12");
	sslContextFactory.setKeyStoreType("PKCS12");
	sslContextFactory.setKeyStorePassword("changeit");
	sslContextFactory.setKeyManagerPassword("changeit");
	sslContextFactory.setTrustAll(true);

	SslConnectionFactory sslConnectionFactory = new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString());

	Server server = new Server();

	ServerConnector httpsConnector = new ServerConnector(server, sslConnectionFactory, httpsConnectionFactory);
	httpsConnector.setName("secure");
	httpsConnector.setPort(httpsConfiguration.getSecurePort());

	server.addConnector(httpsConnector);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        servletContextHandler.setContextPath("/");
        servletContextHandler.setBaseResource(Resource.newResource("/Users/phil/Source/tutorialapi/tutorialapi-server/src/main/resources/www"));
        servletContextHandler.addServlet(DefaultServlet.class, "/");

        server.setHandler(servletContextHandler);

        ServletHolder apiServletHolder = servletContextHandler.addServlet(ServletContainer.class, "/api/*");
        apiServletHolder.setInitParameter("jakarta.ws.rs.Application", ApiApplication.class.getName());
                

        LOGGER.info("Server starting on port: {}", port);
	server.start();
	server.join();

    }

}

package com.tutorialapi.server;
import static org.eclipse.jetty.http.HttpScheme.HTTPS;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialApiServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TutorialApiServer.class);
    public static void main(String... args) throws Exception {
        LOGGER.info("Hello World");

        HttpConfiguration httpsConfiguration = new HttpConfiguration();
        httpsConfiguration.setSecureScheme(HTTPS.asString());
	httpsConfiguration.setSecurePort(8443);
	httpsConfiguration.addCustomizer(new SecureRequestCustomizer());
	httpsConfiguration.setSendServerVersion(false);

	HttpConnectionFactory httpsConnectionFactory = new HttpConnectionFactory(httpsConfiguration);

	SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
	sslContextFactory.setKeyStorePath("tutorialapi-server/src/main/resources/certs/tutorialapi.p12");
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

	server.start();
	server.join();

    }

}

package com.tutorialapi.server.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SystemKeyTest {
        @Test
        public void testDefaultValues() {
                Assertions.assertEquals("8443",SystemKey.PORT.getDefaulValue() );
                Assertions.assertEquals("dev",SystemKey.MODE.getDefaulValue() );
        }
        @Test
        public void testGetKey() {
                Assertions.assertEquals("port",SystemKey.PORT.getKey() );
        }
}

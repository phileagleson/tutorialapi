package com.tutorialapi.server.config;

import static java.util.Locale.ENGLISH;

public enum SystemKey {
        PORT("8443"),
        MODE("dev");

        private final String defaulValue;

        SystemKey(String defaulValue) {
                this.defaulValue = defaulValue;
        }



        public String getDefaulValue() {
                return defaulValue;
        }



        public String getKey() {
                return name().toLowerCase(ENGLISH).replaceAll("_", ".");
        }
}

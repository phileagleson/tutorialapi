package com.tutorialapi.server.config;

public enum SystemKey implements Key {
    PORT("8443"),
    MODE("dev");

    private final String defaulValue;

    SystemKey(String defaulValue) {
        this.defaulValue = defaulValue;
    }

    public String getDefaulValue() {
        return defaulValue;
    }
}

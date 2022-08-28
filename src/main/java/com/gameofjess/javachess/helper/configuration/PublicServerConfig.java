package com.gameofjess.javachess.helper.configuration;

public class PublicServerConfig implements Config {

    private final Encryption encryption = new Encryption(true, "", "");

    private static class Encryption {
        private final boolean useEncryption;
        private final String pathToKeystore;
        private final String keystorePassword;

        private Encryption(boolean useEncryption, String pathToKeystore, String keystorePassword) {
            this.useEncryption = useEncryption;
            this.pathToKeystore = pathToKeystore;
            this.keystorePassword = keystorePassword;
        }
    }

    public boolean getUseEncryption() {
        return encryption.useEncryption;
    }

    public String getPathToKeystore() {
        return encryption.pathToKeystore;
    }

    public String getKeystorePassword() {
        return encryption.keystorePassword;
    }
}

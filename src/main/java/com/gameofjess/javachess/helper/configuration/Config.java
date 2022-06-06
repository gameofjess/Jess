package com.gameofjess.javachess.helper.configuration;

public class Config {
    private final HostMenuConfiguration hostMenuConfiguration = new HostMenuConfiguration(true, "https://ipaddr.gameofjess.com");

    class HostMenuConfiguration {
        /**
         * <p>
         * This specifies whether the public IP Address should be shown and determined in the first place on
         * the host menu. This comes in handy when streaming and also allows for more privacy.
         * </p>
         * <p>
         * <strong>Default:</strong> true
         * </p>
         * <p>
         * <strong>Possible values:</strong> true | false
         * </p>
         */
        private final boolean showPublicIPAddress;
        /**
         * <p>
         * This specifies a server that returns the IP address.
         * </p>
         * <p>
         * Notable examples are:
         * </p>
         * <ul>
         * <li><a href="https://checkip.amazonaws.com/">Amazon Web Services</a></li>
         * <li><a href="https://ifconfig.me/ip">ifconfig.me</a></li>
         * </ul>
         * <p>
         * We also provide our own service under <a href="ipaddr.gameofjess.com">ipaddr.gameofjess.com</a>.
         * However, we can not guarantee for reliability.
         * </p>
         * <p>
         * <strong>Default:</strong> https://ipaddr.gameofjess.com
         * </p>
         * <p>
         * <strong>Possible values:</strong> Only servers that return the IP Address in text form upon a
         * HTML GET-Request are suitable. They also have to use HTTPS.
         * </p>
         */
        private final String ipAddressServer;

        public HostMenuConfiguration(boolean showPublicIPAddress, String ipAddressServer) {
            this.showPublicIPAddress = showPublicIPAddress;
            this.ipAddressServer = ipAddressServer;
        }

        public String getIpAddressServer() {
            return ipAddressServer;
        }

        public boolean getShowPublicIPAddress() {
            return showPublicIPAddress;
        }
    }

    public String getIPAddressServer() {
        return hostMenuConfiguration.getIpAddressServer();
    }

    public boolean getShowPublicIPAddress() {
        return hostMenuConfiguration.getShowPublicIPAddress();
    }


}

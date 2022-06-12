package com.gameofjess.javachess.helper.configuration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Config {

    private static final Logger log = LogManager.getLogger(Config.class);

    private final Logging logging = new Logging("INFO");
    private final HostMenuConfiguration hostMenuConfiguration = new HostMenuConfiguration(true, "https://ipaddr.gameofjess.com");
    private final ServerConfiguration serverConfiguration = new ServerConfiguration(8887, "0.0.0.0");


    private class Logging {
        /**
         * <p>
         * This specifies the user log level.
         * </p>
         *
         * <p>
         * <strong>Default:</strong> INFO
         * </p>
         * <p>
         * <strong>Possible values:</strong>
         * <ul>
         * <li><strong>FATAL</strong>: Logs only fatal exceptions.</li>
         * <li><strong>ERROR</strong>: Also logs errors.</li>
         * <li><strong>WARN</strong>: Also logs warnings.</li>
         * <li><strong>INFO</strong>: Normal logging behavior. Also logs program start etc.</li>
         * <li><strong>DEBUG</strong>: Logs in-depth information about received messages. And in-game board
         * configuration.</li>
         * <li><strong>TRACE</strong>: Also logs internal chess logic and in-depth WebSocket
         * implementation.</li>
         * </ul>
         */
        private final String level;

        Logging(String level) {
            this.level = level;
        }

        String getLogLevel() {
            return level;
        }
    }

    public Level getLogLevel() {
        String level = logging.getLogLevel();
        Level logLevel = Level.getLevel(level);

        if (logLevel == null) {
            log.error("Invalid log level parameter: {}. Going to use default value: INFO!", level);
            return Level.INFO;
        } else {
            return logLevel;
        }
    }

    private class HostMenuConfiguration {
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
        private final boolean loadPublicIPAddress;
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
         * We also provide our own service under
         * <a href="https://ipaddr.gameofjess.com">ipaddr.gameofjess.com</a>. However, we can not guarantee
         * for reliability.
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

        HostMenuConfiguration(boolean loadPublicIPAddress, String ipAddressServer) {
            this.loadPublicIPAddress = loadPublicIPAddress;
            this.ipAddressServer = ipAddressServer;
        }

        String getIpAddressServer() {
            return ipAddressServer;
        }

        boolean getLoadPublicIPAddress() {
            return loadPublicIPAddress;
        }
    }

    public String getIPAddressServer() {
        return hostMenuConfiguration.getIpAddressServer();
    }

    public boolean getShowPublicIPAddress() {
        return hostMenuConfiguration.getLoadPublicIPAddress();
    }

    private class ServerConfiguration {
        /**
         * <p>
         * This specifies the standard port for a server instance. We advice not to change this if not
         * necessary, but it may come in handy in some configurations e.g. when installing a Jess server
         * behind a reverse proxy.
         * </p>
         * <p>
         * This value may be overridden when using the -p flag on startup.
         * </p>
         * <p>
         * <strong>Default:</strong> 8887
         * </p>
         * <p>
         * <strong>Possible values:</strong> All integers from 1000 to 65535
         * </p>
         */
        private final int defaultPort;

        /**
         * <p>
         * This specifies the standard hostname for a server instance. We advice not to change this if not
         * necessary, but it may come in handy in some configurations.
         * </p>
         * <p>
         * This value may be overridden when using the -H flag on startup.
         * </p>
         * <p>
         * <strong>Default:</strong> 0.0.0.0
         * </p>
         * <p>
         * <strong>Possible values:</strong> All possible hostnames. Depends on host system.
         * </p>
         */
        private final String defaultHostname;

        ServerConfiguration(int defaultPort, String defaultHostname) {
            this.defaultPort = defaultPort;
            this.defaultHostname = defaultHostname;
        }

        int getDefaultPort() {
            return defaultPort;
        }

        String getDefaultHostname() {
            return defaultHostname;
        }
    }

    public int getDefaultPort() {
        return serverConfiguration.getDefaultPort();
    }

    public String getDefaultHostname() {
        return serverConfiguration.getDefaultHostname();
    }


}

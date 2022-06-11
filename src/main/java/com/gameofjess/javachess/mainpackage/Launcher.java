package com.gameofjess.javachess.mainpackage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.gameofjess.javachess.helper.argumentparsing.ArgumentParser;
import com.gameofjess.javachess.helper.argumentparsing.Option;
import com.gameofjess.javachess.helper.configuration.Config;
import com.gameofjess.javachess.helper.configuration.ConfigHandler;
import com.gameofjess.javachess.server.Server;
import com.gameofjess.javachess.server.ServerBuilder;
import com.gameofjess.javachess.server.ServerCommandListener;

/**
 * This class is needed to fix the issue with starting JavaFX Applications pointed out here:
 * <a href="https://stackoverflow.com/questions/52144931/how-to-add-javafx-runtime-to-eclipse-in-java-11">...</a>
 *
 * Also, it is used to determine whether the user wants to start a dedicated server or client.
 */
public class Launcher {

    private static final Logger log = LogManager.getLogger(Launcher.class);

    /**
     * Starts the application. This method also parses the options given via command line.
     */
    public static void main(String[] args) {

        Config config;
        try {
            config = new ConfigHandler().loadConfig(new File("config.json"));
        } catch (IOException e) {
            log.error("Could not read config file. Proceeding to use default values!");
            config = new Config();
        }

        Level level = config.getLogLevel();
        log.debug("Setting logger level to {}", level.name());
        Configurator.setLevel(LogManager.getRootLogger(), level);

        try {
            Option[] options = ArgumentParser.getOpts(args);
            List<Option> optionList = Arrays.asList(options);

            if (optionList.contains(Option.dedicatedServer)) {
                ServerBuilder sb = new ServerBuilder();
                if (optionList.contains(Option.port)) {
                    int port = Integer
                            .parseInt(optionList.get(optionList.indexOf(Option.port)).getValue());
                    sb.setPort(port);
                    log.debug("Manually set server port to {}", port);
                }
                if (optionList.contains(Option.host)) {
                    String host = optionList.get(optionList.indexOf(Option.host)).getValue();
                    sb.setHost(host);
                    log.debug("Manually set server hostname to {}", host);
                }
                Server server = sb.build();
                log.debug("Successfully built server");

                ServerCommandListener commandListener =
                        new ServerCommandListener(server, System.in);
                Thread commandListenerThread = new Thread(commandListener);
                commandListenerThread.start();
                log.debug("Started ServerCommandListener Thread");

                log.info("Starting server on {}.", server.getAddress().toString());
                commandListener.parseCommand("start");
            } else {
                log.info("Starting client application");
                Main.main(args);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }



    }
}

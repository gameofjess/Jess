package com.gameofjess.javachess.helper.configuration;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class ConfigHandler {

    private static final Logger log = LogManager.getLogger(ConfigHandler.class);

    private final Gson gson;

    public ConfigHandler() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Loads the config file.
     * 
     * @param file Config file
     * @return instance of the Config class representing the configuration specified in the config file.
     * @throws IOException if an I/O-Error occurs.
     */
    public Config loadConfig(File file) throws IOException {
        if (file.createNewFile()) {

            log.info("Creating new config file {} in the working directory.", file.getName());

            Config newConfig = new Config();

            String json = gson.toJson(JsonParser.parseString(gson.toJson(newConfig)));

            try (PrintWriter out = new PrintWriter(file)) {
                out.println(json);
            }

            return newConfig;

        } else {

            log.debug("Loading config file from {}.", file.getAbsolutePath());

            return gson.fromJson(new String(Files.readAllBytes(file.toPath())), Config.class);
        }
    }

    /**
     * This saves a config file to the specified file upon configuration within the application itself.
     * 
     * @param config Config to be saved.
     * @param file File in which the config should be saved in.
     * @throws IOException if an I/O-Error occurs.
     */
    public void saveConfig(Config config, File file) throws IOException {
        if (file.createNewFile()) {
            log.info("Creating a new config file at {}.", file.getAbsolutePath());
        } else {
            log.info("Overriding config file at {}", file.getAbsolutePath());
        }

        String json = gson.toJson(JsonParser.parseString(gson.toJson(config)));
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(json);
        }
    }
}

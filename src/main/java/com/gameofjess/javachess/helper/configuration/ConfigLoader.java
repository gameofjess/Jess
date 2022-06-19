package com.gameofjess.javachess.helper.configuration;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class ConfigLoader {

    private static final Logger log = LogManager.getLogger(ConfigLoader.class);

    private static final Map<File, Config> loadedConfigs = new HashMap<>();

    private final Gson gson;

    public ConfigLoader() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Loads the config file.
     * 
     * @param file Config file
     * @return instance of the Config class representing the configuration specified in the config file.
     * @throws IOException if an I/O-Error occurs.
     */
    public Config loadConfig(File file, Class<? extends Config> clazz) throws IOException {
        if (loadedConfigs.containsKey(file)) {
            log.debug("Using loaded config from {}", file.getAbsolutePath());
            return loadedConfigs.get(file);
        } else {
            if (file.createNewFile()) {

                log.info("Creating new config file {} in the working directory.", file.getName());

                Config newConfig;
                try {
                    newConfig = clazz.getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    log.fatal("Could not create new config. Aborting...");
                    throw new RuntimeException(e);
                }

                String json = gson.toJson(JsonParser.parseString(gson.toJson(newConfig)));

                try (PrintWriter out = new PrintWriter(file)) {
                    out.println(json);
                }

                loadedConfigs.put(file, newConfig);

                return newConfig;

            } else {

                log.debug("Loading config file from {}.", file.getAbsolutePath());

                Config config = gson.fromJson(new String(Files.readAllBytes(file.toPath())), clazz);

                loadedConfigs.put(file, config);

                return config;
            }
        }
    }
}

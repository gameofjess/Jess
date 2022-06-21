package com.gameofjess.javachess.helper.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ConfigTest {

    /**
     * Tests loading of StandardConfig
     */
    @Test
    void standardConfigTest() throws IOException {
        Config config = new ConfigLoader().loadConfig(new File("someConfigFile.json"), StandardConfig.class);
        assertTrue(config instanceof StandardConfig);
    }

    /**
     * Tests loading of OtherConfig. This is needed to ensure that ConfigLoader is able to load configs
     * different from StandardConfig.
     */
    @Test
    void otherConfigTest() throws IOException {
        Config otherConfig = new OtherConfig();

        assertEquals(otherConfig, new ConfigLoader().loadConfig(new File("someOtherConfigFile.json"), OtherConfig.class));
    }

}

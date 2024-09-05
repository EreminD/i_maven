package com.inzhenerka.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;

public class ConfigurationProvider {
    private static Configuration configuration;

    private ConfigurationProvider() {
    }

    public static Configuration load() {
        if (configuration == null) {
            String path = "/Users/eremin/Documents/java-projects/inzhenerka_maven/src/main/resources/megatool.json";
            ObjectMapper mapper = new ObjectMapper();

            try {
                configuration = mapper.readValue(Path.of(path).toFile(), Configuration.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return configuration;
    }
}

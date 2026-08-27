package com.healthpoint.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {

        String envKey = key
                .toUpperCase()
                .replace(".", "_");

        String envValue = System.getenv(envKey);

        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        String value = properties.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new RuntimeException(
                    "Property not found or empty: " + key
            );
        }

        return value;
    }
}

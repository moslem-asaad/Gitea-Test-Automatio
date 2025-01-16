package org.example;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EnvLoader {
    private static final Map<String, String> customEnv = new HashMap<>();

    public static void loadEnv(String filePath) {
        try {
            Files.lines(Paths.get(filePath))
                    .filter(line -> !line.startsWith("#") && line.contains("=")) // Skip comments and invalid lines
                    .forEach(line -> {
                        String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            String key = parts[0].trim();
                            String value = parts[1].trim();
                            customEnv.put(key, value); // Store in custom map
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Failed to load .env file: " + filePath, e);
        }
    }

    public static String getEnv(String key) {
        return customEnv.getOrDefault(key, System.getenv(key));
    }
}


package com.example.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new RuntimeException("config.properties not found in classpath (src/test/resources)");
            }
            PROPS.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage(), e);
        }
    }

    public static String get(String key) {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) return sys;
        String val = PROPS.getProperty(key);
        if (val == null) throw new RuntimeException("Missing config key: " + key);
        return val;
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
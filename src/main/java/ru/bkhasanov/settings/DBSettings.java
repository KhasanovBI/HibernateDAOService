package ru.bkhasanov.settings;

import java.util.Properties;

import static java.util.Objects.requireNonNull;

/**
 * Created by bulat on 07.03.17.
 */
public class DBSettings {
    private final Properties config;

    public DBSettings(Properties config) {
        this.config = requireNonNull(config);
    }

    public String getUrl() {
        return config.getProperty("url");
    }

    public String getUser() {
        return config.getProperty("user");
    }

    public String getPassword() {
        return config.getProperty("password");
    }
}

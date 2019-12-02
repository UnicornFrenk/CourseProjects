package com.github.config;

import org.springframework.beans.factory.annotation.Value;

public class DatasourseSettings {

    @Value("${url}")
    private String url;

    @Value("${username}")
    private String user;

    @Value("${password}")
    private String password;

    @Value("${driver}")
    private String driver;

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }
}

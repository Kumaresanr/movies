package com.keycloakapp.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:omdb.properties")
public class OmdbApiConfig {

    @Value("${omdb.apikey}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}

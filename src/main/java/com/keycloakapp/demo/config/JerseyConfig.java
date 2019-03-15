package com.keycloakapp.demo.config;

import com.keycloakapp.demo.controller.MovieController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
//        register(RolesAllowedDynamicFeature.class);
        register(MovieController.class);
    }
}
package com.keycloakapp.demo.config;

import com.keycloakapp.demo.controller.MovieController;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@Component
@ApplicationPath("/")
public class JaxRSApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<>();
        set.add(MovieController.class);

        return set;
    }
}

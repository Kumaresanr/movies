package com.keycloakapp.demo.controller;
import com.keycloakapp.demo.exceptions.UnauthorizedException;
import com.keycloakapp.demo.model.Movie;
import com.keycloakapp.demo.services.MovieService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/service/movies")
@Component
public class MovieController {

    @Inject
    private MovieService movieService;

    public MovieController() {
    }

    @GET
    @Path("/search/{query}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movie> getMovieList(@PathParam("query") String query, @Context SecurityContext securityContext) {
        if (securityContext.isUserInRole("admin") || securityContext.isUserInRole("user")) {
            return movieService.getMovieList(query);
        } else {
            throw new UnauthorizedException("Authorization Required");
        }
    }

    @GET
    @Path("/details/{imdbID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Movie getMovieDetails(@PathParam("imdbID") String imdbID, @Context SecurityContext sec) {
        if (sec.isUserInRole("admin")) {
            return movieService.getMovieDetails(imdbID);
        } else {
            throw new UnauthorizedException("Authorization Required");
        }
    }
}
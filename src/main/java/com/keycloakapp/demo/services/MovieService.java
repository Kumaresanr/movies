package com.keycloakapp.demo.services;

import com.keycloakapp.demo.config.OmdbApiConfig;
import com.keycloakapp.demo.model.Movie;
import com.keycloakapp.demo.model.MoviesResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.List;

@Component
public class MovieService {

    @Inject
    private OmdbApiConfig omdbApiConfig;

    public List<Movie> getMovieList(String movieName){
        String targetURL = String.format("http://www.omdbapi.com/?apikey=%s&s=%s",
                omdbApiConfig.getApiKey(),
                movieName);
        return new RestTemplate().getForEntity(targetURL, MoviesResponse.class).getBody().getMovieList();
    }

    public Movie getMovieDetails(String imdbID) {
        String targetURL = String.format("http://www.omdbapi.com/?apikey=%s&i=%s",
                omdbApiConfig.getApiKey(),
                imdbID);
        return new RestTemplate().getForEntity(targetURL, Movie.class).getBody();
    }
}

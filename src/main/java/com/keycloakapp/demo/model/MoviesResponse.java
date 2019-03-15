package com.keycloakapp.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MoviesResponse {

    private List<Movie> movieList;
    private Integer totalResults;
    private boolean success;

    public List<Movie> getMovieList() {
        return movieList;
    }

    @JsonProperty("Search")
    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    @JsonProperty("totalResults")
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public boolean isSuccess() {
        return success;
    }

    @JsonProperty("Response")
    public void setSuccess(boolean success) {
        this.success = success;
    }
}

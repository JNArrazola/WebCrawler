package com.web;

import java.util.List;

/**
 * Actor: 
 * ID
 * name, 
 * movies (Array de Objetos Movie)
 */
public class Actor {
    private String id;
    private String name;
    private List<Movie> movies;

    /**
     * Constructor de la clase Actor
     * @param id
     * @param name
     * @param movies
     */
    public Actor(String id, String name, List<Movie> movies) {
        this.id = id;
        this.name = name;
        this.movies = movies;
    }

    /**
     * Getters
     */
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * Setters
     */
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    /**
     * toString
     */
    @Override
    public String toString() {
        return "Actor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", movies=" + movies +
                '}';
    }
}

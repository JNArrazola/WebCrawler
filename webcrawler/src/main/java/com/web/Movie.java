package com.web;

/**
 * This class will store the following things:
 * ID
 * title, 
 * rating, 
 * synopsis, 
 * actors (Array de Objectos Movie), 
 */
public class Movie {
    private String id;
    private String title;
    private String rating;
    private String synopsis;
    private StringBuilder actors;

    /**
     * Constructor of the movie class
     * @param id
     * @param title
     * @param rating
     * @param synopsis
     * @param actors
     */
    public Movie(String id, String title, String rating, String synopsis, StringBuilder actors) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.synopsis = synopsis;
        this.actors = actors;
    }

    /**
     * Getters
     */
    public String getId() {
        return id;
    }
   
    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public StringBuilder getActors() {
        return actors;
    }

    /**
     * Setters
     */
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setActors(StringBuilder actors) {
        this.actors = actors;
    }

    /**
     * toString
     */
    @Override
    public String toString() {
        return "Movie{" + "\n\t" +
                "id: '" + id + '\'' + "\n\t" +
                "title: '" + title + '\'' + "\n\t" +
                "rating: '" + rating + '\'' + "\n\t" +
                "synopsis: '" + synopsis + '\'' + "\n\t" +
                "actors: " + actors + "\n" +
                '}';
    }
}

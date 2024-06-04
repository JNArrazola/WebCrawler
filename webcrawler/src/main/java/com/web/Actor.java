package com.web;

public class Actor {
    private String actorId;
    private String movieId;
    private String name;

    /**
     * Constructor of the Actor class
     * @param actorId
     * @param movieId
     * @param name
      */
    public Actor(String actorId, String movieId, String name) {
        this.actorId = actorId;
        this.movieId = movieId;
        this.name = name;
    }

    /**
     * Getters
     */
    public String getActorId() {
        return actorId;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getName() {
        return name;
    }

    /**
     * Setters
     */
    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * toString
     */
    @Override
    public String toString() {
        return "Actor{" + "\n\t" + 
                "actorId: '" + actorId + '\'' + "\n\t" +  
                "movieId: '" + movieId + '\'' + "\n\t" +
                "name: " + name + "\n" +
                '}';
    }
}

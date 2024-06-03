package com.web;

public interface LinkHandler {

    /**
     * Stores the link of the movie or actor to be visited
     * @param link
     * @throws Exception
     */
    void queue(String link) throws Exception;

    /**
     * Returns the number of links that have been visited
     * @return
     */
    int size();

    /**
     * Verifies if the link has been visited
     * @param link
     * @return
     */
    boolean visitedMovie(String link);
    boolean visitedActor(String link);

    /**
     * Marks the link as visited
     * @param link
     */
    void addVisitedMovie(String link);
    void addVisitedActor(String link);
    
}
package com.web;

public interface LinkHandler {

    /**
     * Guarda el link en la cosa
     * @param link
     * @throws Exception
     */
    void queueMovie(String link) throws Exception;
    void queueActor(String link) throws Exception;

    /**
     * Retorna el número de links visitados
     * @return
     */
    int size();

    /**
     * Verifica si el link ya ha sido visitado
     * @param link
     * @return
     */
    boolean visitedMovie(String link);
    boolean visitedActor(String link);

    /**
     * Marca el link como visitado
     * @param link
     */
    void addVisitedMovie(String link);
    void addVisitedActor(String link);
    
}
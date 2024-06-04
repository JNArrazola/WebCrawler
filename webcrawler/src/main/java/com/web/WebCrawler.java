package com.web;

import com.web.net.LinkFinder; 
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
// TODO: usar URL en vez de String para manejar los links
// import java.net.URL;

public class WebCrawler implements LinkHandler {
    private final Collection<String> visitedMovies = Collections.synchronizedSet(new HashSet<String>()); // Lista de peliculas visitadas (por id)
    private final Collection<String> visitedActors = Collections.synchronizedSet(new HashSet<String>()); // Lista de actores visitados (por id)
    
    private String url; // Initial link to start crawling
    private ExecutorService executorService; // Executor service to manage threads

    public WebCrawler(String startingURL, int maxThreads) {
        this.url = startingURL;
        executorService = Executors.newFixedThreadPool(maxThreads);
    }

    /**
     * Adds a link to queue
     */
    @Override
    public void queue(String link) throws Exception {
        startNewThread(link);
    }

    /**
     * Adds new processes to the executor
     * @param link
     * @throws Exception
     */
    private void startNewThread(String link) throws Exception {
        executorService.execute(new LinkFinder(link, this));
    }

    /**
     * Starts the crawling process
     */
    public void startCrawling() throws Exception {
        startNewThread(this.url);
    }

    /**
     * Returns the size of the visited movies list
     */
    @Override
    public int size() {
        return visitedActors.size();
    }

    /**
     * Adds to the visited movies list
     */
    @Override
    public void addVisitedMovie(String idMovie) {
        visitedActors.add(idMovie);
    }

    @Override
    public void addVisitedActor(String idActor) {
        visitedActors.add(idActor);
    }

    /**
     * Returns a boolean value if the movie has been visited
     * True: Has been visited
     * False: Has NOT been visited
     */
    @Override
    public boolean visitedMovie(String idPelicula) {
        return visitedActors.contains(idPelicula);
    }

    @Override
    public boolean visitedActor(String idActor) {
        return visitedActors.contains(idActor);
    }
}

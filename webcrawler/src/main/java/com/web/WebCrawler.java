package com.web;

import com.web.net.LinkFinder; 
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
// TODO: usar URL en vez de String para manejar los links
import java.net.URL;

public class WebCrawler implements LinkHandler {
    private final Collection<String> visitedMovies = Collections.synchronizedSet(new HashSet<String>()); // Lista de peliculas visitadas (por id)
    private final Collection<String> visitedActors = Collections.synchronizedSet(new HashSet<String>()); // Lista de actores visitados (por id)
    
    private String url; // Link (URL) inicial del programa
    private ExecutorService executorService; // executor que se enccarga de administrar los Threads

    public WebCrawler(String startingURL, int maxThreads) {
        this.url = startingURL;
        executorService = Executors.newFixedThreadPool(maxThreads);
    }

    /**
     * Agrega un link a la queue
     */
    @Override
    public void queue(String link) throws Exception {
        startNewThread(link);
    }

    /**
     * Agregar procesos a la cola de threads
     * @param link
     * @throws Exception
     */
    private void startNewThread(String link) throws Exception {
        executorService.execute(new LinkFinder(link, this));
    }

    /**
     * Inicia el proceso de crawling
     */
    public void startCrawling() throws Exception {
        startNewThread(this.url);
    }

    /**
     * Devuelve el tamano de la lista (realmente no se usa)
     */
    @Override
    public int size() {
        return visitedActors.size();
    }

    /**
     * Agregar a la lista de cosas visitadas
     */
    @Override
    public void addVisited(String idPelicula) {
        visitedActors.add(idPelicula);
    }

    /**
     * Retorno un booleano que dice si la pelicula ya fue visitada
     * True: Ya fue visitada
     * Falsa: No ha sido visitada
     */
    @Override
    public boolean visited(String idPelicula) {
        return visitedActors.contains(idPelicula);
    }
}

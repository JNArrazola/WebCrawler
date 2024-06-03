package com.web;

import com.web.net.LinkFinder; 
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: Agregar el identificador en vez del nombre para evitar nombres reptidos
// ex: https://www.imdb.com//title/tt15791034/?ref_=nm_knf_t_4
    // id: tt15791034

public class WebCrawler implements LinkHandler {

    // nombre de las peliculas visitadas
    private final Collection<String> visitedNames = Collections.synchronizedSet(new HashSet<String>());
    
    private String url; // Link (URL) inicial del programa
    private ExecutorService executorService; // executor que se enccarga de admi

    public WebCrawler(String startingURL, int maxThreads) {
        this.url = startingURL;
        executorService = Executors.newFixedThreadPool(maxThreads);
    }

    /**
     * Agrega un link a la queue
     */
    @Override
    public void queueLink(String link) throws Exception {
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
     * Devuelve el tamano de 
     */
    @Override
    public int size() {
        return visitedNames.size();
    }

    /**
     * Agregar a la lista de cosas visitadas
     */
    @Override
    public void addVisited(String s) {
        visitedNames.add(s);
    }

    /**
     * Retorno un booleano que dice si la pelicula ya fue visitada
     * True: Ya fue visitada
     * Falsa: No ha sido visitada
     */
    @Override
    public boolean visited(String link) {
        return visitedNames.contains(link);
    }
}

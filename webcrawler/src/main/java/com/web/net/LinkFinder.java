package com.web.net;

import com.web.LinkHandler;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkFinder implements Runnable {

    // Dominio de la página
    private static final String BASE_URL = "https://www.imdb.com/"; 

    // Esto es de la pagina de la pelicula
    private static final String TITLE_SELECTOR = "[data-testid=\"hero__primary-text\"]"; // Título de la película en html de la pagina
    private static final String RATING_SELECTOR = "[data-testid=\"hero-rating-bar__aggregate-rating__score\"]"; // Rating de la película en el html
    private static final String SYNOPSIS_SELECTOR = "[data-testid=plot-xl]"; // Sinopsis de la película en el html
    private static final String ACTOR_SELECTOR = "[data-testid=\"title-cast-item__actor\"]"; // Actores en el html
    
    // Esto es de la pagina de los actores
    private static final String MOVIE_SELECTOR = ".ipc-primary-image-list-card__title"; // Pelpiculas de autores

    private final String url;
    private final LinkHandler linkHandler;

    public LinkFinder(String url, LinkHandler handler) {
        this.url = url;
        this.linkHandler = handler;
    }

    @Override
    public void run() {
        getSimpleLinks(url);
    }

    private void getSimpleLinks(String url) {
        try {
            Document document = Jsoup.connect(url).get();

            Element titleElement = document.selectFirst(TITLE_SELECTOR);
            String title = titleElement != null ? titleElement.text() : "No encontrado"; // no tiene mucho, talvez estaria bien poner un return en vez de un "No encontrado"

            // si ya se visito la pagina, no hacer nada
            if (linkHandler.visited(title)) //&& !title.equals("No encontrado"))
                return;

            // extraer los datos de la pelicula
            Element ratingElement = document.selectFirst(RATING_SELECTOR);
            String rating = ratingElement != null ? ratingElement.text() : "No existe Rating"; 
            
            Element synopsisElement = document.selectFirst(SYNOPSIS_SELECTOR);
            String synopsis = synopsisElement != null ? synopsisElement.text() : "No existe Sinopsis";
            
            // extraer los actores de la pelicula
            Elements actorElements = document.select(ACTOR_SELECTOR);
            
            StringBuilder actorNames = new StringBuilder();
            ArrayList<String> actorLinks = new ArrayList<>();
            
            // recorrer cada uno de los actores y guardar su nombre y link
            for (Element actorElement : actorElements) {
                String actorHref = actorElement.attr("href");
                actorNames.append(actorElement.text()).append(", ");
                actorLinks.add(BASE_URL + actorHref);
            }
            
            if (actorNames.length() > 0)
                actorNames.setLength(actorNames.length() - 2); // ? Quita la coma del final del string de actores
            
            linkHandler.addVisited(title); // agregar el titulo a la lista de visitados
            
            // TODO: talvez sea mejor hacer un objeto que imprima las cosas, de 
            // igual forma, que nos permita de una vez guardar los datos en un csv
            // tambien leerlos de un csv

            // imprimir los datos de la pelicula
            System.out.println("Título: " + title + " - " + url);
            System.out.println("Calificación: " + rating);
            System.out.println("Sinopsis: " + synopsis);
            System.out.println("Actores: " + actorNames.toString());

            // extraer las peliculas de los actores
            for (String actorLink : actorLinks)
                extractMoviesFromActor(actorLink);

        } catch (Exception e) {
        }

    }

    /**
     * Extraer las peliculas que estan dentro del actor
     * @param actorUrl
     */
    private void extractMoviesFromActor(String actorUrl) {
        try {
            Document document = Jsoup.connect(actorUrl).get();
            Elements movieElements = document.select(MOVIE_SELECTOR);

            // recorrer cada una de las peliculas y guardar su link
            for (Element movieElement : movieElements) {
                Thread.sleep(4000); // evitar bloqueo de ip :p
                String movieHref = movieElement.attr("href");
                String movieUrl = BASE_URL + movieHref;

                // agregar la pelicula a la cola de links
                linkHandler.queueLink(movieUrl);
            }
        } catch (Exception e) {
        }
    }
}

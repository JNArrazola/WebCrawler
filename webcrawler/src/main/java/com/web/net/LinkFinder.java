package com.web.net;

import com.web.LinkHandler;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// TODO: Agregar el identificador en vez del nombre para evitar nombres reptidos
    // ex: https://www.imdb.com//title/tt15791034/?ref_=nm_knf_t_4
    // id: tt15791034

// TODO: manejar las cosas con ID en vez de nombre
public class LinkFinder implements Runnable {
    // webpage domain
    private static final String BASE_URL = "https://www.imdb.com/"; 

    // This is related to the movie page
    private static final String TITLE_SELECTOR = "[data-testid=\"hero__primary-text\"]"; // Título de la película en html de la pagina
    private static final String RATING_SELECTOR = "[data-testid=\"hero-rating-bar__aggregate-rating__score\"]"; // Rating de la película en el html
    private static final String SYNOPSIS_SELECTOR = "[data-testid=plot-xl]"; // Sinopsis de la película en el html
    private static final String ACTOR_SELECTOR = "[data-testid=\"title-cast-item__actor\"]"; // Actores en el html
    
    // This is related to the actor page
    private static final String MOVIE_SELECTOR = ".ipc-primary-image-list-card__title"; // Pelpiculas de autores

    //   
    private final String url;
    private final LinkHandler linkHandler;

    /**
     * Constructor of the LinkFinder class
     * @param url
     * @param handler
     */
    public LinkFinder(String url, LinkHandler handler) {
        this.url = url;
        this.linkHandler = handler;
    }

    /**
     * Run the crawling process
     */
    @Override
    public void run() {
        getSimpleLinks(url);
    }

    /**
     * Extract links of the movie page
     * @param url
     */
    private void getSimpleLinks(String url) {
        try {
            //? Obtain the parsed DOM (HTML document)
            Document document = Jsoup.connect(url).get();
            System.out.println("Conectado a: " + url);

            // know if is a movie or an actor
            if (url.contains("name")) {
                extractMoviesFromActor(url);
            }

            //? Get the movie title
            Element titleElement = document.selectFirst(TITLE_SELECTOR);
            String title = titleElement != null ? titleElement.text() : "No encontrado"; // no tiene mucho sentido, talvez estaria bien poner un return en vez de un "No encontrado"

            //! If the movie has been visited, return
            if (linkHandler.visitedMovie(title))
                return;

            //? Extract the rating of the movie
            Element ratingElement = document.selectFirst(RATING_SELECTOR);
            String rating = ratingElement != null ? ratingElement.text() : "No existe Rating"; 
            
            //? Extract the synopsis of the movie
            Element synopsisElement = document.selectFirst(SYNOPSIS_SELECTOR);
            String synopsis = synopsisElement != null ? synopsisElement.text() : "No existe Sinopsis";
            
            //? Extract the actors of the movie
            Elements actorElements = document.select(ACTOR_SELECTOR);
            
            StringBuilder actorNames = new StringBuilder();
            ArrayList<String> actorLinks = new ArrayList<>();
            //! Actor actor = new Actor(title, actorNames.toString(), actorLinks); 
            
            // recorrer cada uno de los actores y guardar su nombre y link
            for (Element actorElement : actorElements) {

                String actorHref = actorElement.attr("href");
                actorNames.append(actorElement.text()).append(", ");
                actorLinks.add(BASE_URL + actorHref);
            }
            
            if (actorNames.length() > 0)
                actorNames.setLength(actorNames.length() - 2); // ? Quita la coma del final del string de actores
            
            linkHandler.addVisitedMovie(title); // agregar el titulo a la lista de visitados
            
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
            Elements movieElements = document.select(MOVIE_SELECTOR); // agarra las peliculas 

            // recorrer cada una de las peliculas y guardar su link
            for (Element movieElement : movieElements) {
                Thread.sleep(4000); // evitar bloqueo de ip :p
                String movieHref = movieElement.attr("href");
                String movieUrl = BASE_URL + movieHref;

                // agregar la pelicula a la cola de links
                linkHandler.queue(movieUrl);
            }
        } catch (Exception e) {
        }
    }
}

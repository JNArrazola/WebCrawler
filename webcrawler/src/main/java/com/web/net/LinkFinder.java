package com.web.net;

import com.web.LinkHandler;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkFinder implements Runnable {

    private static final String BASE_URL = "https://www.imdb.com/";
    private static final String TITLE_SELECTOR = "[data-testid=\"hero__primary-text\"]";
    private static final String RATING_SELECTOR = "[data-testid=\"hero-rating-bar__aggregate-rating__score\"]";
    private static final String SYNOPSIS_SELECTOR = "[data-testid=plot-xl]";
    private static final String ACTOR_SELECTOR = "[data-testid=\"title-cast-item__actor\"]";
    private static final String MOVIE_SELECTOR = ".ipc-primary-image-list-card__title";

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
            String title = titleElement != null ? titleElement.text() : "No encontrado";

            if (!linkHandler.visited(title) && !title.equals("No encontrado"))
                linkHandler.addVisited(title);
            else
                return;

            Element ratingElement = document.selectFirst(RATING_SELECTOR);
            String rating = ratingElement != null ? ratingElement.text() : "No encontrado";

            Element synopsisElement = document.selectFirst(SYNOPSIS_SELECTOR);
            String synopsis = synopsisElement != null ? synopsisElement.text() : "No encontrado";

            Elements actorElements = document.select(ACTOR_SELECTOR);

            StringBuilder actorNames = new StringBuilder();
            ArrayList<String> actorLinks = new ArrayList<>();

            for (Element actorElement : actorElements) {
                String actorHref = actorElement.attr("href");
                actorNames.append(actorElement.text()).append(", ");
                actorLinks.add(BASE_URL + actorHref);
            }

            if (actorNames.length() > 0)
                actorNames.setLength(actorNames.length() - 2); // ? Quitar la coma final

            System.out.println("Título: " + title + " - " + url);
            System.out.println("Calificación: " + rating);
            System.out.println("Sinopsis: " + synopsis);
            System.out.println("Actores: " + actorNames.toString());

            for (String actorLink : actorLinks)
                extractMoviesFromActor(actorLink);

        } catch (Exception e) {
        }

    }

    private void extractMoviesFromActor(String actorUrl) {
        try {
            Document document = Jsoup.connect(actorUrl).get();
            Elements movieElements = document.select(MOVIE_SELECTOR);

            for (Element movieElement : movieElements) {
                Thread.sleep(4000); // evitar bloqueo de ip :p
                String movieHref = movieElement.attr("href");
                String movieUrl = BASE_URL + movieHref;

                linkHandler.queueLink(movieUrl);
            }
        } catch (Exception e) {
        }
    }
}

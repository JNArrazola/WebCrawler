/* package com.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class file {
    public static void main(String[] args) {
        String url = "https://www.imdb.com/title/tt1396484/";

        try {
            Document document = Jsoup.connect(url).get();

            Element titleElement = document.selectFirst("[data-testid=\"hero__primary-text\"]");
            String title = titleElement != null ? titleElement.text() : "No encontrado";

            Element ratingElement = document.selectFirst("[data-testid=\"hero-rating-bar__aggregate-rating__score\"]");
            String rating = ratingElement != null ? ratingElement.text() : "No encontrado";

            Element synopsisElement = document.selectFirst("[data-testid=plot-xl]");
            String synopsis = synopsisElement != null ? synopsisElement.text() : "No encontrado";

            Elements actorElements = document.select("[data-testid=\"title-cast-item__actor\"]");
            StringBuilder actorLinks = new StringBuilder();
            StringBuilder actorNames = new StringBuilder();

            for (Element actorElement : actorElements) {
                String actorHref = actorElement.attr("href");
                actorLinks.append("https://www.imdb.com/").append(actorHref).append(",");
                actorNames.append(actorElement.text()).append(", ");
            }

            if (actorLinks.length() > 0) {
                actorLinks.setLength(actorLinks.length() - 2);
            }

            System.out.println("Título: " + title);
            System.out.println("Calificación: " + rating);
            System.out.println("Sinopsis: " + synopsis);
            System.out.println("ActorNames: " + actorNames.toString());


            String[] actorLinkArray = actorLinks.toString().split(",");
            StringBuilder links = new StringBuilder();

            for (String actorLink : actorLinkArray) 
                links.append(extractMoviesFromActor(actorLink));
            
            if(links.length() > 0)
                links.setLength(links.length() - 1);

            System.out.println(links.toString());
        } catch (IOException e) {
        }
    }

    private static String extractMoviesFromActor(String actorUrl){
        try {
            Document document = Jsoup.connect(actorUrl).get();
            Elements movieElements = document.select(".ipc-primary-image-list-card__title");

            StringBuilder movieLinks = new StringBuilder();
            for (Element movieElement : movieElements) {
                String movieHref = movieElement.attr("href");
                movieLinks.append("https://www.imdb.com/").append(movieHref).append(", ");
            }

            if (movieLinks.length() > 0) 
                movieLinks.setLength(movieLinks.length() - 1);
            
            return movieLinks.toString();
        } catch (IOException e) {}

        return "";
    }

}
 */
package com.web.net;

import com.web.LinkHandler;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LinkFinder implements Runnable {

    private String url;
    private LinkHandler linkHandler;

    /**
     * Se utiliza para estadísticas
     */
    private static final long t0 = System.nanoTime();

    public LinkFinder(String url, LinkHandler handler) {
        this.url = url;
        this.linkHandler = handler;
    }

    @Override
    public void run() {
        getSimpleLinks(url);
    }

    private void getSimpleLinks(String url) {
        // si no se ha visitado
        if (!linkHandler.visited(url)) {
            try {
                /*
                 * URL urilink = new URL(url);
                 * Parser parser = new Parser(urilink.openConnection());
                 * NodeList list = parser.extractAllNodesThatMatch(new
                 * NodeClassFilter(LinkTag.class));
                 * List<String> urls = new ArrayList<>();
                 * 
                 * //System.out.println("Analizando: " + url);
                 * for (int i=0; i < list.size(); i++) {
                 * LinkTag extracted = (LinkTag) list.elementAt(i);
                 * 
                 * if (!extracted.getLink().isBlank() &&
                 * !linkHandler.visited(extracted.getLink())) {
                 * urls.add(extracted.getLink());
                 * }
                 * }
                 * 
                 * // visitatamos el link
                 * linkHandler.addVisited(url);
                 * 
                 * if (linkHandler.size() == 1500) {
                 * System.out.println("Tiempo para visitar 1500 links = " + (System.nanoTime() -
                 * t0));
                 * }
                 * 
                 * for (String l: urls) {
                 * linkHandler.queueLink(l);
                 * }
                 */

                Document document = Jsoup.connect(url).get();

                Element titleElement = document.selectFirst("[data-testid=\"hero__primary-text\"]");
                String title = titleElement != null ? titleElement.text() : "No encontrado";

                Element ratingElement = document
                        .selectFirst("[data-testid=\"hero-rating-bar__aggregate-rating__score\"]");
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

                if (links.length() > 0)
                    links.setLength(links.length() - 1);

                for(String link: links.toString().split(", "))
                    linkHandler.queueLink(link);
                

            } catch (Exception e) {
            }
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

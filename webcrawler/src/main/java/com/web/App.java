package com.web;

public class App {
    ////     Objecto Movie: title, rating, synopsis, actors (Array de Objectos Movie), movies
    ////     Objecto Actor: name, movies (Array de Objetos Movie)
    public static void main(String[] args) {
        try {
            new WebCrawler("https://www.imdb.com/title/tt11097384/?ref_=nm_flmg_t_1_act", 64).startCrawling();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

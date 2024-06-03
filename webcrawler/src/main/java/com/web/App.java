package com.web;

public class App {
    public static void main(String[] args) {
        try {
            new WebCrawler("https://www.imdb.com/title/tt4154756/?language=es-mx", 64).startCrawling();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.web;

public class App {
    public static void main(String[] args) {
        try {
            new WebCrawler("https://www.imdb.com/title/tt1396484/", 64).startCrawling();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}

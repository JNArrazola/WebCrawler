package com.web;

public class App {
    public static void main(String[] args) {
        try {
            new WebCrawler("https://www.imdb.com/title/tt11097384/?ref_=nm_flmg_t_1_act", 64).startCrawling();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

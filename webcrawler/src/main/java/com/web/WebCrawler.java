package com.web;

import com.web.net.LinkFinder; 
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebCrawler implements LinkHandler {

    private final Collection<String> visitedNames = Collections.synchronizedSet(new HashSet<String>());
    // private final Collection<String> visitedNames = Collections.synchronizedList(new ArrayList<String>());
    private String url;
    private ExecutorService executorService;

    public WebCrawler(String startingURL, int maxThreads) {
        this.url = startingURL;
        executorService = Executors.newFixedThreadPool(maxThreads);
    }

    @Override
    public void queueLink(String link) throws Exception {
        startNewThread(link);
    }

    private void startNewThread(String link) throws Exception {
        executorService.execute(new LinkFinder(link, this));
    }

    public void startCrawling() throws Exception {
        startNewThread(this.url);
    }

    @Override
    public int size() {
        return visitedNames.size();
    }

    @Override
    public void addVisited(String s) {
        visitedNames.add(s);
    }

    @Override
    public boolean visited(String link) {
        return visitedNames.contains(link);
    }
}

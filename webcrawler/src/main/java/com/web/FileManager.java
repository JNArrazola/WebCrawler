package com.web;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


/**
 * Class that will manage IO operations with a csv file
 */
// There's gonna be only one csv (peliculas.csv)
public class FileManager {
    private String fileName;

    /**
     * Constructor of the FileManager class
     * @param fileName
     */
    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Saves the list of movies in a .csv file
     * @param movies
     */
    public void saveMovies(List<Movie> movies) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (Movie movie : movies) {
                writer.write('\"' + movie.getId() + '\"' + ",\"" + movie.getTitle() + ",\"" + movie.getRating() + ", \"" + movie.getSynopsis() + "\"\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

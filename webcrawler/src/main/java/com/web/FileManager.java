package com.web;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase que se encarga de guardar y leer de un csv
 */
// va a haber solamente un archivo csv (peliculas.csv)
public class FileManager {
    private String fileName;

    /**
     * Constructor de la clase FileManager
     * @param fileName
     */
    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Guarda una lista de peliculas en un archivo csv
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

    /**
     * Lee un archivo csv y devuelve una lista de peliculas
     * @return
     */
    public List<Movie> readMovies() {
        List<Movie> movies = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                movies.add(new Movie(data[0], data[1], data[2], data[3], new ArrayList<>()));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }    

}

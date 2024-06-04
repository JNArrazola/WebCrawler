package com.web;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Class that will manage IO operations with a csv file
 */
public class FileManager {

    /**
     * Writes an object in a csv file
     * @param object
     * @param file
      */
    public static void writeInCsv(Object object, String file){
        try (FileWriter csvWriter = new FileWriter(file, true)){
            if (object instanceof Movie) {
                Movie movie = (Movie) object;
                csvWriter.append("\""+movie.getId()+"\"");
                csvWriter.append(",");
                csvWriter.append("\""+movie.getTitle()+"\"");
                csvWriter.append(",");
                csvWriter.append("\""+movie.getRating()+"\"");
                csvWriter.append(",");
                csvWriter.append("\""+movie.getSynopsis()+"\"");
                csvWriter.append("\n");
            } else if (object instanceof Actor) {
                Actor actor = (Actor) object;
                csvWriter.append("\""+actor.getActorId()+"\"");
                csvWriter.append(",");
                csvWriter.append("\""+actor.getMovieId()+"\"");
                csvWriter.append(",");
                csvWriter.append("\""+actor.getName()+"\"");
                csvWriter.append("\n");
            }
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

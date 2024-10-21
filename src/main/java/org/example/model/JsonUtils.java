package org.example.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The JsonUtils class provides utility methods for reading and writing JSON data to and from files.
 * @author Abdulkarim Khalili
 */
public class JsonUtils {

    /**
     * Reads a JSON file from the specified file path.
     * @param filePath the path to the JSON file.
     * @return a JSONArray containing the data from the file.
     */
    public static JSONArray readJsonFile(String filePath) {
        try {
            String jsonData = new String(Files.readAllBytes(Paths.get(filePath)));
            if (jsonData.trim().isEmpty()) {
                return new JSONArray();
            }
            return new JSONArray(jsonData);
        } catch (IOException | JSONException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
            return new JSONArray();
        }
    }

    /**
     * Ensures that a file exists at the specified path. If the file does not exist, it creates an empty JSON file.
     * @param filePath the path to the file.
     */
    public static void ensureFileExists(String filePath) {
        try {
            if (!Files.exists(Paths.get(filePath))) {
                Files.createFile(Paths.get(filePath));
                writeJsonFile(filePath, new JSONArray());
            }
        } catch (IOException e) {
            System.err.println("Error ensuring file exists: " + e.getMessage());
        }
    }

    /**
     * Writes a JSONArray to a file at the specified path.
     * @param filePath  the path to the file.
     * @param jsonArray the JSONArray to write.
     */
    public static void writeJsonFile(String filePath, JSONArray jsonArray) {
        try {
            Files.write(Paths.get(filePath), jsonArray.toString().getBytes());
        } catch (IOException e) {
            System.err.println("Error writing JSON file: " + e.getMessage());
        }
    }
}

package org.example.model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The DatabaseJson class is responsible for loading flashcards data from a JSON file.
 */
public class DatabaseJson {
    private static final String DEFAULT_FLASHCARDS_JSON_PATH = "flashcardsInfo.json";
    private final String path;
    private final List<JSONObject> flashcards;

    /**
     * Constructs a new DatabaseJson instance and loads the flashcards data.
     */
    public DatabaseJson() {
        this(DEFAULT_FLASHCARDS_JSON_PATH);
    }

    /**
     * Constructs a new DatabaseJson instance with the specified path and loads the flashcards data.
     *
     * @param path the path to the JSON file.
     */
    public DatabaseJson(String path) {
        this.path = path;
        this.flashcards = loadFlashcards();
    }

    /**
     * Loads flashcards from the specified JSON file path.
     *
     * @return a list of JSONObjects representing the flashcards.
     */
    List<JSONObject> loadFlashcards() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {  // Accessing through ClassLoader
            if (inputStream == null) {
                throw new IOException("Resource not found: " + path);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String jsonData = reader.lines().collect(Collectors.joining());
            JSONArray flashcardsArray = new JSONArray(jsonData);
            return IntStream.range(0, flashcardsArray.length())
                    .mapToObj(flashcardsArray::getJSONObject)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error loading flashcards from JSON: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves the loaded flashcards data.
     *
     * @return a list of JSONObjects representing the flashcards.
     */
    public List<JSONObject> getData() {
        return flashcards;
    }
}

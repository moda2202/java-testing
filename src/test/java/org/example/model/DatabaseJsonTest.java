package org.example.model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The DatabaseJsonTests class contains unit tests for the DatabaseJson class.
 * @author Abdulkarim Khalili
 */
public class DatabaseJsonTest {

    private static final String TEST_JSON_PATH = "flashcardsInfoTest.json";

    /**
     * Sets up the test environment by creating a test JSON file before each test.
     * @throws IOException if an I/O error occurs.
     */
    @BeforeEach
    void setUp() throws IOException {
        // Ensure the test directory exists
        Files.createDirectories(Paths.get("src/test/resources"));

        // Prepare test JSON file content
        String jsonContent = "[{\"Title\":\"Test Title 1\",\"Info\":\"Test Info 1\"}," +
                "{\"Title\":\"Test Title 2\",\"Info\":\"Test Info 2\"}]";
        Files.write(Paths.get(TEST_JSON_PATH), jsonContent.getBytes());
    }

    /**
     * Tests the loadFlashcards method of DatabaseJson to ensure it loads flashcards correctly.
     */
    @Test
    public void testLoadFlashcards() {
        DatabaseJson databaseJson = new DatabaseJson(TEST_JSON_PATH);
        List<JSONObject> flashcards = databaseJson.loadFlashcards();

        // Verify the flashcards are loaded successfully
        assertNotNull(flashcards);
        assertEquals(2, flashcards.size());
        assertEquals("Test Title 1", flashcards.get(0).getString("Title"));
        assertEquals("Test Info 1", flashcards.get(0).getString("Info"));
        assertEquals("Test Title 2", flashcards.get(1).getString("Title"));
        assertEquals("Test Info 2", flashcards.get(1).getString("Info"));
    }

    /**
     * Tests the getData method of DatabaseJson to ensure it retrieves the loaded flashcards correctly.
     */
    @Test
    public void testGetData() {
        DatabaseJson databaseJson = new DatabaseJson(TEST_JSON_PATH);
        List<JSONObject> flashcards = databaseJson.getData();

        // Assert that data size is consistent with expected amount
        assertNotNull(flashcards);
        assertEquals(2, flashcards.size());

        // Verify the content
        assertEquals("Test Title 1", flashcards.get(0).getString("Title"));
        assertEquals("Test Info 1", flashcards.get(0).getString("Info"));
        assertEquals("Test Title 2", flashcards.get(1).getString("Title"));
        assertEquals("Test Info 2", flashcards.get(1).getString("Info"));
    }
}
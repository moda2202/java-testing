package org.example.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The JsonUtilsTest class contains unit tests for the JsonUtils class.
 * @author Abdulkarim Khalili
 */
class JsonUtilsTest {

    private static final String TEST_JSON_PATH = "src/test/resources/test.json";

    /**
     * Sets up the test environment by creating a test JSON file before each test.
     * @throws IOException if an I/O error occurs.
     */
    @BeforeEach
    void setUp() throws IOException {
        // Prepare test JSON file content
        String jsonContent = "[{\"key\":\"value\"}]";
        Files.write(Paths.get(TEST_JSON_PATH), jsonContent.getBytes());
    }

    /**
     * Tests the readJsonFile method of JsonUtils to ensure it reads JSON data correctly.
     */
    @Test
    void testReadJsonFile() {
        JSONArray jsonArray = JsonUtils.readJsonFile(TEST_JSON_PATH);

        assertEquals(1, jsonArray.length());
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        assertEquals("value", jsonObject.getString("key"));
    }

    /**
     * Tests the readJsonFile method of JsonUtils with an empty JSON file to ensure it handles empty files correctly.
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testReadEmptyJsonFile() throws IOException {
        // Prepare empty test JSON file content
        Files.write(Paths.get(TEST_JSON_PATH), "[]".getBytes());

        JSONArray jsonArray = JsonUtils.readJsonFile(TEST_JSON_PATH);

        assertTrue(jsonArray.isEmpty());
    }

    /**
     * Tests the ensureFileExists method of JsonUtils to ensure it creates a file if it does not exist.
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testEnsureFileExists() throws IOException {
        String nonExistentPath = "src/test/resources/non_existent.json";
        JsonUtils.ensureFileExists(nonExistentPath);

        assertTrue(Files.exists(Paths.get(nonExistentPath)));

        // Cleanup
        Files.deleteIfExists(Paths.get(nonExistentPath));
    }

    /**
     * Tests the writeJsonFile method of JsonUtils to ensure it writes JSON data correctly.
     */
    @Test
    void testWriteJsonFile() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(new JSONObject().put("key", "new_value"));

        JsonUtils.writeJsonFile(TEST_JSON_PATH, jsonArray);

        JSONArray readArray = JsonUtils.readJsonFile(TEST_JSON_PATH);
        assertEquals(1, readArray.length());
        assertEquals("new_value", readArray.getJSONObject(0).getString("key"));
    }
}

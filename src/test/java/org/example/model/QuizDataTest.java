package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the QuizData class.
 * @author Abdulkarim Khalili
 */
class QuizDataTest {

    private QuizData quizData;
    private static final String TEST_JSON_PATH = "questionsTest.json";

    /**
     * Sets up the test environment before each test.
     * @throws IOException if an I/O error occurs.
     */
    @BeforeEach
    void setUp() throws IOException {
        // Prepare test JSON file content
        String jsonContent = "[{\"category\":\"Category 1\",\"questions\":[{\"questionText\":\"Question 1\",\"choices\":[\"Choice 1\",\"Choice 2\"],\"correctAnswerIndex\":0}]},{\"category\":\"Category 2\",\"questions\":[{\"questionText\":\"Question 2\",\"choices\":[\"Choice 3\",\"Choice 4\"],\"correctAnswerIndex\":1}]}]";
        Files.createDirectories(Paths.get("src/test/resources"));
        Files.write(Paths.get(TEST_JSON_PATH), jsonContent.getBytes());

        // Create QuizData instance with the test path
        quizData = new QuizData(TEST_JSON_PATH);
    }

    /**
     * Tests the loading of quiz data from the JSON file.
     */
    @Test
    void testLoadQuizData() {
        List<String> categories = quizData.getOrderedCategoryKeys();

        assertEquals(2, categories.size());
        assertEquals("Category 1", categories.get(0));
        assertEquals("Category 2", categories.get(1));

        List<QuizQuestion> questionsCategory1 = quizData.getQuestionsForCategory("Category 1");
        assertEquals(1, questionsCategory1.size());
        QuizQuestion question1 = questionsCategory1.get(0);
        assertEquals("Question 1", question1.getQuestionText());
        assertEquals(2, question1.getChoices().size());
        assertEquals(0, question1.getCorrectAnswerIndex());

        List<QuizQuestion> questionsCategory2 = quizData.getQuestionsForCategory("Category 2");
        assertEquals(1, questionsCategory2.size());
        QuizQuestion question2 = questionsCategory2.get(0);
        assertEquals("Question 2", question2.getQuestionText());
        assertEquals(2, question2.getChoices().size());
        assertEquals(1, question2.getCorrectAnswerIndex());
    }

    /**
     * Tests that an empty list is returned for a non-existent category.
     */
    @Test
    void testGetQuestionsForNonExistentCategory() {
        List<QuizQuestion> questions = quizData.getQuestionsForCategory("NonExistentCategory");

        assertTrue(questions.isEmpty());
    }
}

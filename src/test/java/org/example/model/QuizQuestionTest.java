package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the QuizQuestion class.
 * @author Abdulkarim Khalili
 */
class QuizQuestionTest {

    private QuizQuestion quizQuestion;

    /**
     * Sets up a sample QuizQuestion instance before each test.
     */
    @BeforeEach
    void setUp() {
        quizQuestion = new QuizQuestion("Sample Question", List.of("Choice 1", "Choice 2", "Choice 3"), 1);
    }

    /**
     * Tests that getQuestionText() returns the correct question text.
     */
    @Test
    void testGetQuestionText() {
        assertEquals("Sample Question", quizQuestion.getQuestionText());
    }

    /**
     * Tests that getChoices() returns the correct list of choices.
     */
    @Test
    void testGetChoices() {
        List<String> choices = quizQuestion.getChoices();
        assertEquals(3, choices.size());
        assertEquals("Choice 1", choices.get(0));
        assertEquals("Choice 2", choices.get(1));
        assertEquals("Choice 3", choices.get(2));
    }

    /**
     * Tests that getCorrectAnswerIndex() returns the correct index of the correct answer.
     */
    @Test
    void testGetCorrectAnswerIndex() {
        assertEquals(1, quizQuestion.getCorrectAnswerIndex());
    }
}

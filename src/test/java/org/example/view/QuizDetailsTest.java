package org.example.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for QuizDetails.
 * This class contains unit tests for the QuizDetails class to ensure its functionality.
 *
 * @author Mounir Darwich
 */
class QuizDetailsTest {

    private QuizDetails quizDetails;
    private JButton sourceButton;

    /**
     * Sets up the test environment before each test.
     * Initializes the QuizDetails instance.
     */
    @BeforeEach
    void setUp() {
        sourceButton = new JButton("Source Button");
        quizDetails = new QuizDetails(sourceButton);
    }

    /**
     * Tests that the source button is correctly set.
     */
    @Test
    void testSourceButton() {
        assertEquals(sourceButton, quizDetails.getSourceButton());
    }

    /**
     * Tests that the submit button is correctly set and accessible.
     */
    @Test
    void testSubmitButton() {
        JButton submitButton = quizDetails.getSubmitButton();
        assertNotNull(submitButton);
        assertEquals("Submit", submitButton.getText());
    }

    /**
     * Tests that the correct answers list is initially empty.
     */
    @Test
    void testInitialCorrectAnswersListIsEmpty() {
        assertTrue(quizDetails.getCorrectAnswers().isEmpty());
    }

    /**
     * Tests that the question groups list is initially empty.
     */
    @Test
    void testInitialQuestionGroupsListIsEmpty() {
        assertTrue(quizDetails.getQuestionGroups().isEmpty());
    }

    /**
     * Tests creating a question panel.
     * Verifies that the question panel is correctly created and added to the main panel.
     */
    @Test
    void testCreateQuestionPanel() {
        String question = "What is the capital of France?";
        List<String> options = new ArrayList<>();
        options.add("Berlin");
        options.add("Madrid");
        options.add("Paris");
        options.add("Rome");

        quizDetails.createQuestionPanel(question, options, 2);

        List<JRadioButton> correctAnswers = quizDetails.getCorrectAnswers();
        List<ButtonGroup> questionGroups = quizDetails.getQuestionGroups();

        assertEquals(1, correctAnswers.size());
        assertEquals(1, questionGroups.size());
        assertEquals("Paris", correctAnswers.get(0).getText());
        assertEquals(4, questionGroups.get(0).getButtonCount());
    }
}

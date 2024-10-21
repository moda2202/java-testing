package org.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Controller class.
 * @author Abdulkarim Khalili
 */
public class ControllerTest {
    private Controller controller;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    /**
     * Tests that the createScaledIcon method creates a non-null icon of the correct size.
     */
    @Test
    void testCreateScaledIconShouldCreateNonNullOrReturnCorrectSize() {
        ImageIcon icon = controller.createScaledIcon("checkGrean.png");
        assertNotNull(icon, "Icon should not be null");
        assertEquals(20, icon.getIconHeight(), "Icon height should be 20");
        assertEquals(20, icon.getIconWidth(), "Icon width should be 20");
    }

    /**
     * Tests that the loadFlashcardsButtonStates method populates the buttonInfoMap.
     */
    @Test
    void testLoadFlashcardsButtonStatesShouldNotLeaveButtonInfoMapEmpty() {
        controller.loadFlashcardsButtonStates();
        assertFalse(controller.buttonInfoMap.isEmpty(), "Button info map should not be empty after loading states");
    }

    /**
     * Tests that the loadQuizButtonStates method populates the quizButtonInfoMap.
     */
    @Test
    void testLoadQuizButtonStatesShouldNotLeaveQuizButtonInfoMapEmpty() {
        controller.loadQuizButtonStates();
        assertFalse(controller.quizButtonInfoMap.isEmpty(), "Quiz button info map should not be empty after loading states");
    }

    /**
     * Tests that the applyButtonStates method sets the correct icon for buttons.
     */
    @Test
    void testApplyButtonStatesShouldSetCorrectIcon() {
        // Prepare test environment: Initialize buttons with one having the text "buttonText"
        JButton testButton = new JButton("buttonText");
        controller.mainFrame.getFlashcardFrame().getButtons().add(testButton);

        // Prepare JSON state data
        JSONArray buttonStates = new JSONArray();
        JSONObject buttonState = new JSONObject();
        buttonState.put("text", "buttonText");
        buttonState.put("icon", "green");
        buttonStates.put(buttonState);

        // Apply the states
        controller.applyButtonStates(buttonStates, controller.mainFrame.getFlashcardFrame().getButtons());

        // Assert that the button now has the green icon
        JButton button = controller.mainFrame.getFlashcardFrame().getButtons().stream()
                .filter(b -> "buttonText".equals(b.getText()))
                .findFirst()
                .orElse(null);

        assertNotNull(button, "Button should exist for given text");
        assertEquals(controller.greenIcon, button.getIcon(), "Button icon should be green");
    }

    /**
     * Tests that the showDetailFrame method does not throw an exception.
     */
    @Test
    void testShowDetailFrameShouldNotThrowException() {
        JButton button = new JButton("Test");
        controller.buttonInfoMap.put(button, "Test info");
        assertDoesNotThrow(() -> controller.showDetailFrame(button), "Showing detail frame should not throw exception");
    }

    /**
     * Tests that the showQuizDetails method does not throw an exception.
     */
    @Test
    void testShowQuizDetailsShouldNotThrowException() {
        JButton button = new JButton("Test");
        controller.quizButtonInfoMap.put(button, "Test quiz");
        assertDoesNotThrow(() -> controller.showQuizDetails(button), "Showing quiz details should not throw exception");
    }

    /**
     * Tests that the createButtonForFlashcardFrame method does not throw an exception.
     */
    @Test
    void testCreateButtonForFlashcardFrameShouldNotThrowException() {
        JSONObject jsonObject = new JSONObject()
                .put("Title", "Test Title")
                .put("Info", "Test Info");

        assertDoesNotThrow(() -> controller.createButtonForFlashcardFrame(jsonObject),
                "Creating button for flashcard frame should not throw exception");
    }

    /**
     * Tests that the createButtonForQuizFrame method does not throw an exception.
     */
    @Test
    void testCreateButtonForQuizFrameShouldNotThrowException() {
        assertDoesNotThrow(() -> controller.createButtonForQuizFrame("Test Category"),
                "Creating button for quiz frame should not throw exception");
    }
}

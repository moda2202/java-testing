package org.example.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.JButton;
import java.awt.Dimension;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for QuizFrame.
 * This class contains unit tests for the QuizFrame class to ensure its functionality.
 *
 * @author Mounir Darwich
 */
class QuizFrameTest {

    private QuizFrame quizFrame;
    private MainFrame mainFrame;

    /**
     * Sets up the test environment before each test.
     * Initializes the MainFrame and QuizFrame instances.
     */
    @BeforeEach
    void setUp() {
        mainFrame = new MainFrame();
        quizFrame = new QuizFrame(mainFrame);
    }

    /**
     * Tests that the initial list of buttons in the QuizFrame is empty.
     */
    @Test
    void testInitialButtonListIsEmpty() {
        assertTrue(quizFrame.getButtons().isEmpty());
    }

    /**
     * Tests adding a button to the QuizFrame.
     * Verifies that the button is correctly added to the list of buttons.
     */
    @Test
    void testAddButton() {
        JButton button = new JButton("Test Button");
        quizFrame.addButton(button);

        assertEquals(1, quizFrame.getButtons().size());
        assertEquals("Test Button", quizFrame.getButtons().get(0).getText());
    }

    /**
     * Tests that the size of the QuizFrame is correctly set.
     */
    @Test
    void testFrameSize() {
        Dimension expectedSize = new Dimension(980, 600);
        assertEquals(expectedSize, quizFrame.getSize());
    }

    /**
     * Tests the search functionality of the QuizFrame.
     * Verifies that a button with the search text is visible after the search.
     */
    @Test
    void testSearchFunctionality() {
        JButton button = new JButton("Search Test");
        quizFrame.addButton(button);

        quizFrame.updateButtonVisibility("Search Test");
        assertTrue(quizFrame.getButtons().stream().anyMatch(b -> b.getText().equals("Search Test")));
    }

    /**
     * Tests that a button not matching the search criteria is not visible after the search.
     */
    @Test
    void testButtonNotPresentAfterSearch() {
        JButton button1 = new JButton("Test 1");
        JButton button2 = new JButton("Test 2");
        quizFrame.addButton(button1);
        quizFrame.addButton(button2);

        quizFrame.updateButtonVisibility("Test 1");
        assertFalse(quizFrame.getButtons().stream().anyMatch(b -> b.getText().equals("Test 2") && b.getParent() != null));
    }
}

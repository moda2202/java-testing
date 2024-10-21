package org.example.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JButton;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MainFrame.
 * This class contains unit tests for the MainFrame class to ensure its functionality.
 *
 * @author Mounir Darwich
 */
class MainFrameTest {
    private MainFrame mainFrame;

    /**
     * Sets up the test environment before each test.
     * Initializes a new instance of MainFrame.
     */
    @BeforeEach
    void setUp() {
        mainFrame = new MainFrame();
    }

    /**
     * Tests that the initial mode is not dark mode.
     * Verifies that isDarkMode() returns false initially.
     */
    @Test
    void testInitialDarkMode() {
        assertFalse(mainFrame.isDarkMode());
    }

    /**
     * Tests toggling the dark mode.
     * Verifies that the mode can be toggled between dark mode and light mode.
     */
    @Test
    void testToggleDarkMode() {
        mainFrame.actionPerformed(new ActionEvent(mainFrame.modeButton, ActionEvent.ACTION_PERFORMED, null));
        assertTrue(mainFrame.isDarkMode());

        mainFrame.actionPerformed(new ActionEvent(mainFrame.modeButton, ActionEvent.ACTION_PERFORMED, null));
        assertFalse(mainFrame.isDarkMode());
    }

    /**
     * Tests the frame title.
     * Verifies that the title of the frame is correctly set.
     */
    @Test
    void testFrameTitle() {
        assertEquals("", mainFrame.getTitle());
    }

    /**
     * Tests the action of the Flashcard button.
     * Verifies that clicking the Flashcard button makes the FlashcardFrame visible and hides the MainFrame.
     */
    @Test
    void testFlashcardButtonAction() {
        mainFrame.actionPerformed(new ActionEvent(mainFrame.flachCardButton, ActionEvent.ACTION_PERFORMED, null));
        assertTrue(mainFrame.getFlashcardFrame().isVisible());
        assertFalse(mainFrame.isVisible());
    }

    /**
     * Tests the action of the Quiz button.
     * Verifies that clicking the Quiz button makes the QuizFrame visible and hides the MainFrame.
     */
    @Test
    void testQuizButtonAction() {
        mainFrame.actionPerformed(new ActionEvent(mainFrame.quizButton, ActionEvent.ACTION_PERFORMED, null));
        assertTrue(mainFrame.getQuizFrame().isVisible());
        assertFalse(mainFrame.isVisible());
    }

    /**
     * Tests the frame size.
     */
    @Test
    void testFrameSize() {
        Dimension expectedSize = new Dimension(900, 600);
        assertEquals(expectedSize, mainFrame.getSize());
    }

    /**
     * Tests the frame size.
     * Verifies that the size of the frame is correctly set.
     */
    @Test
    void testButtonTexts() {
        assertEquals("Quiz", mainFrame.quizButton.getText());
        assertEquals("FlashCard", mainFrame.flachCardButton.getText());
        assertEquals("Mode", mainFrame.modeButton.getText());
    }

    /**
     * Tests the mode button icon.
     * Verifies that the mode button has an icon set.
     */
    @Test
    void testModeButtonIcon() {
        assertNotNull(mainFrame.modeButton.getIcon());
    }

    /**
     * Tests the button fonts.
     * Verifies that the fonts of the buttons are correctly set.
     */
    @Test
    void testButtonFonts() {
        Font expectedFont = new Font("Arial", Font.BOLD, 16);
        assertEquals(expectedFont, mainFrame.quizButton.getFont());
        assertEquals(expectedFont, mainFrame.flachCardButton.getFont());
        assertEquals(expectedFont, mainFrame.modeButton.getFont());
    }

    /**
     * Tests the number of buttons in the panel.
     * Verifies that the panel contains the correct number of buttons.
     */
    @Test
    void testButtonCount() {
        // Get the panel from the MainFrame
        JPanel panel = (JPanel) mainFrame.getContentPane().getComponent(0);

        // Count the number of buttons in the panel
        int buttonCount = 0;
        for (Component component : panel.getComponents()) {
            if (component instanceof JButton) {
                buttonCount++;
            }
        }

        // Assert that the number of buttons is as expected
        assertEquals(3, buttonCount);
    }
}

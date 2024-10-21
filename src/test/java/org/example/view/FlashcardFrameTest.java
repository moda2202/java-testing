package org.example.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test class for FlashcardFrame.
 *
 * @author Mounir Darwich
 */
class FlashcardFrameTest {

    private FlashcardFrame flashcardFrame;
    private MainFrame mainFrame;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        mainFrame = new MainFrame();
        flashcardFrame = new FlashcardFrame(mainFrame);
    }

    /**
     * Tests that the initial list of buttons is empty.
     */
    @Test
    void testInitialButtonListIsEmpty() {
        assertTrue(flashcardFrame.getButtons().isEmpty());
    }

    /**
     * Tests adding a button to the frame.
     */
    @Test
    void testAddButton() {
        JButton button = new JButton("Test Button");
        flashcardFrame.addButton(button);

        assertEquals(1, flashcardFrame.getButtons().size());
        assertEquals("Test Button", flashcardFrame.getButtons().get(0).getText());
    }

    /**
     * Tests that the properties of the added buttons are set correctly.
     */
    @Test
    void testButtonProperties() {
        JButton button = new JButton("Property Test Button");
        flashcardFrame.addButton(button);

        JButton addedButton = flashcardFrame.getButtons().get(0);
        assertEquals(SwingConstants.RIGHT, addedButton.getHorizontalTextPosition());
        assertEquals(5, addedButton.getIconTextGap());
        assertEquals(SwingConstants.LEFT, addedButton.getHorizontalAlignment());
        assertEquals(new Font("Helvetica", Font.BOLD, 16), addedButton.getFont());
    }

    /**
     * Tests that the frame size is correctly set.
     */
    @Test
    void testFrameSize() {
        Dimension expectedSize = new Dimension(980, 600);
        assertEquals(expectedSize, flashcardFrame.getSize());
    }

    /**
     * Tests the search functionality.
     */
    @Test
    void testSearchFunctionality() {
        JButton button1 = new JButton("Search Test 1");
        JButton button2 = new JButton("Search Test 2");
        flashcardFrame.addButton(button1);
        flashcardFrame.addButton(button2);

        flashcardFrame.updateButtonVisibility("Search Test 1");
        assertEquals(1, flashcardFrame.getButtons().stream().filter(b -> b.getParent() != null).count());
        assertTrue(flashcardFrame.getButtons().stream().anyMatch(b -> b.getText().equals("Search Test 1")));

        flashcardFrame.updateButtonVisibility("Search Test 2");
        assertEquals(1, flashcardFrame.getButtons().stream().filter(b -> b.getParent() != null).count());
        assertTrue(flashcardFrame.getButtons().stream().anyMatch(b -> b.getText().equals("Search Test 2")));
    }

    /**
     * Tests the search functionality with multiple matches.
     */
    @Test
    void testSearchFunctionalityWithMultipleMatches() {
        JButton button1 = new JButton("Test 1");
        JButton button2 = new JButton("Test 2");
        JButton button3 = new JButton("Another Test");
        flashcardFrame.addButton(button1);
        flashcardFrame.addButton(button2);
        flashcardFrame.addButton(button3);

        flashcardFrame.updateButtonVisibility("Test");
        assertEquals(3, flashcardFrame.getButtons().stream().filter(b -> b.getParent() != null).count());
    }


    /**
     * Tests that the search timer is running when a search is performed.
     */
    @Test
    void testSearchTimer() {
        JButton button = new JButton("Search Test");
        flashcardFrame.addButton(button);

        flashcardFrame.searchField.setText("Search Test");
        flashcardFrame.searchField.postActionEvent();

        assertTrue(flashcardFrame.searchTimer.isRunning());
    }

    /**
     * Tests that the search timer is not running initially.
     */
    @Test
    void testTimerNotRunningInitially() {
        assertFalse(flashcardFrame.searchTimer.isRunning());
    }

    /**
     * Tests that a button not matching the search criteria is not visible after the search.
     */
    @Test
    void testButtonNotPresentAfterSearch() {
        JButton button1 = new JButton("Test 1");
        JButton button2 = new JButton("Test 2");
        flashcardFrame.addButton(button1);
        flashcardFrame.addButton(button2);

        flashcardFrame.updateButtonVisibility("Test 1");
        assertFalse(flashcardFrame.getButtons().stream().anyMatch(b -> b.getText().equals("Test 2") && b.getParent()
                != null));
    }

    /**
     * Tests that the MainFrame is visible again when the FlashcardFrame window is closed.
     */
    @Test
    void testWindowClosing() {
        WindowEvent closingEvent = new WindowEvent(flashcardFrame, WindowEvent.WINDOW_CLOSING);
        flashcardFrame.dispatchEvent(closingEvent);

        assertTrue(mainFrame.isVisible());
    }
}

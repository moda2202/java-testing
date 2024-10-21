package org.example.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for DetailFrame.
 *
 * @author Mounir Darwich
 */
class DetailFrameTest {

    private DetailFrame detailFrame;
    private JButton testButton;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        testButton = new JButton("Test Button");
        detailFrame = new DetailFrame("Test Title", "Test Info", testButton);
    }

    /**
     * Tests that the frame title is set correctly.
     */
    @Test
    void testFrameTitle() {
        assertEquals("Test Title", detailFrame.getTitle());
    }

    /**
     * Tests that the linked button is correctly set.
     */
    @Test
    void testLinkedButton() {
        assertEquals(testButton, detailFrame.linkedButton);
    }

    /**
     * Tests that the done button is correctly created.
     */
    @Test
    void testDoneButton() {
        JButton doneButton = detailFrame.getDoneButton();
        assertNotNull(doneButton);
        assertEquals("Done", doneButton.getText());
    }

    /**
     * Tests that the frame size is set correctly.
     */
    @Test
    void testFrameSize() {
        Dimension expectedSize = new Dimension(900, 600);
        assertEquals(expectedSize, detailFrame.getSize());
    }

    /**
     * Tests that the text area content is set correctly.
     */
    @Test
    void testTextAreaContent() {
        JTextArea textArea = (JTextArea) ((JScrollPane) detailFrame.getContentPane().getComponent(0)).getViewport().getView();
        assertEquals("Test Info", textArea.getText());
    }

    /**
     * Tests that the text area properties are set correctly.
     */
    @Test
    void testTextAreaProperties() {
        JTextArea textArea = (JTextArea) ((JScrollPane) detailFrame.getContentPane().getComponent(0)).getViewport().getView();
        assertFalse(textArea.isEditable());
        assertTrue(textArea.getWrapStyleWord());
        assertTrue(textArea.getLineWrap());
        assertEquals(new Font("SansSerif", Font.BOLD, 16), textArea.getFont());
    }

    /**
     * Tests that the close button disposes of the frame.
     */
    @Test
    void testCloseButtonFunctionality() {
        JButton closeButton = (JButton) ((JPanel) detailFrame.getContentPane().getComponent(1)).getComponent(1);
        closeButton.doClick();
        assertFalse(detailFrame.isDisplayable());
    }
}

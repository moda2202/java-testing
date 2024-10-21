package org.example.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

/**
 * MainFrame class represents the main window of the application.
 * It extends JFrame and provides navigation to other frames like FlashcardFrame and QuizFrame.
 *
 * @author Mounir Darwich
 */
public class MainFrame extends JFrame implements ActionListener {
    JButton quizButton;
    JButton flachCardButton;
    JButton modeButton;
    ImageIcon ModeIcon;
    JPanel panel;
    FlashcardFrame flashcardFrame;
    QuizFrame quisFram;
    boolean isDarkMode = false; // Flag to track mode status

    /**
     * Constructs a MainFrame.
     */
    public MainFrame() {
        flashcardFrame = new FlashcardFrame(this);
        quisFram= new QuizFrame(this);

        panel = new JPanel();
        panel.setBackground(Color.lightGray);
        panel.setLayout(new GridLayout(2, 2, 10, 10));

        // Define button size and font
        Dimension buttonSize = new Dimension(150, 150);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        quizButton = new JButton("Quiz");
        flachCardButton = new JButton("FlashCard");

        try (InputStream iconStream = getClass().getClassLoader().getResourceAsStream("icons8-dark-mode-48.png")) {
            if (iconStream != null) {
                ModeIcon = new ImageIcon(iconStream.readAllBytes());
            } else {
                throw new RuntimeException("Icon file not found: icons8-dark-mode-48.png");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load icon: " + e.getMessage(), e);
        }

        modeButton = new JButton("Mode");
        modeButton.setIcon(ModeIcon);

        // Set button sizes and fonts
        quizButton.setPreferredSize(buttonSize);
        flachCardButton.setPreferredSize(buttonSize);
        modeButton.setPreferredSize(buttonSize);

        quizButton.setFont(buttonFont);
        flachCardButton.setFont(buttonFont);
        modeButton.setFont(buttonFont);

        quizButton.addActionListener(this);
        flachCardButton.addActionListener(this);
        modeButton.addActionListener(this);

        quizButton.setFocusable(false);
        flachCardButton.setFocusable(false);
        modeButton.setFocusable(false);

        quizButton.setForeground(Color.blue);
        flachCardButton.setForeground(Color.blue);
        modeButton.setForeground(Color.blue);

        panel.add(quizButton);
        panel.add(flachCardButton);
        panel.add(modeButton);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(900, 600);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        this.add(panel, gbc);

        updateMode();
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == flachCardButton) {
            flashcardFrame.setVisible(true);
            this.setVisible(false);
        }else if (actionEvent.getSource() == quizButton) {
            quisFram.setVisible(true);
            this.setVisible(false);
        } else if (actionEvent.getSource() == modeButton) {
            isDarkMode = !isDarkMode;
            updateMode();
        }

    }

    /**
     * Updates the mode of the application (dark mode/light mode).
     */
    private void updateMode() {
        if (isDarkMode) {
            this.getContentPane().setBackground(new Color(60, 63, 65));
            panel.setBackground(new Color(60, 63, 65));
        } else {
            this.getContentPane().setBackground(new Color(244, 244, 244));
            panel.setBackground(new Color(244, 244, 244));
        }
    }

    /**
     * Checks if the application is in dark mode.
     *
     * @return true if dark mode is enabled, false otherwise
     */
    public boolean isDarkMode() {
        return isDarkMode;
    }

    public FlashcardFrame getFlashcardFrame() {
        return flashcardFrame;
    }

    public QuizFrame getQuizFrame() {
        return quisFram;
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to be displayed
     */
    public void displayError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

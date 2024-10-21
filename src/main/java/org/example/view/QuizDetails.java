package org.example.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

/**
 * QuizDetails class represents a detailed view of a quiz.
 * It extends JFrame and allows users to view quiz questions and submit answers.
 *
 * @author Mounir Darwich
 */
public class QuizDetails extends JFrame {

    private JButton submitButton, restartButton;
    private final JPanel mainPanel = new JPanel();
    private List<ButtonGroup> questionGroups = new ArrayList<>();
    private List<JRadioButton> correctAnswers = new ArrayList<>();
    private JButton sourceButton;

    /**
     * Constructs a QuizDetails frame.
     *
     * @param sourceButton the button that triggered the creation of this frame
     */
    public QuizDetails(JButton sourceButton) {
        super("Quiz Details");
        this.sourceButton = sourceButton;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitButton = new JButton("Submit");
        restartButton = new JButton("Restart");
        buttonPanel.add(submitButton);
        buttonPanel.add(restartButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Gets the submit button.
     *
     * @return the submit button
     */
    public JButton getSubmitButton() {
        return submitButton;
    }

    /**
     * Gets the source button.
     *
     * @return the source button
     */
    public JButton getSourceButton() {
        return sourceButton;
    }

    /**
     * Gets the list of correct answer buttons.
     *
     * @return the list of correct answer buttons
     */
    public List<JRadioButton> getCorrectAnswers() {
        return correctAnswers;
    }

    /**
     * Gets the list of question groups.
     *
     * @return the list of question groups
     */
    public List<ButtonGroup> getQuestionGroups() {
        return questionGroups;
    }

    /**
     * Creates a question panel with the given question, options, and correct answer index.
     *
     * @param question          the question text
     * @param options           the list of options
     * @param correctAnswerIndex the index of the correct answer
     */
    public void createQuestionPanel(String question, List<String> options, int correctAnswerIndex) {
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        questionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea questionText = new JTextArea(question);
        questionText.setWrapStyleWord(true);
        questionText.setLineWrap(true);
        questionText.setEditable(false);
        questionText.setFont(new Font("Arial", Font.BOLD, 16));
        questionText.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionPanel.add(questionText);

        ButtonGroup buttonGroup = new ButtonGroup();
        questionGroups.add(buttonGroup);
        JRadioButton correctButton = null;
        for (int i = 0; i < options.size(); i++) {
            JRadioButton choiceButton = new JRadioButton(options.get(i));
            choiceButton.setFont(new Font("Arial", Font.PLAIN, 14));
            choiceButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            buttonGroup.add(choiceButton);
            questionPanel.add(choiceButton);
            if (i == correctAnswerIndex) {
                correctButton = choiceButton;
            }
        }
        correctAnswers.add(correctButton);
        mainPanel.add(questionPanel);
    }
}

package org.example.model;

import java.util.List;

/**
 * The QuizQuestion class represents a quiz question with its text, choices, and the index of the correct answer.
 * @author Abdulkarim Khalili
 */
public class QuizQuestion {
    private final String questionText;
    private final List<String> choices;
    private final int correctAnswerIndex;

    /**
     * Constructs a new QuizQuestion instance with the specified question text, choices, and correct answer index.
     *
     * @param questionText       the text of the question.
     * @param choices            a list of choices for the question.
     * @param correctAnswerIndex the index of the correct answer in the choices list.
     */
    public QuizQuestion(String questionText, List<String> choices, int correctAnswerIndex) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    /**
     * Retrieves the text of the question.
     *
     * @return the question text.
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Retrieves the list of choices for the question.
     *
     * @return the list of choices.
     */
    public List<String> getChoices() {
        return choices;
    }

    /**
     * Retrieves the index of the correct answer in the choices list.
     *
     * @return the correct answer index.
     */
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}

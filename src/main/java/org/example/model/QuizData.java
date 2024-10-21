package org.example.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The QuizData class is responsible for loading and managing quiz questions from a JSON file.
 */
public class QuizData {
    private static final String DEFAULT_QUIZ_JSON_PATH = "questions.json"; // Default path for the resource
    private final String quizJsonPath;
    private final Map<String, List<QuizQuestion>> categorizedQuestions;

    /**
     * Constructs a new QuizData instance and loads the quiz data from the default path.
     */
    public QuizData() {
        this(DEFAULT_QUIZ_JSON_PATH);
    }

    /**
     * Constructs a new QuizData instance and loads the quiz data from the specified path.
     * Package-private for testing purposes.
     *
     * @param quizJsonPath the path to the JSON file containing quiz data.
     */
    QuizData(String quizJsonPath) {
        this.quizJsonPath = quizJsonPath;
        this.categorizedQuestions = new LinkedHashMap<>();
        loadQuizData();
    }

    /**
     * Loads quiz data from the specified JSON file path.
     */
    void loadQuizData() {
        String resourcePath = quizJsonPath.startsWith("/") ? quizJsonPath.substring(1) : quizJsonPath;
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {  // Accessing through ClassLoader
            if (inputStream == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String jsonData = reader.lines().collect(Collectors.joining());
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject categoryObject = jsonArray.getJSONObject(i);
                String category = categoryObject.getString("category");
                JSONArray questionsArray = categoryObject.getJSONArray("questions");
                List<QuizQuestion> questions = new ArrayList<>();
                for (int j = 0; j < questionsArray.length(); j++) {
                    JSONObject questionObj = questionsArray.getJSONObject(j);
                    QuizQuestion question = new QuizQuestion(
                            questionObj.getString("questionText"),
                            questionObj.getJSONArray("choices").toList().stream().map(Object::toString).collect(Collectors.toList()),
                            questionObj.getInt("correctAnswerIndex")
                    );
                    questions.add(question);
                }
                categorizedQuestions.put(category, questions);
            }
        } catch (IOException e) {
            System.err.println("Error loading structured quiz data: " + e.getMessage());
        }
    }

    /**
     * Retrieves the ordered category keys.
     *
     * @return a list of category keys in the order they were loaded.
     */
    public List<String> getOrderedCategoryKeys() {
        return new ArrayList<>(categorizedQuestions.keySet());
    }

    /**
     * Retrieves all questions for a specific category.
     *
     * @param category the category name.
     * @return a list of QuizQuestion objects for the specified category.
     */
    public List<QuizQuestion> getQuestionsForCategory(String category) {
        return categorizedQuestions.getOrDefault(category, new ArrayList<>());
    }
}
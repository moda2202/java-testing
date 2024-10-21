package org.example.controller;

import org.example.model.DatabaseJson;
import org.example.model.JsonUtils;
import org.example.model.QuizData;
import org.example.model.QuizQuestion;
import org.example.view.DetailFrame;
import org.example.view.MainFrame;
import org.example.view.QuizDetails;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JOptionPane;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Controller class orchestrates the interaction between the model and the view in the application.
 * It manages the loading and saving of button states, initializes the UI components, and handles user interactions.
 *
 * @author Mounir Darwich
 */
public class Controller {

    private final Path BUTTON_STATE_FILE = Paths.get("button_states.json");
    private final Path BUTTON_STATE_QUIZ_FILE = Paths.get("button_states_quiz.json");

    final MainFrame mainFrame;
    private final DatabaseJson databaseJson;
    private final QuizData quizData;
    final Map<JButton, String> buttonInfoMap;
    final Map<JButton, String> quizButtonInfoMap;

    ImageIcon greenIcon;
    ImageIcon grayIcon;

    /**
     * Constructs a new Controller instance, initializes the UI components, loads the data, and sets up event listeners.
     */
    public Controller() {
        initializeIcons();
        this.databaseJson = new DatabaseJson();
        this.quizData = new QuizData();
        this.mainFrame = new MainFrame();
        this.buttonInfoMap = new HashMap<>();
        this.quizButtonInfoMap = new HashMap<>();

        JsonUtils.ensureFileExists(BUTTON_STATE_FILE.toString());
        JsonUtils.ensureFileExists(BUTTON_STATE_QUIZ_FILE.toString());

        initializeUI();
        loadFlashcardsButtonStates();
        loadQuizButtonStates();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveButtonStates(buttonInfoMap, greenIcon, BUTTON_STATE_FILE.toString());
            saveButtonStates(quizButtonInfoMap, greenIcon, BUTTON_STATE_QUIZ_FILE.toString());
        }));
    }


    public String getBUTTON_STATE_FILE() {
        return BUTTON_STATE_FILE.toString();
    }


    /**
     * Initializes the icons used in the application.
     */
    private void initializeIcons() {
        greenIcon = createScaledIcon("checkGrean.png");
        grayIcon = createScaledIcon("checkGray.png");
    }

    /**
     * Creates a scaled icon from the given path.
     *
     * @param resourceName the resource name of the image file.
     * @return the scaled ImageIcon.
     */
    ImageIcon createScaledIcon(String resourceName) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new IOException("Resource not found: " + resourceName);
            }
            byte[] imageBytes = is.readAllBytes();
            ImageIcon icon = new ImageIcon(imageBytes);
            Image image = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load icon: " + resourceName, e);
        }
    }

    /**
     * Initializes the user interface by creating buttons for flashcards and quizzes and adding listeners to them.
     */
    private void initializeUI() {
        databaseJson.getData().forEach(this::createButtonForFlashcardFrame);
        quizData.getOrderedCategoryKeys().forEach(this::createButtonForQuizFrame);

        addQuizListeners();
        addFlashcardListeners();
    }

    /**
     * Loads the flashcards button states from the JSON file and applies them to the buttons.
     */
    void loadFlashcardsButtonStates() {
        JSONArray buttonStates = JsonUtils.readJsonFile(BUTTON_STATE_FILE.toString());
        applyButtonStates(buttonStates, mainFrame.getFlashcardFrame().getButtons());
    }

    /**
     * Loads the quiz button states from the JSON file and applies them to the buttons.
     */
    void loadQuizButtonStates() {
        JSONArray buttonStates = JsonUtils.readJsonFile(BUTTON_STATE_QUIZ_FILE.toString());
        applyButtonStates(buttonStates, mainFrame.getQuizFrame().getButtons());
    }

    /**
     * Applies the given button states to the provided buttons.
     *
     * @param buttonStates the JSONArray of button states.
     * @param buttons      the list of buttons to update.
     */
    void applyButtonStates(JSONArray buttonStates, List<JButton> buttons) {
        for (int i = 0; i < buttonStates.length(); i++) {
            JSONObject buttonState = buttonStates.getJSONObject(i);
            String buttonText = buttonState.getString("text");
            String iconState = buttonState.getString("icon");
            ImageIcon icon = iconState.equals("green") ? greenIcon : grayIcon;

            buttons.stream()
                    .filter(button -> button.getText().equals(buttonText))
                    .findFirst()
                    .ifPresent(button -> button.setIcon(icon));
        }
    }

    /**
     * Adds listeners to the flashcard buttons.
     */
    private void addFlashcardListeners() {
        for (JButton button : mainFrame.getFlashcardFrame().getButtons()) {
            button.addActionListener(e -> showDetailFrame(button));
        }
    }

    /**
     * Shows the detail frame for the selected flashcard button.
     *
     * @param button the button that was clicked.
     */
    void showDetailFrame(JButton button) {
        String buttonInfo = buttonInfoMap.get(button);
        DetailFrame detailFrame = new DetailFrame("Details for " + button.getText(), buttonInfo, button);
        detailFrame.getDoneButton().addActionListener((ActionEvent s) -> {
            button.setIcon(greenIcon);
            saveButtonStates(buttonInfoMap, greenIcon, BUTTON_STATE_FILE.toString());
            detailFrame.dispose();
        });
    }

    /**
     * Adds listeners to the quiz buttons.
     */
    private void addQuizListeners() {
        for (JButton button : mainFrame.getQuizFrame().getButtons()) {
            button.addActionListener(e -> showQuizDetails(button));
        }
    }

    /**
     * Shows the quiz details for the selected quiz button.
     *
     * @param button the button that was clicked.
     */
    void showQuizDetails(JButton button) {
        String category = button.getText();
        QuizDetails quizDetails = new QuizDetails(button);
        List<QuizQuestion> questions = quizData.getQuestionsForCategory(category);
        questions.forEach(question -> quizDetails.createQuestionPanel(
                question.getQuestionText(),
                question.getChoices(),
                question.getCorrectAnswerIndex()
        ));

        quizDetails.getSubmitButton().addActionListener((ActionEvent s) -> {
            int correctCount = 0;
            int incorrectCount = 0;
            List<ButtonGroup> questionGroups = quizDetails.getQuestionGroups();
            List<JRadioButton> correctAnswers = quizDetails.getCorrectAnswers();

            for (int i = 0; i < questionGroups.size(); i++) {
                ButtonGroup group = questionGroups.get(i);
                JRadioButton correctButton = correctAnswers.get(i);

                for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements(); ) {
                    JRadioButton radioButton = (JRadioButton) buttons.nextElement();
                    if (radioButton.isSelected()) {
                        if (radioButton == correctButton) {
                            correctCount++;
                        } else {
                            incorrectCount++;
                        }
                    }
                }
            }

            JOptionPane.showMessageDialog(quizDetails, "Correct answers: " + correctCount + "\nIncorrect answers: " + incorrectCount);
            if (correctCount == 5) {
                button.setIcon(greenIcon);
                saveButtonStates(quizButtonInfoMap, greenIcon, BUTTON_STATE_QUIZ_FILE.toString());
                quizDetails.dispose();
            }
        });
    }

    /**
     * Creates a button for the flashcard frame with the given title and information.
     *
     * @param title the JSON object containing the title and information for the flashcard.
     */
    void createButtonForFlashcardFrame(JSONObject title) {
        String titleText = title.getString("Title");
        String infoText = title.optString("Info", "No additional information available.");
        JButton button = new JButton(titleText);
        button.setIcon(grayIcon);
        mainFrame.getFlashcardFrame().addButton(button);
        buttonInfoMap.put(button, infoText);
    }

    /**
     * Creates a button for the quiz frame with the given button name.
     *
     * @param buttonName the name of the button to create.
     */
    void createButtonForQuizFrame(String buttonName) {
        JButton button = new JButton(buttonName);
        button.setIcon(grayIcon);
        mainFrame.getQuizFrame().addButton(button);
        quizButtonInfoMap.put(button, buttonName);
    }

    /**
     * Saves the button states to the specified file.
     *
     * @param buttonInfoMap the map of buttons and their information.
     * @param greenIcon     the green icon to represent the completed state.
     * @param filePath      the file path to save the button states to.
     */
    void saveButtonStates(Map<JButton, String> buttonInfoMap, ImageIcon greenIcon, String filePath) {
        JSONArray buttonStates = new JSONArray();
        for (JButton button : buttonInfoMap.keySet()) {
            JSONObject buttonState = new JSONObject();
            buttonState.put("text", button.getText());
            Icon icon = button.getIcon();
            String iconState = (icon != null && icon.equals(greenIcon)) ? "green" : "gray";
            buttonState.put("icon", iconState);
            buttonStates.put(buttonState);
        }
        JsonUtils.writeJsonFile(filePath, buttonStates);
    }
}

package org.example.view;

import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * QuizFrame class represents a frame that displays a list of quizzes.
 * It extends JFrame and provides functionality for searching and displaying quizzes.
 *
 * @author Mounir Darwich
 */
public class QuizFrame extends JFrame implements ActionListener {
    private List<JButton> buttons;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JPanel searchPanel;
    private JTextField searchField;
    private JButton searchButton;
    private Timer searchTimer;

    /**
     * Constructs a QuizFrame.
     *
     * @param mainFrame the main frame that created this frame
     */
    public QuizFrame(MainFrame mainFrame) {
        setupUI();
        setupSearchComponents();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(980, 600);

        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.setVisible(true);
            }
        });
    }

    /**
     * Gets the list of buttons.
     *
     * @return the list of buttons
     */
    public List<JButton> getButtons() {
        return buttons;
    }

    /**
     * Sets up the UI components for the frame.
     */
    private void setupUI() {
        panel = new JPanel(new GridLayout(0, 3, 10, 10));
        panel.setBackground(Color.lightGray);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        this.add(scrollPane, BorderLayout.CENTER);
        buttons = new ArrayList<>();
    }

    /**
     * Sets up the search components for the frame.
     */
    private void setupSearchComponents() {
        searchPanel = new JPanel(new BorderLayout(5, 5));
        searchField = new JTextField();
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        this.add(searchPanel, BorderLayout.NORTH);

        searchTimer = new Timer(300, e -> updateButtonVisibility(searchField.getText()));
        searchTimer.setRepeats(false);
        searchField.addActionListener(e -> searchTimer.restart());
    }

    /**
     * Adds a button to the frame.
     *
     * @param button the button to be added
     */
    public void addButton(JButton button) {
        setButtonProperties(button);
        buttons.add(button);
        panel.add(button);
        refreshUI();
    }

    /**
     * Sets the properties of a button.
     *
     * @param button the button whose properties are to be set
     */
    private void setButtonProperties(JButton button) {
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(5);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Helvetica", Font.BOLD, 16));
    }

    /**
     * Refreshes the UI of the frame.
     */
    private void refreshUI() {
        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchTimer.restart();
        }
    }

    /**
     * Updates the visibility of buttons based on the search text.
     *
     * @param searchText the text to search for
     */
    void updateButtonVisibility(String searchText) {
        panel.removeAll();
        searchText = searchText.toLowerCase().trim();
        boolean found = false;

        for (JButton button : buttons) {
            if (button.getText().toLowerCase().contains(searchText)) {
                panel.add(button);
                found = true;
            }
        }

        if (!found) {
            panel.add(new JLabel("No results found."));
        }
        refreshUI();
    }
}

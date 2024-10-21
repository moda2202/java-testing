package org.example.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

/**
 * DetailFrame class represents a detailed information window.
 * It extends JFrame and displays detailed information passed as a string.
 *
 * @author Mounir darwich
 */
public class DetailFrame extends JFrame {
    JButton linkedButton;
    JButton doneButton ;

    /**
     * Constructs a DetailFrame.
     *
     * @param title        the title of the frame
     * @param info         the detailed information to be displayed
     * @param linkedButton the button linked to this detail frame
     */
    public DetailFrame(String title, String info, JButton linkedButton) {
        super(title);
        this.linkedButton = linkedButton; // Store the reference to the linked button

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Text area setup
        JTextArea textArea = new JTextArea(info);
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("SansSerif", Font.BOLD, 16));
        textArea.setMargin(new Insets(10, 10, 10, 10));

        // Scroll pane for text area
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Done button
        doneButton = new JButton("Done");

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener((ActionEvent e) -> {
            dispose(); // Just close the frame
        });

        buttonPanel.add(doneButton);
        buttonPanel.add(closeButton);

        // Add components to the main frame
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }


    /**
     * Gets the done button.
     *
     * @return the done button
     */
    public JButton getDoneButton() {
        return doneButton;
    }
}
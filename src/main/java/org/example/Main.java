package org.example;

import org.example.controller.Controller;
import javax.swing.SwingUtilities;

/**
 * The Main class serves as the entry point for the application.
 * It initializes the Swing application by invoking the Controller class,
 * which sets up the main user interface and manages the interaction between
 * the model and view components.
 *
 * @author Mounir Darwich
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::new);
    }
}
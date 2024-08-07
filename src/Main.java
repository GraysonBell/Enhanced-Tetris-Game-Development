//Completed don't need to change.

import ui.MainFrame;
import ui.SplashScreen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Show the splash screen
        SplashScreen splash = new SplashScreen(500);
        splash.showSplash();

        //Launch the main application window after splash screen
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        // Create and set up the main application window
        JFrame frame = new MainFrame("Tetris");
    }
}
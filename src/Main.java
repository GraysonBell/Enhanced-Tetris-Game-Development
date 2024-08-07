import ui.MainFrame;
import ui.SplashScreen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen(3000);
            splash.showSplash();

            // Launch the main application window after splash screen
            SwingUtilities.invokeLater(() -> createAndShowGUI());
        });
    }

    private static void createAndShowGUI() {
        // Create and set up the main application window
        JFrame frame = new MainFrame("Tetris");
    }
}


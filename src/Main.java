import ui.MainFrame;
import ui.SplashScreen;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        //Show the splash screen
        SplashScreen splash = new SplashScreen(3000);
        splash.showSplash();

        //Launch the main application window
        javax.swing.swingUtilities.invokeLater(new Runnable() {
            @Override
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
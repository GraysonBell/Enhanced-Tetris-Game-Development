//The main app window that contains all the different panels and the navigation between them.

package ui;

import ui.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(String title) {
        // Title of the Frame
        setTitle(title);

        // Close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the layout manager
        setLayout(new BorderLayout());

        // Create and add the main panel
        MainPanel mainPanel = new MainPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Set the size of the frame
        setSize(750, 850);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Make the frame visible
        setVisible(true);
    }
}

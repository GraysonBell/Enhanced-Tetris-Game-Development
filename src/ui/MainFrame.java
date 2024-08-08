//The main app window that contains all the different panels and the navigation between them.

package ui;

import ui.panel.ConfigurePanel;
import ui.panel.HighScorePanel;
import ui.panel.MainPanel;
import ui.panel.PlayPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    public MainFrame(String title) {


        // Title of the Frame
        setTitle(title);

        // Close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the layout manager
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create and add the main panel
        MainPanel mainPanel = new MainPanel();
        PlayPanel playPanel = new PlayPanel();
        ConfigurePanel configurePanel = new ConfigurePanel();
        HighScorePanel highScorePanel = new HighScorePanel();

        cardPanel.add(mainPanel, "MainPanel");
        cardPanel.add(playPanel, "PlayPanel");
        cardPanel.add(configurePanel, "ConfigurePanel");
        cardPanel.add(highScorePanel, "HighScorePanel");

        getContentPane().add(cardPanel, BorderLayout.CENTER);

        // Set the size of the frame
        setSize(750, 850);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Make the frame visible
        setVisible(true);
    }

    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

}

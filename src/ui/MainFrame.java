package ui;

import model.Game;
import ui.panel.ConfigurePanel;
import ui.panel.HighScorePanel;
import ui.panel.MainPanel;
import ui.panel.PlayPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private PlayPanel playPanel; // Add a reference to PlayPanel

    public MainFrame(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create and add panels
        MainPanel mainPanel = new MainPanel();
        playPanel = new PlayPanel(); // Initialize PlayPanel
        ConfigurePanel configurePanel = new ConfigurePanel();
        HighScorePanel highScorePanel = new HighScorePanel();

        cardPanel.add(mainPanel, "MainPanel");
        cardPanel.add(playPanel, "PlayPanel");
        cardPanel.add(configurePanel, "ConfigurePanel");
        cardPanel.add(highScorePanel, "HighScorePanel");

        getContentPane().add(cardPanel, BorderLayout.CENTER);
        setSize(750, 850);
        setLocationRelativeTo(null);
        setVisible(true);

        Game.SoundHandler.RunMusic("src/sounds/753603__zanderhud__pss560-slow-rock-and-bass-auto-rhythm.wav");

    }

    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);

        // Start the game if the panel being shown is PlayPanel
        if ("PlayPanel".equals(panelName)) {
            playPanel.startGame(); // Start the game when switching to PlayPanel
        }
    }
}

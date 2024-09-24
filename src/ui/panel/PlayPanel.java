package ui.panel;

import ui.MainFrame;
import ui.UIGenerator;
import model.Game;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayPanel extends JPanel {

    private Game game;
    private inGameStatsPanel gamestats;
    private JButton backButton;
    private boolean isGameFinished = false;
    private boolean isPaused = false;

    // Labels for the score and display in game

    private JLabel gameInfo;
    private JLabel playerTypeLabel;
    private JLabel initialLevelLabel;
    private JLabel currentLevelLabel;
    private JLabel nextTetrominoLabel;
    private JLabel scoreLabel;
    private JLabel linesLabel;

    public PlayPanel() {
        setLayout(new BorderLayout());

        game = new Game();  // Initialize the Game instance, but do not start it
        add(game, BorderLayout.CENTER);
        //score = new Score();

        JLabel titleLabel = new JLabel("Play");
        titleLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 25));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);


        //Implements the game stats display
        gamestats = new inGameStatsPanel();
        add(gamestats, BorderLayout.WEST);

        Dimension buttonSize = new Dimension(200, 40);
        Color buttonBorderColor = Color.BLACK;
        backButton = UIGenerator.createButton("Back", buttonSize, buttonBorderColor);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBackButtonClick();
            }
        });

        add(backButton, BorderLayout.SOUTH);

    }



    public void startGame() {
        game.start(); // Start the game when this method is called
    }

    private void handleBackButtonClick() {
        if (isGameFinished) {
            highScoreEnterName();
            navigateToMainMenu();
        } else {
            if (!isPaused) {
                game.pause(); // Pause the game
                isPaused = true;
            }
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit the game?", "Quit Game", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                highScoreEnterName();
                navigateToMainMenu();
            } else {
                game.resume(); // Resume the game
                isPaused = false;
            }
        }
    }

    private void navigateToMainMenu() {
        MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(this);
        frame.showPanel("MainPanel");
    }

    public void setGameFinished(boolean finished) {
        isGameFinished = finished;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    private void highScoreEnterName()
    {
        String playerName = JOptionPane.showInputDialog(this, "Player 1's score is in the top scores, please enter player 1's name:", "High Score", JOptionPane.PLAIN_MESSAGE);

        if (playerName != null && !playerName.trim().isEmpty()) {
            saveScore(playerName);
        }
        else {
            JOptionPane.showMessageDialog(this, "No name entered. Score won't be saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

        navigateToMainMenu();
    }

    // This is just a placeholder. Need to determine how to store score and name in JSON file.
    private void saveScore(String playerName) {

        System.out.println("Score saved for player: " + playerName);
    }
}
package ui.panel;

import ui.MainFrame;
import ui.UIGenerator;
import model.Game;
import model.Score;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayPanel extends JPanel {

    private Game game;
    private Score score;
    private JButton backButton;
    private boolean isGameFinished = false;
    private boolean isPaused = false;

    // Labels for the score and display in game

    private JLabel gameInfo;
    private JLabel playerTypeLabel;
    private JLabel initialLevel;
    private JLabel currentLevel;
    private JLabel nextTetrominoLabel;
    private JLabel scoreLabel;
    private JLabel linesLabel;

    public PlayPanel() {
        setLayout(new BorderLayout());

        game = new Game();  // Initialize the Game instance, but do not start it
        add(game, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Play");
        titleLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 25));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

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

        // Box to display the current game stats.
        JPanel gameStatusDisplay = new JPanel();
        gameStatusDisplay.setLayout(new GridLayout(0, 1));
        gameStatusDisplay.setBorder(new LineBorder(Color.BLACK, 1));

        gameInfo = new JLabel("Game Info (Player 1)", JLabel.CENTER);
        gameInfo.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        playerTypeLabel = new JLabel("Player type: Human", JLabel.CENTER);
        playerTypeLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        initialLevel = new JLabel("Initial level: 1", JLabel.CENTER);
        initialLevel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        currentLevel = new JLabel("Current level: 1", JLabel.CENTER);
        currentLevel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        scoreLabel = new JLabel("Score: ", JLabel.CENTER);
        scoreLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 18));

        linesLabel = new JLabel("Lines Cleared: ", JLabel.CENTER);
        linesLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        nextTetrominoLabel = new JLabel("Next Tetromino:", JLabel.CENTER);
        nextTetrominoLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        gameStatusDisplay.add(gameInfo);
        gameStatusDisplay.add(playerTypeLabel);
        gameStatusDisplay.add(initialLevel);
        gameStatusDisplay.add(currentLevel);
        gameStatusDisplay.add(scoreLabel);
        gameStatusDisplay.add(linesLabel);
        gameStatusDisplay.add(nextTetrominoLabel);

        add(gameStatusDisplay, BorderLayout.WEST);

    }



    public void startGame() {
        game.start(); // Start the game when this method is called
    }

    private void handleBackButtonClick() {
        if (isGameFinished) {
            navigateToMainMenu();
        } else {
            if (!isPaused) {
                game.pause(); // Pause the game
                isPaused = true;
            }
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit the game?", "Quit Game", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
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
}

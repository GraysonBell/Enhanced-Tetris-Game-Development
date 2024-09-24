package ui.panel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import model.Score;

public class inGameStatsPanel extends JPanel {

    private JLabel gameInfo;
    private JLabel playerTypeLabel;
    private JLabel initialLevelLabel;
    private JLabel currentLevelLabel;
    private JLabel nextTetrominoLabel;
    private JLabel scoreLabel;
    private JLabel linesLabel;

    public inGameStatsPanel() {
        setLayout(new BorderLayout());

        JPanel gameStatusDisplay = new JPanel();
        gameStatusDisplay.setLayout(new GridLayout(0, 1));
        gameStatusDisplay.setBorder(new LineBorder(Color.BLACK, 1));

        gameInfo = new JLabel("Game Info (Player 1)", JLabel.CENTER);
        gameInfo.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        playerTypeLabel = new JLabel("Player type: Human", JLabel.CENTER);
        playerTypeLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        initialLevelLabel = new JLabel("Initial level: ", JLabel.CENTER);
        initialLevelLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        currentLevelLabel = new JLabel("Current level: ", JLabel.CENTER);
        currentLevelLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        scoreLabel = new JLabel("Score: ", JLabel.CENTER);
        scoreLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 18));

        linesLabel = new JLabel("Lines Cleared: ", JLabel.CENTER);
        linesLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        nextTetrominoLabel = new JLabel("Next Tetromino:", JLabel.CENTER);
        nextTetrominoLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        gameStatusDisplay.add(gameInfo);
        gameStatusDisplay.add(playerTypeLabel);
        gameStatusDisplay.add(initialLevelLabel);
        gameStatusDisplay.add(currentLevelLabel);
        gameStatusDisplay.add(scoreLabel);
        gameStatusDisplay.add(linesLabel);
        gameStatusDisplay.add(nextTetrominoLabel);

        // Box showing next Tetromino

        JPanel nextTetrominoBox = new JPanel();
        nextTetrominoBox.setBorder(new ShadowBorder(Color.GRAY, Color.WHITE, 2));
        nextTetrominoBox.setPreferredSize(new Dimension(60, 40));

        gameStatusDisplay.add(nextTetrominoBox);

        add(gameStatusDisplay);

    }

}
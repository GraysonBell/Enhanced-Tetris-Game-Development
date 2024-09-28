package ui.panel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import model.Score;
import model.MetaConfig;
import model.TetrisShape;

public class inGameStatsPanel extends JPanel {

    private MetaConfig config;
    private Score score;

    private JLabel gameInfo;
    private JLabel playerTypeLabel;
    private JLabel initialLevelLabel;
    private JLabel currentLevelLabel;
    private JLabel nextTetrominoLabel;
    private JLabel scoreLabel;
    private JLabel linesLabel;

    public inGameStatsPanel() {
        setLayout(new BorderLayout());
        score = new Score("----", 0, MetaConfig.getInstance());
        config = MetaConfig.getInstance();

        JPanel gameStatusDisplay = new JPanel();
        gameStatusDisplay.setLayout(new GridLayout(0, 1));
        gameStatusDisplay.setBorder(new LineBorder(Color.BLACK, 1));

        gameInfo = new JLabel("Game Info (Player 1)", JLabel.CENTER);
        gameInfo.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        playerTypeLabel = new JLabel("Player type: " + getPlayerType(), JLabel.CENTER);
        playerTypeLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));


        initialLevelLabel = new JLabel("Initial level: " + config.getInitLevel(), JLabel.CENTER);
        initialLevelLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        currentLevelLabel = new JLabel("Current level: " + Score.getCurrentLevel(), JLabel.CENTER);
        currentLevelLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));
        Score.addObserver(currentLevelLabel);

        scoreLabel = new JLabel("Score: " + Score.getScore(), JLabel.CENTER);
        scoreLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 18));
        Score.addObserver(scoreLabel);

        linesLabel = new JLabel("Lines Cleared: " + Score.getLinesCleared(), JLabel.CENTER);
        linesLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));
        Score.addObserver(linesLabel);

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
    // this is to help get player type as a string.
    private String getPlayerType() {
        return switch (config.getPlayerOneType()) {
            case 1 -> "AI";
            case 2 -> "External";
            default -> "Human";
        };
    }

}
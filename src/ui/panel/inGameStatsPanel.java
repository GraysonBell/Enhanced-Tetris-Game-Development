package ui.panel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import model.Score;
import model.MetaConfig;

public class inGameStatsPanel extends JPanel implements MetaConfig.MetaConfigObserver {

    private MetaConfig config;

    // Player 1 Labels
    private JLabel currentLevelLabel1;
    private JLabel scoreLabel1;
    private JLabel linesLabel1;

    // Player 2 Labels
    private JLabel currentLevelLabel2;
    private JLabel scoreLabel2;
    private JLabel linesLabel2;

    public inGameStatsPanel() {
        setLayout(new GridLayout(1, 2));
        config = MetaConfig.getInstance();


        JPanel player1StatsPanel = createPlayerStatsPanel(1);
        add(player1StatsPanel);

        JPanel player2StatsPanel = createPlayerStatsPanel(2);
        add(player2StatsPanel);


        MetaConfig.getInstance().addObserver(this);
    }

    private JPanel createPlayerStatsPanel(int playerNumber) {
        JPanel gameStatusDisplay = new JPanel();
        gameStatusDisplay.setLayout(new GridLayout(0, 1));
        gameStatusDisplay.setBorder(new LineBorder(Color.BLACK, 1));

        JLabel gameInfo = new JLabel("Game Info (Player " + playerNumber + ")", JLabel.CENTER);
        gameInfo.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        JLabel playerTypeLabel = new JLabel("Player type: " + (playerNumber == 1 ? config.getPlayerType() : "AI"), JLabel.CENTER);
        playerTypeLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        JLabel initialLevelLabel = new JLabel("Initial level: " + config.getInitLevel(), JLabel.CENTER);
        initialLevelLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));


        if (playerNumber == 1) {
            currentLevelLabel1 = new JLabel("Current level: " + Score.getCurrentLevel(), JLabel.CENTER);
            currentLevelLabel1.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));
            Score.addObserver(currentLevelLabel1);
            gameStatusDisplay.add(currentLevelLabel1);
        } else {
            currentLevelLabel2 = new JLabel("Current level: " + Score.getCurrentLevel(), JLabel.CENTER);
            currentLevelLabel2.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));
            Score.addObserver(currentLevelLabel2);
            gameStatusDisplay.add(currentLevelLabel2);
        }

        // Initialize score labels for Player 1 and Player 2
        if (playerNumber == 1) {
            scoreLabel1 = new JLabel("Score: " + Score.getScore(), JLabel.CENTER);
            scoreLabel1.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 18));
            Score.addObserver(scoreLabel1);
            gameStatusDisplay.add(scoreLabel1);
        } else {
            scoreLabel2 = new JLabel("Score: " + Score.getScore(), JLabel.CENTER);
            scoreLabel2.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 18));
            Score.addObserver(scoreLabel2);
            gameStatusDisplay.add(scoreLabel2);
        }

        // Initialize lines labels for Player 1 and Player 2
        if (playerNumber == 1) {
            linesLabel1 = new JLabel("Lines Cleared: " + Score.getLinesCleared(), JLabel.CENTER);
            linesLabel1.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));
            Score.addObserver(linesLabel1);
            gameStatusDisplay.add(linesLabel1);
        } else {
            linesLabel2 = new JLabel("Lines Cleared: " + Score.getLinesCleared(), JLabel.CENTER);
            linesLabel2.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));
            Score.addObserver(linesLabel2);
            gameStatusDisplay.add(linesLabel2);
        }

        JLabel nextTetrominoLabel = new JLabel("Next Tetromino:", JLabel.CENTER);
        nextTetrominoLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        gameStatusDisplay.add(gameInfo);
        gameStatusDisplay.add(playerTypeLabel);
        gameStatusDisplay.add(initialLevelLabel);
        gameStatusDisplay.add(nextTetrominoLabel);


        JPanel nextTetrominoBox = new JPanel();
        nextTetrominoBox.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        nextTetrominoBox.setPreferredSize(new Dimension(60, 40));

        gameStatusDisplay.add(nextTetrominoBox);

        return gameStatusDisplay;
    }

    public void resetStats() {
        Score.clearObservers();
        Score.reset();

        // Update Player 1 Stats
        currentLevelLabel1.setText("Current level: " + Score.getCurrentLevel());
        scoreLabel1.setText("Score: " + Score.getScore());
        linesLabel1.setText("Lines Cleared: " + Score.getLinesCleared());

        // Update Player 2 Stats
        currentLevelLabel2.setText("Current level: " + Score.getCurrentLevel());
        scoreLabel2.setText("Score: " + Score.getScore());
        linesLabel2.setText("Lines Cleared: " + Score.getLinesCleared());
    }

    @Override
    public void onExtendModeChanged(boolean extendMode) {
        if (extendMode) {
            currentLevelLabel2.setVisible(true);
            scoreLabel2.setVisible(true);
            linesLabel2.setVisible(true);
        } else {
            currentLevelLabel2.setVisible(false);
            scoreLabel2.setVisible(false);
            linesLabel2.setVisible(false);
        }
    }

    public JPanel getPlayerStatsPanel(int playerNumber) {
        return createPlayerStatsPanel(playerNumber);
    }
}
package ui.panel;

import model.*;
import ui.MainFrame;
import ui.UIGenerator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;

public class PlayPanel extends JPanel implements MetaConfig.MetaConfigObserver {

    private Game[] games;
    private Score score_data;
    private inGameStatsPanel gamestats;
    private JButton backButton;
    private boolean isGameFinished = false;
    private boolean isPaused = false;
    public static JLabel musicLabel;
    public JLabel soundLabel;
    private int playerNumber;

    public PlayPanel(int playerNumber) {
        this.playerNumber = playerNumber;
        setLayout(new BorderLayout());

        // Create North Panel for Title, Music, and Sound
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Play");
        titleLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 25));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        northPanel.add(titleLabel);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout());

        musicLabel = new JLabel("MUSIC: " + (MetaConfig.getInstance().isMusicOn() ? "ON" : "OFF"));
        musicLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 20));
        labelPanel.add(musicLabel);

        soundLabel = new JLabel("SOUND: " + (MetaConfig.getInstance().isSoundOn() ? "ON" : "OFF"));
        soundLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 20));
        labelPanel.add(soundLabel);

        northPanel.add(labelPanel);
        add(northPanel, BorderLayout.NORTH);

        // In-game stats and Tetris pieces area
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, playerNumber * 2)); // Two columns for each player

        games = new Game[playerNumber]; // Initialize game instances
        gamestats = new inGameStatsPanel(); // Instantiate the stats panel

        for (int i = 0; i < playerNumber; i++) {
            // Add player stats first
            centerPanel.add(gamestats.getPlayerStatsPanel(i + 1)); // Add the stats panel for this player

            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new BorderLayout());

            playerPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.BLACK, 2),
                    "Player " + (i + 1),
                    TitledBorder.CENTER,
                    TitledBorder.TOP
            ));

            games[i] = new Game(i); // Create each game instance with player index
            playerPanel.add(games[i], BorderLayout.CENTER); // Add game instance to the player panel
            centerPanel.add(playerPanel); // Add to center panel
        }


        updatePlayerVisibility(centerPanel);
        add(centerPanel, BorderLayout.CENTER);

        // back button
        backButton = UIGenerator.createButton("Back", new Dimension(200, 40), Color.BLACK);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBackButtonClick();
            }
        });

        add(backButton, BorderLayout.SOUTH);


        MetaConfig.getInstance().addObserver(this);
    }

    public void startGame() {
        gamestats.resetStats();
        for (Game game : games) {
            game.start(); // Start each game instance
        }
    }

    private void handleBackButtonClick() {
        if (isGameFinished) {
            highScoreEnterName();
            score_data.reset();
            navigateToMainMenu();
        } else {
            if (!isPaused) {
                for (Game game : games) {
                    game.pause(); // Pause each game
                }
                isPaused = true;
            }
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit the game?", "Quit Game", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                highScoreEnterName();
                score_data.reset();
                navigateToMainMenu();
            } else {
                for (Game game : games) {
                    game.resume(); // Resume each game
                }
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

    private void highScoreEnterName() {
        int currentScore = Score.getScore();
        String playerName = JOptionPane.showInputDialog(this, "Player 1's score is in the top scores, please enter player 1's name:", "High Score", JOptionPane.PLAIN_MESSAGE);
        if (playerName != null && !playerName.trim().isEmpty()) {
            if (ScoreList.getInstance().getScores().size() < ScoreList.MAX_SCORE_NUM ||
                    currentScore > ScoreList.getInstance().getHighScoreOrder(ScoreList.MAX_SCORE_NUM - 1).score()) {
                saveScore(playerName, currentScore);
            } else {
                JOptionPane.showMessageDialog(this, "Your score did not make it to the top scores.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No name entered. Score won't be saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
        navigateToMainMenu();
    }

    private void saveScore(String playerName, int currentScore) {
        MetaConfig metaConfig = MetaConfig.getInstance();
        int fieldWidth = metaConfig.getFieldWidth();
        int fieldHeight = metaConfig.getFieldHeight();
        int initLevel = metaConfig.getInitLevel();
        boolean extendMode = metaConfig.isExtendMode();
        ScoreRecords newScore = new ScoreRecords(playerName, currentScore, fieldWidth, fieldHeight, initLevel, extendMode);
        ScoreList.getInstance().addScore(newScore);
        System.out.println("Score saved for player: " + playerName + " with score: " + currentScore);
    }


    public void updatePlayerVisibility(JPanel centerPanel) {
        if (!MetaConfig.getInstance().isExtendMode() && playerNumber > 1) {
            centerPanel.getComponent(2).setVisible(false); // Hide the 2nd player stats
            centerPanel.getComponent(3).setVisible(false); // Hide the 2nd player game
        } else {
            centerPanel.getComponent(2).setVisible(true); // Show the 2nd player stats
            centerPanel.getComponent(3).setVisible(true); // Show the 2nd player game
        }
    }

    @Override
    public void onExtendModeChanged(boolean extendMode) {
        updatePlayerVisibility((JPanel) getComponent(1)); // Assuming the centerPanel is at index 1
    }
}
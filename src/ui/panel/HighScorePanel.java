// Pretty Straight forward this one.
// Reminder that this just contains mock information not actual.

package ui.panel;

import com.google.gson.*;
import model.*;
import ui.MainFrame;
import ui.UIGenerator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class HighScorePanel extends JPanel {

    public HighScorePanel() {

        // Have to set the layout so I know what type of Layout it is for me to put things places.
        setLayout(new BorderLayout());

        MetaConfig config = MetaConfig.getInstance();
        ScoreList scoreList = ScoreList.getInstance();
        java.util.List<ScoreRecords> scores = scoreList.getScores();


        // Title

        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 50));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Clear scores button

        JPanel buttonPanel = new JPanel();
        JButton clearButton = new JButton("Clear High Score");
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.EAST);

        //Main panel with GridLayout

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 4, 1, 1));
        mainPanel.setPreferredSize(new Dimension(800, 400));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 0)); // Top, Left, Bottom, Right padding
        add(mainPanel);



        // Column headings

        JLabel rankingLabel = new JLabel("#", JLabel.CENTER);
        rankingLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        JLabel nameLabel = new JLabel("Name", JLabel.CENTER);
        nameLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        JLabel scoreLabel = new JLabel("Score", JLabel.CENTER);
        scoreLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        JLabel configLabel = new JLabel("Config", JLabel.CENTER);
        configLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        mainPanel.add(rankingLabel);
        mainPanel.add(nameLabel);
        mainPanel.add(scoreLabel);
        mainPanel.add(configLabel);

        // Add the records

        for (int i = 0; i < scores.size(); i++) {
            ScoreRecords score = scores.get(i);
            mainPanel.add(new JLabel(String.valueOf(i + 1), JLabel.CENTER));
            mainPanel.add(new JLabel(score.name(), JLabel.CENTER));
            mainPanel.add(new JLabel(String.valueOf(score.score()), JLabel.CENTER));

            // Config column
            String configuration = String.format("%dx%d(%d) %b", score.fieldWidth(), score.fieldHeight(), score.initLevel(), score.extendMode());
            mainPanel.add(new JLabel(configuration, JLabel.CENTER));
        }

        mainPanel.setVisible(true);

        // Action listener for clear scores
        clearButton.addActionListener(e -> {
            scoreList.getScores().clear(); //Clear scores from list.
            ScoreList.saveScores(); // Save changes to JSON file
            refreshScorePanel(mainPanel, scoreList);
        });

        // Created the Back Button
        Dimension buttonSize = new Dimension(200, 40);
        Color buttonBorderColor = Color.BLACK;
        JButton configureButton = UIGenerator.createButton("Back", buttonSize, buttonBorderColor);
        configureButton.addActionListener(e -> {
            MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            frame.showPanel("MainPanel");
        });

        // Add the button to the south section
        add(configureButton, BorderLayout.SOUTH);

    }

    private static void refreshScorePanel(JPanel mainPanel, ScoreList scoreList) {
        mainPanel.removeAll(); // Clear existing components

        // Add column headers again
        JLabel rankingLabel = new JLabel("#", JLabel.CENTER);
        rankingLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        JLabel nameLabel = new JLabel("Name", JLabel.CENTER);
        nameLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        JLabel scoreLabel = new JLabel("Score", JLabel.CENTER);
        scoreLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        JLabel configLabel = new JLabel("Config", JLabel.CENTER);
        configLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 14));

        mainPanel.add(rankingLabel);
        mainPanel.add(nameLabel);
        mainPanel.add(scoreLabel);
        mainPanel.add(configLabel);

        // Populate with update score data.

        java.util.List<ScoreRecords> scores = scoreList.getScores();
        for (int i = 0; i < scores.size(); i++) {
            ScoreRecords score = scores.get(i);
            mainPanel.add(new JLabel(String.valueOf(i + 1), JLabel.CENTER));
            mainPanel.add(new JLabel(score.name(), JLabel.CENTER));
            mainPanel.add(new JLabel(String.valueOf(score.score()), JLabel.CENTER));

            // Config column
            String configuration = String.format("%dx%d(%d) %b", score.fieldWidth(), score.fieldHeight(), score.initLevel(), score.extendMode());
            mainPanel.add(new JLabel(configuration, JLabel.CENTER));
        }

        mainPanel.revalidate();
        mainPanel.repaint();


    }

}

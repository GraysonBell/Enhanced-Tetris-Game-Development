// A record that probably represents a players score. a Record class makes things immutable.

package model;


import javax.swing.*;
import java.awt.*;

public class Score extends JPanel {

    private int linesCleared;
    private int score;

    private JLabel scoreLabel;
    private JLabel linesLabel;

    public Score() {
        setLayout(new GridLayout(2, 1));

        linesCleared = 0;
        score = 0;

        // Initialize the labels
        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        scoreLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 18));

        linesLabel = new JLabel("Lines Cleared: 0", JLabel.CENTER);
        linesLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 18));

        // Add labels to the panel
        add(scoreLabel);
        add(linesLabel);

        // Set panel border
        setOpaque(false);
    }

    // Method to update the score based on the number of lines cleared at once
    public void updateScore(int lines) {
        switch (lines) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 500;
                break;
            case 4:
                score += 800;
                break;
            default:
                break;
        }

        linesCleared += lines;

        // Update the labels with the new score and lines cleared
        scoreLabel.setText("Score: " + score);
        linesLabel.setText("Lines Cleared: " + linesCleared);
    }

    // Method to reset the score and lines cleared
    public void reset() {
        linesCleared = 0;
        score = 0;

        // Reset the labels
        scoreLabel.setText("Score: 0");
        linesLabel.setText("Lines Cleared: 0");
    }

    // Getters for score and lines cleared
    public int getScore() {
        return score;
    }

    public int getLinesCleared() {
        return linesCleared;
    }
}

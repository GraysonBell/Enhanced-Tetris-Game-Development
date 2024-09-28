// This class calculates the score and lines cleared data.

package model;

import javax.swing.*;
import java.util.ArrayList;

public class Score extends JPanel {

    private static int linesCleared;
    private static int score;
    private static int currentLevel;

    private static final ArrayList<JComponent> observers = new ArrayList<>();

    public static void addObserver(JComponent comp) {observers.add(comp); }

    public static void clearObservers() {observers.clear(); }

    public static void informObservers() {
        for (JComponent observer : observers) {
            if (observer instanceof JLabel) {
                JLabel label = (JLabel) observer;
                if (label.getText().startsWith("Score:")) {
                    label.setText("Score: " + getScore());
                } else if (label.getText().startsWith("Lines Cleared:")) {
                    label.setText("Lines Cleared: " + getLinesCleared());
                } else if (label.getText().startsWith("Current level:")) {
                    label.setText("Current level: " + getCurrentLevel());
                }
            }
            observer.repaint();
        }
    }

    public Score(String s, int i, MetaConfig instance) {
        linesCleared = 0;
        score = 0;
        currentLevel = MetaConfig.getInstance().getInitLevel();
    }

    // Method to update the score based on the number of lines cleared at once
    public void updateScore(int lines) {
        switch (lines) {
            case 1: score += 100; break;
            case 2: score += 300; break;
            case 3: score += 600; break;
            case 4: score += 1000; break;
            default:
                break;
        }
        linesCleared += lines;

        if (linesCleared > 0 && linesCleared % 10 == 0) {
            currentLevel++;
        }

        Score.informObservers();
    }

    // Method to reset the score and lines cleared
    public void reset() {
        linesCleared = 0;
        score = 0;
        currentLevel = MetaConfig.getInstance().getInitLevel();
        Score.informObservers();
    }

    // Getters for score and lines cleared
    public static int getScore() {
        return score;
    }

    public static int getLinesCleared() {
        return linesCleared;
    }

    public static int getCurrentLevel() {return currentLevel;}

}
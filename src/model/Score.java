// This class calculates the score and lines cleared data.

package model;

import javax.swing.*;
import java.util.ArrayList;

public class Score extends JPanel {

    static int linesCleared;
    static int score;
    static int currentLevel;

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

    private ScoreSystem scoreSystem;

    public Score(String s, int i, MetaConfig instance) {
        linesCleared = 0;
        score = 0;
        currentLevel = MetaConfig.getInstance().getInitLevel();

        scoreSystem = ScoreFactory.createScoreSystem("default");
    }

    public void updateScore(int lines) {
        scoreSystem.updateScore(this, lines);
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
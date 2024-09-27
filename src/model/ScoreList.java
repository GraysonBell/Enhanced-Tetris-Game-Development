// This creates a JSON file that can hold the configuration data and saves it when it needs to be reopened again.

package model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreList {
    private static ScoreList instance;
    private ArrayList<ScoreRecords> scores = new ArrayList<>();
    private static final String SCORE_FILE = "TetrisScores.json";
    public static final int MAX_SCORE_NUM = 10;

    // Private constructor for Singleton
    private ScoreList() {
    }

    // Singleton instance retrieval
    public static ScoreList getInstance() {
        if (instance == null) {
            instance = loadScores();
            if (instance == null) {
                // If loading scores failed, create a new ScoreList and save it immediately
                instance = new ScoreList();
                saveScores();
            }
        }
        return instance;
    }

    // Load the scores from the JSON file
    private static ScoreList loadScores() {
        try (FileReader reader = new FileReader(SCORE_FILE)) {
            Gson gson = new Gson();
            ScoreList loadedScores = gson.fromJson(reader, ScoreList.class);
            return loadedScores != null ? loadedScores : new ScoreList();
        } catch (IOException e) {
            System.out.println("Scores file not found. Creating a new one...");
            return null;
        } catch (JsonSyntaxException e) {
            System.out.println("Error processing JSON string: " + e.getMessage());
            return null;
        }
    }

    // Save the scores to the JSON file
    public static void saveScores() {
        if (instance == null) {
            instance = new ScoreList();
        }
        Gson gson = new Gson();
        String jsonString = gson.toJson(instance);
        try (FileWriter writer = new FileWriter(SCORE_FILE)) {
            writer.write(jsonString);
        } catch (IOException e) {
            System.out.println("Error saving scores file: " + e.getMessage());
        }
    }

    // Getter for the score list
    public ArrayList<ScoreRecords> getScores() {
        return scores;
    }

    // Method to add a new score to the list
    public void addScore(ScoreRecords score) {
        scores.add(score);
        Collections.sort(scores, Comparator.comparingInt(ScoreRecords::score).reversed());

        // Keep only the top scores
        if (scores.size() > MAX_SCORE_NUM) {
            scores.remove(scores.size() - 1); // Remove the lowest score
        }
        saveScores();
    }

    // I need to get the score in order.
    public ScoreRecords getHighScoreOrder(int rank) {
        if (rank >= 0 && rank < scores.size()) {
            return scores.get(rank);
        } else {
            return new ScoreRecords("----", 0, 0, 0, 1, false);
        }
    }

}

package model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MetaConfig {
    private static MetaConfig instance;
    private static final String CONFIG_FILE = "JavaTetris25Config.json";

    private int fieldWidth = 10;
    private int fieldHeight = 20;
    private int initLevel = 1;
    private boolean isMusicOn = true;
    private boolean isSoundOn = true;
    private boolean isExtendMode = false;
    private int playerOneType = 0; // Corresponds to Human, AI, External
    private int playerTwoType = 0; // Corresponds to Human, AI, External

    // List of observers (UI components that need to be notified)
    private static List<MetaConfigObserver> observers = new ArrayList<>();

    private MetaConfig() {}

    public static MetaConfig getInstance() {
        if (instance == null) {
            instance = loadConfigFile();
            if (instance == null) {
                instance = new MetaConfig(); // Create instance with default values
                saveConfigFile(); // Save defaults to create the config file
            }
        }
        return instance;
    }

    // Observer methods
    public static void addObserver(MetaConfigObserver observer) {
        observers.add(observer);
    }

    public static void removeObserver(MetaConfigObserver observer) {
        observers.remove(observer);
    }

    private static void notifyObservers() {
        for (MetaConfigObserver observer : observers) {
            observer.onExtendModeChanged(instance.isExtendMode());
        }
    }

    // Load and save Config files
    private static MetaConfig loadConfigFile() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, MetaConfig.class);
        } catch (IOException e) {
            System.out.println("Error reading config file: " + e.getMessage());
            return null; // Return null if the file doesn't exist or can't be read
        } catch (JsonSyntaxException e) {
            System.out.println("Error processing JSON string: " + e.getMessage());
            return null; // Return null if JSON is malformed
        }
    }

    public static void saveConfigFile() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(instance);
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            writer.write(jsonString);
        } catch (IOException e) {
            System.out.println("Error saving config file: " + e.getMessage());
        }
        notifyObservers(); // Notify UI to refresh after saving
    }

    // Getters and setters for each field
    public int getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
        saveConfigFile();
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
        saveConfigFile();
    }

    public int getInitLevel() {
        return initLevel;
    }

    public void setInitLevel(int initLevel) {
        this.initLevel = initLevel;
        saveConfigFile();
    }

    public boolean isMusicOn() {
        return isMusicOn;
    }

    public void setMusicOn(boolean musicOn) {
        this.isMusicOn = musicOn;
        notifyObservers();
        saveConfigFile();
    }

    public boolean isSoundOn() {
        return isSoundOn;
    }

    public void setSoundOn(boolean soundOn) {
        this.isSoundOn = soundOn;
        notifyObservers();
        saveConfigFile();
    }

    public boolean isExtendMode() {
        return isExtendMode;
    }

    public void setExtendMode(boolean extendMode) {
        this.isExtendMode = extendMode;
        notifyObservers(); // Notify observers when extend mode changes
        saveConfigFile(); // Save the change
    }

    public String getPlayerType() {
        return switch (playerOneType) {
            case 1 -> "AI";
            case 2 -> "External";
            default -> "Human";
        };
    }

    public int getPlayerOneType() {
        return playerOneType;
    }

    public void setPlayerOneType(int playerOneType) {
        this.playerOneType = playerOneType;
        notifyObservers();
        saveConfigFile();
    }

    public int getPlayerTwoType() {
        return playerTwoType;
    }

    public void setPlayerTwoType(int playerTwoType) {
        this.playerTwoType = playerTwoType;
        notifyObservers();
        saveConfigFile();
    }

    // Observer interface for extend mode changes
    public interface MetaConfigObserver {
        void onExtendModeChanged(boolean extendMode);
    }
}
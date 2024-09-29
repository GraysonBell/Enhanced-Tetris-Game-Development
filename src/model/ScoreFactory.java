package model;

public class ScoreFactory {
    public static ScoreSystem createScoreSystem(String type) {
        switch (type) {
            case "default":
                return new scoreBreakdown();
            default:
                throw new IllegalArgumentException("Unknown score system type");
        }
    }
}

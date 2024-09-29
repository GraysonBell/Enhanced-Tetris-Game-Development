package model;

public class scoreBreakdown implements ScoreSystem {
    @Override
    public void updateScore(Score score, int lines) {
        switch (lines) {
            case 1: Score.score += 100; break;
            case 2: Score.score += 300; break;
            case 3: Score.score += 600; break;
            case 4: Score.score += 1000; break;
            default: break;
        }

        Score.linesCleared += lines;

        if(Score.linesCleared > 0 && Score.linesCleared % 10 == 0) {
            Score.currentLevel++;
        }
    }
}

package model;

public class scoreBreakdown implements ScoreSystem {
    @Override
    public void updateScore(Score score, int lines) {
        switch (lines) {
            case 1: score.score += 100; break;
            case 2: score.score += 300; break;
            case 3: score.score += 600; break;
            case 4: score.score += 1000; break;
            default: break;
        }

        score.linesCleared += lines;

        if(score.linesCleared > 0 && score.linesCleared % 10 == 0) {
            score.currentLevel++;
        }
    }
}

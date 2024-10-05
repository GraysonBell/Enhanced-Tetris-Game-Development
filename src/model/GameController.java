package model;

import model.Game;
import model.GameConfig;
import model.MetaConfig;
import util.CommFun;

public class GameController {
    private final Game game;

    public GameController(Game cfg, int player) {
        this.game = new Game(cfg, player);
    }

    public Game getGame() {
        return this.game;
    }

    public void moveLeft() {
        if (this.game.canMoveLeft()) {
            this.game.moveLeft();
            playSound("");
        }
    }

    public void moveRight() {
        if (this.game.canMoveRight()) {
            this.game.moveRight();
            playSound("");
        }
    }

    public void moveDown() {
        if (this.game.canMoveDown())
            this.game.moveDown();
    }

    public void rotate() {
        if (this.game.canRotate()) {
            this.game.rotate();
            playSound("");
        }
    }

    public void pauseGame() {
        this.game.pauseGame();
    }

    public void endGame() {
        this.game.endGame();
    }

    private void playSound(String name) {
        if (MetaConfig.getMetaConfig().isSoundOn())
            CommFun.playSound(name);
    }
}

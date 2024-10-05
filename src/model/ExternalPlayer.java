package model;

import Controller.GameController;
import model.Game;
import model.OpMove;
import model.PureGame;
import util.CommFun;

public class ExternalPlayer implements Runnable {
    private final GameController gameController;

    private final Game game;

    private int shapeIdx;

    private int optimalColumn;

    private int optimalRotate;

    public ExternalPlayer(GameController controller) {
        this.gameController = controller;
        this.game = this.gameController.getGame();
        this.optimalColumn = -1;
        this.optimalRotate = -1;
    }

    private synchronized void findOptimization() {
        this.shapeIdx = this.game.getCount();
        PureGame pg = new PureGame(this.game.getFieldHeight(), this.game.getFieldWidth());
        pg.setCells(this.game.cloneCells());
        pg.setCurrentShape(this.game.cloneCurrentShape().getShape());
        pg.setNextShape(this.game.getNextShape().getShape());
        OpMove opMove = CommFun.sendObject(pg);
        if (opMove != null) {
            this.optimalColumn = opMove.opX();
            this.optimalRotate = opMove.opRotate();
        }
    }

    private synchronized void moveToOptimization() {
        if (this.optimalColumn < 0)
            return;
        int currentRotate = this.game.getShapeRotate();
        if (currentRotate != this.optimalRotate) {
            this.gameController.rotate();
            return;
        }
        int currentX = this.game.getShapeX();
        if (currentX > this.optimalColumn) {
            this.gameController.moveLeft();
            return;
        }
        if (currentX < this.optimalColumn) {
            this.gameController.moveRight();
            return;
        }
        this.gameController.moveDown();
    }

    private synchronized void ExternalProcess() {
        if (!this.game.isRunning())
            return;
        if (this.shapeIdx < this.game.getCount())
            findOptimization();
        moveToOptimization();
    }

    public void run() {
        while (this.game.isPlaying()) {
            ExternalProcess();
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

package model;

public class PureGame {
    private final int width;
    private final int height;
    private final int[][] cells;
    private int[][] currentShape;
    private int[][] nextShape;

    public PureGame(int height, int width) {
        this.height = height;
        this.width = width;
        this.cells = new int[height][width];
    }

    public void setCells(int[][] cells) {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++)
                this.cells[y][x] = (cells[y][x] == -1) ? 0 : 1;
        }
    }

    public void setCurrentShape(int[][] currentShape) {
        this.currentShape = currentShape;
    }

    public void setNextShape(int[][] nextShape) {
        this.nextShape = nextShape;
    }
}

//An enum that most likely defines the different shapes or tetris's?? tetrominoes?? like the L shape line shape etc

package model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class TetrisShape {

    public enum Shape {
        NoShape, ZShape, SShape, LineShape, TShape, SquareShape, LShape, MirroredLShape
    }

    private Shape pieceShape;
    private int[][] coords;

    public TetrisShape() {
        coords = new int[4][2];
        setShape(Shape.NoShape);
    }

    public void setShape(Shape shape) {
        // Define coordinates for each shape
        int[][][] coordsTable = new int[][][]{
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}}, // NoShape
                {{0, -1}, {0, 0}, {1, 0}, {1, 1}}, // SShape
                {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}}, // ZShape
                {{0, -1}, {0, 0}, {0, 1}, {0, 2}}, // LineShape
                {{-1, 0}, {0, 0}, {1, 0}, {0, 1}}, // TShape
                {{1, -1}, {0, -1}, {0, 0}, {0, 1}}, // MirroredLShape
                {{-1, -1}, {0, -1}, {0, 0}, {0, 1}}, // LShape
                {{0, 0}, {1, 0}, {0, 1}, {1, 1}}, // SquareShape
        };

        for (int i = 0; i < 4; i++) {
            System.arraycopy(coordsTable[shape.ordinal()][i], 0, coords[i], 0, 2);
        }

        pieceShape = shape;
    }

    public void setRandomShape() {
        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;
        Shape[] values = Shape.values();
        setShape(values[x]);
    }


    public int minY() {
        int m = coords[0][1];
        for (int i = 0; i < 4; i++) {
            m = Math.min(m, coords[i][1]);
        }
        return m;
    }


    public TetrisShape rotateRight() {
        if (pieceShape == Shape.SquareShape)
            return this;

        TetrisShape result = new TetrisShape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {
            result.coords[i][0] = -coords[i][1];
            result.coords[i][1] = coords[i][0];
        }
        return result;


    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TetrisShape)) return false;
        TetrisShape other = (TetrisShape) obj;
        if (this.pieceShape != other.pieceShape) return false;
        for (int i = 0; i < 4; i++) {
            if (this.coords[i][0] != other.coords[i][0] || this.coords[i][1] != other.coords[i][1]) {
                return false;
            }
        }
        return true;
    }

    public int[][] getCoords() {
        return coords;
    }

    public Shape getShape() {
        return pieceShape;
    }

    public int getWidth() {
        int minX = getMinX();
        int maxX = getMaxX();
        return maxX - minX + 1;
    }

    public int getMinX() {
        int m = coords[0][0];
        for (int i = 1; i < 4; i++) {
            m = Math.min(m, coords[i][0]);
        }
        return m;
    }

    public int getMaxX() {
        int m = coords[0][0];
        for (int i = 1; i < 4; i++) {
            m = Math.max(m, coords[i][0]);
        }
        return m;
    }
}

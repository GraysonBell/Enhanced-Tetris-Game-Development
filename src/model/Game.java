// this probably manages the games state, rules and overall game flow.

package model;


import ui.panel.PlayPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game extends JPanel implements ActionListener {

    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 20;
    private final Timer timer;
    private boolean isFallingFinished = false;
    private boolean isStarted = true;
    private boolean isPaused = false;
    private int curX = 0;
    private int curY = 0;
    private int numLinesRemoved = 0;
    private TetrisShape curPiece;
    private TetrisShape.Shape[] board;
    private Score scorePanel;
    private String pauseMessage = "";

    public Game() {
        setFocusable(true);
        curPiece = new TetrisShape();
        timer = new Timer(400, this);
        timer.start();

        board = new TetrisShape.Shape[BOARD_WIDTH * BOARD_HEIGHT];
        clearBoard();
        setupKeyBindings();


        scorePanel = new Score();
        setLayout(new BorderLayout());
        add(scorePanel, BorderLayout.EAST); // Add the score panel
    }


    private void setupKeyBindings() {
        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap(); // Initialising the Key Controls

        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft"); // Move piece left
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryMove(curPiece, curX - 1, curY);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight"); // Move piece right
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryMove(curPiece, curX + 1, curY);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown"); // Move piece down
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oneLineDown();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("UP"), "rotate"); //Rotate Piece by pressing 'up' key
        actionMap.put("rotate", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryMove(curPiece.rotateRight(), curX, curY);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "dropDown"); // Drop piece to bottom
        actionMap.put("dropDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropDown();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("P"), "pause"); // Pause Game(Not working yet)
        actionMap.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause();
            }
        });
    }

    private void dropDown() {
        int newY = curY;
        while (newY > 0) {
            if (!tryMove(curPiece, curX, newY - 1)) {
                break;
            }
            newY--;
        }
        pieceDropped();  // After dropping, the piece is set in place, and the new piece is generated
    }

    private void pause() {
        if (!isStarted) {
            return;
        }

        isPaused = !isPaused;
        if (isPaused) {
            timer.stop();
            repaint();
        } else {
            timer.start();
            repaint();// Restart the game timer, resuming the game
        }
    }


    public void start() {
        isStarted = true;
        isPaused = false;
        clearBoard();
        newPiece();
        requestFocusInWindow();
        timer.start();
    }

    private void clearBoard() {
        for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++) {
            board[i] = TetrisShape.Shape.NoShape;
        } // Clear Board
    }

    private void newPiece() {
        curPiece.setRandomShape();
        curX = BOARD_WIDTH / 2 + 1;
        curY = BOARD_HEIGHT - 1 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY)) {
            curPiece.setShape(TetrisShape.Shape.NoShape);
            timer.stop();
            isStarted = false;
            notifyGameOver();
        }
    }

    private void notifyGameOver() {
        if (getParent() instanceof PlayPanel) {
            ((PlayPanel) getParent()).setGameFinished(true);
        }
    }

    private boolean tryMove(TetrisShape newPiece, int newX, int newY) {
        for (int i = 0; i < 4; i++) {
            int x = newX + newPiece.getCoords()[i][0];
            int y = newY - newPiece.getCoords()[i][1];

            if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {
                return false;
            }

            if (shapeAt(x, y) != TetrisShape.Shape.NoShape) {
                return false;
            }
        }

        curPiece = newPiece;
        curX = newX;
        curY = newY;
        repaint();
        return true;
    }

    private TetrisShape.Shape shapeAt(int x, int y) {
        return board[(y * BOARD_WIDTH) + x];
    }

    private void oneLineDown() {
        if (!tryMove(curPiece, curX, curY - 1)) {
            pieceDropped();
        }
    }

    private void pieceDropped() {
        for (int i = 0; i < 4; i++) {
            int x = curX + curPiece.getCoords()[i][0];
            int y = curY - curPiece.getCoords()[i][1];
            board[(y * BOARD_WIDTH) + x] = curPiece.getShape();
        }

        removeFullLines();

        if (!isFallingFinished) {
            newPiece();
        }
    }

    private void removeFullLines() {
        int numFullLines = 0;

        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean lineIsFull = true;

            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (shapeAt(j, i) == TetrisShape.Shape.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                numFullLines++;

                for (int k = i; k < BOARD_HEIGHT - 1; k++) {
                    for (int j = 0; j < BOARD_WIDTH; j++) {
                        board[(k * BOARD_WIDTH) + j] = shapeAt(j, k + 1);
                    }
                }
            }
        }

        if (numFullLines > 0) {
            scorePanel.updateScore(numFullLines);
            numLinesRemoved += numFullLines;
            isFallingFinished = true;
            curPiece.setShape(TetrisShape.Shape.NoShape);
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isPaused){
            return;
        }

        if (isFallingFinished) {
            isFallingFinished = false;
            newPiece();
        } else {
            oneLineDown();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);

        }


    private void doDrawing(Graphics g) {
        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();

        if (isPaused){
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            FontMetrics fm = g.getFontMetrics();
            pauseMessage = "Paused, Press P to Unpause";
            int x = (getWidth() - fm.stringWidth(pauseMessage)) / 2;
            int y = getHeight() / 2;
            g.drawString(pauseMessage, x, y);
        }

        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                TetrisShape.Shape shape = shapeAt(j, BOARD_HEIGHT - i - 1);
                if (shape != TetrisShape.Shape.NoShape) {
                    drawSquare(g, 0 + j * squareWidth(),
                            boardTop + i * squareHeight(), shape);
                }
            }
        }

        if (curPiece.getShape() != TetrisShape.Shape.NoShape) {
            for (int i = 0; i < 4; i++) {
                int x = curX + curPiece.getCoords()[i][0];
                int y = curY - curPiece.getCoords()[i][1];
                drawSquare(g, 0 + x * squareWidth(),
                        boardTop + (BOARD_HEIGHT - y - 1) * squareHeight(),
                        curPiece.getShape());
            }
        }
    }

    private void drawSquare(Graphics g, int x, int y, TetrisShape.Shape shape) {

        Color colors[] = {
                new Color(0, 0, 0), new Color(204, 102, 102),
                new Color(102, 204, 102), new Color(102, 102, 204),
                new Color(206, 213, 102), new Color(206, 102, 204),
                new Color(107, 204, 119), new Color(218, 170, 0)
        };

        Color color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
    }

    private int squareWidth() {
        return (int) getSize().getWidth() / BOARD_WIDTH;
    }

    private int squareHeight() {
        return (int) getSize().getHeight() / BOARD_HEIGHT;
    }

}

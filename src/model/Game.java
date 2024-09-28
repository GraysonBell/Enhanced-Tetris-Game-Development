// this probably manages the games state, rules and overall game flow.

package model;


import ui.panel.PlayPanel;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;


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
    private PlayPanel playPanel;
    private String pauseMessage = "";
    private boolean isMusicOn = true;
    private boolean isSoundOn = true;
    private boolean isAIEnabled = false;

    // Alex - testing to see if I can add observers for tetronimo
    private static final ArrayList<JComponent> observers = new ArrayList<>();

    public static void addObserver(JComponent comp) {observers.add(comp); }

    public static void clearObservers() {observers.clear(); }

    public static void informObservers() {
        for (JComponent observer : observers) {
            if (observer instanceof JLabel) {
                JLabel label = (JLabel) observer;
                if (label.getText().startsWith("Next Tetromino:")) {
                    label.setText("Next Tetromino:");
                }
            }
            observer.repaint();
        }
    }


    public Game() {

        setFocusable(true);
        curPiece = new TetrisShape();
        timer = new Timer(400, this);
        timer.start();

        board = new TetrisShape.Shape[BOARD_WIDTH * BOARD_HEIGHT];
        clearBoard();
        setupKeyBindings();


        scorePanel = new Score("----", 0, MetaConfig.getInstance());
        setLayout(new BorderLayout());
        add(scorePanel, BorderLayout.EAST); // Add the score panel
    }

    // Music and sound effects
    public class SoundHandler {
        private static boolean isMusicOn = true;
        private static boolean isSoundOn = true;
        private static Clip musicClip;

        public static void setMusicOn(boolean on) {
            isMusicOn = on;
            if (musicClip != null) {
                if (!isMusicOn) {
                    musicClip.stop();
                    musicClip.close();
                } else {
                    try {
                        musicClip.setFramePosition(0);
                        musicClip.start();
                        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public static void setSoundOn(boolean on) {
            isSoundOn = on;
        }

        public static void RunMusic(String path) {
            try {
                if (musicClip != null) {
                    musicClip.stop();
                    musicClip.close();
                }
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(path));
                musicClip = AudioSystem.getClip();
                musicClip.open(inputStream);
                if (isMusicOn) {
                    musicClip.start();
                    musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        public static void BlockPlace(String path) {
            if (!isSoundOn) return;
            try {
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(path));
                Clip clip = AudioSystem.getClip();
                clip.open(inputStream);
                clip.loop(0);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        public static void SplashMusic(String path) {
            try {
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(path));
                Clip clip = AudioSystem.getClip();
                clip.open(inputStream);
                clip.loop(0);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        public static void ClearedLineSound(String path) {
            if (!isSoundOn) return;
            try {
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(path));
                Clip clip = AudioSystem.getClip();
                clip.open(inputStream);
                clip.loop(0);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
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

        inputMap.put(KeyStroke.getKeyStroke("P"), "pause"); // Pause Game
        actionMap.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("M"), "music"); // toggles music on/off
        actionMap.put("muisc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicToggle();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("S"), "sounds"); // toggles sound effects on/off
        actionMap.put("sounds", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundToggle();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("A"), "toggleAI"); // Toggle AI control
        actionMap.put("toggleAI", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAIEnabled = !isAIEnabled;
                System.out.println("AI mode: " + (isAIEnabled ? "Enabled" : "Disabled"));
            }
        });
    }

    private void makeAIMove() {
        Move bestMove = findBestMove();
        if (bestMove != null) {
            // Apply the best move
            curPiece = bestMove.piece;
            curX = bestMove.x;
            curY = bestMove.y;
            dropDown();
        }
    }

    private class Move {
        public TetrisShape piece;
        public int x;
        public int y;
        public double score;

        public Move(TetrisShape piece, int x, int y, double score) {
            this.piece = piece;
            this.x = x;
            this.y = y;
            this.score = score;
        }
    }

    private Move findBestMove() {
        double bestScore = Double.NEGATIVE_INFINITY; // Keep track of the highest score
        Move bestMove = null; // The best move found

        // Get all possible rotations of the current piece
        TetrisShape[] rotations = getAllRotations(curPiece);

        // Loop through each rotation
        for (TetrisShape rotation : rotations) {
            int minX = rotation.getMinX();
            int maxX = rotation.getMaxX();

            // Create a list of possible x positions and shuffle it to randomize order
            List<Integer> xPositions = new ArrayList<>();
            for (int x = -minX; x < BOARD_WIDTH - maxX; x++) {
                xPositions.add(x);
            }
            Collections.shuffle(xPositions); // Randomize the column order

            // Try every possible horizontal position for this rotation in random order
            for (int x : xPositions) {
                // Determine the drop height for this piece at this position
                int y = getDropHeight(rotation, x);
                if (y < 0) {
                    continue; // Skip invalid positions
                }

                // Simulate the board with the piece placed at (x, y)
                TetrisShape.Shape[] boardCopy = board.clone(); // Clone the board to simulate
                placePiece(rotation, x, y, boardCopy); // Simulate placing the piece

                // Evaluate the board after placing the piece
                double score = evaluateBoard(boardCopy); // Score the board
                System.out.println("Testing move: x=" + x + ", y=" + y + ", rotation=" + rotation + ", score=" + score + ", Best Score=" + bestScore);
                // Only update the bestMove if this move has a higher score
                if (score > bestScore) {
                    bestScore = score; // Update the best score found
                    bestMove = new Move(rotation, x, y, score); // Store the best move
                }
            }
        }

        return bestMove; // Return the best move after evaluating all positions
    }

    private TetrisShape[] getAllRotations(TetrisShape piece) {
        List<TetrisShape> rotations = new ArrayList<>();
        TetrisShape currentRotation = piece;

        // Add the original rotation
        rotations.add(currentRotation);

        // Generate all rotations (up to 4 for a standard Tetris piece)
        for (int i = 0; i < 3; i++) {
            currentRotation = currentRotation.rotateRight();
            // Only add if this rotation has not been added already
            boolean isDuplicate = false;
            for (TetrisShape rotation : rotations) {
                if (rotation.equals(currentRotation)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                rotations.add(currentRotation);
            }
        }

        return rotations.toArray(new TetrisShape[0]);
    }

    private int getDropHeight(TetrisShape piece, int x) {
        int y = curY;
        while (y > 0) {
            if (!tryMove(piece, x, y - 1, board)) {
                break;
            }
            y--;
        }
        return y;
    }

    private boolean tryMove(TetrisShape newPiece, int newX, int newY, TetrisShape.Shape[] board) {
        for (int i = 0; i < 4; i++) {
            int x = newX + newPiece.getCoords()[i][0];
            int y = newY - newPiece.getCoords()[i][1];

            if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {
                return false;
            }

            if (board[(y * BOARD_WIDTH) + x] != TetrisShape.Shape.NoShape) {
                return false;
            }
        }
        return true;
    }

    private void placePiece(TetrisShape piece, int x, int y, TetrisShape.Shape[] board) {
        for (int i = 0; i < 4; i++) {
            int px = x + piece.getCoords()[i][0];
            int py = y - piece.getCoords()[i][1];
            if (py >= 0 && py < BOARD_HEIGHT && px >= 0 && px < BOARD_WIDTH) {
                board[(py * BOARD_WIDTH) + px] = piece.getShape();
            }
        }
        removeFullLines(board);
    }

    private void removeFullLines(TetrisShape.Shape[] board) {
        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean lineIsFull = true;

            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[(i * BOARD_WIDTH) + j] == TetrisShape.Shape.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                for (int k = i; k < BOARD_HEIGHT - 1; k++) {
                    for (int j = 0; j < BOARD_WIDTH; j++) {
                        board[(k * BOARD_WIDTH) + j] = board[((k + 1) * BOARD_WIDTH) + j];
                    }
                }
                i++; // Recheck the same line
            }
        }
    }

    private double evaluateBoard(TetrisShape.Shape[] board) {
        int aggregateHeight = 0;
        int completeLines = 0;
        int holes = 0;
        int bumpiness = 0;

        int[] columnHeights = new int[BOARD_WIDTH];

        // Calculate column heights
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                if (board[(y * BOARD_WIDTH) + x] != TetrisShape.Shape.NoShape) {
                    columnHeights[x] = BOARD_HEIGHT - y;
                    break;
                }
            }
        }

        // Aggregate height and bumpiness
        for (int x = 0; x < BOARD_WIDTH; x++) {
            aggregateHeight += columnHeights[x];
            if (x > 0) {
                bumpiness += Math.abs(columnHeights[x] - columnHeights[x - 1]);
            }
        }

        // Count complete lines
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            boolean isLineComplete = true;
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (board[(y * BOARD_WIDTH) + x] == TetrisShape.Shape.NoShape) {
                    isLineComplete = false;
                    break;
                }
            }
            if (isLineComplete) {
                completeLines++;
            }
        }

        // Count holes
        for (int x = 0; x < BOARD_WIDTH; x++) {
            boolean blockFound = false;
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                if (board[(y * BOARD_WIDTH) + x] != TetrisShape.Shape.NoShape) {
                    blockFound = true;
                } else if (blockFound) {
                    holes++;
                }
            }
        }

        // Evaluation function weights (these can be adjusted)
        double a = 2.9;
        double b = 2.5;
        double c = -1.0;
        double d = -1.0;

        // Compute the score
        return (a * aggregateHeight) + (b * completeLines) + (c * holes) + (d * bumpiness);
    }


    private void dropDown() {
        int newY = curY;
        while (newY > 0) {
            if (!tryMove(curPiece, curX, newY - 1)) {
                break;
            }
            newY--;
        }
        pieceDropped();
        {
            Game.SoundHandler.BlockPlace("src/sounds/hitmarker.wav");
            // After dropping, the piece is set in place, and the new piece is generated
        }
    }

    private void musicToggle() {
        isMusicOn = !isMusicOn;
        SoundHandler.setMusicOn(isMusicOn);
        System.out.println("Music toggled: " + (isMusicOn ? "ON" : "OFF"));
    }

    private void soundToggle() {
        isSoundOn = !isSoundOn;
        SoundHandler.setSoundOn(isSoundOn);
    }

    public void pause() {
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

    public void resume() {
        // Resume the game logic
        timer.start();
        isPaused = false;
    }

    public void start() {
        isStarted = true;
        isPaused = false;
        clearBoard();
        newPiece();
        requestFocusInWindow();
        timer.start();
        SoundHandler.RunMusic("src/sounds/753603__zanderhud__pss560-slow-rock-and-bass-auto-rhythm.wav");
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
            Game.SoundHandler.ClearedLineSound("src/sounds/vs-anime-sound-10.wav");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isPaused) {
            return;
        }

        if (isFallingFinished) {
            isFallingFinished = false;
            newPiece();
        } else {
            if (isAIEnabled) {
                makeAIMove(); // Let the AI decide the move
            } else {
                oneLineDown(); // Existing behavior
            }
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

        if (isPaused) {
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


package ui.panel;

import ui.MainFrame;
import ui.UIGenerator;
import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayPanel extends JPanel {

    private Game game;
    private JButton backButton;
    private boolean isGameFinished = false;
    private boolean isPaused = false;

    public PlayPanel() {
        setLayout(new BorderLayout());

        game = new Game();  // Initialize the Game instance, but do not start it
        add(game, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Play");
        titleLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 25));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        Dimension buttonSize = new Dimension(200, 40);
        Color buttonBorderColor = Color.BLACK;
        backButton = UIGenerator.createButton("Back", buttonSize, buttonBorderColor);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBackButtonClick();
            }
        });

        add(backButton, BorderLayout.SOUTH);
    }

    public void startGame() {
        game.start(); // Start the game when this method is called
    }

    private void handleBackButtonClick() {
        if (isGameFinished) {
            navigateToMainMenu();
        } else {
            if (!isPaused) {
                game.pause(); // Pause the game
                isPaused = true;
            }
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit the game?", "Quit Game", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                navigateToMainMenu();
            } else {
                game.resume(); // Resume the game
                isPaused = false;
            }
        }
    }

    private void navigateToMainMenu() {
        MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(this);
        frame.showPanel("MainPanel");
    }

    public void setGameFinished(boolean finished) {
        isGameFinished = finished;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}

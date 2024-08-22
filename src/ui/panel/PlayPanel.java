// Panel where the user interacts with the Game (gameplay area)

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

    public PlayPanel(){
        // Have to set the layout so I know what type of Layout it is for me to put things places.
        setLayout(new BorderLayout());

        //Setting the border so i know what it is
        setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));

        BorderLayout layout = new BorderLayout();

        Game game = new Game();
        add(game, BorderLayout.CENTER);

        //Created the title Page
        JLabel titleLabel = new JLabel("Play");
        titleLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN,  25));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);


        // Created the Back Button
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

    private void handleBackButtonClick() {
        if (isGameFinished) {
            navigateToMainMenu();
        } else if (isPaused) {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit the game?", "Quit Game", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                navigateToMainMenu();
            }
        } else {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit the game?", "Quit Game", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                navigateToMainMenu();
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

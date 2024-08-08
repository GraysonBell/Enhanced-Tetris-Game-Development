// I think this is the central navigation hub, provides buttons to the other panels.
// will contain the options to navigate to the following panels.
// Play, High Scores, Configuration, Exit

// Working on placements and button sizes - Omar

package ui.panel;

import ui.MainFrame;
import ui.UIGenerator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        // Placing a border around the panel, so I can see where everything fits. Can remove afterward
        setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        // Create a JLabel panel for the title
        JLabel titleLabel = new JLabel("Tetris");
        titleLabel.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD,  100));
        // Placed a border around the title to see where the panel is. Can remove afterward
        titleLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
        add(titleLabel, gbc);

        // Adding vertical space between the title and the buttons
        gbc.insets = new Insets(-200, 0, 0, 0);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create a JPanel for the buttons to sit on.
        JPanel buttons = new JPanel(new GridBagLayout());
        // Placed a border around the button panel to see where the panel is. Can remove afterward
        buttons.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridwidth = GridBagConstraints.REMAINDER;
        buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
        buttonConstraints.insets = new Insets(5, 5, 5, 5);

        //Setting the button sizes and border color, then using the button method created in the UIGenerator Class.
        Dimension buttonSize = new Dimension(200, 40);
        Color buttonBorderColor = Color.BLACK;

        // Made the "Play" button - Works
        JButton playButton = UIGenerator.createButton("Play", buttonSize, buttonBorderColor);
        playButton.addActionListener(e -> {
            MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            frame.showPanel("PlayPanel");
        });

        // Made the "Configure" button - Works
        JButton configureButton = UIGenerator.createButton("Configure", buttonSize, buttonBorderColor);
        configureButton.addActionListener(e -> {
            MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            frame.showPanel("ConfigurePanel");
        });

        // Made the "High Score" button - Works
        JButton highScoresButton = UIGenerator.createButton("High Scores", buttonSize, buttonBorderColor);
        highScoresButton.addActionListener(e -> {
            MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            frame.showPanel("HighScorePanel");
        });

        // Made the "Exit" button - Works
        JButton exitButton = UIGenerator.createButton("Exit", buttonSize, buttonBorderColor);
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        buttons.add(playButton, buttonConstraints);
        buttons.add(configureButton, buttonConstraints);
        buttons.add(highScoresButton, buttonConstraints);
        buttons.add(exitButton, buttonConstraints);
        gbc.weighty = 1;
        add(buttons, gbc);
    }
}
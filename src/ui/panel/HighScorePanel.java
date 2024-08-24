// Pretty Straight forward this one.
// Reminder that this just contains mock information not actual.

package ui.panel;

import ui.MainFrame;
import ui.UIGenerator;

import javax.swing.*;
import java.awt.*;

public class HighScorePanel extends JPanel {

    public HighScorePanel(){
        // Have to set the layout so I know what type of Layout it is for me to put things places.
        setLayout(new BorderLayout());

        BorderLayout layout = new BorderLayout();


        //Created the title Page
        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN,  50));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Left most section for name labels

        JPanel namePanel = new JPanel(new GridBagLayout());
        GridBagConstraints nameConstraints = new GridBagConstraints();
        nameConstraints.anchor = GridBagConstraints.CENTER;
        nameConstraints.insets = new Insets(20, 150, 20, 0);

        nameConstraints.gridx = 0;
        nameConstraints.gridy = 0;

        JLabel nameHeading;
        nameHeading = new JLabel("Name");
        nameHeading.setFont(new Font("Gill Sans Bold", Font.PLAIN,  20));
        nameHeading.setHorizontalAlignment(JLabel.CENTER);
        namePanel.add(nameHeading, nameConstraints);
        nameConstraints.gridy++;

        namePanel.add(new JLabel("Alex"), nameConstraints);
        nameConstraints.gridy++;
        namePanel.add(new JLabel("Omar"), nameConstraints);
        nameConstraints.gridy++;
        namePanel.add(new JLabel("Grayson"), nameConstraints);
        nameConstraints.gridy++;
        namePanel.add(new JLabel("Melvin"), nameConstraints);
        nameConstraints.gridy++;
        namePanel.add(new JLabel("Diljot"), nameConstraints);
        nameConstraints.gridy++;
        namePanel.add(new JLabel("Omar"), nameConstraints);
        nameConstraints.gridy++;
        namePanel.add(new JLabel("Melvin"), nameConstraints);
        nameConstraints.gridy++;
        namePanel.add(new JLabel("Alex"), nameConstraints);
        nameConstraints.gridy++;
        namePanel.add(new JLabel("Grayson"), nameConstraints);
        nameConstraints.gridy++;
        namePanel.add(new JLabel("Diljot"), nameConstraints);
        nameConstraints.gridy++;

        // Right most section for score labels

        JPanel scorePanel = new JPanel(new GridBagLayout());
        GridBagConstraints scoreConstraints = new GridBagConstraints();
        scoreConstraints.anchor = GridBagConstraints.CENTER;
        scoreConstraints.insets = new Insets(20, 0, 20, 150);

        scoreConstraints.gridx = 0;
        scoreConstraints.gridy = 0;

        JLabel scoreHeading;
        scoreHeading = new JLabel("Score");
        scoreHeading.setFont(new Font("Gill Sans Bold", Font.PLAIN,  20));
        scoreHeading.setHorizontalAlignment(JLabel.CENTER);
        scorePanel.add(scoreHeading, scoreConstraints);
        scoreConstraints.gridy++;

        scorePanel.add(new JLabel("869613"), scoreConstraints);
        scoreConstraints.gridy++;
        scorePanel.add(new JLabel("754569"), scoreConstraints);
        scoreConstraints.gridy++;
        scorePanel.add(new JLabel("642871"), scoreConstraints);
        scoreConstraints.gridy++;
        scorePanel.add(new JLabel("549280"), scoreConstraints);
        scoreConstraints.gridy++;
        scorePanel.add(new JLabel("537726"), scoreConstraints);
        scoreConstraints.gridy++;
        scorePanel.add(new JLabel("452740"), scoreConstraints);
        scoreConstraints.gridy++;
        scorePanel.add(new JLabel("366765"), scoreConstraints);
        scoreConstraints.gridy++;
        scorePanel.add(new JLabel("326181"), scoreConstraints);
        scoreConstraints.gridy++;
        scorePanel.add(new JLabel("301649"), scoreConstraints);
        scoreConstraints.gridy++;
        scorePanel.add(new JLabel("260598"), scoreConstraints);
        scoreConstraints.gridy++;

        add(namePanel, BorderLayout.WEST);
        add(scorePanel, BorderLayout.EAST);

        // Created the Back Button
        Dimension buttonSize = new Dimension(200, 40);
        Color buttonBorderColor = Color.BLACK;
        JButton configureButton = UIGenerator.createButton("Back", buttonSize, buttonBorderColor);
        configureButton.addActionListener(e -> {
            MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            frame.showPanel("MainPanel");
        });

        // Add the button to the south section
        add(configureButton, BorderLayout.SOUTH);

    }
}

// Pretty Straight forward this one.
// Reminder that this just contains mock information not actual.

package ui.panel;

import ui.MainFrame;
import ui.UIGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HighScorePanel extends JPanel {

    public HighScorePanel(){
        // Have to set the layout so I know what type of Layout it is for me to put things places.
        setLayout(new BorderLayout());

        //Setting the border so i know what it is
        setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));

        BorderLayout layout = new BorderLayout();


        //Created the title Page
        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN,  50));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);


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

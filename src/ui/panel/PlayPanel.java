// Panel where the user interacts with the Game (gameplay area)

package ui.panel;

import ui.MainFrame;
import ui.UIGenerator;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {

    public PlayPanel(){
        // Have to set the layout so I know what type of Layout it is for me to put things places.
        setLayout(new BorderLayout());

        //Setting the border so i know what it is
        setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));

        BorderLayout layout = new BorderLayout();


        //Created the title Page
        JLabel titleLabel = new JLabel("Play");
        titleLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN,  25));
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

// I think this is the central navigation hub, provides buttons to the other panels.
// will contain the options to navigate to the following panels.
// Play, High Scores, Configuration, Exit

// Working on placements and button sizes before i link them into other panels. - Omar

package ui.panel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        //Placing a border around the Panel so i can see where everything fits.
        setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        // Placed a border around the title and to see where it is.
        JLabel titleLabel = new JLabel("Tetris");
        titleLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 36));
        titleLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        add(titleLabel, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Placed a border around the button panel to see where the panel is.
        JPanel buttons = new JPanel(new GridBagLayout());
        buttons.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.fill = GridBagConstraints.HORIZONTAL;

        //this allows me to put space around the buttons apparently.
        buttonConstraints.insets = new Insets(5, 5, 5, 5);

        buttons.add(new JButton("Play"), buttonConstraints);
        buttons.add(new JButton("Configure"), buttonConstraints);
        buttons.add(new JButton("High Scores"), buttonConstraints);
        buttons.add(new JButton("Exit"), buttonConstraints);

        gbc.weighty = 1;
        add(buttons, gbc);
    }
}








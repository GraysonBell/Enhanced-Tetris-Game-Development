//A utility class function that helps create and configure UI stuff and layouts to make sure its consistent across the UI
package ui;

import javax.swing.*;
import java.awt.*;

public class UIGenerator {


    //Create a button method so that we can reuse it over and over again.
    public static JButton createButton(String text, Dimension size, Color borderColor){
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setBorder(BorderFactory.createLineBorder(borderColor,1));
        return button;
    }

    public static JLabel createHeaderLabel(String txt) {
        JLabel lb = new JLabel(txt);
        lb.setFont(new Font("Arial", 1, 14));
        lb.setHorizontalAlignment(0);
        return lb;
    }


}

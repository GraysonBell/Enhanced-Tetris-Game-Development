// Splash Screen like the load up screen that start the application
// Used the tutorial in Canvas
// Completed don't need to change

package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;


public class SplashScreen extends JWindow {

    private int duration;

    public SplashScreen(int duration) {
        this.duration = duration;
    }

    public void showSplash() {
        Game.SoundHandler.SplashMusic("src/sounds/jungle-ring.wav");
        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);
        content.setLayout(new BorderLayout());

        // Load the image and get its dimensions to make it fit the whole picture without having to have a background in
        ImageIcon splashImage = new ImageIcon("src/images/tetrisSplash.jpg");
        int width = splashImage.getIconWidth();
        int height = splashImage.getIconHeight();

        // Setting the window bounds as per the above size of the pic (not in canvas)
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        // Build the splash screen
        JLabel label = new JLabel(splashImage);
        JLabel copyryt = new JLabel("Authors: Team 25 ", JLabel.CENTER);
        copyryt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        content.add(copyryt, BorderLayout.SOUTH);

        // Display the splash screen
        setVisible(true);

        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setVisible(false);
    }
}



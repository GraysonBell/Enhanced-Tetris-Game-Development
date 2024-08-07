// Splash Screen like the load up screen that start the application

package ui;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {

    private int duration;

    public SplashScreen(int duration) {
        this.duration = duration;
    }

    public void showSplash() {
        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);
        content.setLayout(new BorderLayout());

        // Load the image and get its dimensions
        ImageIcon splashImage = new ImageIcon("src/images/tetrisSplash.jpg");
        int width = splashImage.getIconWidth();
        int height = splashImage.getIconHeight();

        // Set the window bounds and center it
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

        // Use a Swing Timer to handle the splash screen duration without blocking the EDT
        new Timer(duration, e -> {
            setVisible(false);
            ((Timer) e.getSource()).stop();
            System.exit(0);
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen(3000);
            splash.showSplash();
        });
    }
}

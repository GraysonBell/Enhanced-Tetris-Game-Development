// Contains the configuration panel where you can configure the game settings.
// Reminder!! This is mock info and interaction doesn't actually affect the gameplay.

package ui.panel;

import ui.MainFrame;
import ui.UIGenerator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.ImageObserver;
import java.util.Observable;

public class ConfigurePanel extends JPanel {
    public ConfigurePanel(){
        // Have to set the layout so I know what type of Layout it is for me to put things places.
        setLayout(new BorderLayout());

        //Setting the border so i know what it is
        setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));

        BorderLayout layout = new BorderLayout();


        //Created the title Page
        JLabel titleLabel = new JLabel("Configuration");
        titleLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN,  50));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Left most section for config labels

        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(20, 30, 20, 0);

        constraints.gridx = 0;
        constraints.gridy = 0;
        leftPanel.add(new JLabel("Field Width (No of cells):"), constraints);
        constraints.gridy++;
        leftPanel.add(new JLabel("Field Height (No of cells):"), constraints);
        constraints.gridy++;
        leftPanel.add(new JLabel("Game Level:"), constraints);
        constraints.gridy++;
        leftPanel.add(new JLabel("Music (On/Off):"), constraints);
        constraints.gridy++;
        leftPanel.add(new JLabel("Sound Effects (On/Off):"), constraints);
        constraints.gridy++;
        leftPanel.add(new JLabel("AI Play (On/Off):"), constraints);
        constraints.gridy++;
        leftPanel.add(new JLabel("Extend Mode (On/Off):"), constraints);
        constraints.gridy++;

        // Middle section for sliders and check boxes

        JPanel middlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.anchor = GridBagConstraints.CENTER;
        constraints2.insets = new Insets(5, 20, 20, 20);

        //Sliders
        JSlider widthSlider;
        widthSlider = new JSlider(5, 15, 10);
        widthSlider.setPreferredSize(new Dimension(300, 35));
        widthSlider.setPaintTicks(true);
        widthSlider.setMajorTickSpacing(1);
        widthSlider.setPaintTrack(true);
        widthSlider.setSnapToTicks(true);
        widthSlider.setPaintLabels(true);

        JLabel width_label = new JLabel(" " + widthSlider.getValue());

        widthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                width_label.setText(" " + widthSlider.getValue());
            }
        });

        JSlider heightSlider;
        heightSlider = new JSlider(15, 30, 20);
        heightSlider.setPreferredSize(new Dimension(300, 35));
        heightSlider.setPaintTicks(true);
        heightSlider.setMajorTickSpacing(1);
        heightSlider.setPaintTrack(true);
        heightSlider.setSnapToTicks(true);
        heightSlider.setPaintLabels(true);

        JLabel height_label = new JLabel(" " + heightSlider.getValue());

        heightSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                height_label.setText(" " + heightSlider.getValue());
            }
        });

        JSlider gameLevelSlider;
        gameLevelSlider = new JSlider(1, 10, 1);
        gameLevelSlider.setPreferredSize(new Dimension(300, 35));
        gameLevelSlider.setPaintTicks(true);
        gameLevelSlider.setMajorTickSpacing(1);
        gameLevelSlider.setPaintTrack(true);
        gameLevelSlider.setSnapToTicks(true);
        gameLevelSlider.setPaintLabels(true);

        JLabel gameLevel_label = new JLabel(" " + gameLevelSlider.getValue());

        gameLevelSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                gameLevel_label.setText(" " + gameLevelSlider.getValue());
            }
        });

        //Check boxes
        JCheckBox musicCheckBox;
        musicCheckBox = new JCheckBox();
        JLabel music_label = new JLabel("Off");

        musicCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (musicCheckBox.isSelected()) {
                    music_label.setText("On");
                } else {
                    music_label.setText("Off");
                }
            }
        });


        JCheckBox soundEffectsCheckBox;
        soundEffectsCheckBox = new JCheckBox();
        JLabel soundEffect_label = new JLabel("Off");

        soundEffectsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (soundEffectsCheckBox.isSelected()) {
                    soundEffect_label.setText("On");
                } else {
                    soundEffect_label.setText("Off");
                }
            }
        });

        JCheckBox AIPlayCheckBox;
        AIPlayCheckBox = new JCheckBox();
        JLabel AIPlay_label = new JLabel("Off");

        AIPlayCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (AIPlayCheckBox.isSelected()) {
                    AIPlay_label.setText("On");
                } else {
                    AIPlay_label.setText("Off");
                }
            }
        });


        JCheckBox extendModeCheckBox;
        extendModeCheckBox = new JCheckBox();
        JLabel extendMode_label = new JLabel("Off");

        extendModeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (extendModeCheckBox.isSelected()) {
                    extendMode_label.setText("On");
                } else {
                    extendMode_label.setText("Off");
                }
            }
        });



        constraints2.gridx = 0;
        constraints2.gridy = 0;
        constraints2.weightx = 1.0;
        middlePanel.add(widthSlider, constraints2);
        constraints2.gridy++;
        middlePanel.add(heightSlider, constraints2);
        constraints2.gridy++;
        middlePanel.add(gameLevelSlider, constraints2);
        constraints2.gridy++;
        middlePanel.add(musicCheckBox, constraints2);
        constraints2.gridy++;
        middlePanel.add(soundEffectsCheckBox, constraints2);
        constraints2.gridy++;
        middlePanel.add(AIPlayCheckBox, constraints2);
        constraints2.gridy++;
        middlePanel.add(extendModeCheckBox, constraints2);
        constraints2.gridy++;

        // Right section for slider values and on/off switches.

        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints3 = new GridBagConstraints();
        constraints3.anchor = GridBagConstraints.EAST;
        constraints3.insets = new Insets(20, 0, 20, 30);

        constraints3.gridx = 0;
        constraints3.gridy = 0;
        rightPanel.add(width_label, constraints3);
        constraints3.gridy++;
        rightPanel.add(height_label, constraints3);
        constraints3.gridy++;
        rightPanel.add(gameLevel_label, constraints3);
        constraints3.gridy++;
        rightPanel.add(music_label, constraints3);
        constraints3.gridy++;
        rightPanel.add(soundEffect_label, constraints3);
        constraints3.gridy++;
        rightPanel.add(AIPlay_label, constraints3);
        constraints3.gridy++;
        rightPanel.add(extendMode_label, constraints3);
        constraints3.gridy++;

        add(leftPanel, BorderLayout.WEST);
        add(middlePanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);


        // Create field width slider.

        //JSlider FieldWidthSlider = new JSlider(JSlider.HORIZONTAL, 5, 15, 10);
        //FieldWidthSlider.setPreferredSize(new Dimension(400, 200));
        //FieldWidthSlider.setMajorTickSpacing(1);
        //FieldWidthSlider.setPaintTicks(true);
        //FieldWidthSlider.setPaintLabels(true);
        //FieldWidthSlider.setSnapToTicks(true);
        //add(FieldWidthSlider, BorderLayout.CENTER);

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

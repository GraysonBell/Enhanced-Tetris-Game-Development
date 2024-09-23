package ui.panel;

import ui.MainFrame;
import ui.UIGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class ConfigurePanel extends JPanel {
    public ConfigurePanel() {
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Configuration");
        titleLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 50));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Main panel with GridLayout
        JPanel mainPanel = new JPanel(new GridLayout(8, 3, 1, 1)); // Adjust the gaps if needed
        mainPanel.setPreferredSize(new Dimension(800, 400));

        // Add padding to mainPanel
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 0)); // Top, Left, Bottom, Right padding
        add(mainPanel, BorderLayout.CENTER);

        // Add components to the main panel
        addLabel(mainPanel, "Field Width (No of cells):");
        JSlider widthSlider = createSlider(5, 15, 10, mainPanel);
        JLabel widthLabel = createLabelForSlider(widthSlider);
        mainPanel.add(widthLabel);

        addLabel(mainPanel, "Field Height (No of cells):");
        JSlider heightSlider = createSlider(15, 30, 20, mainPanel);
        JLabel heightLabel = createLabelForSlider(heightSlider);
        mainPanel.add(heightLabel);

        addLabel(mainPanel, "Game Level:");
        JSlider gameLevelSlider = createSlider(1, 10, 1, mainPanel);
        JLabel gameLevelLabel = createLabelForSlider(gameLevelSlider);
        mainPanel.add(gameLevelLabel);

        // Add checkboxes with associated labels
        addLabel(mainPanel, "Music (On/Off):");
        JCheckBox musicCheckBox = createCheckBox(mainPanel, "Music (On/Off):");

        addLabel(mainPanel, "Sound Effects (On/Off):");
        JCheckBox soundEffectsCheckBox = createCheckBox(mainPanel, "Sound Effects (On/Off):");

        addLabel(mainPanel, "Extend Mode (On/Off):");
        JCheckBox extendModeCheckBox = createCheckBox(mainPanel, "Extend Mode (On/Off):");

        // Player One Type with radio buttons and selection confirmer.
        addLabel(mainPanel, "Player One Type:");

        JPanel playerOneTypePanel = new JPanel();
        playerOneTypePanel.setLayout(new BoxLayout(playerOneTypePanel, BoxLayout.Y_AXIS));

        JRadioButton humanButton1 = new JRadioButton("Human");
        JRadioButton aiButton1 = new JRadioButton("AI");
        JRadioButton externalButton1 = new JRadioButton("External");

        ButtonGroup playerOneGroup = new ButtonGroup();
        playerOneGroup.add(humanButton1);
        playerOneGroup.add(aiButton1);
        playerOneGroup.add(externalButton1);

        JLabel playerOneLabel = new JLabel("Selected: None");

        ActionListener playerOneListener = e -> playerOneLabel.setText("Selected: " + ((JRadioButton)e.getSource()).getText());
        humanButton1.addActionListener(playerOneListener);
        aiButton1.addActionListener(playerOneListener);
        externalButton1.addActionListener(playerOneListener);

        playerOneTypePanel.add(humanButton1);
        playerOneTypePanel.add(aiButton1);
        playerOneTypePanel.add(externalButton1);

        mainPanel.add(playerOneTypePanel);
        mainPanel.add(playerOneLabel);

        // Player Two Type with radio buttons and selection confirmer.
        addLabel(mainPanel, "Player Two Type:");

        JPanel playerTwoTypePanel = new JPanel();
        playerTwoTypePanel.setLayout(new BoxLayout(playerTwoTypePanel, BoxLayout.Y_AXIS));

        JRadioButton humanButton2 = new JRadioButton("Human");
        JRadioButton aiButton2 = new JRadioButton("AI");
        JRadioButton externalButton2 = new JRadioButton("External");

        ButtonGroup playerTwoGroup = new ButtonGroup();
        playerTwoGroup.add(humanButton2);
        playerTwoGroup.add(aiButton2);
        playerTwoGroup.add(externalButton2);

        JLabel playerTwoLabel = new JLabel("Selected: None");

        ActionListener playerTwoListener = e -> playerTwoLabel.setText("Selected: " + ((JRadioButton)e.getSource()).getText());
        humanButton2.addActionListener(playerTwoListener);
        aiButton2.addActionListener(playerTwoListener);
        externalButton2.addActionListener(playerTwoListener);

        playerTwoTypePanel.add(humanButton2);
        playerTwoTypePanel.add(aiButton2);
        playerTwoTypePanel.add(externalButton2);

        // Initially hide Player Two options
        playerTwoTypePanel.setVisible(false);
        playerTwoLabel.setVisible(false);

        mainPanel.add(playerTwoTypePanel);
        mainPanel.add(playerTwoLabel);

        // Add ActionListener to Extend Mode checkbox
        extendModeCheckBox.addActionListener(e -> {
            boolean isSelected = extendModeCheckBox.isSelected();
            playerTwoTypePanel.setVisible(isSelected);
            playerTwoLabel.setVisible(isSelected);
        });

        // Back Button
        Dimension buttonSize = new Dimension(200, 40);
        Color buttonBorderColor = Color.BLACK;
        JButton configureButton = UIGenerator.createButton("Back", buttonSize, buttonBorderColor);
        configureButton.addActionListener(e -> {
            MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            frame.showPanel("MainPanel");
        });

        add(configureButton, BorderLayout.SOUTH);
    }

    // Methods to reduce code.
    private void addLabel(JPanel panel, String text) {
        panel.add(new JLabel(text));
    }

    private JSlider createSlider(int min, int max, int initial, JPanel panel) {
        JSlider slider = new JSlider(min, max, initial);
        slider.setPreferredSize(new Dimension(300, 35));
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(1);
        slider.setPaintTrack(true);
        slider.setSnapToTicks(true);
        slider.setPaintLabels(true);
        panel.add(slider);
        return slider;
    }

    private JLabel createLabelForSlider(JSlider slider) {
        JLabel label = new JLabel(" " + slider.getValue());
        slider.addChangeListener(e -> label.setText(" " + slider.getValue()));
        return label;
    }

    private JCheckBox createCheckBox(JPanel panel, String labelText) {
        JCheckBox checkBox = new JCheckBox();
        JLabel label = new JLabel("Off");
        checkBox.addActionListener(e -> label.setText(checkBox.isSelected() ? "On" : "Off"));

        panel.add(checkBox);
        panel.add(label);
        return checkBox;
    }
}

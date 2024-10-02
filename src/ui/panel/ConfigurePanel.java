package ui.panel;

import model.Game;
import model.MetaConfig;
import ui.MainFrame;
import ui.UIGenerator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConfigurePanel extends JPanel {

    public ConfigurePanel() {
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Configuration");
        titleLabel.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 50));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Main panel with GridLayout
        JPanel mainPanel = new JPanel(new GridLayout(8, 3, 1, 1));
        mainPanel.setPreferredSize(new Dimension(800, 400));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 0));
        add(mainPanel, BorderLayout.CENTER);

        // Load configuration from MetaConfig
        MetaConfig config = MetaConfig.getInstance();

        // Add components to the main panel
        addLabel(mainPanel, "Field Width (No of cells):");
        JSlider widthSlider = createSlider(5, 15, config.getFieldWidth(), mainPanel);
        JLabel widthLabel = createLabelForSlider(widthSlider);
        mainPanel.add(widthLabel);

        addLabel(mainPanel, "Field Height (No of cells):");
        JSlider heightSlider = createSlider(15, 30, config.getFieldHeight(), mainPanel);
        JLabel heightLabel = createLabelForSlider(heightSlider);
        mainPanel.add(heightLabel);

        addLabel(mainPanel, "Game Level:");
        JSlider gameLevelSlider = createSlider(1, 10, config.getInitLevel(), mainPanel);
        JLabel gameLevelLabel = createLabelForSlider(gameLevelSlider);
        mainPanel.add(gameLevelLabel);

        // Add checkboxes with associated labels
        addLabel(mainPanel, "Music (On/Off):");
        JCheckBox musicCheckBox = createCheckBox(mainPanel, "Music (On/Off):");
        musicCheckBox.setSelected(config.isMusicOn());

        addLabel(mainPanel, "Sound Effects (On/Off):");
        JCheckBox soundEffectsCheckBox = createCheckBox(mainPanel, "Sound Effects (On/Off):");
        soundEffectsCheckBox.setSelected(config.isSoundOn());

        addLabel(mainPanel, "Extend Mode (On/Off):");
        JCheckBox extendModeCheckBox = createCheckBox(mainPanel, "Extend Mode (On/Off):");
        extendModeCheckBox.setSelected(config.isExtendMode());

        // Player One Type with radio buttons
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

        humanButton1.setSelected(true); // Default selection for Player One
        JLabel playerOneLabel = new JLabel("Selected: Human");

        // Action listener for Player One selection
        ActionListener playerOneListener = e -> {
            String selectedText = ((JRadioButton) e.getSource()).getText();
            playerOneLabel.setText("Selected: " + selectedText);
            if (selectedText.equals("Human")) {
                config.setPlayerOneType(0);
            } else if (selectedText.equals("AI")) {
                config.setPlayerOneType(1);
            } else if (selectedText.equals("External")) {
                config.setPlayerOneType(2);
            }
        };

        humanButton1.addActionListener(playerOneListener);
        aiButton1.addActionListener(playerOneListener);
        externalButton1.addActionListener(playerOneListener);

        playerOneTypePanel.add(humanButton1);
        playerOneTypePanel.add(aiButton1);
        playerOneTypePanel.add(externalButton1);

        mainPanel.add(playerOneTypePanel);
        mainPanel.add(playerOneLabel);

        // Player Two Type with radio buttons
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

        // Set the initial selection for Player Two based on Extend Mode
        playerTwoTypePanel.setVisible(config.isExtendMode());
        if (config.isExtendMode()) {
            humanButton2.setSelected(true); // Default to Human if Extend Mode is enabled
        }

        JLabel playerTwoLabel = new JLabel("Selected: Human");

        // Action listener for Player Two selection
        ActionListener playerTwoListener = e -> {
            String selectedText = ((JRadioButton) e.getSource()).getText();
            playerTwoLabel.setText("Selected: " + selectedText);
            if (selectedText.equals("Human")) {
                config.setPlayerTwoType(0);
            } else if (selectedText.equals("AI")) {
                config.setPlayerTwoType(1);
            } else if (selectedText.equals("External")) {
                config.setPlayerTwoType(2);
            }
        };

        humanButton2.addActionListener(playerTwoListener);
        aiButton2.addActionListener(playerTwoListener);
        externalButton2.addActionListener(playerTwoListener);

        playerTwoTypePanel.add(humanButton2);
        playerTwoTypePanel.add(aiButton2);
        playerTwoTypePanel.add(externalButton2);

        mainPanel.add(playerTwoTypePanel);
        mainPanel.add(playerTwoLabel);

        // Add ActionListener to Extend Mode checkbox
        extendModeCheckBox.addActionListener(e -> {
            boolean isSelected = extendModeCheckBox.isSelected();
            playerTwoTypePanel.setVisible(isSelected);
            playerTwoLabel.setVisible(isSelected);
            config.setExtendMode(isSelected);
            if (isSelected) {
                humanButton2.setSelected(true); // Default to Human for Player Two
                config.setPlayerTwoType(0); // Set Player Two type to Human
            }
            // Refresh the panel to ensure visibility changes are applied
            mainPanel.revalidate();
            mainPanel.repaint();
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
        slider.addChangeListener(e -> {
            label.setText(" " + slider.getValue());
            if (slider.getValueIsAdjusting()) {
                return; // Prevent updating while adjusting
            }
            if (slider.getMinimum() == 5) {
                MetaConfig.getInstance().setFieldWidth(slider.getValue());
            } else if (slider.getMinimum() == 15) {
                MetaConfig.getInstance().setFieldHeight(slider.getValue());
            } else {
                MetaConfig.getInstance().setInitLevel(slider.getValue());
            }
        });
        return label;
    }

    private JCheckBox createCheckBox(JPanel panel, String labelText) {
        JCheckBox checkBox = new JCheckBox();
        boolean isChecked = (labelText.contains("Music") && MetaConfig.getInstance().isMusicOn()) ||
                (labelText.contains("Sound Effects") && MetaConfig.getInstance().isSoundOn()) ||
                (labelText.contains("Extend Mode") && MetaConfig.getInstance().isExtendMode());

        checkBox.setSelected(isChecked);
        JLabel label = new JLabel(isChecked ? "On" : "Off");

        checkBox.addActionListener(e -> {
            label.setText(checkBox.isSelected() ? "On" : "Off");
            if (labelText.contains("Music")) {
                MetaConfig.getInstance().setMusicOn(checkBox.isSelected());
                Game.SoundHandler.setMusicOn(checkBox.isSelected());
            } else if (labelText.contains("Sound Effects")) {
                MetaConfig.getInstance().setSoundOn(checkBox.isSelected());
                Game.SoundHandler.setSoundOn(checkBox.isSelected());
            } else if (labelText.contains("Extend Mode")) {
                MetaConfig.getInstance().setExtendMode(checkBox.isSelected());
            }
        });

        panel.add(checkBox);
        panel.add(label);
        return checkBox;
    }
}
package com.seahorse.view;

import com.seahorse.utils.Sound_main;
import java.awt.*;
import javax.swing.*;

public class optionsPage extends JPanel {
    private Sound_main bgrMusic;
    private JComboBox soundComboBox;
    private JComboBox<String> themeComboBox;
    private JComboBox<String> clockComboBox;
    private MainMenu parent;

    public optionsPage(MainMenu parent, Sound_main bgrMusic) {
        this.parent = parent;
        this.bgrMusic = bgrMusic;

        setOpaque(false);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("SETTINGS", JLabel.CENTER);
        titleLabel.setFont(new Font("Cinzel", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridy++;
        themeComboBox = new JComboBox<>(new String[]{"Default", "Dark", "Light"});
        themeComboBox.setFont(new Font("Cinzel", Font.PLAIN, 14));
        themeComboBox.setBackground(Color.LIGHT_GRAY);
        themeComboBox.setPreferredSize(new Dimension(200, 30));
        JPanel themePanel = createComboBox("Theme:", new String[]{"Default", "Dark", "Light"});
        themePanel.add(themeComboBox, BorderLayout.EAST);
        add(themePanel, gbc);

        gbc.gridy++;
        soundComboBox = new JComboBox<>(new String[]{"On", "Off"});
        soundComboBox.setFont(new Font("Cinzel", Font.PLAIN, 14));
        soundComboBox.setBackground(Color.LIGHT_GRAY);
        soundComboBox.setPreferredSize(new Dimension(200, 30));
        soundComboBox.addActionListener(e -> handleSoundOptions());

        JPanel soundPanel = new JPanel(new BorderLayout());
        soundPanel.setOpaque(false);

        JLabel soundLabel = new JLabel("Sound Effects:");
        soundLabel.setFont(new Font("Cinzel", Font.BOLD, 16));
        soundLabel.setForeground(Color.WHITE);

        soundPanel.add(soundLabel, BorderLayout.WEST);
        soundPanel.add(soundComboBox, BorderLayout.EAST);
        add(soundPanel, gbc);

        gbc.gridy++;
        clockComboBox = new JComboBox<>(new String[]{"15 minutes", "30 minutes", "1 hour"});
        clockComboBox.setFont(new Font("Cinzel", Font.PLAIN, 14));
        clockComboBox.setBackground(Color.LIGHT_GRAY);
        clockComboBox.setPreferredSize(new Dimension(200, 30));
        JPanel clockPanel = createComboBox("Clock:", new String[]{"15 minutes", "30 minutes", "1 hour"});
        clockPanel.add(clockComboBox, BorderLayout.EAST);
        add(clockPanel, gbc);

        gbc.gridy++;
        JButton saveButton = createButton("Save", new Color(144, 238, 144));
        saveButton.addActionListener(e -> {
            String selectedTheme = (String) themeComboBox.getSelectedItem();
            String selectedClock = (String) clockComboBox.getSelectedItem();
            String selectedSound = (String) soundComboBox.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Settings saved:\nTheme: " + selectedTheme + "\nSound: " + selectedSound + "\nClock: " + selectedClock);
        });
        add(saveButton, gbc);

        gbc.gridy++;
        JButton backButton = createButton("Back", Color.DARK_GRAY);
        backButton.addActionListener(e -> parent.showMainMenu());
        add(backButton, gbc);
    }

    private JPanel createComboBox(String label, String[] options) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Cinzel", Font.BOLD, 16));
        lbl.setForeground(Color.WHITE);

        JComboBox<String> comboBox = new JComboBox<>(options);
        if(label.equals("Sound effects: ")){
            soundComboBox = comboBox;
            soundComboBox.addActionListener(e -> handleSoundOptions()); 
        }

        comboBox.setFont(new Font("Cinzel", Font.PLAIN, 14));
        comboBox.setBackground(Color.LIGHT_GRAY);
        comboBox.setPreferredSize(new Dimension(200, 30));

        panel.add(lbl, BorderLayout.WEST);
        panel.add(comboBox, BorderLayout.EAST);
        return panel;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Cinzel", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }

    private void handleSoundOptions() {
        String selectedOption = (String) soundComboBox.getSelectedItem();
        if ("On".equals(selectedOption)) {
            bgrMusic.playLoop();
        } else {
            bgrMusic.stop();
        }
    }
}
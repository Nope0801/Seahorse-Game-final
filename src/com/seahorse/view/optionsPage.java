package com.seahorse.view;

import com.seahorse.model.GameSetting;
import com.seahorse.utils.Sound_main;
import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class optionsPage extends JPanel {
    private MainMenu parent;
    private Sound_main bgrMusic;
    private JComboBox<String> soundComboBox;
    private JComboBox<String> resolutionComboBox;
    private JComboBox<String> backgroundComboBox;
    private final String[] backgrounds = {
            "One_fish",
            "Many_fish",
            "No_fish"
    };

    private final String[] resolutions = {
            "1280 x 720",
            "1366 x 768",
            "1600 x 900",
    };

    public optionsPage(MainMenu parent, Sound_main bgrMusic) {
        this.parent = parent;
        this.bgrMusic = bgrMusic;

        setOpaque(false);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("OPTIONS", JLabel.CENTER);
        titleLabel.setFont(new Font("Pixel Intv", Font.BOLD, 36));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 0));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // resolution
        gbc.gridy++;
        JLabel resolutionLabel = new JLabel("Resolution:");
        resolutionLabel.setFont(new Font("Pixel Intv", Font.PLAIN, 16));
        resolutionLabel.setForeground(Color.WHITE);

        resolutionComboBox = new JComboBox<>(resolutions);
        resolutionComboBox.setFont(new Font("Pixel Intv", Font.PLAIN, 14));
        resolutionComboBox.setBackground(Color.LIGHT_GRAY);
        String currentResolution = parent.getWidth() + " x " + parent.getHeight();
        resolutionComboBox.setSelectedItem(currentResolution);
        resolutionComboBox.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;

        JPanel resolutionPanel = new JPanel(new BorderLayout());
        resolutionPanel.setOpaque(false);

        resolutionPanel.add(resolutionLabel, BorderLayout.WEST);
        resolutionPanel.add(resolutionComboBox, BorderLayout.EAST);
        add(resolutionPanel, gbc);

        //theme
        gbc.gridy++;
        JLabel themeLabel = new JLabel("Theme: ");
        themeLabel.setFont(new Font("Pixel Intv", Font.PLAIN, 16));
        themeLabel.setForeground(Color.WHITE);

        backgroundComboBox = new JComboBox<>(backgrounds);
        backgroundComboBox.setFont(new Font("Pixel Intv", Font.PLAIN, 14));
        backgroundComboBox.setBackground(Color.LIGHT_GRAY);
        backgroundComboBox.setSelectedIndex(0);
        backgroundComboBox.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;

        JPanel themePanel = new JPanel(new BorderLayout());
        themePanel.setOpaque(false);
        themePanel.add(themeLabel, BorderLayout.WEST);
        themePanel.add(backgroundComboBox, BorderLayout.EAST);
        add(themePanel, gbc);

        // sound
        gbc.gridy++;
        soundComboBox = new JComboBox<>(new String[]{"On", "Off"});
        soundComboBox.setFont(new Font("Pixel Intv", Font.PLAIN, 14));
        soundComboBox.setBackground(Color.LIGHT_GRAY);
        soundComboBox.setPreferredSize(new Dimension(200, 30));

        JPanel soundPanel = new JPanel(new BorderLayout());
        soundPanel.setOpaque(false);

        JLabel soundLabel = new JLabel("Sound Effects:");
        soundLabel.setFont(new Font("Pixel Intv", Font.PLAIN, 16));
        soundLabel.setForeground(Color.WHITE);

        soundPanel.add(soundLabel, BorderLayout.WEST);
        soundPanel.add(soundComboBox, BorderLayout.EAST);
        add(soundPanel, gbc);

        //save
        gbc.gridy++;
        URL save_button = getClass().getResource("/resources/sprites/Button/save_button_pixel.png");
        JButton saveButton = createButton("Save", new Color(144, 238, 144));
        Image img = Toolkit.getDefaultToolkit().getImage(save_button);
        ImageIcon icon = new ImageIcon(img.getScaledInstance(30, 25, Image.SCALE_SMOOTH));
        saveButton.setIcon(icon);
        saveButton.addActionListener(e -> {
            String selectedResolution = (String) resolutionComboBox.getSelectedItem();
            String selectedSound = (String) soundComboBox.getSelectedItem();
            int selectedBackgroundIndex = backgroundComboBox.getSelectedIndex();
            handleSoundOptions();
            applyResolution(selectedResolution);
            if(parent != null){
                parent.changeBackground(selectedBackgroundIndex);
            }
            JOptionPane.showMessageDialog(this, 
                "Settings Applied: \nResolution: " + selectedResolution + 
                "\nSound: " + selectedSound + 
                "\nTheme: " + backgrounds[selectedBackgroundIndex],
                "Options Saved",
                JOptionPane.INFORMATION_MESSAGE);
        });
        add(saveButton, gbc);

        //back
        gbc.gridy++;
        URL back_icon_pixel = getClass().getResource("/resources/sprites/Button/arrow_left_pixel.png");
        JButton backButton = createButton("Back", Color.DARK_GRAY);
        Image icon_button = Toolkit.getDefaultToolkit().createImage(back_icon_pixel);
        Image scaled = icon_button.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        backButton.setIcon(new ImageIcon(scaled));
        backButton.addActionListener(e -> parent.showMainMenu());
        add(backButton, gbc);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Pixel Intv", Font.BOLD, 18));
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

    private void applyResolution(String resolution) {
        if(resolution == null || parent == null){
            return;
        }

        try {
            String[] parts = resolution.split(" x ");
            if (parts.length == 2) {
                int width = Integer.parseInt(parts[0].trim());
                int height = Integer.parseInt(parts[1].trim());
                GameSetting.screenWidth = width;
                GameSetting.screenHeight = height;
                parent.setSize(width, height);
                parent.setLocationRelativeTo(null);
            }
            else{
                System.out.println("Invalid resolution format: " + resolution);
            }
        } catch (NumberFormatException e) {
        }
    }
}
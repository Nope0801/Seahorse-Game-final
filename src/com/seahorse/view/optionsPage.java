package com.seahorse.view;

import javax.swing.*;
import java.awt.*;

public class optionsPage extends JPanel {
    private MainMenu parent;

    public optionsPage(MainMenu parent) {
        this.parent = parent;
        setOpaque(false); // Giữ nền trong suốt
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
        add(createComboBox("Theme:", new String[]{"Classic", "Dark", "Wood"}), gbc);

        gbc.gridy++;
        add(createComboBox("Sound Effects:", new String[]{"On", "Off"}), gbc);

        gbc.gridy++;
        add(createComboBox("Clock:", new String[]{"1 minute", "5 minutes", "10 minutes"}), gbc);

        gbc.gridy++;
        add(createButton("Save", new Color(144, 238, 144)), gbc);

        gbc.gridy++;
        JButton backButton = createButton("Back", Color.DARK_GRAY);
        backButton.addActionListener(e -> parent.showMainMenu()); // Quay lại menu chính
        add(backButton, gbc);
    }

    private JPanel createComboBox(String label, String[] options) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Cinzel", Font.BOLD, 16));
        lbl.setForeground(Color.WHITE);

        JComboBox<String> comboBox = new JComboBox<>(options);
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
}

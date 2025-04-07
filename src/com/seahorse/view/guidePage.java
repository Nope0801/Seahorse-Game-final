package com.seahorse.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class guidePage extends JPanel {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private HashMap<String, JLabel> guideImages = new HashMap<>();

    public guidePage(MainMenu mainMenu) {
        this.setLayout(new BorderLayout());

        // Menu 6 phần
        JPanel buttonPanel = new JPanel(new GridLayout(1, 6, 10, 0));
        String[] parts = {"Phần 1", "Phần 2", "Phần 3", "Phần 4", "Phần 5", "Phần 6"};

        for (int i = 0; i < parts.length; i++) {
            JButton btn = new JButton(parts[i]);
            int index = i + 1;
            btn.addActionListener(e -> showPart("part" + index));
            buttonPanel.add(btn);
        }

        // Panel nội dung chính
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Load nội dung ảnh/video từng phần
        for (int i = 1; i <= 6; i++) {
            JLabel label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER);

            // Load ảnh hoặc gif vào ImageIcon
            ImageIcon icon = new ImageIcon("src/resources/guide_assets/part" + i + ".gif");
            label.setIcon(icon);

            guideImages.put("part" + i, label);
            contentPanel.add(label, "part" + i);
        }

        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    private void showPart(String partName) {
        cardLayout.show(contentPanel, partName);
    }
}

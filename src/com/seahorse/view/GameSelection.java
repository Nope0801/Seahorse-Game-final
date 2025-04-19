package com.seahorse.view;

import com.seahorse.utils.Sound_main;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GameSelection extends JPanel {

    private MainMenu parent;
    private Sound_main bgrMusic;

    public GameSelection(MainMenu parent) { 
        this.parent = parent;
        this.bgrMusic = parent.bgrMusic;

        setOpaque(false);
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(80, 150, 50, 150));

        // Title
        JLabel titleLabel = new JLabel("Selection", JLabel.CENTER);
        titleLabel.setFont(new Font("Pixel Intv", Font.BOLD, 40));
        titleLabel.setForeground(Color.RED);
        add(titleLabel, BorderLayout.NORTH);

        // --- Bottom Panel for Buttons ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        buttonPanel.setOpaque(false);

        JButton startButton = createStyledButton("START GAME");
        startButton.addActionListener(e -> startGameAction());

        JButton backButton = createStyledButton("BACK TO MENU");
        backButton.addActionListener(e -> parent.showMainMenu());

        buttonPanel.add(startButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Pixel Intv", Font.BOLD, 20)); 
        button.setPreferredSize(new Dimension(250, 50)); 
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 100, 0));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 150, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 100, 0));
            }
        });
        return button;
    }

    private void startGameAction() {
        this.bgrMusic.stop();
        this.parent.dispose();
        new GameFrame();
    }
}
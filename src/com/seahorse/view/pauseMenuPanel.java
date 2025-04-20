package com.seahorse.view;

import com.seahorse.controller.GameController;
import com.seahorse.controller.GameThread;
import java.awt.*;
import javax.swing.*;

public class pauseMenuPanel extends JPanel {

    private GameThread gameThread;
    private GameController gameController;
    private GameFrame gameFrame;

    public pauseMenuPanel(GameThread gameThread, GameController gameController, GameFrame gameFrame) {
        this.gameThread = gameThread;
        this.gameController = gameController;
        this.gameFrame = gameFrame;

        setOpaque(false);
        setBackground(new Color(128, 128, 128, 150));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 50, 15, 50);

        //Tiêu đề
        JLabel titleLabel = new JLabel("PAUSED", JLabel.CENTER);
        titleLabel.setFont(new Font("Pixel Intv", Font.BOLD, 48));
        titleLabel.setForeground(Color.YELLOW);
        add(titleLabel, gbc);

        //Các nút bấm
        JButton continueButton = createStyledButton("CONTINUE");
        continueButton.addActionListener(e -> gameThread.resumeGame());
        add(continueButton, gbc);

        JButton saveGameButton = createStyledButton("SAVE GAME");
        saveGameButton.addActionListener(e -> {
            gameController.saveGame();
            gameFrame.returnToMainMenu();
        });
        add(saveGameButton, gbc);

        JButton returnMenuButton = createStyledButton("RETURN TO MENU");
        returnMenuButton.addActionListener(e -> gameFrame.returnToMainMenu());
        add(returnMenuButton, gbc);

        setVisible(false);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Pixel Intv", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(50, 50, 150));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(300, 55));
        return button;
    }
}
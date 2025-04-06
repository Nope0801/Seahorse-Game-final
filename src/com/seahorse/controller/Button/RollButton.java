package com.seahorse.controller.Button;

import com.seahorse.controller.GameController;
import com.seahorse.utils.ImageFromPath;
import com.seahorse.utils.PaintComponent;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.Timer;

public class RollButton implements PaintComponent{
    private JButton button;
    private int x, y;
    private int width, height;

    private BufferedImage unpressImg;
    private BufferedImage pressedImg;
    private BufferedImage currentImg;

    private Timer rollDiceTimer;

    public RollButton(int x, int y, GameController gameController) {
        PaintComponent.AddPaint(this);
        this.x = x;
        this.y = y;

        unpressImg = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Button/roll_button.png");
        pressedImg = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Button/roll_button_pressed.png");

        width = 200;
        height = 80;

        button = new JButton();
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBorder(null);
        
        rollDiceTimer = new Timer(10, (ActionEvent e) -> {
            gameController.RollDice();
        });

        currentImg = unpressImg;
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                rollDiceTimer.start();
                currentImg = pressedImg;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // currentImg = unpressImg;
                button.setVisible(false);
                rollDiceTimer.stop();
                gameController.StartPlayerTurn();
            }
        });
    }

    @Override
    public void Paint(Graphics g) {
        g.drawImage(currentImg, x, y, width, height, null);
    }
    
    public JButton getButton() {
        return button;
    }

    public void ResetButton() {
        button.setVisible(true);
        currentImg = unpressImg;
    }
}

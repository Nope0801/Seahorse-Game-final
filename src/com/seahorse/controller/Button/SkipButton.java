package com.seahorse.controller.Button;

import com.seahorse.controller.GameController;
import com.seahorse.utils.ImageFromPath;
import com.seahorse.utils.PaintComponent;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JButton;

public class SkipButton implements PaintComponent{
    private JButton button;
    private int x, y;
    private int width, height;

    private BufferedImage unpressImg;
    private BufferedImage pressedImg;
    private BufferedImage currentImg;

    public SkipButton(int x, int y, GameController gameController) {
        PaintComponent.AddPaint(this);
        this.x = x;
        this.y = y;

        unpressImg = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Button/skip_button.png");
        pressedImg = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Button/skip_button_pressed.png");

        width = 200;
        height = 80;
        
        button = new JButton();
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBorder(null);
        currentImg = unpressImg;
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentImg = pressedImg;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentImg = unpressImg;
                gameController.EndPlayerTurn();
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

    public void UnactiveButton() {
        currentImg = pressedImg;
        button.setVisible(false);
    }

    public void ResetButton() {
        currentImg = unpressImg;
        button.setVisible(true);
    }
}

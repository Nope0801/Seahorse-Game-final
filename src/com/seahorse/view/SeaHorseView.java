package com.seahorse.view;

import com.seahorse.utils.PaintComponent;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Timer;

public class SeaHorseView implements PaintComponent{
    private ArrayList<BufferedImage> currentAnimation;
    private int currentAnimationIndex = 0;
    private int x, y;

    private Timer animTimer; 

    public boolean flip = false;

    public SeaHorseView() {
        // PaintComponent.AddPaint(this);

        animTimer = new Timer((100), (ActionEvent e) -> {
            currentAnimationIndex++;
            if (currentAnimationIndex == currentAnimation.size()) {
                currentAnimationIndex = 0;
            }
        });

        animTimer.start();
    }

    @Override
    public void Paint(Graphics g) {
        if (flip) {
            g.drawImage(currentAnimation.get(currentAnimationIndex), x + 64, y-40, -64, 64, null);
        }
        else {
            g.drawImage(currentAnimation.get(currentAnimationIndex), x, y-40, 64, 64, null);
        }
    }    

    public void setCurrentAnimation(ArrayList<BufferedImage> animation) {
        currentAnimation = animation;
        currentAnimationIndex = 0;

    }

    public void setPosition(int _x, int _y) {
        x = _x;
        y = _y;
    }
}

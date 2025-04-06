package com.seahorse.view;

import com.seahorse.model.Board;
import com.seahorse.utils.PaintComponent;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Timer;

public class PlayerView implements PaintComponent{
    private ArrayList<BufferedImage> buttonAnimation;

    private boolean buttonsActive[] = new boolean[5];
    public int buttonPos[][] = new int[5][2];

    private Timer buttonAnimTimer;
    private int currentAnimationIndex = 0;

    public PlayerView(ArrayList<BufferedImage> _buttonAnim) {
        PaintComponent.AddPaint(this);

        buttonAnimation = _buttonAnim;

        buttonAnimTimer = new Timer((100), (ActionEvent e) -> {
            currentAnimationIndex++;
            if (currentAnimationIndex == buttonAnimation.size()) {
                currentAnimationIndex = 0;
            }
        });
    }

    @Override
    public void Paint(Graphics g) {
        for (int i = 0; i < 5; i++) {
            if (buttonsActive[i]) {
                int c[] = Board.changeRelativeCoordinates(buttonPos[i][0], buttonPos[i][1]);
                g.drawImage(buttonAnimation.get(currentAnimationIndex), c[0], c[1] - 64, 64, 64, null);
            }
        }   
    }

    public void ActiveButton(int index, int[] pos) {
        buttonsActive[index] = true;
        buttonPos[index] = pos;
        currentAnimationIndex = 0;
        buttonAnimTimer.start();
    }

    public void DeactiveAllButton() {
        for (int i = 0; i < 5; i++) {
            buttonsActive[i] = false;
        }
        buttonAnimTimer.stop();
    }
}

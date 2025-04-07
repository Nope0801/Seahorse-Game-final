package com.seahorse.view;

import com.seahorse.controller.GameThread;
import com.seahorse.model.GameSetting;
import com.seahorse.utils.ImageFromPath;
import com.seahorse.utils.PaintComponent;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Timer;

public class GameView implements PaintComponent{
    public BufferedImage bgrImg;

    private ArrayList<BufferedImage> bgrAnimation = new ArrayList<>();
    private Timer animationTimer;
    private int currentAnimationIndex = 0;
    public GameView(BufferedImage _bgrImg, GameThread panel) {
        PaintComponent.AddPaint(this);
        bgrImg = _bgrImg;

        // ImageIcon gifIcon = new ImageIcon("src/resources/sprites/Background/mimimoss.gif");
        File files[] = new File("src/resources/sprites/Background/background_animation").listFiles();
        for (File file : files) {
            bgrAnimation.add(ImageFromPath.GetBufferedImageFromPath(file.getPath()));
        }
        Arrays.sort(files, (f1, f2) -> f1.getName().compareToIgnoreCase(f2.getName()));

        animationTimer = new Timer((100), (ActionEvent e) -> {
            bgrImg = bgrAnimation.get(currentAnimationIndex);
            currentAnimationIndex++;
            if (currentAnimationIndex == bgrAnimation.size()) {
                currentAnimationIndex = 0;
            }
        });

        animationTimer.start();
    }

    @Override
    public void Paint(Graphics g) {
        //DRAW BGR
        g.drawImage(bgrImg, 0, 0, GameSetting.screenWidth, GameSetting.screenHeight, null);
    }
    
}

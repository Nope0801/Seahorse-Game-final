package com.seahorse.view;

import com.seahorse.controller.GameThread;
import com.seahorse.model.Game;
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
    Game game;

    public BufferedImage bgrImg;

    private ArrayList<BufferedImage> bgrAnimation = new ArrayList<>();
    private Timer animationTimer;
    private int currentAnimationIndex = 0;

    private ArrayList<Integer> activeNumber = new ArrayList<>();
    private BufferedImage activeImg[] = new BufferedImage[4];
    private BufferedImage unactiveImg[] = new BufferedImage[4];
    private int startPos[][] = {
        {0, 0},
        {GameSetting.screenWidth - (64 * 4) - 16, 0},
        {0, GameSetting.screenHeight - 64 - 36},
        {GameSetting.screenWidth - (64 * 4) - 16, GameSetting.screenHeight - 64 - 36}
    };

    public GameView(BufferedImage _bgrImg, GameThread panel, Game g) {
        PaintComponent.AddPaint(this);
        bgrImg = _bgrImg;
        game = g;

        for (int i = 0; i < 4; i++) {
            activeImg[i] = ImageFromPath.GetBufferedImageFromPath("src/resources/sprites/Avatar/active_" + i + ".png");
            unactiveImg[i] = ImageFromPath.GetBufferedImageFromPath("src/resources/sprites/Avatar/unactive_" + i + ".png");
        }

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
        
        startPos = new int[][]{
            {0, 0},
            {GameSetting.screenWidth - (64 * 4) - 16, 0},
            {0, GameSetting.screenHeight - 64 - 36},
            {GameSetting.screenWidth - (64 * 4) - 16, GameSetting.screenHeight - 64 - 36}
        };

        for (int i = 0; i < game.getPlayersNumber(); i++) {
            for (int j = 0; j < 4; j++) {
                if (j < game.getPlayersController().get(i).getSeaHorsesInGoalNumber()) {
                    g.drawImage(activeImg[i], startPos[i][0] + j * 64, startPos[i][1], 64, 64, null);
                }
                else {
                    g.drawImage(unactiveImg[i], startPos[i][0] + j * 64, startPos[i][1], 64, 64, null);
                }

                
            }
        }
    }
    
    public void setActiveNumber(int index, int number) {
        if (activeNumber.size() != 0) {
            activeNumber.set(index, number);
            System.out.println(index + " " + number);
        }
    }
}

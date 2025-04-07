package com.seahorse.model;

import com.seahorse.utils.ImageFromPath;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Dice {
    private Random random;

    private BufferedImage diceBorder[] = new BufferedImage[4];
    private BufferedImage[] diceImages = new BufferedImage[7];

    public Dice() {
        random = new Random();
        
        diceBorder[0] = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Dices/red_border.png");
        diceBorder[1] = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Dices/blue_border.png");
        diceBorder[2] = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Dices/green_border.png");
        diceBorder[3] = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Dices/yellow_border.png");

        for (int i = 0; i < 7; i++) {
            diceImages[i] = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Dices/dice_" + i + ".png");
        }
    }

    public Random getRandom() {
        return random;
    }

    public BufferedImage getDiceBorder(int index) {
        return diceBorder[index];
    }

    public BufferedImage getDiceImages(int i) {
        return diceImages[i];
    }
}
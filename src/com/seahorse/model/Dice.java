package com.seahorse.model;

import com.seahorse.utils.ImageFromPath;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Dice {
    private Random random;

    private BufferedImage diceBorder;
    private BufferedImage[] diceImages = new BufferedImage[7];

    public Dice() {
        random = new Random();
        
        diceBorder = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Dices/dice_border_1.png");

        for (int i = 0; i < 7; i++) {
            diceImages[i] = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Dices/dice_" + i + ".png");
        }
    }

    public Random getRandom() {
        return random;
    }

    public BufferedImage getDiceBorder() {
        return diceBorder;
    }

    public BufferedImage getDiceImages(int i) {
        return diceImages[i];
    }
}
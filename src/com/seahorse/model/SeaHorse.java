package com.seahorse.model;

import com.seahorse.utils.ImageFromPath;
import com.seahorse.utils.SeaHorseState;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SeaHorse {
    private int relativeX, relativeY;
    private int x, y;
    private int x1, y1;
    private int x2, y2;

    private int stepLeft;

    private SeaHorseState state;

    private int currentIndexOnLinePixels;
    private List<int[]> linePixels;
    private int pixelSpeed;

    private ArrayList<BufferedImage> seaHorseMoveAnimation = new ArrayList<>();
    private ArrayList<BufferedImage> seaHorseIdleAnimation = new ArrayList<>();
    private File seaHorseMoveAnimationFolder[] = new File("src/resources/sprites/SeaHorse/default/move_animation").listFiles();
    private File seaHorseIdleAnimationFolder[] = new File("src/resources/sprites/SeaHorse/default/idle_animation").listFiles();


    public SeaHorse() {
        state = SeaHorseState.InStable;

        for (File file : seaHorseMoveAnimationFolder) {
            seaHorseMoveAnimation.add(ImageFromPath.GetBufferedImageFromPath(file.getPath()));
        }
        for (File file : seaHorseIdleAnimationFolder) {
            seaHorseIdleAnimation.add(ImageFromPath.GetBufferedImageFromPath(file.getPath()));
        }
    }

    public int getRelativeX() {
        return relativeX;
    }

    public void setRelativeX(int relativeX) {
        this.relativeX = relativeX;
    }

    public int getRelativeY() {
        return relativeY;
    }

    public void setRelativeY(int relativeY) {
        this.relativeY = relativeY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public SeaHorseState getState() {
        return state;
    }

    public void setState(SeaHorseState state) {
        this.state = state;
    }

    public ArrayList<BufferedImage> getSeaHorseMoveAnimation() {
        return seaHorseMoveAnimation;
    }

    public ArrayList<BufferedImage> getSeaHorseIdleAnimation() {
        return seaHorseIdleAnimation;
    }

    public int getStepLeft() {
        return stepLeft;
    }

    public void setStepLeft(int stepLeft) {
        this.stepLeft = stepLeft;
    }

    public List<int[]> getLinePixels() {
        return linePixels;
    }

    public void setLinePixels(List<int[]> linePixels) {
        this.linePixels = linePixels;
        pixelSpeed = linePixels.size() / (GameSetting.maxFPS * 1);
        if (pixelSpeed == 0) pixelSpeed = 1;
    }

    public int getCurrentIndexOnLinePixels() {
        if (currentIndexOnLinePixels >= linePixels.size()) currentIndexOnLinePixels = linePixels.size() - 1;
        return currentIndexOnLinePixels;
    }

    public void setCurrentIndexOnLinePixels(int currentIndexOnLinePixels) {
        this.currentIndexOnLinePixels = currentIndexOnLinePixels;
    }

    public int getPixelSpeed() {
        return pixelSpeed;
    }
    

}

package com.seahorse.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.seahorse.utils.ImageFromPath;

public class ImagesReader {
    private BufferedImage tileImage1;
    private BufferedImage tileImage2;
    private BufferedImage blueflat;
    private BufferedImage blue;
    private BufferedImage greenflat;
    private BufferedImage green;
    private BufferedImage redflat;
    private BufferedImage red;
    private BufferedImage yellowflat;
    private BufferedImage yellow;
    private BufferedImage bluecircle;
    private BufferedImage greencircle;
    private BufferedImage redcircle;
    private BufferedImage yellowcircle;
    private BufferedImage B31;
    private BufferedImage B30;
    private BufferedImage B29;
    
    private BufferedImage background1;

    public ImagesReader(){
        this.tileImage1 = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_white.png");
        this.tileImage2 = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_white_flat.png");
        this.blueflat = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_blue_flat.png");
        this.blue = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_blue.png");
        this.greenflat = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_green_flat.png");
        this.green = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_green.png");
        this.redflat = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_red_flat.png");
        this.red = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_red.png");
        this.yellowflat = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_yellow_flat.png");
        this.yellow = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_yellow.png");
        this.bluecircle = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_blue_circle_flat.png");
        this.greencircle = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_green_circle_flat.png");
        this.redcircle = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_red_circle_flat.png");
        this.yellowcircle = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_yellow_circle_flat.png");
        // this.B30 = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_30.png");
        this.B31 = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_31.png");
        this.B29 = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Land/blocks_29.png");      
    }

    public BufferedImage getTileImage1() {
        return tileImage1;
    }

    public BufferedImage getTileImage2() {
        return tileImage2;
    }

    public BufferedImage getBlueflat() {
        return blueflat;
    }

    public BufferedImage getBlue() {
        return blue;
    }

    public BufferedImage getGreenflat() {
        return greenflat;
    }

    public BufferedImage getGreen() {
        return green;
    }

    public BufferedImage getRedflat() {
        return redflat;
    }

    public BufferedImage getRed() {
        return red;
    }

    public BufferedImage getYellowflat() {
        return yellowflat;
    }

    public BufferedImage getYellow() {
        return yellow;
    }

    public BufferedImage getBluecircle() {
        return bluecircle;
    }

    public BufferedImage getGreencircle() {
        return greencircle;
    }

    public BufferedImage getRedcircle() {
        return redcircle;
    }

    public BufferedImage getYellowcircle() {
        return yellowcircle;
    }

    public BufferedImage getB31() {
        return B31;
    }

    public BufferedImage getB30() {
        return B30;
    }

    public BufferedImage getB29() {
        return B29;
    }

}

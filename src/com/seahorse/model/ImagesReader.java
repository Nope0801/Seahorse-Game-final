package com.seahorse.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImagesReader {
    private BufferedImage tileImage;
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
    public ImagesReader(){
        try{
            this.tileImage = ImageIO.read(new File("../../resources/sprites/Land/blocks_white.png"));
            this.tileImage2 = ImageIO.read(new File("../../resources/sprites/Land/blocks_white.png"));
            this.blueflat = ImageIO.read(new File("../../resources/sprites/Land/blocks_white.png"));
            this.blue = ImageIO.read(new File("../../resources/sprites/Land/blocks_white.png"));
            this.greenflat = ImageIO.read(new File("../../resources/sprites/Land/blocks_white.png"));
            this.green = ImageIO.read(new File("../../resources/sprites/Land/blocks_white.png"));
            this.redflat = ImageIO.read(new File("../../resources/sprites/Land/blocks_white.png"));
            this.red = ImageIO.read(new File("../../resources/sprites/Land/blocks_white.png"));
            this.yellowflat = ImageIO.read(new File("../../resources/sprites/Land/blocks_white.png"));
            this.yellow = ImageIO.read(new File("../../resources/sprites/Land/blocks_white.png"));
            this.bluecircle = ImageIO.read(new File("../../resources/sprites/Land/blocks_white.png"));
            this.greencircle = ImageIO.read(new File("../../resources/sprites/Land/blocks_white.png"));
            this.redcircle = ImageIO.read(new File("../../resources/sprites/Land/blocks_white.png"));
            this.yellowcircle = ImageIO.read(new File("../../resources/sprites/Land/blocks_white.png"));
        }catch(IOException e){
            System.out.println("Lỗi load hình ảnh: " + e.getMessage());
        }      
    }
    public BufferedImage getTileImage() {
        return tileImage;
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

}

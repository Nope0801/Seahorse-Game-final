package com.seahorse.model;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Board {
    private BufferedImage[][] map;

    private int TILE_WIDTH = 64;
    private int TILE_HEIGHT = 32;

    //kich thuoc ban co
    private int BOARD_SIZE = 15;

    //toa do ban co
    private int startX = 651;
    private int startY = 100;

    //load hinh anh
    private ImagesReader images;

    public Board(){
        map = new BufferedImage[BOARD_SIZE][BOARD_SIZE];
        images = new ImagesReader();
    }
    public void loadMapFromFile(String filename){
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int y = 0;
            while ((line = br.readLine()) != null && y < BOARD_SIZE) {
                String[] tiles = line.trim().split("\\s+");
                for (int x = 0; x < BOARD_SIZE && x < tiles.length; x++) {
                    map[y][x] = getImageFromTileType(tiles[x]);
                }
                y++;
            }
        } catch (IOException e) {
            System.out.println("Lỗi không load được file");
        }
    }

    private BufferedImage getImageFromTileType(String type) {
        switch (type) {
            case "T1": return images.getTileImage();
            case "T2": return images.getTileImage2();
            case "BF": return images.getBlueflat();
            case "B": return images.getBlue();
            case "G": return images.getGreen();
            case "GF": return images.getGreenflat();
            case "RF": return images.getRedflat();
            case "R": return images.getRed();
            case "Y": return images.getYellow();
            case "YF": return images.getYellowflat();
            case "BC": return images.getBluecircle();
            case "YC": return images.getYellowcircle();
            case "RC": return images.getRedcircle();
            case "GC": return images.getGreencircle();
            default: return null;
        }
    }
    public BufferedImage getTile(int x, int y){
        if (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE) {
            return map[y][x];
        }
        return null;
    }

    
    // }
    // public Board(){
    //     loadImages();
    // }
    
    // private BufferedImage currentImage = ImageIO.read(new File("../../resources/sprites/Land/blocks_white.png"));
    // public void Paint(Graphics g){
    //     Graphics2D g2d = (Graphics2D) g;
    //     for(int row = 0; row < BOARD_SIZE ; row++){
    //         for(int  col = 0; col < BOARD_SIZE; col++){
    //             int x = startX + (col - row) * TILE_WIDTH/2 ;
    //             int y = startY + (col + row) * TILE_HEIGHT/2 ;
    //             g2d.drawImage(getCurrentImage(), x, y, null);
    //         }
    //     }
    // }

    // public BufferedImage getCurrentImage() {
    //     return currentImage;
    // }

    // public void setCurrentImage(BufferedImage currentImage) {
    //     this.currentImage = currentImage;
    // }

}

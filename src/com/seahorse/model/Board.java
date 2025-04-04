package com.seahorse.model;

import com.seahorse.utils.PaintComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Board implements PaintComponent{
    private BufferedImage[][] map;
    private TileType[][] TilesType;
    
    private enum TileType {
        T1, T2, BF, B, G, GF, RF, R, Y, YF, BC, YC, RC, GC;
    }


    private int TILE_WIDTH = 64;
    private int TILE_HEIGHT = 32;

    // kich thuoc ban co
    private int BOARD_SIZE = 15;

    // toa do ban co
    private int startX = 651;
    private int startY = 100;

    // load hinh anh
    private ImagesReader images;

    public Board() {
        map = new BufferedImage[BOARD_SIZE][BOARD_SIZE];
        images = new ImagesReader();
    }

    public void loadMapFromFile(String filename) {
        InputStream is = getClass().getResourceAsStream(filename);
        if (is == null) {
            System.out.println("Board.java: can't find file " + filename);
            return;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            int y = 0;
            while ((line = br.readLine()) != null && y < BOARD_SIZE) {
                String[] tiles = line.trim().split(" ");
                for (int x = 0; x < BOARD_SIZE && x < tiles.length; x++) {
                    map[y][x] = getImageFromTileType(tiles[x]);
                }
                y++;
            }
        } catch (IOException e) {
            System.out.println("Board.java:" + e.getMessage());
        }
        // try{
        //     InputStream is = getClass().getResourceAsStream(filename);
        //     BufferedReader br = new BufferedReader(is);
        //     String line = br.readLine();
        //     int col = 0, row = 0;
        //     while(col < 15 && row < 15){
        //         String[] tiles = line.split(" ");


        //     }
        // }catch(Exception e){

        // }
    }

    // private enum TileType {
    //     T1, T2, BF, B, G, GF, RF, R, Y, YF, BC, YC, RC, GC;
    // }

    private BufferedImage getImageFromTileType(String type) {
        return switch (type) {
            case "T1" -> images.getTileImage1();
            case "T2" -> images.getTileImage2();
            case "BF" -> images.getBlueflat();
            case "B" -> images.getBlue();
            case "G" -> images.getGreen();
            case "GF" -> images.getGreenflat();
            case "RF" -> images.getRedflat();
            case "R" -> images.getRed();
            case "Y" -> images.getYellow();
            case "YF" -> images.getYellowflat();
            case "BC" -> images.getBluecircle();
            case "YC" -> images.getYellowcircle();
            case "RC" -> images.getRedcircle();
            case "GC" -> images.getGreencircle();
            default -> null;
        };
    }

    public BufferedImage getTile(int x, int y) {
        if (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE) {
            return map[y][x];
        }
        return null;
    }

    @Override
    public void Paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        for(int row = 0; row < BOARD_SIZE; row++){
            for(int col = 0; col < BOARD_SIZE; col++){
                BufferedImage tile = map[row][col];
                if (tile != null) {
                    int x = startX + (col - row) * TILE_WIDTH / 2;
                    int y = startY + (col + row) * TILE_HEIGHT / 2;
                    g2d.drawImage(tile, x, y, null);
                }
            }
        }
    }

    // }
    // public Board(){
    // loadImages();
    // }

    // private BufferedImage currentImage = ImageIO.read(new
    // File("../../resources/sprites/Land/blocks_white.png"));
    // public void Paint(Graphics g){
    // Graphics2D g2d = (Graphics2D) g;
    // for(int row = 0; row < BOARD_SIZE ; row++){
    // for(int col = 0; col < BOARD_SIZE; col++){
    // int x = startX + (col - row) * TILE_WIDTH/2 ;
    // int y = startY + (col + row) * TILE_HEIGHT/2 ;
    // g2d.drawImage(getCurrentImage(), x, y, null);
    // }
    // }
    // }

    // public BufferedImage getCurrentImage() {
    // return currentImage;
    // }

    // public void setCurrentImage(BufferedImage currentImage) {
    // this.currentImage = currentImage;
    // }

}

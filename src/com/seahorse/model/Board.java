package com.seahorse.model;

import com.seahorse.utils.PaintComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class Board implements PaintComponent{
    private BufferedImage[][] map;
    private TileType[][] tilesType;
    public static int specialPath[][] = { { 1, 6 }, { 2, 6 }, { 3, 6 }, { 4, 6 }, { 5, 6 }, { 6, 6 }, { 6, 5 },
            { 6, 4 }, { 6, 3 }, { 6, 2 }, { 6, 1 }, { 6, 0 }, { 8, 1 }, { 8, 2 }, { 8, 3 }, { 8, 4 }, { 8, 5 },
            { 8, 6 }, { 9, 6 }, { 10, 6 }, { 11, 6 }, { 12, 6 }, { 13, 6 }, { 14, 6 }, { 13, 8 }, { 12, 8 }, { 11, 8 },
            { 10, 8 }, { 9, 8 }, { 8, 8 }, { 8, 9 }, { 8, 10 }, { 8, 11 }, { 8, 12 }, { 8, 13 }, { 8, 14 }, { 6, 13 },
            { 6, 12 }, { 6, 11 }, { 6, 10 }, { 6, 9 }, { 6, 8 }, { 5, 8 }, { 4, 8 }, { 3, 8 }, { 2, 8 }, { 1, 8 },
            { 0, 8 } };
    public static int path[][] = {{1, 6}, {2,6}, {3, 6}, {4, 6}, {5, 6}, {6, 6}, {6, 5}, { 6, 4}, {6, 3} , { 6, 2}, {6, 1} , {6, 0}, {7, 0},{8,1},{8,2},{8,3},{8,4},{8,5},{8,6},{9,6},{10,6},{11,6},{12,6},{13,6},{14,6},{14,7},{13,8},{12,8},{11, 8}, {10,8}, {9,8}, {8,8},{8,9},{8,10},{8,11},{8,12},{8,13},{8,14},{7,14},{6,13},{6,12},{6,11},{6,10},{6,9},{6,8},{5,8},{4,8},{3,8},{2,8},{1,8},{0,8},{0,7}};
    public static int winPath[][] = {{7,1, 0},{7,2, 0},{7,3, 0},{7,4, 0},{7,5, 0},{7,6, 0},{1,7, 0},{2,7, 0},{3,7, 0},{4,7, 0},{5,7, 0},{6,7, 0},{7,13,0}, {7,12,0},{7,11,0},{7,10,0},{7,9,0},{7,8,0}, {13,7,0}, {12, 7,0}, {11, 7,0}, {10, 7,0}, {9, 7,0}, {8, 7, 0}};
    public int startStableCoordinates[][][] = {
        {{10,1},{10,4},{13,1},{13,4}},
        {{1,1},{1,4},{4, 1},{4, 4}},
        {{1, 10}, {1, 13}, {4, 10}, {4, 13}},
        {{10,10}, {10,13}, {13,10}, {13,13}},
    };
    public static int deployCoordinates[][] = {
        {8,0},
        {0,6},
        {6,14},
        {14,8}
    };

    public static int deployIndex[] = {13, 52, 39, 26};
    
    public enum TileType {
        T1, T2, BF, B, G, GF, RF, R, Y, YF, BC, YC, RC, GC, B31, B29, B30;
    }


    private static int TILE_WIDTH = 64;
    private static int TILE_HEIGHT = 32;

    // kich thuoc ban co
    private int BOARD_SIZE = 15;

    // toa do ban co
    private static int startX = GameSetting.screenWidth / 2 - 32;
    private static int startY = GameSetting.screenHeight / 2 - 286;

    // load hinh anh
    private ImagesReader images;

    public Board() {
        PaintComponent.AddPaint(this);
        map = new BufferedImage[BOARD_SIZE][BOARD_SIZE];
        tilesType = new TileType[BOARD_SIZE][BOARD_SIZE];
        images = new ImagesReader();
    }

    public void loadMapFromFile(String filename) {
        // InputStream is = getClass().getResourceAsStream(filename);
        InputStream is = this.getClass().getResourceAsStream(filename);
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
                    TileType tileType = parseTileType(tiles[x]);
                    tilesType[x][y] = tileType;
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
    public void resetTileType() {
        loadMapFromFile("./../../../resources/map/map03.txt");
    }
    public BufferedImage getImageFromTileType(String type) {
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
            case "B31" -> images.getB31();
            case "B29" -> images.getB29();
            case "B30" -> images.getB30();
            default -> null;
        };
    }

    public BufferedImage getTileIMG(int x, int y) {
        if (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE) {
            return map[y][x];
        }
        return null;
    }
    
    private TileType parseTileType(String type) {
        try {
            return TileType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Board.java: Không nhận diện được loại ô '" + type + "', đặt thành mặc định.");
        }
        return null;
    }
    public TileType getTileEnum(int x, int y) {
        if (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE) {
            return tilesType[y][x];
        }
        return null;
    }

    @Override
    public void Paint(Graphics g) {
        startX = GameSetting.screenWidth / 2 - 32;
        startY = GameSetting.screenHeight / 2 - 286;
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

    //doi toa do
    public static int[] changeRelativeCoordinates(int row, int col){
        int[] result = new int[2];
        result[0] = startX + (col - row) * TILE_WIDTH / 2;
        result[1] = startY + (col + row) * TILE_HEIGHT / 2;
        return result;
        
    }

    //getnext
    public static int[] getNextTile(int row, int col){
        int[] result = new int[2];
        for (int i = 0; i < deployIndex.length; i++){
            if (row == deployCoordinates[i][0] && col == deployCoordinates[i][1]) {
                if (i == 1) {
                    result[0] = path[0][0];
                    result[1] = path[0][1];
                    return result;
                }
                result[0] = path[deployIndex[i]][0];
                result[1] = path[deployIndex[i]][1];
                return result;
            }
        }

        for (int i = 0; i < path.length; i++) {
            if (row == path[i][0] && col == path[i][1]) {
                if (i + 1 == path.length) {
                    result[0] = path[0][0];
                    result[1] = path[0][1];
                    break;
                }
                result[0] = path[i + 1][0];
                result[1] = path[i + 1][1];
                break;
            }
        }
        return  result;
    }
    public TileType[][] getTilesType() {
        return tilesType;
    }
    public void setTilesType(TileType[][] tilesType) {
        this.tilesType = tilesType;
    }
    public void updateTileImage(int row, int col, BufferedImage newImage) {
        if (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE) {
            map[row][col] = newImage;
            System.out.println("Updated tile image at (" + row + ", " + col + ")");
        } else {
            System.out.println("Invalid coordinates: (" + row + ", " + col + ")");
        }
    }
    public void randomizeTileType() {
        Random random = new Random();
        Random random2 = new Random();
        int randomIndex = random.nextInt(specialPath.length);
        int x = specialPath[randomIndex][0];
        int y = specialPath[randomIndex][1];
        int randomTile = random.nextInt(3) + 1;
        TileType test = null;
        switch (randomTile) {
            case 1:
                test = TileType.B29;
                break;
            case 2:
                test = TileType.B30;
                break;
            case 3:
                test = TileType.B31;
                break;
            default:
                throw new AssertionError();
        }
        // SpecialTilesTypeMap[] specialTiles = SpecialTilesTypeMap.values();
        // SpecialTilesTypeMap randomTileType = specialTiles[random.nextInt(specialTiles.length)];

        tilesType[x][y] = test;

        if (test != null) {
            BufferedImage newImage = getImageFromTileType(test.name());
            updateTileImage(x, y, newImage);
        } else {
            System.err.println("Error: TileType 'test' is null.");
        }

        // System.out.println("Tile at (" + x + ", " + y + ") changed to " +
        // randomTileType);
    }
    public void PaintTile(){
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                if (tilesType[i][j]==TileType.B29||tilesType[i][j]==TileType.B30||tilesType[i][j]==TileType.B31) {
                    
                    BufferedImage newImage = getImageFromTileType(tilesType[i][j].name());
                    updateTileImage(i, j, newImage);
                }
            }
        }
    }
    
    public void setTilesTypeAt(int x, int y, TileType tileType) {
        tilesType[x][y] = tileType;
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

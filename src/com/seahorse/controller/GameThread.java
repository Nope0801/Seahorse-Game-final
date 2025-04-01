package com.seahorse.controller;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;


public class GameThread extends JPanel implements Runnable {
    
    private int maxScreenCol = 42;
    private int maxScreenRow = 24;
    // private int screenHeight = 768;
    // private int screenWidth = 1366;

    public int boardStartX = (maxScreenCol - 15) / 2;
    public int boardStartY = (maxScreenRow - 15) / 2;

    public GameThread() {
        // // // this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        // // this.setBackground(new Color(219, 253, 255));
        // // this.setDoubleBuffered(true);
        this.setLayout(null); //make button free positon in screen 
    }
    // private void loadBackroundImage(){
    //     try {
    //         image = ImageIO.read(new File("../../resources/sprites/Background/bgr_img.jpg"));
    //     } catch (IOException e) {       
    //         System.err.println("Error loading background image: " + e.getMessage());
    //         image = null; 
    //     }
    // }
    @Override
    public void paintComponent(Graphics grp) {
        super.paintComponent(grp);
        Graphics2D grp2D = (Graphics2D)(grp);
        // ImagePanel("./assets/bgr_img.jpg");
        // grp2D.drawImage(image, 0, 0, screenWidth, screenHeight,this);
        // grp.fillRect(2 * 32, 1 * 32, 32, 32);
        // matchManager.Paint(grp);
    }
    Thread gameThread;
    int maxFPS = 60;

    public  void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void run(){
        long timePerFrame = 1000000000 / maxFPS;
        repaint();
        // Start();
        while (gameThread != null) {
            long startFrameTime = System.nanoTime();
            repaint();
            // Update();
            try {
                long remainFrameTime = timePerFrame - (System.nanoTime() - startFrameTime);
                if (remainFrameTime < 0) {
                    remainFrameTime = 0;
                }
                System.out.println("DIT ME TUAN BU CAC TAO");
                // dùng while thay vì sleep để giảm thiểu sai số
                Thread.sleep(remainFrameTime / 1000000);
            } catch (InterruptedException e) {
                
            }
        }
    }
    // public void run(){

    // }
    
}

package com.seahorse.view;

import com.seahorse.model.GameSetting;
import com.seahorse.utils.ImageFromPath;
import com.seahorse.utils.PaintComponent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class WinMenu implements PaintComponent {
    private boolean isEnd = false;
    private int winIndex;
    private BufferedImage winPlayerBanner;
    private BufferedImage winText = ImageFromPath.GetBufferedImageFromPath("src/resources/sprites/WinMenu/winner_banner_nobgr.png");
    private BufferedImage bgr = ImageFromPath.GetBufferedImageFromPath("src/resources/sprites/WinMenu/win_bgr.png");

    public WinMenu(int index) {
        winIndex = index;
        switch (index) {
            case 0:
                winPlayerBanner = ImageFromPath.GetBufferedImageFromPath("src/resources/sprites/WinMenu/red_win_banner-removebg.png");
                break;
            case 1:
                winPlayerBanner = ImageFromPath.GetBufferedImageFromPath("src/resources/sprites/WinMenu/blue_win_banner-removebg.png");
                break;
            case 2:
                winPlayerBanner = ImageFromPath.GetBufferedImageFromPath("src/resources/sprites/WinMenu/green_win_banner-removebg.png");
                break;
            case 3:
                winPlayerBanner = ImageFromPath.GetBufferedImageFromPath("src/resources/sprites/WinMenu/yellow_win_banner-removebg.png");
                break;
                
        }
        PaintComponent.AddPaint(this);
    }

    @Override
    public void Paint(Graphics g) {
        if (!isEnd) return;
        g.drawImage(bgr, 0, 0, GameSetting.screenWidth, GameSetting.screenHeight, null);
        g.drawImage(winText, 0, 20, GameSetting.screenWidth, GameSetting.screenHeight, null);
        g.drawImage(winPlayerBanner, 0, -100, GameSetting.screenWidth, GameSetting.screenHeight, null);
    }
    
    public void setIndex(int index) {
        switch (index) {
            case 0:
                winPlayerBanner = ImageFromPath.GetBufferedImageFromPath("src/resources/sprites/WinMenu/red_win_banner-removebg.png");
                break;
            case 1:
                winPlayerBanner = ImageFromPath.GetBufferedImageFromPath("src/resources/sprites/WinMenu/blue_win_banner-removebg.png");
                break;
            case 2:
                winPlayerBanner = ImageFromPath.GetBufferedImageFromPath("src/resources/sprites/WinMenu/green_win_banner-removebg.png");
                break;
            case 3:
                winPlayerBanner = ImageFromPath.GetBufferedImageFromPath("src/resources/sprites/WinMenu/yellow_win_banner-removebg.png");
                break;
        }
        isEnd = true;
    }
}

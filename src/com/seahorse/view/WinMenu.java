    package com.seahorse.view;

import com.seahorse.model.GameSetting;
import com.seahorse.utils.ImageFromPath;
import com.seahorse.utils.PaintComponent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

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
        if (canShow) {
            g.setFont(new Font("Arial", Font.BOLD, 100));
            g.setColor(Color.WHITE);
            g.drawImage(bgr, 0, GameSetting.screenHeight / 2 - (GameSetting.screenHeight / 3) / 2, GameSetting.screenWidth, GameSetting.screenHeight / 3, null);
            g.drawImage(effectText,0, GameSetting.screenHeight / 2 - (GameSetting.screenHeight / 3) / 2, GameSetting.screenWidth, GameSetting.screenHeight / 3, null);
        }

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

    private boolean canShow = false;
    private BufferedImage effectText;
    public void ShowContext(int i) {
        canShow = true;
        switch (i) {
            case 0:
                effectText = ImageFromPath.GetBufferedImageFromPath("src/resources/sprites/EffectText/bonus_txt.png");
                break;
            case 1:
                effectText = ImageFromPath.GetBufferedImageFromPath("src/resources/sprites/EffectText/fall_txt.png");  
                break;
        }
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                canShow = false;
                timer.cancel();
            }
        };

        timer.schedule(task, 2000);
    }
}

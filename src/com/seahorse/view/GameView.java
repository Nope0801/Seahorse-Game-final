package com.seahorse.view;

import com.seahorse.model.GameSetting;
import com.seahorse.utils.PaintComponent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GameView implements PaintComponent{
    public BufferedImage bgrImg;

    public GameView(BufferedImage _bgrImg) {
        PaintComponent.AddPaint(this);
        bgrImg = _bgrImg;
    }

    @Override
    public void Paint(Graphics g) {
        //DRAW BGR
        g.drawImage(bgrImg, 0, 0, GameSetting.screenWidth, GameSetting.screenHeight, null);
    }
    
}

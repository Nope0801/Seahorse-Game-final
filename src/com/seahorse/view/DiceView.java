package com.seahorse.view;

import com.seahorse.model.GameSetting;
import com.seahorse.utils.PaintComponent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class DiceView implements PaintComponent{
    private BufferedImage diceImage;
    private BufferedImage diceBorder;

    private int x1, y1, x2, y2;
    private int h1, w1, h2, w2;

    public DiceView(BufferedImage _diceBorder) {
        PaintComponent.AddPaint(this);

        diceBorder = _diceBorder;
        
        w1 = h1 = 150;
        w2 = h2 = 110;
        x1 = x2 = GameSetting.screenWidth / 2 - w1 / 2;
        y1 = y2 = GameSetting.screenHeight - w1 - 40;
    }
    
    @Override
    public void Paint(Graphics g) {
        g.drawImage(diceBorder, x1, y1, w1, h1, null);

        g.drawImage(diceImage, x2 + (w1 - w2) / 2, y2 + (w1 - w2) / 2, w2, h2, null);
    }

    public void SetDiceImage(BufferedImage _diceImage) {
        diceImage = _diceImage;
    }
}

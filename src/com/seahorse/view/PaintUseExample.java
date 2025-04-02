package com.seahorse.view;

import com.seahorse.utils.PaintComponent;
import java.awt.Color;
import java.awt.Graphics;

public class PaintUseExample implements PaintComponent{
    public PaintUseExample() {
        PaintComponent.AddPaint(this);// them dong nay vao ham khoi tao
    }

    @Override
    public void Paint(Graphics g) {
        //tat ca nhung thu can ve len man hinh moi frame dung nhu paintComponent
        g.setColor(Color.BLACK);
        g.drawRect(50, 50, 50, 50);
    }
}

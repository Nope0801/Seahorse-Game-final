package com.seahorse.utils;

import com.seahorse.model.PaintData;
import java.awt.Graphics;

public interface PaintComponent {
    static void AddPaint(PaintComponent THIS) {
        PaintData.paintEntities.add(THIS);
    }
    
    public void Paint(Graphics g);
}

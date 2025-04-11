package com.seahorse.view.guide_assets;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class JScrollBar_CustomUI extends BasicScrollBarUI {

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (!scrollbar.isEnabled() || thumbBounds.width > thumbBounds.height) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(255, 255, 255, 120));
        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);

//        g2.dispose();
    }

//    @Override
//    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {}

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }
}

package com.seahorse.view.guide_assets;
import javax.swing.*;
import java.awt.*;

public class JScrollBar_Custom extends JScrollBar {
    public JScrollBar_Custom(int orientation) {
        super(orientation);
        setUI(new JScrollBar_CustomUI());
        setOpaque(false);
    }
}

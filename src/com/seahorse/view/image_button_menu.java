package com.seahorse.view;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class image_button_menu extends JButton {
    private final ImageIcon defaultIcon;
    private final ImageIcon hoverIcon;

    public image_button_menu(String defaultIconPath, String hoverIconPath, int weight, int height) {
        defaultIcon = new ImageIcon(defaultIconPath);
        hoverIcon = new ImageIcon(hoverIconPath);

        setIcon(resizeIcon(defaultIcon, weight, height));
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);

        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(resizeIcon(hoverIcon, weight, height));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(resizeIcon(defaultIcon, weight, height));
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}

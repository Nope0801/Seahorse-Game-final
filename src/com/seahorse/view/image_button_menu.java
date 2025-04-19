package com.seahorse.view;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL; // Import URL
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class image_button_menu extends JButton {
    private ImageIcon defaultIcon;
    private ImageIcon hoverIcon;
    private int targetWidth; // Lưu lại kích thước để resize khi hover/exit
    private int targetHeight;

    // Sửa constructor để nhận đường dẫn tương đối từ resources
    public image_button_menu(String defaultIconPath, String hoverIconPath, int width, int height) {
        this.targetWidth = width;
        this.targetHeight = height;

        // Sử dụng getClass().getResource() để tải ảnh từ classpath
        URL defaultUrl = getClass().getResource(defaultIconPath);
        URL hoverUrl = getClass().getResource(hoverIconPath);

        if (defaultUrl != null) {
            this.defaultIcon = new ImageIcon(defaultUrl);
        } else {
            System.err.println("Không tìm thấy ảnh nút mặc định: " + defaultIconPath);
            this.defaultIcon = createPlaceholderIcon(width, height); // Ảnh thay thế
        }

        if (hoverUrl != null) {
            this.hoverIcon = new ImageIcon(hoverUrl);
        } else {
            System.err.println("Không tìm thấy ảnh nút hover: " + hoverIconPath);
            // Dùng ảnh mặc định nếu hover không có, hoặc tạo placeholder khác
            this.hoverIcon = this.defaultIcon;
        }

        setIcon(resizeIcon(this.defaultIcon, width, height));
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Đặt sẵn con trỏ tay

        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(resizeIcon(hoverIcon, targetWidth, targetHeight));
                // Không cần setCursor ở đây nữa vì đã set ở constructor
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(resizeIcon(defaultIcon, targetWidth, targetHeight));
                // Không cần setCursor ở đây nữa
            }
        });
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        if (icon == null || icon.getImage() == null) {
            return createPlaceholderIcon(width, height); // Trả về placeholder nếu icon gốc bị lỗi
        }
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    // Tạo icon trống thay thế nếu không tải được ảnh
    private ImageIcon createPlaceholderIcon(int width, int height) {
        java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        // Có thể vẽ gì đó lên đây, ví dụ 1 hình chữ nhật
        java.awt.Graphics2D g2d = image.createGraphics();
        g2d.setColor(java.awt.Color.GRAY);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(java.awt.Color.WHITE);
        g2d.drawString("?", width/2 - 4, height/2 + 4); // Chữ ? ở giữa
        g2d.dispose();
        return new ImageIcon(image);
    }
}
package com.seahorse.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class guidePage extends JPanel {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private HashMap<String, JPanel> guideParts = new HashMap<>();

    public guidePage(MainMenu mainMenu) {
        this.setLayout(new BorderLayout());

        // ====== MENU NÚT BẤM ======
        JPanel buttonPanel = new JPanel(new GridLayout(1, 6, 10, 0));
        String[] parts = {"MÔ TẢ CÁCH CHƠI", "MỤC TIÊU ĐỂ CHIẾN THẮNG"};

        for (int i = 0; i < parts.length; i++) {
            JButton btn = new JButton(parts[i]);
            int index = i + 1;
            btn.addActionListener(e -> showPart("part" + index));
            buttonPanel.add(btn);
        }

        // ====== NỘI DUNG CHÍNH (CARD LAYOUT) ======
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setOpaque(false);

        // ====== PHẦN 1: CÁCH CHƠI ======
        JPanel part1Panel = new JPanel(new GridLayout(1, 2));
        part1Panel.setOpaque(false);

        // ==== CÁCH CHƠI ====
        JTextArea howToPlay = new JTextArea();
        howToPlay.setText("""
              🎮 Cách chơi:
        - Mỗi người chơi có 4 quân.
        - Đổ xúc xắc, được 6 mới ra quân.
        - Khi đi qua các ô đặc biệt, sẽ có hiệu ứng.
        - Khi chạm quân khác, sẽ đá về chuồng.
        """);
        howToPlay.setLineWrap(true);
        howToPlay.setWrapStyleWord(true);
        howToPlay.setFont(new Font("Serif", Font.PLAIN, 32));
        howToPlay.setEditable(false);
        howToPlay.setOpaque(false);
        howToPlay.setForeground(Color.BLACK);

        JScrollPane scrollPanePlay = new JScrollPane(howToPlay);
        scrollPanePlay.setOpaque(false);
        scrollPanePlay.getViewport().setOpaque(false);
        scrollPanePlay.setBorder(null);
        scrollPanePlay.getVerticalScrollBar().setUnitIncrement(16);
        part1Panel.add(scrollPanePlay);

        // ==== ẢNH GIF ====
        JLabel gifLabel1 = new JLabel();
        gifLabel1.setHorizontalAlignment(JLabel.CENTER);
        gifLabel1.setVerticalAlignment(JLabel.CENTER);
        gifLabel1.setOpaque(false);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/guide_assets/part1.gif"));
            gifLabel1.setIcon(icon);
        } catch (Exception e) {
            gifLabel1.setText("Không tìm thấy ảnh");
        }
        part1Panel.add(gifLabel1);

        contentPanel.add(part1Panel, "part1");
        guideParts.put("part1", part1Panel);

        // ====== PHẦN 2: MỤC TIÊU CHIẾN THẮNG ======
        JPanel part2Panel = new JPanel(new GridLayout(1, 2));
        part2Panel.setOpaque(false);

        JTextArea howToWin = new JTextArea();
        howToWin.setText("""
                🏆 Mục tiêu để chiến thắng:
        - Đưa toàn bộ 4 quân về đích trước các đối thủ.
        - Tránh bị đá về chuồng.
        - Tận dụng các ô đặc biệt để tăng tốc hoặc cản đối thủ.
        """);
        howToWin.setLineWrap(true);
        howToWin.setWrapStyleWord(true);
        howToWin.setFont(new Font("Serif", Font.PLAIN, 32));
        howToWin.setEditable(false);
        howToWin.setOpaque(false);
        howToWin.setForeground(Color.BLACK);

        JScrollPane scrollPaneWin = new JScrollPane(howToWin);
        scrollPaneWin.setOpaque(false);
        scrollPaneWin.getViewport().setOpaque(false);
        scrollPaneWin.setBorder(null);
        scrollPaneWin.getVerticalScrollBar().setUnitIncrement(16);
        part2Panel.add(scrollPaneWin);

        // ==== ẢNH GIF ====
        JLabel gifLabel2 = new JLabel();
        gifLabel2.setHorizontalAlignment(JLabel.CENTER);
        gifLabel2.setVerticalAlignment(JLabel.CENTER);
        gifLabel2.setOpaque(false);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("src/resources/sprites/Tutorial/stand_dice.gif"));
            gifLabel2.setIcon(icon);
        } catch (Exception e) {
            gifLabel2.setText("Không tìm thấy ảnh");
        }
        part2Panel.add(gifLabel2);

        contentPanel.add(part2Panel, "part2");
        guideParts.put("part2", part2Panel);

        // ==== ADD TO MAIN PANEL ====
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
        this.setOpaque(false);
    }

    private void showPart(String partName) {
        cardLayout.show(contentPanel, partName);
    }
}

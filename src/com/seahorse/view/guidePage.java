package com.seahorse.view;

import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import javax.swing.*;

public class guidePage extends JPanel {
    private MainMenu parent;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private HashMap<String, JPanel> guideParts = new HashMap<>();

    public guidePage(MainMenu mainMenu) {
        this.parent = mainMenu;
        this.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 6, 10, 0));
        String[] parts = {"MÔ TẢ CÁCH CHƠI", "MỤC TIÊU ĐỂ CHIẾN THẮNG"};

        for (int i = 0; i < parts.length; i++) {
            JButton btn = new JButton(parts[i]);
            int index = i + 1;
            btn.addActionListener(e -> showPart("part" + index));
            btn.setFont(new Font("SansSerif", Font.BOLD, 24));
            buttonPanel.add(btn);
        }

        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setOpaque(false);

        JPanel part1Panel = new JPanel(new GridLayout(1, 2));
        part1Panel.setOpaque(false);

        JTextArea howToPlay = new JTextArea();
        howToPlay.setText("""
                   🎮 CÁCH CHƠI CHI TIẾT:

                        👥 Người chơi:
      - Gồm 2 đến 4 người chơi.
      - Mỗi người điều khiển 4 quân cờ cùng màu: đỏ, xanh dương, xanh lá, vàng.

                        🎲 Bắt đầu lượt:
      - Người chơi lần lượt đổ xúc xắc theo thứ tự.
      - Nếu đổ được số 6, có quyền:
        + Ra quân nếu còn quân trong chuồng.
        + Hoặc đi tiếp 6 bước nếu đã có quân trên bàn.
        + Sau đó được đổ thêm 1 lần nữa.

                       🏃‍♂️ Di chuyển:
      - Quân cờ đi theo chiều kim đồng hồ.
      - Mỗi lượt đi theo số bước tương ứng với kết quả xúc xắc.

                       ⚔️ Đá quân:
      - Nếu quân bạn đi vào ô có quân đối phương, đối phương bị đá về chuồng.
      - Không được đá quân của chính mình.

                       ✨ Ô đặc biệt:
      - Có một số ô đặc biệt (ô tròn màu) giúp tăng tốc hoặc đổi hướng, tùy vào luật mở rộng.

                       🏁 Về đích:
      - Khi đi hết một vòng, quân sẽ rẽ vào đường màu của mình.
      - Phải đi chính xác số bước để vào ô trung tâm (đích).

                       ✅ Lưu ý:
      - Mỗi người cần đưa cả 4 quân về đích để thắng.
      - Nếu chưa đủ điều kiện di chuyển (ví dụ không có quân ra bàn mà không đổ được 6), thì mất lượt.
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

        JPanel gifPanel1 = new JPanel(new BorderLayout());
        gifPanel1.setOpaque(false);

        String[] gifPaths1 = {
                "/resources/sprites/Tutorial/stand_dice.gif",
                "/resources/sprites/Tutorial/move.gif",
                "/resources/sprites/Tutorial/attack.gif"
        };

        String[] gifCaptions1 = {
                "Hình 1: quân cờ đứng yên",
                "Hình 2: quân cờ chạy",
                "Hình 3: quân cờ đá đối thủ"
        };

        ImageIcon[] gifIcons1 = new ImageIcon[gifPaths1.length];
        for (int i = 0; i < gifPaths1.length; i++) {
            URL url = getClass().getResource(gifPaths1[i]);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image scaledImage = icon.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT);
                gifIcons1[i] = new ImageIcon(scaledImage);
            } else {
                gifIcons1[i] = new ImageIcon();
                System.err.println("Không tìm thấy ảnh: " + gifPaths1[i]);
            }
        }

        JLabel gifLabel1 = new JLabel(gifIcons1[0], JLabel.CENTER);
        gifLabel1.setOpaque(false);
        gifPanel1.add(gifLabel1, BorderLayout.CENTER);

        JButton prevBtn1 = new JButton("⏪");
        JButton nextBtn1 = new JButton("⏩");

        JPanel controlPanel1 = new JPanel();
        controlPanel1.setOpaque(false);
        controlPanel1.setLayout(new BoxLayout(controlPanel1, BoxLayout.X_AXIS));

        JLabel gifCaption1 = new JLabel("Hình 1: quân cờ đứng yên");
        gifCaption1.setFont(new Font("SansSerif", Font.ITALIC, 18));

        prevBtn1.setAlignmentY(Component.CENTER_ALIGNMENT);
        gifCaption1.setAlignmentY(Component.CENTER_ALIGNMENT);
        nextBtn1.setAlignmentY(Component.CENTER_ALIGNMENT);

        controlPanel1.add(Box.createHorizontalStrut(190));
        controlPanel1.add(prevBtn1);
        controlPanel1.add(Box.createHorizontalStrut(10));
        controlPanel1.add(gifCaption1);
        controlPanel1.add(Box.createHorizontalStrut(10));
        controlPanel1.add(nextBtn1);
        controlPanel1.add(Box.createHorizontalStrut(10));

        gifPanel1.add(controlPanel1, BorderLayout.SOUTH);

        // nut back
        JButton backButton = new JButton("⬅ Quay lại menu");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> mainMenu.showMainMenu());

        JPanel backPanel = new JPanel();
        backPanel.setOpaque(false);
        backPanel.add(backButton);

        final int[] currentIndex1 = {0};
        prevBtn1.addActionListener(e -> {
            currentIndex1[0] = (currentIndex1[0] - 1 + gifIcons1.length) % gifIcons1.length;
            gifLabel1.setIcon(gifIcons1[currentIndex1[0]]);
            gifCaption1.setText(gifCaptions1[currentIndex1[0]]);
        });

        nextBtn1.addActionListener(e -> {
            currentIndex1[0] = (currentIndex1[0] + 1) % gifIcons1.length;
            gifLabel1.setIcon(gifIcons1[currentIndex1[0]]);
            gifCaption1.setText(gifCaptions1[currentIndex1[0]]);
        });


        part1Panel.add(gifPanel1);


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

        JPanel gifPanel2 = new JPanel(new BorderLayout());
        gifPanel2.setOpaque(false);

        String[] gifPaths2 = {
                "/resources/sprites/Tutorial/Winner.gif",
        };

        ImageIcon[] gifIcons2 = new ImageIcon[gifPaths2.length];
        for (int i = 0; i < gifPaths2.length; i++) {
            URL url = getClass().getResource(gifPaths2[i]);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image scaledImage = icon.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT);
                gifIcons2[i] = new ImageIcon(scaledImage);
            } else {
                gifIcons2[i] = new ImageIcon();
                System.err.println("Không tìm thấy ảnh: " + gifPaths2[i]);
            }
        }

        JLabel gifLabel2 = new JLabel(gifIcons2[0], JLabel.CENTER);
        gifLabel2.setOpaque(false);
        gifPanel2.add(gifLabel2, BorderLayout.CENTER);

        JPanel controlPanel2 = new JPanel();
        controlPanel2.setOpaque(false);
        gifPanel2.add(controlPanel2, BorderLayout.SOUTH);

        part2Panel.add(gifPanel2);

        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
        this.setOpaque(false);

        contentPanel.add(part1Panel, "part1");
        contentPanel.add(part2Panel, "part2");

        cardLayout.show(contentPanel, "part1");

        this.add(backPanel, BorderLayout.SOUTH);
    }

    private void showPart(String partName) {
        cardLayout.show(contentPanel, partName);
    }
}
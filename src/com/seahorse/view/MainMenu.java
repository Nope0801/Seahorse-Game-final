package com.seahorse.view;

import com.seahorse.utils.Sound_main;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private Sound_main bgrMusic;
    private JPanel mainContentPanel;
    private guidePage guidePanel;
    private optionsPage optionsPanel;
    private CardLayout cardLayout;

    public MainMenu(int themeIndex) {
        setTitle("Game Cờ Cá Ngựa");
        setSize(1366, 774);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ======= Âm nhạc =======
        bgrMusic = new Sound_main("/resources/sprites/Soundtrack/Main_theme_sound.wav");
        bgrMusic.playLoop();

        // ======= Layered Pane để xếp lớp nền và giao diện =======
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1366, 774));
        setContentPane(layeredPane);

        // ======= Nền động =======
        String[] imageBgrGif = {
                "/resources/sprites/Background/bgr_fish.gif",
                "/resources/sprites/Background/bgr_purpil.gif",
                "/resources/sprites/Background/bgr_darkness.gif"
        };
        AnimatedGifPanel backgroundPanel = new AnimatedGifPanel(imageBgrGif[themeIndex]);
        backgroundPanel.setBounds(0, 0, 1366, 774);
        layeredPane.add(backgroundPanel, new Integer(0)); // Layer thấp

        // ======= Panel chính chứa các thành phần giao diện =======
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBounds(0, 0, 1366, 774);
        layeredPane.add(mainPanel, new Integer(1)); // Layer cao hơn

        // ======= Title ảnh động =======
        ImageIcon titleGifIcon = new ImageIcon("src/resources/sprites/Background/rambo_seahorse.gif");
        JLabel titleLabel = new JLabel(titleGifIcon);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 400));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // ======= Card layout chứa menu / guide / options =======
        mainContentPanel = new JPanel(cardLayout = new CardLayout());
        mainContentPanel.setOpaque(false);
        mainPanel.add(mainContentPanel, BorderLayout.CENTER);

        // ======= Menu Panel =======
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 535, 50, 0));

        image_button_menu startButton = new image_button_menu(
                "src/resources/sprites/menu_button/play_button.png",
                "src/resources/sprites/menu_button/play_button_hover.png",
                200, 45
        );
        startButton.addActionListener(e -> {
            bgrMusic.stop();
            this.dispose();
            new GameFrame();
        });

        image_button_menu guideButton = new image_button_menu(
                "src/resources/sprites/menu_button/guide_button.png",
                "src/resources/sprites/menu_button/guide_button_hover.png",
                200, 45
        );
        guideButton.addActionListener(e -> showGuide());

        image_button_menu optionsButton = new image_button_menu(
                "src/resources/sprites/menu_button/options_button.png",
                "src/resources/sprites/menu_button/options_button_hover.png",
                200, 45
        );
        optionsButton.addActionListener(e -> showOptions());

        image_button_menu exitButton = new image_button_menu(
                "src/resources/sprites/menu_button/quit_button.png",
                "src/resources/sprites/menu_button/quit_button_hover.png",
                200, 45
        );
        exitButton.addActionListener(e -> System.exit(0));

        menuPanel.add(Box.createVerticalStrut(100));
        menuPanel.add(wrapButton(startButton));
        menuPanel.add(wrapButton(guideButton));
        menuPanel.add(wrapButton(optionsButton));
        menuPanel.add(wrapButton(exitButton));

        // ======= Các trang phụ =======
        guidePanel = new guidePage(this);
        optionsPanel = new optionsPage(this, bgrMusic);

        mainContentPanel.add(menuPanel, "menu");
        mainContentPanel.add(guidePanel, "guide");
        mainContentPanel.add(optionsPanel, "options");

        // Hiển thị menu mặc định
        cardLayout.show(mainContentPanel, "menu");

        setVisible(true);
    }

    public MainMenu() {
        this(0);
    }

    public void showGuide() {
        cardLayout.show(mainContentPanel, "guide");
    }

    public void showOptions() {
        cardLayout.show(mainContentPanel, "options");
    }

    public void showMainMenu() {
        cardLayout.show(mainContentPanel, "menu");
    }

    private JPanel wrapButton(JButton button) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        panel.add(button);
        return panel;
    }

    // ======= Nền ảnh gif động chiếm toàn bộ màn hình =======
    class AnimatedGifPanel extends JPanel {
        private ImageIcon gifIcon;

        public AnimatedGifPanel(String gifPath) {
            setOpaque(false);
            gifIcon = new ImageIcon(getClass().getResource(gifPath));

            // Đảm bảo gif luôn chạy
            Timer t = new Timer(100, e -> repaint());
            t.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (gifIcon != null) {
                g.drawImage(gifIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}

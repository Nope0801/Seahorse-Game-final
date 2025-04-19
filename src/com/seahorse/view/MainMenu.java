package com.seahorse.view;

import com.seahorse.utils.Sound_main;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class MainMenu extends JFrame {
    Sound_main bgrMusic;
    private JPanel mainContentPanel;
    private guidePage guidePanel;
    private optionsPage optionsPanel;
    private GameSelection gameSelectionPanel;
    private CardLayout cardLayout;
    private AnimatedGifPanel backgroundPanel;
    private JLayeredPane layeredPane;
    private int currentThemeIndex;

    String[] imageBgrGif = {
        "/resources/sprites/Background/bgr_fish.gif",
        "/resources/sprites/Background/bgr_fish_2.gif",
        "/resources/sprites/Background/bgr_fish_3.gif"
    };

    public MainMenu(int themeIndex) {
        this.currentThemeIndex = themeIndex;
        setTitle("Game Cờ Cá Ngựa");
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            bgrMusic = new Sound_main("/resources/sprites/Soundtrack/Main_theme_sound.wav");
            bgrMusic.playLoop();
        } catch (Exception e) {
            System.err.println("Lỗi không thể tải hoặc phát nhạc nền: " + e.getMessage());
            bgrMusic = null;
        }

        // ======= Layered Pane để xếp lớp nền và giao diện =======
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1366, 774)); 
        setContentPane(layeredPane);

        backgroundPanel = new AnimatedGifPanel(imageBgrGif[themeIndex]);
        backgroundPanel.setBounds(0, 0, layeredPane.getPreferredSize().width, layeredPane.getPreferredSize().height);
        layeredPane.add(backgroundPanel, Integer.valueOf(0));

        // ======= Panel chính chứa các thành phần giao diện =======
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBounds(0, 0, layeredPane.getPreferredSize().width, layeredPane.getPreferredSize().height);
        layeredPane.add(mainPanel, Integer.valueOf(1));

        // ======= Card layout chứa menu / guide / options / selection =======
        mainContentPanel = new JPanel(cardLayout = new CardLayout());
        mainContentPanel.setOpaque(false);
        mainPanel.add(mainContentPanel, BorderLayout.CENTER);

        // ======= Menu Panel =======
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(90, 0, 50, 0));

        image_button_menu startButton = new image_button_menu(
                "/resources/sprites/menu_button/play_button.png",
                "/resources/sprites/menu_button/play_button_hover.png",
                200, 45
        );
        startButton.addActionListener(e -> showGameSelection());

        image_button_menu guideButton = new image_button_menu(
                "/resources/sprites/menu_button/guide_button.png",
                "/resources/sprites/menu_button/guide_button_hover.png",
                200, 45
        );
        guideButton.addActionListener(e -> showGuide());

        image_button_menu optionsButton = new image_button_menu(
                "/resources/sprites/menu_button/options_button.png",
                "/resources/sprites/menu_button/options_button_hover.png",
                200, 45
        );
        optionsButton.addActionListener(e -> showOptions());

        image_button_menu exitButton = new image_button_menu(
                "/resources/sprites/menu_button/quit_button.png",
                "/resources/sprites/menu_button/quit_button_hover.png",
                200, 45
        );
        exitButton.addActionListener(e -> System.exit(0));

        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(centerHorizontally(startButton));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        menuPanel.add(centerHorizontally(guideButton));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        menuPanel.add(centerHorizontally(optionsButton));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        menuPanel.add(centerHorizontally(exitButton));
        menuPanel.add(Box.createVerticalGlue());

        // ======= Các trang phụ =======
        guidePanel = new guidePage(this);
        optionsPanel = new optionsPage(this, bgrMusic);
        gameSelectionPanel = new GameSelection(this);

        mainContentPanel.add(menuPanel, "menu");
        mainContentPanel.add(guidePanel, "guide");
        mainContentPanel.add(optionsPanel, "options");
        mainContentPanel.add(gameSelectionPanel, "selection");

        cardLayout.show(mainContentPanel, "menu");

        // ========== THÊM COMPONENT LISTENER ==========
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int newWidth = layeredPane.getWidth();
                int newHeight = layeredPane.getHeight();

                if (backgroundPanel != null) {
                    backgroundPanel.setBounds(0, 0, newWidth, newHeight);
                }
                if (mainPanel != null) {
                     mainPanel.setBounds(0, 0, newWidth, newHeight);
                }
                layeredPane.revalidate();
                layeredPane.repaint();
            }
        });

        setVisible(true);

        EventQueue.invokeLater(() -> {
            ComponentEvent initialResizeEvent = new ComponentEvent(MainMenu.this, ComponentEvent.COMPONENT_RESIZED);
            MainMenu.this.dispatchEvent(initialResizeEvent);
            System.out.println("Initial resize triggered.");
        });
    }

    // Constructor mặc định
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

    public void showGameSelection() {
        cardLayout.show(mainContentPanel, "selection");
    }

    private Component centerHorizontally(Component component) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(component);
        wrapper.setMaximumSize(new Dimension(Short.MAX_VALUE, component.getPreferredSize().height + 10));
        return wrapper;
    }

    public void changeBackground(int themeIndex) {
        if (themeIndex >= 0 && themeIndex < imageBgrGif.length && themeIndex != this.currentThemeIndex) {
            System.out.println("Changing background to index: " + themeIndex);
            this.currentThemeIndex = themeIndex;

            int currentWidth = layeredPane.getWidth();
            int currentHeight = layeredPane.getHeight();

            if (backgroundPanel != null) {
                backgroundPanel.stopAnimation();
                layeredPane.remove(backgroundPanel);
                System.out.println("Removed old background panel.");
            }

            AnimatedGifPanel newBackgroundPanel = new AnimatedGifPanel(imageBgrGif[themeIndex]);
            newBackgroundPanel.setBounds(0, 0, currentWidth, currentHeight);

            layeredPane.add(newBackgroundPanel, Integer.valueOf(0));
            layeredPane.setLayer(newBackgroundPanel, 0);

            this.backgroundPanel = newBackgroundPanel;

            layeredPane.revalidate();
            layeredPane.repaint();
            System.out.println("Added and displayed new background panel.");

        } else if (themeIndex == this.currentThemeIndex) {
             System.out.println("Background index " + themeIndex + " is already selected. No change.");
        } else {
             System.err.println("Invalid theme index for background change: " + themeIndex);
        }
    }

    class AnimatedGifPanel extends JPanel {
        private ImageIcon gifIcon;
        private Timer timer;

        public AnimatedGifPanel(String gifPath) {
            setOpaque(false);
            try {
                java.net.URL imgUrl = getClass().getResource(gifPath);
                if (imgUrl != null) {
                    gifIcon = new ImageIcon(imgUrl);
                    if (gifIcon.getImageObserver() != null) {
                         timer = new Timer(100, e -> repaint());
                         timer.start();
                     }
                } else {
                    System.err.println("Không tìm thấy file GIF nền: " + gifPath);
                    gifIcon = null;
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi tải GIF nền: " + e.getMessage());
                gifIcon = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (gifIcon != null && gifIcon.getImage() != null) {
                // Luôn vẽ để lấp đầy kích thước hiện tại của panel này
                g.drawImage(gifIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }

        public void stopAnimation() {
             if (timer != null && timer.isRunning()) {
                 timer.stop();
             }
        }
    }

    @Override
    public void dispose() {
        if (bgrMusic != null) {
            bgrMusic.stop();
        }
        if (backgroundPanel != null) {
             backgroundPanel.stopAnimation();
        }
        this.removeComponentListener(this.getComponentListeners()[this.getComponentListeners().length-1]);
        super.dispose();
    }
}
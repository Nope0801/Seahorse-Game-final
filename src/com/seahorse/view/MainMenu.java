package com.seahorse.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JFrame {
    private JPanel menuPanel;
    private guidePage guidePanel;
    private optionsPage optionsPanel;
    private BackgroundPanel bgrPanel;

    private CardLayout cardLayout;
    private JPanel mainContentPanel;

    public MainMenu() {
        setTitle("Game Co ca ngua");
        setSize(1366, 774);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        bgrPanel = new BackgroundPanel("src/resources/sprites/Background/bgr_img.jpg");
        bgrPanel.setLayout(new BorderLayout());

        ImageIcon titleIcon = new ImageIcon("src/resources/sprites/Background/title_seahorse.png");
        JLabel titleLabel = new JLabel(titleIcon);
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 1020));
        titlePanel.add(titleLabel);
        bgrPanel.add(titlePanel, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setOpaque(false);

        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 50, 0));

        JButton startButton = createMenuButton("Play");
        startButton.addActionListener(e -> {
            this.dispose();
            new GameFrame();
        });

        JButton guideButton = createMenuButton("Guide");
        guideButton.addActionListener(e -> showGuide());

        JButton optionsButton = createMenuButton("Options");
        optionsButton.addActionListener(e -> showOptions());

        JButton exitButton = createMenuButton("Quit");
        exitButton.addActionListener(e -> System.exit(0));

        menuPanel.add(Box.createVerticalStrut(100));
        menuPanel.add(wrapButton(startButton));
        menuPanel.add(wrapButton(guideButton));
        menuPanel.add(wrapButton(optionsButton));
        menuPanel.add(wrapButton(exitButton));


        guidePanel = new guidePage(this);

        optionsPanel = new optionsPage(this);

        mainContentPanel.add(menuPanel, "menu");
        mainContentPanel.add(guidePanel, "guide");
        mainContentPanel.add(optionsPanel, "options");

        bgrPanel.add(mainContentPanel, BorderLayout.CENTER);

        setContentPane(bgrPanel);
        setVisible(true);
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

    private RoundedButton createMenuButton(String text) {
        RoundedButton button = new RoundedButton(text);
        button.setPreferredSize(new Dimension(200, 60));
        button.setMaximumSize(new Dimension(200, 60));
        button.setFont(new Font("Cinzel", Font.BOLD, 18));
        return button;
    }

    private JPanel wrapButton(JButton button) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        panel.add(button);
        return panel;
    }

    class RoundedButton extends JButton {
        private Color normalColor = Color.BLACK;
        private Color hoverColor = new Color(255, 255, 0);

        public RoundedButton(String text) {
            super(text);
            setFont(new Font("Cinzel", Font.BOLD, 18));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setForeground(normalColor);
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setForeground(hoverColor);
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setForeground(normalColor);
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
        }
    }

    class BackgroundPanel extends JPanel {
        private Image bgrImage;

        public BackgroundPanel(String imagePath) {
            bgrImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bgrImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

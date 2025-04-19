package com.seahorse.view;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class guidePage extends JPanel {
    private MainMenu parent;
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public guidePage(MainMenu mainMenu) {
        this.parent = mainMenu;

        // ========== BUTTON BAR ========== //
        String[] iconPaths = {
            "/resources/sprites/Button/warning_icon_pixel.png",
            "/resources/sprites/Button/heart_icon_pixel.png"
        };

        ImageIcon[] icons = new ImageIcon[iconPaths.length];

        for (int i = 0; i < iconPaths.length; i++) {
            URL url = getClass().getResource(iconPaths[i]);
            Image img = Toolkit.getDefaultToolkit().createImage(url);
            Image scaled = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            icons[i] = new ImageIcon(scaled);
        }

        JPanel buttonPanel = new JPanel(new GridLayout(1, 6, 10, 0));
        String[] parts = {"DESCRIBE HOW TO PLAY", "GOALS TO WIN"};

        for (int i = 0; i < parts.length; i++) {
            JButton btn = new JButton(parts[i]);
            btn.setIcon(icons[i]);
            int index = i + 1;
            btn.addActionListener(e -> showPart("part" + index));
            btn.setFont(new Font("Pixel Intv", Font.BOLD, 24));
            buttonPanel.add(btn);
        }

        JPanel buttonContainer = new JPanel(new BorderLayout());
        buttonContainer.setOpaque(false);
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        buttonContainer.add(buttonPanel, BorderLayout.CENTER);

        // ========== CONTENT PANEL ==========
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setOpaque(false);

        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setOpaque(false);
        contentWrapper.add(contentPanel, BorderLayout.CENTER);
        contentWrapper.setMaximumSize(new Dimension(1366, 550));

        // ========== PART 1 ========== //
        JPanel part1Panel = new JPanel(new GridLayout(1, 2));
        part1Panel.setOpaque(false);

        JTextPane howToPlay = new JTextPane();
        howToPlay.setEditable(false);
        howToPlay.setOpaque(false);
        setStyledText(howToPlay, """
* DETAILED GAMEPLAY INSTRUCTIONS:

          * Players:
    - 2 to 4 players.
    - Each other controls 4 pawns of the same color: red, blue, green, or yellow.

          * Starting a turn:
    - Player take turns rolling the dice in sequence.
    - If a player rolls a 6, they can:
        + Bring a pawn out of the home base if they still have any inside.
        + Or move an existing pawn forward 6 steps.
        + Then roll the dice again for an additional turn.

          * Movement:
    - Pawns move clockwise around the board.
    - Each turn, the pawn moves forward a number of steps equal to the dice result.

          * Kicking Pawns:
    - If your pawn lands on a space occupied by an opponent's pawn, the opponent's pawn is kicked back to their home base.
    - You can't kick your own pawns.

          * Special spaces:
    - Some spaces have special effects

         * Reaching the Goal:
    - After completing a full round on the board, a pawn turns into its color's home stretch.
    - You must roll the exact number needed to enter the center (goal) tile.

         * Notes:
    - A player must get all 4 of their pawns to the goal to win the game.
    - If a player can't make a valid move (e.g., no pawns on the board and didn't roll a 6), their turn is skipped.
""");

        howToPlay.setCaretPosition(0);
        JScrollPane scrollPanePlay = new JScrollPane(howToPlay);
        scrollPanePlay.setOpaque(false);
        scrollPanePlay.getViewport().setOpaque(false);
        scrollPanePlay.setBorder(null);
        // scrollPanePlay.getVerticalScrollBar().setUnitIncrement(16);
        part1Panel.add(scrollPanePlay);

        JPanel gifPanel1 = new JPanel(new BorderLayout());
        gifPanel1.setOpaque(false);

        String[] gifPaths1 = {
            "/resources/sprites/Tutorial/stand_dice.gif",
            "/resources/sprites/Tutorial/move.gif",
            "/resources/sprites/Tutorial/attack.gif"
        };

        String[] gifCaptions1 = {
            "Image 1: Pawn standing still",
            "Image 2: Pawn moving",
            "Image 3: Pawn attacking"
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

        JLabel gifCaption1 = new JLabel("Image 1: Pawn standing");
        gifCaption1.setFont(new Font("Pixel Intv", Font.PLAIN, 18));

        controlPanel1.add(Box.createHorizontalStrut(190));
        controlPanel1.add(prevBtn1);
        controlPanel1.add(Box.createHorizontalStrut(10));
        controlPanel1.add(gifCaption1);
        controlPanel1.add(Box.createHorizontalStrut(10));
        controlPanel1.add(nextBtn1);
        controlPanel1.add(Box.createHorizontalStrut(10));

        gifPanel1.add(controlPanel1, BorderLayout.SOUTH);

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

        // ========== PART 2 ========== //
        JPanel part2Panel = new JPanel(new GridLayout(1, 2));
        part2Panel.setOpaque(false);

        JTextPane howToWin = new JTextPane();
        howToWin.setEditable(false);
        howToWin.setOpaque(false);
        setStyledText(howToWin, """
* Objectives to Win:
    - Bring all 4 pawns to the goal before your opponents.
    - Avoid being kicked back to your base.
    - Take advantage of special spaces to accelerate or block others.
""");

        howToWin.setCaretPosition(0);
        JScrollPane scrollPaneWin = new JScrollPane(howToWin);
        scrollPaneWin.setOpaque(false);
        scrollPaneWin.getViewport().setOpaque(false);
        scrollPaneWin.setBorder(null);
        scrollPaneWin.getVerticalScrollBar().setUnitIncrement(16);
        part2Panel.add(scrollPaneWin);

        JPanel gifPanel2 = new JPanel(new BorderLayout());
        gifPanel2.setOpaque(false);

        String[] gifPaths2 = {
            "/resources/sprites/Tutorial/Winner.gif"
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

        JLabel gifCaption_win = new JLabel("CHICKEN CHICKEN WINNER");
        gifCaption_win.setFont(new Font("Pixel Intv", Font.PLAIN, 36));
        controlPanel2.add(gifCaption_win);

        gifPanel2.add(controlPanel2, BorderLayout.SOUTH);

        part2Panel.add(gifPanel2);

        contentPanel.add(part1Panel, "part1");
        contentPanel.add(part2Panel, "part2");

        cardLayout.show(contentPanel, "part1");

        // ========== NÚT BACK ==========
        URL back_icon_pixel = getClass().getResource("/resources/sprites/Button/arrow_left_pixel.png");
        JButton backButton = new JButton("BACK TO MENU");
        if (back_icon_pixel != null) {
            Image img = Toolkit.getDefaultToolkit().createImage(back_icon_pixel);
            Image scaled = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            backButton.setIcon(new ImageIcon(scaled));
        }
        backButton.setFont(new Font("Pixel Intv", Font.BOLD, 18));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> mainMenu.showMainMenu());

        JPanel backPanel = new JPanel();
        backPanel.setOpaque(false);
        backPanel.add(backButton);

        // ========== BỌC TẤT CẢ ==========
        JPanel mainWrapper = new JPanel();
        mainWrapper.setOpaque(false);
        mainWrapper.setLayout(new BoxLayout(mainWrapper, BoxLayout.Y_AXIS));
        mainWrapper.add(Box.createVerticalGlue()); 
        mainWrapper.add(buttonContainer);
        mainWrapper.add(Box.createVerticalStrut(0));
        mainWrapper.add(contentWrapper);
        mainWrapper.add(backPanel);

        this.setLayout(new BorderLayout());
        this.add(mainWrapper, BorderLayout.CENTER);
        this.setOpaque(false);
    }

    private void showPart(String partName) {
        cardLayout.show(contentPanel, partName);
    }

    private void setStyledText(JTextPane textPane, String content) {
        StyledDocument doc = textPane.getStyledDocument();    
        
        Style defaultStyle = textPane.addStyle("default", null);
        StyleConstants.setForeground(defaultStyle, Color.BLACK);
        StyleConstants.setFontFamily(defaultStyle, "Pixel Intv");
        StyleConstants.setFontSize(defaultStyle, 24);
    
        Style highlightStyle = textPane.addStyle("highlight", null);
        StyleConstants.setForeground(highlightStyle, new Color(255, 215, 0));
        StyleConstants.setBold(highlightStyle, true);
        StyleConstants.setItalic(highlightStyle, true);
        StyleConstants.setFontFamily(highlightStyle, "Pixel Intv");
        StyleConstants.setFontSize(highlightStyle, 24);
    
        Style minusStyle = textPane.addStyle("minus", defaultStyle);
        StyleConstants.setForeground(minusStyle, new Color(255, 255, 255)); 
    
        Style plusStyle = textPane.addStyle("plus", defaultStyle);
        StyleConstants.setForeground(plusStyle, new Color(152, 255, 152));

        try {
            doc.remove(0, doc.getLength());
    
            for (String line : content.split("\n")) {
                String trimmedLine = line.trim();
                Style styleToUse;
                if (trimmedLine.startsWith("*")) {
                    styleToUse = highlightStyle;
                } else if (trimmedLine.startsWith("-")) {
                    styleToUse = minusStyle;
                } else if (trimmedLine.startsWith("+")) {
                    styleToUse = plusStyle;
                } else {
                    styleToUse = defaultStyle;
                }
    
                doc.insertString(doc.getLength(), line + "\n", styleToUse);
            }
        } catch (BadLocationException e) {
            System.err.println("Lỗi khi định dạng văn bản trong JTextPane: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

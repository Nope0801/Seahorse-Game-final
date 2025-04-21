package com.seahorse.view;

import com.seahorse.model.PlayerNumberAndSkin;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class GameSelection extends JPanel {
    private MainMenu parent;
    private final int MAX_PLAYERS = 4;
    private List<JLabel> characterLabels;
    private List<Integer> selectedCharacterIndices;
    private List<JPanel> characterSelectionPanels;
    private List<JButton> leftArrowButtons;
    private List<JButton> rightArrowButtons;

    private String[] characterAnimationFolderPaths = {
        "/resources/sprites/SeaHorse/red/dinasour/idle_animation/", 
        "/resources/sprites/SeaHorse/blue/dinasour/idle_animation/", 
        "/resources/sprites/SeaHorse/green/dinasour/idle_animation/", 
        "/resources/sprites/SeaHorse/yellow/dinasour/idle_animation/",
        "/resources/sprites/SeaHorse/red/default/idle_animation/",
        "/resources/sprites/SeaHorse/blue/default/idle_animation/",
        "/resources/sprites/SeaHorse/green/default/idle_animation/",
        "/resources/sprites/SeaHorse/yellow/default/idle_animation/",

    };
    private String[] characterBaseColors = {
         "Red", "Blue", "Green", "Yellow",
    };
    
    private List<List<ImageIcon>> allCharacterIdleFrames;
    private final int ICON_SIZE = 128;

    private Timer animationTimer;
    private int currentAnimationFrameIndex = 0;
    private final int ANIMATION_DELAY = 150; 

    private JComboBox<Integer> playerCountComboBox;
    private final Integer[] playerCounts = {2, 3, 4};

    public GameSelection(MainMenu parent) {
        this.parent = parent;

        setOpaque(false);
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(200, 0, 0, 0));

        //panel chua title va selec
        JPanel topPanel = new JPanel(new BorderLayout(0, 0)); // Border layout cho top
        topPanel.setOpaque(false);
        add(topPanel, BorderLayout.NORTH);

        //title
        JLabel titleLabel = new JLabel("SELECT YOUR CHARACTER", JLabel.CENTER);
        titleLabel.setFont(new Font("Pixel Intv", Font.BOLD, 36));
        titleLabel.setForeground(Color.RED);
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // countPlayer
        JPanel numPlayer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        numPlayer.setOpaque(false);
        topPanel.add(numPlayer, BorderLayout.CENTER);
        topPanel.setBorder(new EmptyBorder(30, 0, 0, 0)); // Thêm khoảng cách dưới cùng

        JLabel numPlayersLabel = createPixelLabel("Number of Players:", 20, Color.WHITE);
        playerCountComboBox = new JComboBox<>(playerCounts);
        playerCountComboBox.setFont(new Font("Pixel Intv", Font.PLAIN, 20));
        playerCountComboBox.setPreferredSize(new Dimension(100, 30));
        playerCountComboBox.addActionListener(e -> updateVisiblePlayers());
        numPlayer.add(numPlayersLabel);
        numPlayer.add(playerCountComboBox);


        // --- Panel Trung Tâm Chọn Nhân Vật ---
        int horizontalGap = 150;
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, horizontalGap, 20));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        add(centerPanel, BorderLayout.CENTER);

        // --- Khởi tạo dữ liệu nhân vật ---
        loadCharacterAnimations();
        characterLabels = new ArrayList<>();
        selectedCharacterIndices = new ArrayList<>();
        characterSelectionPanels = new ArrayList<>();
        leftArrowButtons = new ArrayList<>();
        rightArrowButtons = new ArrayList<>();

        // --- Tạo 4 khu vực chọn nhân vật ---
        if (allCharacterIdleFrames != null && !allCharacterIdleFrames.isEmpty()) {
            for (int i = 0; i < MAX_PLAYERS; i++) {
                // Chọn skin mặc định ban đầu
                selectedCharacterIndices.add(i % allCharacterIdleFrames.size());
                JPanel selectionArea = createCharacterSelectionPanel(i);
                if (selectionArea != null) {
                    centerPanel.add(selectionArea);
                }
            }
        } else {
            System.err.println("Lỗi: Không thể tạo khu vực chọn nhân vật vì không load được animation.");
            centerPanel.add(new JLabel("Error loading character animations..."));
        }

        // --- Panel Nút Bấm Dưới Cùng ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        buttonPanel.setOpaque(false);

        JButton startButton = createStyledButton("START GAME");
        startButton.addActionListener(e -> startGameAction());

        JButton backButton = createStyledButton("BACK TO MENU");
        backButton.addActionListener(e -> parent.showMainMenu());

        buttonPanel.add(startButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        playerCountComboBox.setSelectedIndex(2);
        updateVisiblePlayers();

        setupAnimationTimer();
    }

    //ham an/hien o chon
    private void updateVisiblePlayers() {
        if (playerCountComboBox == null || characterSelectionPanels == null || leftArrowButtons == null || rightArrowButtons == null) {
            System.err.println("Lỗi: updateVisiblePlayers được gọi trước khi khởi tạo hoàn tất.");
            return;
        }

       Object selectedItem = playerCountComboBox.getSelectedItem();
       if (selectedItem == null) return;

       int selectedCount = (int) selectedItem;
       System.out.println("Updating visibility for " + selectedCount + " players.");

       for (int i = 0; i < MAX_PLAYERS; i++) {
            boolean isVisible = i < selectedCount; 
            if (i < characterSelectionPanels.size()) {
               characterSelectionPanels.get(i).setVisible(isVisible);
            }
            if (i < leftArrowButtons.size()) {
               leftArrowButtons.get(i).setVisible(isVisible);
            }
            if (i < rightArrowButtons.size()) {
                rightArrowButtons.get(i).setVisible(isVisible);
            }
       }
       revalidate();
       repaint();
    }

    // Hàm tải và resize các icon nhân vật
    private void loadCharacterAnimations() {
        allCharacterIdleFrames = new ArrayList<>();
        for (String folderPath : characterAnimationFolderPaths) {
            List<ImageIcon> frames = new ArrayList<>();
            int frameIndex = 0;
            while (true) {
                String filePath = folderPath + frameIndex + ".png";
                URL imgUrl = getClass().getResource(filePath);
                if (imgUrl != null) {
                    try {
                        ImageIcon originalIcon = new ImageIcon(imgUrl);
                        if (originalIcon.getIconWidth() > 0) {
                            Image scaledImg = originalIcon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
                            frames.add(new ImageIcon(scaledImg));
                            frameIndex++;
                        } else {
                            System.err.println("Cảnh báo: Ảnh frame không hợp lệ: " + filePath);
                            break;
                        }
                    } catch (Exception e) {
                         System.err.println("Lỗi khi tạo ImageIcon frame: " + filePath + " - " + e.getMessage());
                         break;
                    }
                } else {
                    // Không tìm thấy file frame tiếp theo -> kết thúc chuỗi anim cho skin này
                    if (frameIndex == 0) { 
                        System.err.println("Lỗi: Không tìm thấy frame animation nào trong: " + folderPath);
                    }
                    break;
                }
            }
            if (!frames.isEmpty()) {
                allCharacterIdleFrames.add(frames);
            } else {
                 System.err.println("Thêm placeholder cho skin tại: " + folderPath);
                 List<ImageIcon> placeholderFrames = new ArrayList<>();
                 placeholderFrames.add(createPlaceholderImageIcon(ICON_SIZE, ICON_SIZE, "ERR"));
                 allCharacterIdleFrames.add(placeholderFrames);
            }
        }
        if (allCharacterIdleFrames.isEmpty()) {
             System.err.println("Lỗi nghiêm trọng: Không load được bất kỳ animation nhân vật nào.");
        }
    }

    private JPanel createCharacterSelectionPanel(int playerIndex) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel playerLabel = createPixelLabel("PLAYER " + (playerIndex + 1), 20, Color.CYAN);
        if(playerLabel != null) { playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT); panel.add(playerLabel); }
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel charLabel;
        ImageIcon initialIcon = createPlaceholderImageIcon(ICON_SIZE, ICON_SIZE, "ERR");
        if (allCharacterIdleFrames != null && playerIndex < selectedCharacterIndices.size() &&
            selectedCharacterIndices.get(playerIndex) < allCharacterIdleFrames.size()) {
            List<ImageIcon> frames = allCharacterIdleFrames.get(selectedCharacterIndices.get(playerIndex));
            if (frames != null && !frames.isEmpty()) {
                initialIcon = frames.get(0);
            } else { System.err.println("Lỗi: List frame rỗng cho player " + playerIndex); }
        } else { System.err.println("Lỗi: Index không hợp lệ khi lấy frame đầu tiên cho player " + playerIndex); }

        charLabel = new JLabel(initialIcon);
        charLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        charLabel.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        if (characterLabels != null) characterLabels.add(charLabel);
        panel.add(charLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel arrowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        arrowPanel.setOpaque(false);
        arrowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton leftArrow = createArrowButton("<", playerIndex, -1);
        JButton rightArrow = createArrowButton(">", playerIndex, 1);

        if (leftArrowButtons != null) leftArrowButtons.add(leftArrow);
        if (rightArrowButtons != null) rightArrowButtons.add(rightArrow);

        if (leftArrow != null) arrowPanel.add(leftArrow);
        if (rightArrow != null) arrowPanel.add(rightArrow);
        panel.add(arrowPanel);

        return panel;
    }

    // Hàm tạo nút mũi tên
    private JButton createArrowButton(String text, int playerIndex, int direction) {
        JButton button = new JButton(text);
        button.setFont(new Font("Pixel Intv", Font.BOLD, 20));
        button.setMargin(new Insets(5, 10, 5, 10));
        button.setFocusPainted(false);
        button.addActionListener(e -> changeCharacter(playerIndex, direction));
        return button;
    }

    // Hàm xử lý khi nhấn nút mũi tên
    private void changeCharacter(int playerIndex, int direction) {
        int currentIndex = selectedCharacterIndices.get(playerIndex);
        int newIndex = (currentIndex + direction + allCharacterIdleFrames.size()) % allCharacterIdleFrames.size();
        selectedCharacterIndices.set(playerIndex, newIndex);

        // Cập nhật ảnh hiển thị ngay lập tức thành frame đầu tiên của skin mới
        if (newIndex < allCharacterIdleFrames.size() && !allCharacterIdleFrames.get(newIndex).isEmpty()) {
             characterLabels.get(playerIndex).setIcon(allCharacterIdleFrames.get(newIndex).get(0));
        } else {
             System.err.println("Lỗi khi đổi sang skin index " + newIndex);
             characterLabels.get(playerIndex).setIcon(createPlaceholderImageIcon(ICON_SIZE, ICON_SIZE, "ERR"));
        }
    }

    private void setupAnimationTimer(){
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop(); // Dừng timer cũ nếu có
        }
        animationTimer = new Timer(ANIMATION_DELAY, e -> updateAnimation());
        animationTimer.start();
        System.out.println("Animation timer started.");
    }

    private void updateAnimation() {
        int maxFrames = 0;
        for (List<ImageIcon> frames : allCharacterIdleFrames) {
            if (frames.size() > maxFrames) {
                maxFrames = frames.size();
            }
        }
        if (maxFrames == 0) return;

        currentAnimationFrameIndex = (currentAnimationFrameIndex + 1) % maxFrames;

        // Cập nhật icon cho từng label nhân vật
        for (int i = 0; i < characterLabels.size(); i++) {
            int skinIndex = selectedCharacterIndices.get(i);
            // Đảm bảo skinIndex hợp lệ
            if (skinIndex < allCharacterIdleFrames.size()) {
                List<ImageIcon> currentSkinFrames = allCharacterIdleFrames.get(skinIndex);
                if (!currentSkinFrames.isEmpty()) {
                    int frameToShowIndex = currentAnimationFrameIndex % currentSkinFrames.size();
                    characterLabels.get(i).setIcon(currentSkinFrames.get(frameToShowIndex));
                }
            }
        }
    }

    public void stopAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
            System.out.println("Animation timer stopped.");
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Pixel Intv", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(250, 50));
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 100, 0));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(0, 150, 0));
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(0, 100, 0));
            }
        });
        return button;
    }

    private JLabel createPixelLabel(String text, int size, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Pixel Intv", Font.BOLD, size));
        label.setForeground(color);
        return label;
    }

    // Hàm tạo ảnh placeholder
    private ImageIcon createPlaceholderImageIcon(int width, int height, String text) {
        java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("SansSerif", Font.BOLD, height / 3));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g2d.drawString(text, (width - textWidth) / 2, height / 2 + fm.getAscent() / 3);
        g2d.dispose();
        return new ImageIcon(image);
    }

    // Hàm xử lý bắt đầu game
    private void startGameAction() {
        stopAnimation();

        int numPlayers = (int) playerCountComboBox.getSelectedItem(); // Lấy số người chơi

        List<String> finalPlayerColors = new ArrayList<>();
        List<String> finalPlayerSkinPaths = new ArrayList<>(); // <<--- List mới để lưu đường dẫn skin
        Set<String> uniqueColorCheck = new HashSet<>();

        // Chỉ lặp qua số người chơi đã chọn
        for (int i = 0; i < numPlayers; i++) {
            int selectedSkinIndex = selectedCharacterIndices.get(i); // Lấy index skin đã chọn

            // Lấy màu tương ứng (cần cẩn thận với index)
            String color;
            if (selectedSkinIndex < characterBaseColors.length) {
                 color = characterBaseColors[selectedSkinIndex];
                 // Hoặc bạn có thể có logic phức tạp hơn để xác định màu từ skin path
            } else {
                 // Nếu index vượt quá số màu cơ bản, dùng màu mặc định hoặc báo lỗi
                 color = "DefaultColor" + i; // Ví dụ: đảm bảo không trùng
                 System.err.println("Cảnh báo: Không tìm thấy màu cơ bản cho skin index " + selectedSkinIndex);
            }

            // Kiểm tra trùng màu
             if (!uniqueColorCheck.add(color)) {
                 JOptionPane.showMessageDialog(this,
                     "Duplicate color ('" + color + "') selected for Player " + (i + 1) + ".\nPlease choose unique base colors.",
                     "Selection Error", JOptionPane.ERROR_MESSAGE);
                 setupAnimationTimer();
                 return;
             }
            finalPlayerColors.add(color); // Thêm màu vào list cuối cùng

            // Lấy đường dẫn thư mục skin đã chọn
            if (selectedSkinIndex < characterAnimationFolderPaths.length) {
                finalPlayerSkinPaths.add(characterAnimationFolderPaths[selectedSkinIndex]); // <<--- Thêm đường dẫn skin
            } else {
                 JOptionPane.showMessageDialog(this, "Invalid skin index for Player " + (i + 1), "Error", 0);
                 setupAnimationTimer();
                 return;
            }
        }

        System.out.println("Starting game with " + numPlayers + " players.");
        System.out.println("Selected colors: " + finalPlayerColors);
        System.out.println("Selected skin paths: " + finalPlayerSkinPaths); // <<--- In ra đường dẫn skin

        PlayerNumberAndSkin.playersNumber = getPlayerCount();
        for (int i = 0; i < getPlayerCount(); i++) {
            PlayerNumberAndSkin.playersSkin[i] = getSelectedCharacterSkins().get(i);
        }

        // --- Khởi động game ---
        if (parent.bgrMusic != null) { parent.bgrMusic.stop(); }
        parent.dispose();

        // *** GỌI CONSTRUCTOR GameFrame MỚI (CẦN TẠO) ***
        // Truyền cả danh sách màu và danh sách đường dẫn skin
        GameFrame gameFrame = new GameFrame();
    }

    //ham tra ve so luong nguoi choi
    public int getPlayerCount() {
        return (int) playerCountComboBox.getSelectedItem();
    }
    
    //ham tra ve danh sach cac skin
    public List<String> getSelectedCharacterSkins() {
        List<String> selectedSkins = new ArrayList<>();
        int numPlayers = getPlayerCount();
        for (int i = 0; i < numPlayers; i++) {
            int selectedIndex = selectedCharacterIndices.get(i);
            if (selectedIndex >= 0 && selectedIndex < characterAnimationFolderPaths.length) {
                selectedSkins.add(characterAnimationFolderPaths[selectedIndex]);
            } else {
                selectedSkins.add("Invalid skin index");
            }
        }
        return selectedSkins;
    }    
}
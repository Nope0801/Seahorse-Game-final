// --- Sửa đổi GameFrame.java ---
package com.seahorse.view;

import com.seahorse.controller.GameThread;
import com.seahorse.model.GameSaveData; // Import GameSaveData
import com.seahorse.model.GameSetting;
import com.seahorse.model.PlayerNumberAndSkin;
import javax.swing.*;

public class GameFrame extends JFrame {

    private GameThread gameThread; // Giữ tham chiếu đến GameThread

    // Constructor mặc định (cho New Game)
    public GameFrame() {
        initializeFrame();
        gameThread = new GameThread(this); // Truyền tham chiếu GameFrame
        this.add(gameThread);
        // Bắt đầu game thread sau khi frame hiển thị
        SwingUtilities.invokeLater(() -> {
             gameThread.startGameThread();
             this.setVisible(true);
        });
    }

    public GameFrame(GameSaveData saveData) {
        if (saveData == null) {
            // JOptionPane.showMessageDialog(null, "Failed to load save data. Returning to main menu.", "Load Error", JOptionPane.ERROR_MESSAGE);
            SwingUtilities.invokeLater(MainMenu::new);
            dispose();
            return;
        }
        initializeFrame();
        gameThread = new GameThread(this);
        this.add(gameThread);

        SwingUtilities.invokeLater(() -> {
            PlayerNumberAndSkin.playersNumber = saveData.getPlayersNumber();
            gameThread.startGameThread();
             Timer waitTimer = new Timer(100, e -> {
                 if (gameThread.getGameController() != null) {
                      ((Timer)e.getSource()).stop();
                      gameThread.getGameController().loadGame(gameThread.getGameController(), saveData);
                      System.out.println("Load game called after thread start.");
                 } else {
                     System.out.println("Waiting for GameController to initialize...");
                 }
             });
             waitTimer.setRepeats(true);
             waitTimer.start();
             this.setVisible(true);
        });
    }


    // Hàm khởi tạo chung cho frame
    private void initializeFrame() {
        setTitle("SeaHorse Game"); // Đặt tiêu đề ở đây
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Có thể để true nếu muốn thay đổi kích thước
        setSize(GameSetting.screenWidth, GameSetting.screenHeight);
        setLocationRelativeTo(null); // Căn giữa màn hình
    }

    // Phương thức để quay về Main Menu
    public void returnToMainMenu() {
        // Dừng game loop (nếu đang chạy)
        if (gameThread != null && gameThread.gameThread != null) {
             gameThread.gameThread.interrupt(); // Ngắt luồng game
             gameThread.gameThread = null; // Đặt là null để vòng lặp dừng
        }

        // Đóng cửa sổ game hiện tại
        this.dispose();

        // Mở lại Main Menu
        SwingUtilities.invokeLater(() -> new MainMenu()); // Tạo instance mới của MainMenu
    }
}
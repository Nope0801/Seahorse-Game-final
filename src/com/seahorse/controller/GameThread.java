package com.seahorse.controller;

import com.seahorse.model.GameSaveData;
import com.seahorse.model.GameSetting;
import com.seahorse.model.PaintData;
import com.seahorse.model.UpdateData;
import com.seahorse.utils.GameSaveManager;
import com.seahorse.utils.PaintComponent;
import com.seahorse.utils.UpdateComponent;
import com.seahorse.view.GameFrame;
import com.seahorse.view.pauseMenuPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class GameThread extends JPanel implements Runnable {

    private int maxScreenCol = 42;
    private int maxScreenRow = 24;
    private GameController gameController;
    private GameFrame gameFrame; 
    private pauseMenuPanel pauseMenuPanel;
    private volatile boolean paused = false;
    public Thread gameThread;

    public int boardStartX = (maxScreenCol - 15) / 2;
    public int boardStartY = (maxScreenRow - 15) / 2;

    public GameThread(GameFrame frame) {
        this.gameFrame = frame;
        this.setLayout(null);
    }

    public void setupGame() {
        gameController = new GameController(this);

        pauseMenuPanel = new pauseMenuPanel(this, gameController, gameFrame);
        int menuWidth = 450;
        int menuHeight = 400;
        pauseMenuPanel.setBounds((GameSetting.screenWidth - menuWidth) / 2,
                                (GameSetting.screenHeight - menuHeight) / 2,
                                menuWidth, menuHeight);
        this.add(pauseMenuPanel);
        this.setComponentZOrder(pauseMenuPanel, 0); // Đặt menu ở trên cùng

        InputMap im = this.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = this.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "togglePause");
        am.put("togglePause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });

        JButton saveButton = new JButton("save");
        // saveButton.setBounds(GameSetting.screenWidth - 100 - 36 - 100, GameSetting.screenHeight / 2 + 50, 64, 32);
        saveButton.addActionListener(e -> gameController.saveGame());
        this.add(saveButton);

        JButton loadButton = new JButton("load");
        // loadButton.setBounds(GameSetting.screenWidth - 100 - 36 - 200, GameSetting.screenHeight / 2 + 50, 64, 32);

        loadButton.addActionListener(e -> {
            System.out.println("Load button clicked inside GameThread.");
            GameSaveData saveData = GameSaveManager.loadGame(); // Bước 1: Load data từ file
            if (saveData != null) {
                // Bước 2: Gọi loadGame của controller với data vừa load
                gameController.loadGame(gameController, saveData);
                System.out.println("Called gameController.loadGame with loaded data."); // Debug
            } else {
                System.err.println("Failed to load save data from file in GameThread load button.");
                // Hiển thị thông báo lỗi cho người dùng
                JOptionPane.showMessageDialog(this,
                    "Could not load save file or file is empty/corrupted.",
                    "Load Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        this.add(loadButton);

    }

    public void togglePause() {
        if (paused) {
            resumeGame();
        } else {
            pauseGame();
        }
    }

    public void pauseGame() {
        if (!paused) {
            paused = true;
            pauseMenuPanel.setVisible(true);
            // System.out.println("Game Paused");
        }
    }

    public void resumeGame() {
        if (paused) {
            paused = false;
            pauseMenuPanel.setVisible(false);
            // System.out.println("Game Resumed");
        }
    }

    @Override
    public void paintComponent(Graphics grp) {
        super.paintComponent(grp);
        Graphics2D grp2D = (Graphics2D) (grp);
        for (PaintComponent paintEntity : PaintData.paintEntities) {
            paintEntity.Paint(grp);
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        setupGame();

        long timePerFrame = 1000000000 / GameSetting.maxFPS;
        while (gameThread != null) {
            long startFrameTime = System.nanoTime();

            if (!paused) {
                Update();
            }
            repaint();

            try {
                long processTime = System.nanoTime() - startFrameTime;
                long sleepTime = (timePerFrame - processTime) / 1000000;

                if (sleepTime < 0) {
                    sleepTime = 5; // Ngủ tối thiểu để tránh CPU 100%
                }
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                 Thread.currentThread().interrupt();
                 System.err.println("Game thread interrupted");
            }
        }
    }

    private void Update() {
        for (UpdateComponent update : UpdateData.updateComponents) {
            update.Update();
        }
    }

    public GameController getGameController() { return gameController; }
}

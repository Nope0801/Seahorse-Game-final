package com.seahorse.view;

import com.seahorse.controller.GameThread;
import com.seahorse.model.GameSetting;
import javax.swing.JFrame;

public class GameFrame {
    private JFrame window;
    // private MenuView menuView;
    // private GameView gameView;
    // private Game game;
    // private GameController gameController;

    public GameFrame() {
        window = new JFrame("SeaHorse Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(GameSetting.screenWidth, GameSetting.screenHeight);
        GameThread gameThread = new GameThread();
        window.add(gameThread);

        gameThread.startGameThread();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        // menuView = new MenuView();
        // MenuController menuController = new MenuContainer(menuView, this::startGame);

    }
}

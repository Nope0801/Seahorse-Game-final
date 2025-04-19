package com.seahorse.view;

import com.seahorse.controller.GameThread;
import com.seahorse.model.GameSetting;
import java.util.List;
import javax.swing.*;

public class GameFrame extends JFrame {

    private int numberOfPlayers;
    private List<String> playerColors;
    private final JFrame window;

    public GameFrame() {
        // this.playerColors = new ArrayList<>(null);
        // this.numberOfPlayers = this.playerColors.size();


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
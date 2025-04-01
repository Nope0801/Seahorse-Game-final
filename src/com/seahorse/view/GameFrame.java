package com.seahorse.view;

import javax.swing.JFrame;

import com.seahorse.controller.GameController;
import com.seahorse.controller.MenuController;
import com.seahorse.model.Game;

public class GameFrame {
    private JFrame window;
    // private MenuView menuView;
    // private GameView gameView;
    // private Game game;
    // private GameController gameController;

    public GameFrame(){
        window = new JFrame("SeaHorse Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        // menuView = new MenuView();
        // MenuController menuController = new MenuContainer(menuView, this::startGame);
        
        public void start(){
            window.setVisible(true);
        }

        
        
    }
}

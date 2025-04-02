package com.seahorse.model;

import java.util.List;

//Lưu trữ tất cả entities
public class Game {
    //PLAYER
    private List<Player> players;
    private Dice dice;
    private int currentPlayerIndex;

    //BOARD
    private Board board;

    public Game() {
        
    }
}

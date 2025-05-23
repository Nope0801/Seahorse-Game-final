package com.seahorse.model;

import java.io.Serializable;
import java.util.List;

import com.seahorse.model.Board.TileType;

public class GameSaveData implements Serializable {
    
    private List<PlayerSaveData> players;
    private int currentPlayerIndex;
    private int diceNumber;
    private int playersNumber;
    private String playersSkin[] ;
    private boolean isBot[];
    private TileType[][] tiles;

    public GameSaveData(List<PlayerSaveData> players, int currentPlayerIndex, int diceNumber, int nb,String playersSkin[] , boolean isBot[], TileType[][] tiles) {
        
        this.players = players;
        this.currentPlayerIndex = currentPlayerIndex;
        this.diceNumber = diceNumber;
        playersNumber = nb;
        this.playersSkin=playersSkin;
        this.isBot = isBot;
        this.tiles = tiles;
    }
    // public GameSaveData( List<PlayerSaveData> players, int currentPlayerIndex, int diceNumber) {
       
    //     this.players = players;
    //     this.currentPlayerIndex = currentPlayerIndex;
    //     this.diceNumber = diceNumber;
    // }
   
    public List<PlayerSaveData> getPlayers() {
        return players;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getDiceNumber() {
        return diceNumber;
    }
    public int getPlayersNumber() {
        return playersNumber;
    }
    public TileType[][] getTiles() {
        return tiles;
    }
    public String[] getPlayersSkin() {
        return playersSkin;
    }

    public boolean[] getIsBot() {
        return isBot;
    }
}

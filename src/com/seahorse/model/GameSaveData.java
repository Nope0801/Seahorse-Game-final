package com.seahorse.model;

import java.io.Serializable;
import java.util.List;

public class GameSaveData implements Serializable {
    
    private List<PlayerSaveData> players;
    private int currentPlayerIndex;
    private int diceNumber;
    private int playersNumber;

    public GameSaveData(List<PlayerSaveData> players, int currentPlayerIndex, int diceNumber, int nb) {
        
        this.players = players;
        this.currentPlayerIndex = currentPlayerIndex;
        this.diceNumber = diceNumber;
        playersNumber = nb;
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
}

package com.seahorse.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.seahorse.controller.PlayerController;

public class PlayerSaveData implements Serializable {
    private int playerIndex; 
    private String color; 
    private int rerollCount; 

    private List<SeaHorseSaveData> seaHorses;

   public PlayerSaveData(PlayerController pc) {
        this.playerIndex = pc.getPlayerData().getPlayerIndex();
        this.color = pc.getColor();
        this.seaHorses = new ArrayList<>();
        for (SeaHorse horse : pc.getSeaHorseDataList()) {
            this.seaHorses.add(new SeaHorseSaveData(horse));
        }

       
    }

    // Getters & Setters
    public String getColor() {
        return color;
    }
    public int getPlayerIndex() {
        return playerIndex;
    }
    public int getRerollCount() {
        return rerollCount;
    }

    public void setRerollCount(int rerollCount) {
        this.rerollCount = rerollCount;
    }

    public List<SeaHorseSaveData> getSeaHorses() {
        return seaHorses;
    }

    public void setSeaHorses(List<SeaHorseSaveData> seaHorses) {
        this.seaHorses = seaHorses;
    }
}

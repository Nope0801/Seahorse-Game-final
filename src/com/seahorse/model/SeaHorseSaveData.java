package com.seahorse.model;
import com.seahorse.utils.SeaHorseState;
import java.io.Serializable;

public class SeaHorseSaveData implements Serializable {
    private int x,y;
    private int relativeX;
    private int relativeY;
    private SeaHorseState state;

    private String skinId; 

    public SeaHorseSaveData(SeaHorse seaHorse) {
        this.x = seaHorse.getX();
        this.y = seaHorse.getY();
        this.relativeX = seaHorse.getRelativeX();
        this.relativeY = seaHorse.getRelativeY();
        this.state = seaHorse.getState(); 
    }

    // Getters & Setters
    public int getRelativeX() {
        return relativeX;
    }

    public void setRelativeX(int relativeX) {
        this.relativeX = relativeX;
    }

    public int getRelativeY() {
        return relativeY;
    }

    public void setRelativeY(int relativeY) {
        this.relativeY = relativeY;
    }


    public SeaHorseState getState() {
        return state;
    }

    public void setState(SeaHorseState state) {
        this.state = state;
    }

    public String getSkinId() {
        return skinId;
    }

    public void setSkinId(String skinId) {
        this.skinId = skinId;
    }
}

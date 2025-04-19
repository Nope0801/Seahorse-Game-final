package com.seahorse.model;
import com.seahorse.utils.SeaHorseState;
import java.io.Serializable;

public class SeaHorseSaveData implements Serializable {
    private int x,y;
    private int relativeX;
    private int relativeY;
    private SeaHorseState state;
    private boolean isInFinish;
    private boolean isInGoal;
    private String skinId; 

    public SeaHorseSaveData(SeaHorse seaHorse) {
        this.x = seaHorse.getX();
        this.y = seaHorse.getY();
        this.relativeX = seaHorse.getRelativeX();
        this.relativeY = seaHorse.getRelativeY();
        this.state = seaHorse.getState(); 
        this.isInFinish = seaHorse.getIsInFinish();
        this.isInGoal = seaHorse.getIsInGoal();
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
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean getIsInFinish(){
        return isInFinish;
    }
    public void setInFinish(boolean isInFinish) {
        this.isInFinish = isInFinish;
    }
    public void setInGoal(boolean isInGoal) {
        this.isInGoal = isInGoal;
    }
    public boolean getIsInGoal(){
        return isInGoal;
    }
}

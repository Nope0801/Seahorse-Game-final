package com.seahorse.model;

import java.util.Random;

public class Dice {
    private Random random;

    public Dice(){
        random = new Random();
    }
    public int roll(){
        return random.nextInt(6) + 1;
    }
    public Random getRandom() {
        return random;
    }
}
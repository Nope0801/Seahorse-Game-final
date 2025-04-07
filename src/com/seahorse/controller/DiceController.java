package com.seahorse.controller;

import com.seahorse.model.Dice;
import com.seahorse.model.Game;
import com.seahorse.view.DiceView;

public class DiceController{
    private Dice diceData;
    private DiceView diceView;
    private Game gameData;

    public DiceController(Game gd) {
        diceData = new Dice();
        diceView = new DiceView(diceData.getDiceBorder(0));
        gameData = gd;
        SetDiceDefault();
    }

    public int Roll() {
        // int number = diceData.getRandom().nextInt(6) + 1;
        int number = 6;
        diceView.SetDiceImage(diceData.getDiceImages(number));
        return number;
    }

    public void SetDiceDefault() {
        diceView.SetDiceImage(diceData.getDiceImages(0));
        diceView.setBorderImage(diceData.getDiceBorder(gameData.getCurrentPlayerIndex()));
    }
}

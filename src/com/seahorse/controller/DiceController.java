package com.seahorse.controller;

import com.seahorse.model.Dice;
import com.seahorse.model.Game;
import com.seahorse.model.GameSetting;
import com.seahorse.view.DiceView;
import javax.swing.JButton;

public class DiceController{
    private Dice diceData;
    private DiceView diceView;
    private Game gameData;

    public DiceController(Game gd, GameThread panel) {
        diceData = new Dice();
        diceView = new DiceView(diceData.getDiceBorder(0));
        gameData = gd;
        SetDiceDefault();

        JButton cheatButton = new JButton("Cheat");
        cheatButton.setBounds((GameSetting.screenWidth) - 64 - 36, GameSetting.screenHeight / 2, 64, 64);
        cheatButton.addActionListener(e -> ActiveCheatMode());
        panel.add(cheatButton);
    }

    public int Roll() {
        int number;
        if (!cheatEnable) {
            number = diceData.getRandom().nextInt(6) + 1;
        }else {
            number = 6;
        }
        diceView.SetDiceImage(diceData.getDiceImages(number));
        return number;
    }

    public void SetDiceDefault() {
        diceView.SetDiceImage(diceData.getDiceImages(0));
        diceView.setBorderImage(diceData.getDiceBorder(gameData.getCurrentPlayerIndex()));
    }

    private boolean cheatEnable = false;
    public void ActiveCheatMode() {
        System.out.println("Cheat Mode");
        cheatEnable = !cheatEnable;
    }
}

package com.seahorse.model;

import com.seahorse.controller.PlayerController;
import com.seahorse.controller.SeaHorseController;
import com.seahorse.utils.ImageFromPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;

public class Player{
    private String color;
    private int playerIndex;

    private int spawnPos[][];
    private int startPos[];

    private SeaHorseController seaHorses[] = new SeaHorseController[4];

    private JButton chooseSeaHorseButtons[] = new JButton[4];
    private JButton spawnSeaHorseButton = new JButton();

    private ArrayList<BufferedImage> buttonAnim = new ArrayList<>();
    private File buttonImgsFolder[] = new File("src/resources/sprites/Player/arrow_button_animation").listFiles();

    public Player(String _color, int _playerIndex, int _spawnPos[][], int _startPos[], PlayerController pc) {
        color = _color;
        playerIndex = _playerIndex;
        spawnPos = _spawnPos;
        startPos = _startPos;

        for (File file : buttonImgsFolder) {
            buttonAnim.add(ImageFromPath.GetBufferedImageFromPath(file.getPath()));
        }

        spawnSeaHorseButton.setBounds(0, 0, 64, 64);
        spawnSeaHorseButton.setOpaque(false);
        spawnSeaHorseButton.setContentAreaFilled(false);
        spawnSeaHorseButton.setBorderPainted(false);
        spawnSeaHorseButton.setBorder(null);
        spawnSeaHorseButton.setVisible(false);

        for (int i = 0; i < 4; i++) {
            chooseSeaHorseButtons[i] = new JButton();
            chooseSeaHorseButtons[i].setBounds(0, 0, 64, 64);
            chooseSeaHorseButtons[i].setOpaque(false);
            chooseSeaHorseButtons[i].setContentAreaFilled(false);
            chooseSeaHorseButtons[i].setBorderPainted(false);
            chooseSeaHorseButtons[i].setBorder(null);
            chooseSeaHorseButtons[i].setVisible(false);
        }

        for (int i = 0; i < 4; i++) {
            seaHorses[i] = new SeaHorseController(pc, spawnPos[i][0], spawnPos[i][1]);
        } 
    }

    public SeaHorseController[] getSeaHorses() {
        return seaHorses;
    }

    public JButton[] getChooseSeaHorseButtons() {
        return chooseSeaHorseButtons;
    }

    public JButton getSpawnSeaHorseButton() {
        return spawnSeaHorseButton;
    }

    public int[] getStartPos() {
        return startPos;
    }

    public int[][] getSpawnPos() {
        return spawnPos;
    }

    public ArrayList<BufferedImage> getButtonAnim() {
        return buttonAnim;
    }

    public String getColor() {
        return color;
    }
}

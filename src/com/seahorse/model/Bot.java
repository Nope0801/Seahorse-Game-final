package com.seahorse.model;

import com.seahorse.controller.PlayerController;

public class Bot extends Player {
    public Bot(String _color, int _playerIndex, int _spawnPos[][], int _startPos[], PlayerController pc) {
        super(_color, _playerIndex, _spawnPos, _startPos, pc);
        // Bot không cần nút giao diện, vì vậy không khởi tạo chooseSeaHorseButtons và spawnSeaHorseButton
        // Các thuộc tính khác (color, playerIndex, seaHorses, spawnPos, startPos) được kế thừa từ Player
    }
}
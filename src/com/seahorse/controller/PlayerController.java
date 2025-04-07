package com.seahorse.controller;

import com.seahorse.model.Board;
import com.seahorse.model.Player;
import com.seahorse.utils.SeaHorseState;
import com.seahorse.view.PlayerView;

public class PlayerController {
    private GameController gameController;
    private Player playerData;
    private PlayerView playerView;

    String color = "";
    public PlayerController(GameThread panel, GameController _gameController, int[][] spawnPos, int[] startPos, int playerIndex) {
        gameController = _gameController;
        switch (playerIndex) {
            case 0:
                color = "red";
                break;
            case 1:
                color = "blue";
                break;
            case 2:
                color = "green";
                break;
            case 3:
                color = "yellow";
                break;
        }
        playerData = new Player(color, playerIndex, spawnPos, startPos, this);
        playerView = new PlayerView(playerData.getButtonAnim());

        for (int i = 0; i < 4; i++) {
            gameController.AddSeaHorseOnMap(playerData.getSeaHorses()[i], playerData.getSpawnPos()[i][0], playerData.getSpawnPos()[i][1]);
        }
        
        playerData.getSpawnSeaHorseButton().addActionListener(e -> {
            SpawnSeaHorse();
        });
        panel.add(playerData.getSpawnSeaHorseButton());//them vao panel
        
        for (int i = 0; i < 4; i++) {
            int index = i;
            playerData.getChooseSeaHorseButtons()[i].addActionListener(e -> {
                ChooseSeaHorseToMove(index);
            });
            panel.add(playerData.getChooseSeaHorseButtons()[i]);//them vao panel
        }
    }

    public void StartTurn(int diceNumber) {
        //Kiem tra so cua dice
            //Neu dice = 6
                //Hien thi lua chon spawn
        int c[];
        if (diceNumber == 6 && gameController.CheckTile(playerData.getStartPos()[0], playerData.getStartPos()[1])) {
            c = Board.changeRelativeCoordinates(playerData.getStartPos()[0], playerData.getStartPos()[1]);
            playerData.getSpawnSeaHorseButton().setBounds(c[0], c[1] - 64, 64, 128);
            playerData.getSpawnSeaHorseButton().setVisible(true);
            playerView.ActiveButton(4, playerData.getStartPos());
        }
        
        //Kiem tra co seahorse nao co the di chuyen ko
            //Neu co
                //Hien thi nut di chuyen cua seahorse
        for (int i = 0; i < 4; i++) {
            if (playerData.getSeaHorses()[i].getState() == SeaHorseState.CanMove && !playerData.getSeaHorses()[i].getSeaHorseData().isInGoal) {
                c = Board.changeRelativeCoordinates(playerData.getSeaHorses()[i].getRelative()[0], playerData.getSeaHorses()[i].getRelative()[1]);
                playerData.getChooseSeaHorseButtons()[i].setBounds(c[0], c[1] - 64, 64, 128);
                playerData.getChooseSeaHorseButtons()[i].setVisible(true);
                playerView.ActiveButton(i, playerData.getSeaHorses()[i].getRelative());
            }
        }
    }

    //Nguoi dung bam nut

    //Thuc thi khi nguoi dung bam di chuyen
    public void ChooseSeaHorseToMove(int index) {
        //Lay ra seahorse can di chuyen tuong ung
        SeaHorseController sh = playerData.getSeaHorses()[index];
        //Gan step, chuyen seahorse sang trang thai buoc dau trong di chuyen
        sh.StartSeaHorseAction();
        sh.getSeaHorseData().setStepLeft(gameController.getGameData().getDiceNumber());
        //Bao cho gamecontroller biet seahorse dc chon de di chuyen va bat dau di chuyen
        gameController.SeaHorseInControl(sh);
        EndPlayerAction();
    }

    //Thuc thi khi nguoi dung bam spawn
    public void SpawnSeaHorse() {
        //Tim mot seahorse chua dc spawn va lay ra
        //Dat seahorse vao vi tri spawn va chuyen seahorse sang trang thai co the di chuyen
        //Bao cho gamecontroller biet seahorse dc spawn
        for (int i = 0; i < 4; i++) {
            if (playerData.getSeaHorses()[i].getState() == SeaHorseState.InStable) {
                gameController.RemoveSeaHorseOnMap(playerData.getSpawnPos()[i][0], playerData.getSpawnPos()[i][1]);
                gameController.AddSeaHorseOnMap(playerData.getSeaHorses()[i], playerData.getStartPos()[0], playerData.getStartPos()[1]);
                gameController.SeaHorseInControl(playerData.getSeaHorses()[i]);
                playerData.getSeaHorses()[i].setState(SeaHorseState.IsStep);
                playerData.getSeaHorses()[i].Move(1, playerData.getStartPos()[0], playerData.getStartPos()[1]);
                System.out.println(playerData.getSeaHorses()[i].getRelative()[0] + " " + playerData.getSeaHorses()[i].getRelative()[1]);
                break;
            }
        }
        EndPlayerAction();
    }

    public void EndPlayerAction() {
        playerData.getSpawnSeaHorseButton().setVisible(false);

        for (int i = 0; i < 4; i++) {
            playerData.getChooseSeaHorseButtons()[i].setVisible(false);
        }

        playerView.DeactiveAllButton();
    }

    public Player getPlayerData() {
        return playerData;
    }

    public String getColor() {
        return color;
    }

    public int getSeaHorsesInGoalNumber() {
        int c = 0;
        for (SeaHorseController sh : playerData.getSeaHorses()) {
            c += (sh.getSeaHorseData().isInGoal) ? 1 : 0;
        }
        return c;
    }
}

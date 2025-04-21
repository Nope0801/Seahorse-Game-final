package com.seahorse.utils;

import com.seahorse.controller.GameController;
import com.seahorse.controller.PlayerController;
import com.seahorse.controller.SeaHorseController;
import com.seahorse.model.Board;
import com.seahorse.utils.SeaHorseState;

public class GameUpdate {

    public static void spawnHorseForPlayer(GameController gameController, int playerIndex, int horseIndex, int relX, int relY, SeaHorseState state, boolean ifn, boolean ig, String sId) {
        PlayerController pc = gameController.getGameData().getPlayersController().get(playerIndex);
        SeaHorseController horseCtrl = pc.getSeaHorses()[horseIndex];

        // Cập nhật tọa độ tương đối
        horseCtrl.setRelative(relX, relY);

        // Tính tọa độ thực tế (pixel)
        int[] abs = Board.changeRelativeCoordinates(relX, relY);
        horseCtrl.getSeaHorseData().setX(abs[0]);
        horseCtrl.getSeaHorseData().setY(abs[1]);

        // Cập nhật view (hiển thị)
        horseCtrl.getSeaHorseView().setPosition(abs[0], abs[1]);

        // cập nhập lại state
        horseCtrl.setState(state);
        horseCtrl.getSeaHorseData().setState(state);
        horseCtrl.getSeaHorseData().setInFinish(ifn);
        horseCtrl.getSeaHorseData().setInGoal(ig);
        horseCtrl.getSeaHorseData().setSkinID(sId);

        // Thêm vào bản đồ
        gameController.getGameData().getEntitiesMap().AddSeaHorseToTile(horseCtrl, relX, relY);
    }
    
}
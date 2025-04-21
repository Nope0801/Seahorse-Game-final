package com.seahorse.controller;
import com.seahorse.controller.Button.SkipButton;
import com.seahorse.model.SeaHorse;
import com.seahorse.utils.SeaHorseState;
import com.seahorse.view.PlayerView;
import java.util.List;
import com.seahorse.model.Board;
import java.util.ArrayList;
import java.util.Random;

public class BotController extends PlayerController {
    public BotController(GameThread panel, GameController _gameController, int[][] spawnPos, int[] startPos, int playerIndex) {
        super(panel, _gameController, spawnPos, startPos, playerIndex);
        // Bot không cần giao diện, vì vậy không cần thêm nút vào panel
        // Tạo một PlayerView mặc định (có thể bỏ qua nếu không cần)
        playerView = new PlayerView(new java.util.ArrayList<>());
    }


    @Override
    public void StartTurn(int diceNumber) {
        // Kiểm tra các quân cờ có thể di chuyển
        boolean canMove = false;
        for (int i = 0; i < 4; i++) {
            if (getPlayerData().getSeaHorses()[i].getState() == SeaHorseState.CanMove && !getPlayerData().getSeaHorses()[i].getSeaHorseData().isInGoal) {
                canMove = true;
                break;
            }
        }

        // Kiểm tra xem có thể đưa quân cờ ra từ chuồng không
        boolean canSpawn = false;
        if (diceNumber == 6) {
            for (int i = 0; i < 4; i++) {
                if (getPlayerData().getSeaHorses()[i].getState() == SeaHorseState.InStable) {
                    canSpawn = true;
                    break;
                }
            }
        }

        // Logic AI đơn giản: Ưu tiên đưa quân cờ ra nếu xúc xắc là 6 và có thể spawn
        if (diceNumber == 6 && canSpawn && getGameController().CheckTile(getPlayerData().getStartPos()[0], getPlayerData().getStartPos()[1])) {
            SpawnSeaHorse();
        } else if (canMove) {
            // Chọn quân cờ ngẫu nhiên để di chuyển
            java.util.Random rand = new java.util.Random();
            int index;
            do {
                index = rand.nextInt(4);
            } while (getPlayerData().getSeaHorses()[index].getState() != SeaHorseState.CanMove || getPlayerData().getSeaHorses()[index].getSeaHorseData().isInGoal);
            ChooseSeaHorseToMove(index);
        } else {
            // Nếu không thể di chuyển hoặc spawn, bỏ lượt
           gameController.EndPlayerTurn();
            
        }
    }

    @Override
    public void SpawnSeaHorse() {
        for (int i = 0; i < 4; i++) {
            if (getPlayerData().getSeaHorses()[i].getState() == SeaHorseState.InStable) {
                getGameController().RemoveSeaHorseOnMap(getPlayerData().getSpawnPos()[i][0], getPlayerData().getSpawnPos()[i][1]);
                getGameController().AddSeaHorseOnMap(getPlayerData().getSeaHorses()[i], getPlayerData().getStartPos()[0], getPlayerData().getStartPos()[1]);
                getGameController().SeaHorseInControl(getPlayerData().getSeaHorses()[i]);
                getPlayerData().getSeaHorses()[i].setState(SeaHorseState.IsStep);
                getPlayerData().getSeaHorses()[i].Move(1, getPlayerData().getStartPos()[0], getPlayerData().getStartPos()[1]);
                System.out.println("Bot spawned SeaHorse at " + getPlayerData().getSeaHorses()[i].getRelative()[0] + " " + getPlayerData().getSeaHorses()[i].getRelative()[1]);
                break;
            }
        }
        // EndPlayerAction();
    }

    @Override
    public void ChooseSeaHorseToMove(int index) {
        SeaHorseController sh = getPlayerData().getSeaHorses()[index];
        sh.StartSeaHorseAction();
        sh.getSeaHorseData().setStepLeft(getGameController().getGameData().getDiceNumber());
        getGameController().SeaHorseInControl(sh);
        EndPlayerAction();
    }

    @Override
    public void EndPlayerAction() {
        // Bot không cần ẩn nút giao diện, chỉ cần thông báo kết thúc hành động
        playerView.DeactiveAllButton();
       
        System.out.println("Bot ended action");
    }
}
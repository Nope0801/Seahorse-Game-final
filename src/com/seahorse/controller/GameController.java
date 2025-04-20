package com.seahorse.controller;

import com.seahorse.model.Board;
import com.seahorse.model.Board.TileType;
import com.seahorse.model.Game;
import com.seahorse.model.GameSaveData;
import com.seahorse.model.GameSetting;
import com.seahorse.model.PlayerSaveData;
import com.seahorse.model.SeaHorseSaveData;
import com.seahorse.utils.GameSaveManager;
import com.seahorse.utils.GameUpdate;
import com.seahorse.utils.SeaHorseState;
import com.seahorse.utils.UpdateComponent;
import com.seahorse.view.GameView;
import java.util.ArrayList;
import java.util.List;

public class GameController implements UpdateComponent {
    private GameThread panel;
    private Game game;
    private GameView gameView;

    private boolean canShowActive = false;

    public GameController(GameThread _panel) {
        panel = _panel;
        UpdateComponent.AddUpdate(this);

        game = new Game();

        gameView = new GameView(game.getBackgroundImage(), panel, game);

        game.setBoard();

        game.setEntitiesMap();

        game.setPlayersController(4, panel, this);
        game.setCurrentPlayerIndex(0);

        game.setSkipButton(GameSetting.screenWidth / 2 - 275, GameSetting.screenHeight - 117, this);
        panel.add(game.getSkipButton().getButton());

        game.setRollButton(GameSetting.screenWidth / 2 + 75, GameSetting.screenHeight - 117, this);
        panel.add(game.getRollButton().getButton());

        game.setDiceController(game, panel);
        game.getDiceController().SetDiceDefault();

        game.setWinMenu(0);
    }

    @Override
    public void Update() {
        SeaHorseMonitor();
        CheckPlayersProgress();
    }

    public void RollDice() {
        game.setDiceNumber(game.getDiceController().Roll());
    }

    public void StartPlayerTurn() {
        game.getPlayerController().StartTurn(game.getDiceNumber());
    }

    public void SeaHorseInControl(SeaHorseController inControl) {
        game.setControlledSeaHorse(inControl);
        game.getSkipButton().UnactiveButton();
    }

    public void SeaHorseMonitor() {
        if (game.getControlledSeaHorse() == null)
            return;

        SeaHorseController sh = game.getControlledSeaHorse();

        // Kiem tra trang thai cua sh co phai move ko
        // Neu co
        // return
        if (sh.getState() == SeaHorseState.IsStep) {
            return;
        }

        // Kiem tra trang thai co phai start ko
        // Neu co
        // Kiem tra o tiep theo co ngua khac ngua dong minh ko
        // Neu co
        // Xem co phai buoc cuoi ko
        // Neu co
        // Da
        // Neu ko
        // Ket thuc di chuyen
        if (sh.getState() == SeaHorseState.StartStep && !sh.getSeaHorseData().isInFinish) {
            int nextTile[] = Board.getNextTile(sh.getRelative()[0], sh.getRelative()[1]);
            if (CheckTile(nextTile[0], nextTile[1])) {
                RemoveSeaHorseOnMap(sh.getRelative()[0], sh.getRelative()[1]);
                AddSeaHorseOnMap(sh, nextTile[0], nextTile[1]);
                sh.Move(sh.getSeaHorseData().getStepLeft(), nextTile[0], nextTile[1]);
                sh.setState(SeaHorseState.IsStep);
            } else {
                if (game.getEntitiesMap().GetTileSeaHorse(nextTile[0], nextTile[1]).player == sh.player) {
                    sh.Move(0, sh.getRelative()[0], sh.getRelative()[1]);
                    sh.setState(SeaHorseState.EndAction);
                } else {
                    if (sh.getSeaHorseData().getStepLeft() == 1) {
                        // da ngua
                        SeaHorseController shk = game.getEntitiesMap().GetTileSeaHorse(nextTile[0], nextTile[1]);
                        shk.BackToStable();
                        shk.setState(SeaHorseState.IsStep);
                        shk.getSeaHorseData().isInFinish = false;
                        AddSeaHorseOnMap(shk, shk.getRelative()[0], shk.getRelative()[1]);

                        RemoveSeaHorseOnMap(sh.getRelative()[0], sh.getRelative()[1]);
                        AddSeaHorseOnMap(sh, nextTile[0], nextTile[1]);
                        sh.Move(sh.getSeaHorseData().getStepLeft(), nextTile[0], nextTile[1]);
                        sh.setState(SeaHorseState.IsStep);
                    } else {
                        sh.Move(0, sh.getRelative()[0], sh.getRelative()[1]);
                        sh.setState(SeaHorseState.EndAction);
                    }
                }
            }
        } else if (sh.getState() == SeaHorseState.StartStep && sh.getSeaHorseData().isInFinish) {
            int nextTile[] = {game.getBoard().winPath[game.getDiceNumber() - 1 + (6 * game.getCurrentPlayerIndex())][0], game.getBoard().winPath[game.getDiceNumber() - 1 + (6 * game.getCurrentPlayerIndex())][1]};
            System.out.println(nextTile[0] + "" + nextTile[1]);
            if (CheckTile(nextTile[0], nextTile[1])) {
                RemoveSeaHorseOnMap(sh.getRelative()[0], sh.getRelative()[1]);
                AddSeaHorseOnMap(sh, nextTile[0], nextTile[1]);
                sh.Move(1, nextTile[0], nextTile[1]);
                sh.setState(SeaHorseState.IsStep);
                sh.getSeaHorseData().isInGoal = true;
            } else {
                sh.Move(0, sh.getRelative()[0], sh.getRelative()[1]);
                sh.setState(SeaHorseState.EndAction);
            }
        }

        // Kiem tra trang thai co phai end ko
        // neu co
        // kiem tra o nay da la o dich chua
        // neu co
        // dung di chuyen chuyen sang trang thai di len chuong dich
        // end action
        if (sh.getState() == SeaHorseState.EndStep) {
            if (sh.getRelative()[0] == 7 || sh.getRelative()[1] == 7) {
                System.out.println(game.getBoard().getTileEnum(sh.getRelative()[0], sh.getRelative()[1]));
                TileType currentType = TileType.RC;
                switch (game.getCurrentPlayerIndex()) {
                    case 1:
                        currentType = TileType.BC;
                        break;
                    case 2:
                        currentType = TileType.GC;
                        break;
                    case 3:
                        currentType = TileType.YC;
                        break;
                }
                if (currentType == game.getBoard().getTileEnum(sh.getRelative()[0], sh.getRelative()[1])) {
                    sh.setState(SeaHorseState.EndStep);
                    System.out.println(sh.getSeaHorseData().getStepLeft());
                    sh.Move(0, sh.getRelative()[0], sh.getRelative()[1]);
                    sh.getSeaHorseData().isInFinish = true;
                }
            }
            sh.EndAction();
        }

        // Kiem tra trang thai co phai endaction ko
        // Neu co
        // xoa csh va ket thuc luot
        if (sh.getState() == SeaHorseState.EndAction) {
            sh.setState(SeaHorseState.CanMove);
            EndPlayerTurn();
        }
    }

    public void AddSeaHorseOnMap(SeaHorseController sh, int relaX, int relaY) {
        // game.getEntitiesMap().GetSeaHorsesMap()[relaX][relaY] = sh;
        game.getEntitiesMap().AddSeaHorseToTile(sh, relaX, relaY);
    }

    public void RemoveSeaHorseOnMap(int relaX, int relaY) {
        // game.getEntitiesMap().GetSeaHorsesMap()[relaX][relaY] = null;
        game.getEntitiesMap().DeleteSeaHorseFromTile(relaX, relaY);
    }

    public boolean CheckTile(int relaX, int relaY) {
        // return game.getEntitiesMap().GetSeaHorsesMap()[relaX][relaY] == null;
        return game.getEntitiesMap().GetTileSeaHorse(relaX, relaY) == null;
    }

    public void EndPlayerTurn() {
        game.getPlayerController().EndPlayerAction();
        if (game.getDiceNumber() != 6) {
            game.setCurrentPlayerIndex(game.getCurrentPlayerIndex() + 1);
            if (game.getCurrentPlayerIndex() == game.getPlayersNumber()) {
                game.setCurrentPlayerIndex(0);
            }
        }
        game.setControlledSeaHorse(null);

        game.getDiceController().SetDiceDefault();
        game.setDiceNumber(0);

        game.getRollButton().ResetButton();
        game.getSkipButton().ResetButton();
        // System.out.println(game.getCurrentPlayerIndex());
    }

    public Game getGameData() {
        return game;
    }

    private boolean isEnd = false;

    public void CheckPlayersProgress() {
        int index = -1;
        for (int i = 0; i < game.getPlayersNumber(); i++) {
            if (game.getPlayersController().get(i).getSeaHorsesInGoalNumber() == 4) {
                index = i;
                break;
            }
        }

        if (index == -1)
            return;

        isEnd = true;
        game.getRollButton().UnactiveButton();
        game.getSkipButton().UnactiveButton();
        game.getWinMenu().setIndex(index);
    }

    public void saveGame() {
        List<PlayerSaveData> players = new ArrayList<>();

        for (PlayerController pc : game.getPlayersController()) {
            players.add(new PlayerSaveData(pc));
        }

        int currentPlayerIndex = game.getCurrentPlayerIndex();
        int diceNumber = game.getDiceNumber();
        int playersNumber = game.getPlayersController().size();

        GameSaveData saveData = new GameSaveData(players, currentPlayerIndex, diceNumber, playersNumber);

        GameSaveManager.saveGame(saveData);
    }

    public void loadGame(GameController gameController, GameSaveData saveData) {
        if (saveData == null) {
            System.out.println("Không thể load dữ liệu game (saveData is null).");
            return;
        }
        Game game = gameController.getGameData();

        // Lặp qua từng người chơi
        for (int i = 0; i < saveData.getPlayers().size(); i++) {
            if (i >= game.getPlayersController().size()) break;

            PlayerSaveData playerData = saveData.getPlayers().get(i);
            PlayerController player = game.getPlayersController().get(i);

            // Lặp qua từng quân cờ
            for (int j = 0; j < playerData.getSeaHorses().size(); j++) {
                 if (j >= player.getSeaHorses().length) break; // Đảm bảo không vượt quá số ngựa

                SeaHorseSaveData horseData = playerData.getSeaHorses().get(j);
                // Gọi hàm cập nhật ngựa (đã có)
                GameUpdate.spawnHorseForPlayer(
                    gameController,
                    i,
                    j,
                    horseData.getRelativeX(),
                    horseData.getRelativeY(),
                    horseData.getState()
                );
            }
        }

        // Cập nhật lượt chơi và trạng thái xúc xắc
        game.setCurrentPlayerIndex(saveData.getCurrentPlayerIndex());
        game.getDiceController().SetDiceDefault();
        game.setDiceNumber(0);

        // Reset các nút bấm
        game.getRollButton().ResetButton();
        game.getSkipButton().ResetButton();

        // Có thể cần reset trạng thái khác (ví dụ: controlledSeaHorse)
        game.setControlledSeaHorse(null);
        System.out.println("Load game thành công từ GameSaveData!");
    }

    private void clearEntitiesMap() {
        if (game != null && game.getEntitiesMap() != null) {
            for (int x = 0; x < 15; x++) { // Giả sử kích thước map là 15x15
                for (int y = 0; y < 15; y++) {
                    game.getEntitiesMap().DeleteEntityFromTile(x, y);
                    game.getEntitiesMap().DeleteSeaHorseFromTile(x, y);
                }
            }
            System.out.println("Entities map cleared.");
        }
    }
}

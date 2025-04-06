package com.seahorse.controller;

import com.seahorse.model.Board;
import com.seahorse.model.Game;
import com.seahorse.model.GameSetting;
import com.seahorse.utils.SeaHorseState;
import com.seahorse.utils.UpdateComponent;
import com.seahorse.view.GameView;

public class GameController implements UpdateComponent{
    private GameThread panel;
    private Game game;
    private GameView gameView;

    public GameController(GameThread _panel) {
        panel = _panel;
        UpdateComponent.AddUpdate(this);
        
        game = new Game();

        gameView = new GameView(game.getBackgroundImage());

        game.setPlayersController(4, panel, this);
        game.setCurrentPlayerIndex(0);

        game.setSkipButton(GameSetting.screenWidth / 2 - 275, GameSetting.screenHeight - 117, this);
        panel.add(game.getSkipButton().getButton());

        game.setRollButton(GameSetting.screenWidth / 2 + 75, GameSetting.screenHeight - 117, this);
        panel.add(game.getRollButton().getButton());

        game.setDiceController();
    }

    @Override
    public void Update() {
        SeaHorseMonitor();
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
        if (game.getControlledSeaHorse() == null) return;

        SeaHorseController sh = game.getControlledSeaHorse();

        //Kiem tra trang thai cua sh co phai move ko
            //Neu co   
                //return
        if (sh.getState() == SeaHorseState.IsStep) {
            return;
        }
                
        //Kiem tra trang thai co phai start ko
            //Neu co
                //Kiem tra o tiep theo
                    //Neu co
                        //Xem co phai buoc cuoi ko
                            //Neu co
                                //Da
                            //Neu ko
                                //Ket thuc di chuyen
        if (sh.getState() == SeaHorseState.StartStep) {
            int nextTile[] = Board.getNextTile(sh.getRelative()[0], sh.getRelative()[1]);
            if (game.getEntitiesMap().GetSeaHorsesMap()[nextTile[0]][nextTile[1]] == null) {
                RemoveSeaHorseOnMap(sh.getRelative()[0], sh.getRelative()[1]);
                AddSeaHorseOnMap(sh, nextTile[0], nextTile[1]);
                sh.Move(sh.getSeaHorseData().getStepLeft(), nextTile[0], nextTile[1]);
                sh.setState(SeaHorseState.IsStep);
            }
            else {
                if (game.getEntitiesMap().GetSeaHorsesMap()[nextTile[0]][nextTile[1]].player == sh.player) {
                    sh.Move(0, sh.getRelative()[0], sh.getRelative()[1]);
                    sh.setState(SeaHorseState.EndAction);
                }
                else {
                    if (sh.getSeaHorseData().getStepLeft() == 1) {
                        //da ngua
                        
                        game.getEntitiesMap().GetSeaHorsesMap()[nextTile[0]][nextTile[1]].BackToStable();
                        game.getEntitiesMap().GetSeaHorsesMap()[nextTile[0]][nextTile[1]].setState(SeaHorseState.IsStep);

                        RemoveSeaHorseOnMap(sh.getRelative()[0], sh.getRelative()[1]);
                        AddSeaHorseOnMap(sh, nextTile[0], nextTile[1]);
                        sh.Move(sh.getSeaHorseData().getStepLeft(), nextTile[0], nextTile[1]);
                        sh.setState(SeaHorseState.IsStep);
                    }
                    else {
                        sh.Move(0, sh.getRelative()[0], sh.getRelative()[1]);
                        sh.setState(SeaHorseState.EndAction);
                    }
                }
            }
        }

        //Kiem tra trang thai co phai end ko
            //neu co
                //end action
        if (sh.getState() == SeaHorseState.EndStep) {

        }
                
        //Kiem tra trang thai co phai endaction ko
            //Neu co
                //xoa csh va ket thuc luot
        if (sh.getState() == SeaHorseState.EndAction) {
            sh.setState(SeaHorseState.CanMove);
            EndPlayerTurn();
        }
    }

    public void AddSeaHorseOnMap(SeaHorseController sh, int relaX, int relaY) {
        game.getEntitiesMap().GetSeaHorsesMap()[relaX][relaY] = sh;
    }

    public void RemoveSeaHorseOnMap(int relaX, int relaY) {
        game.getEntitiesMap().GetSeaHorsesMap()[relaX][relaY] = null;
    }

    public boolean CheckTile(int relaX, int relaY) {
        return game.getEntitiesMap().GetSeaHorsesMap()[relaX][relaY] == null;
    }

    public void EndPlayerTurn() {
        game.getPlayerController().EndPlayerAction();
        game.setCurrentPlayerIndex(game.getCurrentPlayerIndex() + 1);
        if (game.getCurrentPlayerIndex() == game.getPlayersNumber()) {
            game.setCurrentPlayerIndex(0);
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
}

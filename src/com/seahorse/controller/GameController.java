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

        gameView = new GameView(game.getBackgroundImage(), panel);

        game.setBoard();
        
        game.setEntitiesMap();

        game.setPlayersController(4, panel, this);
        game.setCurrentPlayerIndex(0);

        game.setSkipButton(GameSetting.screenWidth / 2 - 275, GameSetting.screenHeight - 117, this);
        panel.add(game.getSkipButton().getButton());

        game.setRollButton(GameSetting.screenWidth / 2 + 75, GameSetting.screenHeight - 117, this);
        panel.add(game.getRollButton().getButton());

        game.setDiceController(game);
        game.getDiceController().SetDiceDefault();
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
                //Kiem tra o tiep theo co ngua khac ngua dong minh ko
                    //Neu co
                        //Xem co phai buoc cuoi ko
                            //Neu co
                                //Da
                            //Neu ko
                                //Ket thuc di chuyen
        if (sh.getState() == SeaHorseState.StartStep && !sh.getSeaHorseData().isInFinish) {
            int nextTile[] = Board.getNextTile(sh.getRelative()[0], sh.getRelative()[1]);
            if (CheckTile(nextTile[0], nextTile[1])) {
                RemoveSeaHorseOnMap(sh.getRelative()[0], sh.getRelative()[1]);
                AddSeaHorseOnMap(sh, nextTile[0], nextTile[1]);
                sh.Move(sh.getSeaHorseData().getStepLeft(), nextTile[0], nextTile[1]);
                sh.setState(SeaHorseState.IsStep);
            }
            else {
                if (game.getEntitiesMap().GetTileSeaHorse(nextTile[0], nextTile[1]).player == sh.player) {
                    sh.Move(0, sh.getRelative()[0], sh.getRelative()[1]);
                    sh.setState(SeaHorseState.EndAction);
                }
                else {
                    if (sh.getSeaHorseData().getStepLeft() == 1) {
                        //da ngua
                        SeaHorseController shk = game.getEntitiesMap().GetTileSeaHorse(nextTile[0], nextTile[1]);
                        shk.BackToStable();
                        shk.setState(SeaHorseState.IsStep);
                        shk.getSeaHorseData().isInFinish = false;
                        AddSeaHorseOnMap(shk, shk.getRelative()[0], shk.getRelative()[1]);

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
        else if (sh.getState() == SeaHorseState.StartStep && sh.getSeaHorseData().isInFinish) {

        }

        //Kiem tra trang thai co phai end ko
            //neu co
                //kiem tra o nay da la o dich chua
                    //neu co
                        //dung di chuyen chuyen sang trang thai di len chuong dich
                //end action
        if (sh.getState() == SeaHorseState.EndStep) {
            if (sh.getRelative()[0] == 7 || sh.getRelative()[1] == 7) {
                System.out.println(game.getBoard().getTileEnum(sh.getRelative()[0], sh.getRelative()[1]));
                // sh.Move(0, sh.getRelative()[0], sh.getRelative()[1]);
                // sh.setState(SeaHorseState.EndAction);
                // sh.getSeaHorseData().isInFinish = true;
            }
            sh.EndAction();
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

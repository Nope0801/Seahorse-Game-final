package com.seahorse.model;

import com.seahorse.controller.Button.RollButton;
import com.seahorse.controller.Button.SkipButton;
import com.seahorse.controller.DiceController;
import com.seahorse.controller.GameController;
import com.seahorse.controller.GameThread;
import com.seahorse.controller.PlayerController;
import com.seahorse.controller.SeaHorseController;
import com.seahorse.utils.ImageFromPath;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

//Lưu trữ tất cả entities
public class Game {
    //PLAYER
    private List<Player> players;
    private int currentPlayerIndex;
    private int playersNumber;
    private SeaHorseController controlledSeaHorse;

    //BACKGROUND
    private BufferedImage backgroundImage;

    //BOARD
    private Board board;
    private EntitiesMapData entitiesMap;

    //PLAYER
    private ArrayList<PlayerController> playersController = new ArrayList<PlayerController>();

    //SKIP, ROLL, DICE
    private SkipButton skipButton;
    private RollButton rollButton;
    private DiceController diceController;
    private int diceNumber;

    public Game() {
        backgroundImage = ImageFromPath.GetBufferedImageFromPath("./src/resources/sprites/Background/bgr_img.jpg");
        entitiesMap = new EntitiesMapData();
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public ArrayList<PlayerController> getPlayersController() {
        return playersController;
    }
    public PlayerController getPlayerController() {
        return playersController.get(currentPlayerIndex);
    }

    public void setPlayersController(int playersNumber, GameThread panel, GameController gameController) {
        this.playersNumber = playersNumber;
        board = new Board();
        board.loadMapFromFile("./../../../resources/map/map03.txt");
        for (int i = 0; i < playersNumber; i++) {
            playersController.add(new PlayerController(panel, gameController, board.startStableCoordinates[i], board.deployCoordinates[i]));
        }
    }

    public EntitiesMapData getEntitiesMap() {
        return entitiesMap;
    }

    public void setEntitiesMap(EntitiesMapData entitiesMap) {
        this.entitiesMap = entitiesMap;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public SkipButton getSkipButton() {
        return skipButton;
    }

    public void setSkipButton(int x, int y, GameController gameController) {
        skipButton = new SkipButton(x, y, gameController);
    }

    public RollButton getRollButton() {
        return rollButton;
    }

    public void setRollButton(int x, int y, GameController gameController) {
        rollButton = new RollButton(x, y, gameController);
    }

    public DiceController getDiceController() {
        return diceController;
    }

    public void setDiceController() {
        diceController = new DiceController();
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public void setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
    }

    public SeaHorseController getControlledSeaHorse() {
        return controlledSeaHorse;
    }

    public void setControlledSeaHorse(SeaHorseController controlledSeaHorse) {
        this.controlledSeaHorse = controlledSeaHorse;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}

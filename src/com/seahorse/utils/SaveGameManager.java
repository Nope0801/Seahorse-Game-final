package com.seahorse.utils;

import com.seahorse.model.Game;

import java.io.*;

public class SaveGameManager {

    public static void saveGame(Game game) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat"))) {
            oos.writeObject(game);
            System.out.println("Game saved to " + "save.dat");
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Game loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat"))) {
            Game game = (Game) ois.readObject();
            System.out.println("Game loaded from " + "save.dat");
            return game;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load game: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}

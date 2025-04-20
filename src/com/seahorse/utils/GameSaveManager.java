package com.seahorse.utils;

import com.seahorse.model.Game;
import com.seahorse.model.GameSaveData;
import java.io.*;

public class GameSaveManager {

    // Hàm lưu game
    public static void saveGame(GameSaveData gamedata) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.dat"))) {
            out.writeObject(gamedata);  // Lưu đối tượng GameSaveData vào file
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving the game.");
        }
    }

    // Hàm tải game
    public static GameSaveData loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.dat"))) {
            GameSaveData saveData = (GameSaveData) in.readObject();  // Đọc dữ liệu từ file
            System.out.println("Game loaded successfully.");
            return saveData;  // Trả về dữ liệu game đã tải
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error loading the game.");
            return null;
        }
    }
}


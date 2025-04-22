package com.seahorse.utils;
import javax.sound.sampled.*;
import java.io.*;

public class WavPlayer {
    public static void PlaySound(String path) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(WavPlayer.class.getResource(path));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Không thể phát âm thanh: " + path);
        }
    }
}

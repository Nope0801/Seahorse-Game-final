package com.seahorse.utils;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound_main {
    private Clip clip;

    public Sound_main(String soundFilePath) {
        try {
            URL soundURL = getClass().getResource(soundFilePath);
            if (soundURL == null) {
                System.err.println("Không tìm thấy file âm thanh: " + soundFilePath);
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playLoop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}

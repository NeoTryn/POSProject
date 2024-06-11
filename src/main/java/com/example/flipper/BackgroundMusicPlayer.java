package com.example.flipper;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusicPlayer {
    private Clip clip;
    private FloatControl volumeControl;

    public BackgroundMusicPlayer(String musicFilePath, float volume) {
        try {
            File musicFile = new File(musicFilePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Set the volume
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playLooping() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Play the clip continuously
        }
    }

    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0); // Rewind to the beginning
        }
    }

    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}

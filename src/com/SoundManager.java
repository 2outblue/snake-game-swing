package com;

import com.constants.AudioConst;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    private static SoundManager soundManager;

    private SoundManager() {

    }

    public void playButtonClick() {

        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(AudioConst.BUTTON_CLICK));
            Clip c = AudioSystem.getClip();
            c.open(a);
            c.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public void playButtonHover() {

        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(AudioConst.BUTTON_HOVER));
            Clip c = AudioSystem.getClip();
            c.open(a);
            c.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public void playTurnLeft() {

        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(AudioConst.TURN_LEFT));
            Clip c = AudioSystem.getClip();
            c.open(a);
            c.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public void playTurnRight() {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(AudioConst.TURN_RIGHT));
            Clip c = AudioSystem.getClip();
            c.open(a);
            c.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public void playTurnUp() {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(AudioConst.TURN_UP));
            Clip c = AudioSystem.getClip();
            c.open(a);
            c.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public void playTurnDown() {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(AudioConst.TURN_DOWN));
            Clip c = AudioSystem.getClip();
            c.open(a);
            c.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public void playEndGame() {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(AudioConst.END_GAME));
            Clip c = AudioSystem.getClip();
            c.open(a);
            c.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public void playFoodCollision() {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(AudioConst.FOOD_COLLISION));
            Clip c = AudioSystem.getClip();
            c.open(a);
            c.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public static SoundManager getInstance() {
        if (soundManager == null) {
            soundManager = new SoundManager();
        }
        return soundManager;
    }
}

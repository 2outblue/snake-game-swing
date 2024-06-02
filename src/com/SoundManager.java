package com;

import com.components.constants.AudioConst;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    private static SoundManager soundManager;

    // TODO: class fields are not needed, every method loads the resource anyway - delete all class fields and modify the methods accordingly
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

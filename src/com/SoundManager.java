package com;

import com.components.constants.AudioConst;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    private static SoundManager soundManager;

    private Clip buttonClick;
    private Clip buttonHover;

    private Clip turnLeft;
    private Clip turnRight;
    private Clip turnUp;
    private Clip turnDown;

    private Clip endGame;
    private Clip foodCollision;


    // TODO: class fields are not needed, every method loads the resource anyway - delete all class fields and modify the methods accordingly
    private SoundManager() {

    }


    public void playButtonClick() {

        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(AudioConst.BUTTON_CLICK));
            buttonClick = AudioSystem.getClip();
            buttonClick.open(a);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }

        buttonClick.start();
    }

    public void playButtonHover() {

        try {
            AudioInputStream a2 = AudioSystem.getAudioInputStream(new File(AudioConst.BUTTON_HOVER));
            buttonHover = AudioSystem.getClip();
            buttonHover.open(a2);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
        buttonHover.start();

    }

    public void playTurnLeft() {

        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(AudioConst.TURN_LEFT));
            turnLeft = AudioSystem.getClip();
            turnLeft.open(a);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }

        turnLeft.start();
    }

    public void playTurnRight() {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(AudioConst.TURN_RIGHT));
            turnRight = AudioSystem.getClip();
            turnRight.open(a);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
        turnRight.start();
    }

    public void playTurnUp() {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(AudioConst.TURN_UP));
            turnUp = AudioSystem.getClip();
            turnUp.open(a);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
        turnUp.start();
    }

    public void playTurnDown() {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(AudioConst.TURN_DOWN));
            turnDown = AudioSystem.getClip();
            turnDown.open(a);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
        turnDown.start();
    }

    public void playEndGame() {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(AudioConst.END_GAME));
            endGame = AudioSystem.getClip();
            endGame.open(a);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }

        endGame.start();
    }

    public void playFoodCollision() {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(AudioConst.FOOD_COLLISION));
            foodCollision = AudioSystem.getClip();
            foodCollision.open(a);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }

        foodCollision.start();
    }

    public static SoundManager getInstance() {
        if (soundManager == null) {
            soundManager = new SoundManager();
        }
        return soundManager;
    }
}

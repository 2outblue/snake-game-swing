package com;

import com.GameManager;
import com.components.ComponentFactory;

import javax.swing.*;

public class ScoreManager {

    private static int score;

    private static int bestScore;


    public static void increaseScore() {
        score++;
        GameManager.getInstance().updateScore(String.valueOf(score));
    }

    public static int getScore() {
        return score;
    }

    public static void resetScore() {
        score = 0;
        GameManager.getInstance().updateScore("0");
    }

}

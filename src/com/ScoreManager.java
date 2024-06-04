package com;

import com.game_utility.CoordinateStore;
import com.game_utility.Difficulty;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ScoreManager {

    private static ScoreManager instance;

    private final Properties properties;
    private int score;
    private int bestEasy;
    private int bestMedium;
    private int bestHard;

    public ScoreManager() {
        properties = new Properties();
        loadSavedScores();
    }

    public void increaseScore() {
        score++;
        GameManager.getInstance().updateCurrentScore(String.valueOf(score));
        if (Integer.parseInt(getCurrentBest()) < score) {
            GameManager.getInstance().updateBestScore(String.valueOf(score));
        }
    }
    public String getCurrentBest() {
        Difficulty dif = CoordinateStore.getDifficulty();
        // fancy switch :O
        return switch (dif) {
            case EASY -> String.valueOf(bestEasy);
            case MEDIUM -> String.valueOf(bestMedium);
            case HARD -> String.valueOf(bestHard);
        };
    }

    public void resetScore() {
        score = 0;
        GameManager.getInstance().updateCurrentScore("0");
    }

    public synchronized void saveScore() {
        Difficulty dif = CoordinateStore.getDifficulty();
        switch (dif) {
            case EASY -> {
                if(bestEasy < score) {
                    properties.setProperty("easy", String.valueOf(score));
                    bestEasy = score;
                }
            }
            case MEDIUM -> {
                if(bestMedium < score) {
                    properties.setProperty("medium", String.valueOf(score));
                    bestMedium = score;
                }
            }
            case HARD -> {
                if(bestHard < score) {
                    properties.setProperty("hard", String.valueOf(score));
                    bestHard = score;
                }
            }
        }
        // TODO:
        // doesn't need to save if the current score is not bigger, but it works for now...
        // also the path should be a constant
        try (FileOutputStream output = new FileOutputStream("src/save/score.properties")) {
            properties.store(output, "Save of best scores");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSavedScores() {
        try (FileInputStream input = new FileInputStream("src/save/score.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bestEasy = Integer.parseInt(properties.getProperty("easy"));
        bestMedium = Integer.parseInt(properties.getProperty("medium"));
        bestHard = Integer.parseInt(properties.getProperty("hard"));
    }
    public static ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }
}

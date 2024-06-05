package com.event_listeners.menu_events;

import com.GameManager;
import com.SoundManager;
import com.game_utility.Difficulty;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuButtonActionListener implements ActionListener {

// Should the listeners be actionPerformed or something else ?
    @Override
    public void actionPerformed(ActionEvent e) {
        SoundManager.getInstance().playButtonClick();
        String command = e.getActionCommand();

        switch (command) {
            case "play":
//                SoundManager.getInstance().playButtonClick();
                GameManager gameManager = GameManager.getInstance();
                gameManager.startGame();
                break;
            case "easy":
                GameManager.getInstance().setDifficulty(Difficulty.EASY);
                break;
            case "medium":
                GameManager.getInstance().setDifficulty(Difficulty.MEDIUM);
                break;
            case "hard":
                GameManager.getInstance().setDifficulty(Difficulty.HARD);
                break;
            case "quit":
                GameManager.getInstance().quitGame();
                break;
        }

    }
}

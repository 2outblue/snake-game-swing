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

        GameManager gameManager = GameManager.getInstance();

        String command = e.getActionCommand();
        switch (command) {
            case "play":
                gameManager.startGame();
                break;
            case "easy":
                gameManager.setDifficulty(Difficulty.EASY);
                break;
            case "medium":
                gameManager.setDifficulty(Difficulty.MEDIUM);
                break;
            case "hard":
                gameManager.setDifficulty(Difficulty.HARD);
                break;
            case "quit":
                gameManager.quitGame();
                break;
        }

    }


}

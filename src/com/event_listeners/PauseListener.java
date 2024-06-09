package com.event_listeners;

import com.GameManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PauseListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (GameManager.getInstance().inGame()) {
                GameManager.getInstance().pauseGame();
            }
        }
    }
}

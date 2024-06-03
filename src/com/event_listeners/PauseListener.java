package com.event_listeners;

import com.GameManager;
import com.game_objects.Snake;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PauseListener extends KeyAdapter {

    public PauseListener() {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (!GameManager.getInstance().gameOver()) {
                GameManager.getInstance().pauseGame();
            }
        }
    }
}

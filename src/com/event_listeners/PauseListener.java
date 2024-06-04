package com.event_listeners;

import com.GameManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PauseListener extends KeyAdapter {

    public PauseListener() {

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println("event triggered");
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
//            System.out.println("key check passed");
            if (GameManager.getInstance().inGame()) {
//                System.out.println("in game boolean check passed");
                GameManager.getInstance().pauseGame();
            }
        }
    }
}

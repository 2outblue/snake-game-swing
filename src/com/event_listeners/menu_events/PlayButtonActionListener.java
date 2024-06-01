package com.event_listeners.menu_events;

import com.GameManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayButtonActionListener implements ActionListener {


    @Override
    public void actionPerformed(ActionEvent e) {
        GameManager gameManager = GameManager.getInstance();

        gameManager.startGame();
    }
}

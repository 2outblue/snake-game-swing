package com.event_listeners.menu_events;

import com.GameManager;
import com.ScoreManager;
import com.SoundManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackToMenuListener implements ActionListener {


    @Override
    public void actionPerformed(ActionEvent e) {
        ScoreManager.getInstance().saveScore();
        SoundManager.getInstance().playButtonClick();
        GameManager.getInstance().restart();
    }
}

package com.event_listeners.menu_events;

import com.GameManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackToMenuListener implements ActionListener {


    @Override
    public void actionPerformed(ActionEvent e) {

        // TODO: this doesn't work
        GameManager.getInstance().restart();
    }
}
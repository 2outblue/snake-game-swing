package com.event_listeners.menu_events;

import com.SoundManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonHoverListener extends MouseAdapter {

    @Override
    public void mouseEntered(MouseEvent e) {
        SoundManager.getInstance().playButtonHover();
    }
}

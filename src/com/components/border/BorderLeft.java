package com.components.border;


import com.game_objects.Border;

public class BorderLeft extends BorderComponent {

    public BorderLeft(int width, int length) {
        super(width, length);
        y = 30;
        x = Border.INSET;
    }
}

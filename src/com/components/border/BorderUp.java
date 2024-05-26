package com.components.border;

import com.game_objects.Border;

public class BorderUp extends BorderComponent{

    public BorderUp(int width, int length) {
        super(length, width);
        y = 30;
        x = Border.INSET;
    }
}

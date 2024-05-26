package com.components.border;

import com.game_objects.Border;

public class BorderDown extends BorderComponent {

    public BorderDown(int width, int length) {
        super(length, width);
        y = 680;
        x = Border.INSET;
    }
}

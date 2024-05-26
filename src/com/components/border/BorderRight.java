package com.components.border;

import com.components.constants.BorderConstant;
import com.game_objects.Border;

import javax.swing.*;
import java.awt.*;

public class BorderRight extends BorderComponent {

    public BorderRight(int width, int length) {
        super(width, length);
        y = 30;
        x = 800 - width - Border.INSET;
    }

}

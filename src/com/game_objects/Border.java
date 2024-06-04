package com.game_objects;

import com.components.border.*;

// TODO: remove all border stuff
public class Border {

    public static final int INSET = 50;
    private final int WIDTH = 50;
    private final int LENGTH = 700;

    private BorderRight borderRight;
    private BorderLeft borderLeft;
    private BorderUp borderUp;
    private BorderDown borderDown;

    private BorderComponent[] borderComponents;

    public Border() {
        this.borderComponents = new BorderComponent[4];
        this.borderUp = new BorderUp(WIDTH, LENGTH);
        borderComponents[0] = borderUp;
        this.borderRight = new BorderRight(WIDTH, LENGTH);
        borderComponents[1] = borderRight;
        this.borderDown = new BorderDown(WIDTH, LENGTH);
        borderComponents[2] = borderDown;
        this.borderLeft = new BorderLeft(WIDTH, LENGTH);
        borderComponents[3] = borderLeft;
    }


    public BorderComponent[] getBorderComponents() {
        return borderComponents;
    }
}

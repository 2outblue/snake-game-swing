package com.components.snake;

import com.components.constants.Direction;

import javax.swing.*;
import java.awt.*;

public interface SnakeComponent{

    Direction getDirection();

    Rectangle getBounds();

}

package com.game_objects;

import com.components.snake.SnakeBody;
import com.components.snake.SnakeHead;
import com.components.constants.Direction;

import java.awt.*;
import java.util.List;

public class Snake {

    private static Snake snake;
   private SnakeHead head;
   private List<SnakeBody> body;

   private Direction currentDirectionInput;
   private Direction previousDirectionInput;

   private boolean paused;

    private Snake(SnakeHead head, List<SnakeBody> body) {
        this.head = head;
        this.body = body;
    }

    public synchronized SnakeHead getHead(){
        return head;
    }

    public synchronized List<SnakeBody> getBody(){
        return body;
    }

    public synchronized static Snake getInstance(){
        return snake;
    }

    public synchronized static void instantiateSnake(SnakeHead head, List<SnakeBody> body) {
        if (snake == null) {
            snake = new Snake(head, body);
        }
    }

    public void grow(SnakeBody b) {
        b.setDirection(body.getLast().getDirection());
        body.addLast(b);
    }

    public synchronized void paintBody(Rectangle headBounds) {
        Rectangle previousBounds = headBounds;
        Direction previousDirection = getHead().getReverseDirection();
        previousBounds.x+= 0;
        previousBounds.y-= 0;
//
//
//        for (SnakeBody bodyPart : getBody()) {
//            Rectangle nextBounds = bodyPart.getBounds();
//            Direction nextDirection = bodyPart.getDirection();
//
//            if (nextDirection == Direction.DOWN) {
//                // y+
//                bodyPart.setBounds(previousBounds.x, previousBounds.y, previousBounds.width, previousBounds.height);
//                bodyPart.setDirection(previousDirection);
//            } else if (nextDirection == Direction.UP) {
//                //y-
//                bodyPart.setBounds(previousBounds.x, previousBounds.y, previousBounds.width, previousBounds.height);
//                bodyPart.setDirection(previousDirection);
//            } else if (nextDirection == Direction.LEFT) {
//                //x-
//                bodyPart.setBounds(previousBounds.x, previousBounds.y, previousBounds.width, previousBounds.height);
//                bodyPart.setDirection(previousDirection);
//            } else if (nextDirection == Direction.RIGHT) {
//                //x+
//                bodyPart.setBounds(previousBounds.x, previousBounds.y, previousBounds.width, previousBounds.height);
//                bodyPart.setDirection(previousDirection);
//            }
//
//
//            previousBounds = nextBounds;
//            previousDirection = nextDirection;
//        }


        for (int i = 0; i < getBody().size(); i++) {
            SnakeBody bodyPart = getBody().get(i);
            if (i < 1) {
//                bodyPart.getGraphics().setColor(Color.GREEN);
//                bodyPart.getGraphics().fillRect(0,0, bodyPart.getWidth(), bodyPart.getHeight());
                bodyPart.setSquareTransparency(0.0F);
                bodyPart.repaint();
            }

            Rectangle nextBounds = bodyPart.getBounds();
            Direction nextDirection = bodyPart.getDirection();

            if (nextDirection == Direction.DOWN) {
                // y+
                bodyPart.setBounds(previousBounds.x, previousBounds.y, previousBounds.width, previousBounds.height);
                bodyPart.setDirection(previousDirection);
            } else if (nextDirection == Direction.UP) {
                //y-
                bodyPart.setBounds(previousBounds.x, previousBounds.y, previousBounds.width, previousBounds.height);
                bodyPart.setDirection(previousDirection);
            } else if (nextDirection == Direction.LEFT) {
                //x-
                bodyPart.setBounds(previousBounds.x, previousBounds.y, previousBounds.width, previousBounds.height);
                bodyPart.setDirection(previousDirection);
            } else if (nextDirection == Direction.RIGHT) {
                //x+
                bodyPart.setBounds(previousBounds.x, previousBounds.y, previousBounds.width, previousBounds.height);
                bodyPart.setDirection(previousDirection);
            }


            previousBounds = nextBounds;
            previousDirection = nextDirection;
        }
    }

    public synchronized Direction getCurrentDirectionInput() {
        return currentDirectionInput;
    }

    public synchronized void setCurrentDirectionInput(Direction currentDirectionInput) {
        getHead().setDirection(currentDirectionInput);
        this.currentDirectionInput = currentDirectionInput;
    }

    public synchronized boolean isPaused() {
        return paused;
    }

    public synchronized void setPaused(boolean paused) {
        this.paused = paused;
    }

    public synchronized Direction getPreviousDirectionInput() {
        return previousDirectionInput;
    }

    public synchronized void setPreviousDirectionInput(Direction previousDirectionInput) {
        this.previousDirectionInput = previousDirectionInput;
    }
}

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

   private Direction currentInput;

   private boolean paused;

    private Snake(SnakeHead head, List<SnakeBody> body) {
        this.head = head;
        this.body = body;
    }

//    private void setBody(List<SnakeComponent<JComponent>> list){
////        if (list.size() < 3) {
////            throw new IllegalArgumentException("Snake body can't be less than 3 components");
////        }
////        if (list.get(0).getClass().getSimpleName().equals("SnakeHead")) {
////            throw new IllegalArgumentException("First component of snake is not a head");
////        }
//        body = list;
//    }

    public synchronized SnakeHead getHead(){
        return head;
    }

    public synchronized List<SnakeBody> getBody(){
        return body;
    }

    public synchronized void updateHead(Rectangle bounds, Direction direction) {
        head.setDirection(direction);
        head.setBounds(bounds);
    }

    public synchronized void setBody(List<SnakeBody> newBody) {
        body = newBody;
    }

    public synchronized static Snake getInstance(){
        return snake;
    }

    public synchronized static void instantiateSnake(SnakeHead head, List<SnakeBody> body) {
        if (snake == null) {
            snake = new Snake(head, body);
        }
    }

    public synchronized void grow(SnakeBody b) {
        b.setDirection(body.getLast().getDirection());
        body.addLast(b);
    }

    public synchronized void paintBody() {
        Rectangle previousBounds = getHead().getBounds();
        Direction previousDirection = getHead().getReverseDirection();


        for (SnakeBody snakeBody : getBody()) {
            Rectangle nextBounds = snakeBody.getBounds();
            Direction nextDirection = snakeBody.getDirection();

            if (nextDirection == Direction.DOWN) {
                // y+
                snakeBody.setBounds(previousBounds.x, previousBounds.y, previousBounds.width, previousBounds.height);
                snakeBody.setDirection(previousDirection);
            } else if (nextDirection == Direction.UP) {
                //y-
                snakeBody.setBounds(previousBounds.x, previousBounds.y, previousBounds.width, previousBounds.height);
                snakeBody.setDirection(previousDirection);
            } else if (nextDirection == Direction.LEFT) {
                //x-
                snakeBody.setBounds(previousBounds.x, previousBounds.y, previousBounds.width, previousBounds.height);
                snakeBody.setDirection(previousDirection);
            } else if (nextDirection == Direction.RIGHT) {
                //x+
                snakeBody.setBounds(previousBounds.x, previousBounds.y, previousBounds.width, previousBounds.height);
                snakeBody.setDirection(previousDirection);
            }


            previousBounds = nextBounds;
            previousDirection = nextDirection;
        }
    }

    public synchronized Direction getCurrentInput() {
        return currentInput;
    }

    public synchronized void setCurrentInput(Direction currentInput) {
        getHead().setDirection(currentInput);
        this.currentInput = currentInput;
    }

    public synchronized boolean isPaused() {
        return paused;
    }

    public synchronized void setPaused(boolean paused) {
        this.paused = paused;
    }
}

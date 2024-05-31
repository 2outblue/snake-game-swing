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
   private List<SnakeBody> tail;
   private Direction currentDirectionInput;
   private Direction previousDirectionInput;

   private boolean paused;

    private Snake(SnakeHead head, List<SnakeBody> body, List<SnakeBody> tail) {
        this.head = head;
        this.body = body;
        this.tail = tail;
    }

    public synchronized SnakeHead getHead(){
        return head;
    }

    public synchronized List<SnakeBody> getBody(){
        return body;
    }

    public synchronized List<SnakeBody> getTail() {
        return tail;
    }

    public synchronized static Snake getInstance(){
        return snake;
    }

    public synchronized static void instantiateSnake(SnakeHead head, List<SnakeBody> body, List<SnakeBody> tail) {
        if (snake == null) {
            snake = new Snake(head, body, tail);
        }
    }

    public void grow(SnakeBody b) {
        b.setDirection(body.getLast().getDirection());
        body.addLast(b);
    }

    // if there are issues you can try to make this method synchronized - (it was previously but
    // you changed it because you thought it shouldn't need to be synchronized)...
    public void paintBody(Rectangle headBounds) {
        Rectangle previousBounds = headBounds;
        Direction previousDirection = getHead().getReverseDirection();

        for (int i = 0; i < getBody().size(); i++) {
            SnakeBody bodyPart = getBody().get(i);
            // first body part is invisible so it doesn't cause issues with the display of the head and itself (since it overlaps)
            if (i < 1) {
                bodyPart.setSquareTransparency(0.0F);
                bodyPart.repaint();
            }

            Rectangle nextBounds = bodyPart.getBounds();
            Direction nextDirection = bodyPart.getDirection();

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

            bodyPart.setBounds(previousBounds);
            bodyPart.setDirection(previousDirection);


            previousBounds = nextBounds;
            previousDirection = nextDirection;
        }
        paintTail();
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

    private void paintTail() {
        Rectangle previousBounds = getBody().getLast().getBounds();
        Direction previousDirection = getBody().getLast().getDirection();
        Direction previousPartCurrentDirection = null;

        for (int i = 0; i < getTail().size(); i++) {
            SnakeBody tailPart = getTail().get(i);

            if (i > 0) {
                previousPartCurrentDirection = getTail().get(i-1).getDirection();
            }

            Rectangle nextBounds = tailPart.getBounds();
            Direction nextDirection = tailPart.getDirection();

            // this part makes the tail turn more smoothly and keep closer together when not turning - since the
            // icons are smaller, if you don't do that, they appear as small dots behind the snake and not really as a tail
            // could be even smoother with fine-tuning, but it works OK
            int modifier = 0;
            int deModifier = 0;
            int turnCompensatorFactor = 0;
            if (i == 4) {
                modifier = 1;
                deModifier = 1;
                turnCompensatorFactor = 1;
            } else if (i == 5) {
                modifier = 1;
                deModifier = 0;
                turnCompensatorFactor = 0;
            } else if (i == 6) {
                modifier = 1;
                deModifier = 0;
                turnCompensatorFactor = 2;
            } else if (i > 6) {
                modifier = 1;
                deModifier = 0;
                turnCompensatorFactor = 1;
            }

            if (nextDirection == Direction.DOWN) {
                // y+
                previousBounds.y -= modifier;
                if (previousPartCurrentDirection == Direction.LEFT) {
                    previousBounds.y += deModifier; // - same as next direction (closer together)
                    previousBounds.x += turnCompensatorFactor; // turn compensator
                } else if (previousPartCurrentDirection == Direction.RIGHT) {
                    previousBounds.y += deModifier; // - same as next direction (closer together)
                    previousBounds.x -= turnCompensatorFactor; // turn compensator
                }
            } else if (nextDirection == Direction.UP) {
                //y-
                previousBounds.y += modifier;
                if (previousPartCurrentDirection == Direction.LEFT) {
                    previousBounds.y -= deModifier; // - same as next direction (closer together)
                    previousBounds.x += turnCompensatorFactor; // turn compensator
                } else if (previousPartCurrentDirection == Direction.RIGHT) {
                    previousBounds.y -= deModifier; // - same as next direction (closer together)
                    previousBounds.x -= turnCompensatorFactor; // turn compensator
                }
            } else if (nextDirection == Direction.LEFT) {
                //x-
                previousBounds.x += modifier;
                if (previousPartCurrentDirection == Direction.UP) {
                    previousBounds.y += turnCompensatorFactor; // turn compensator
                    previousBounds.x -= deModifier; // - same as next direction (closer together)

                } else if (previousPartCurrentDirection == Direction.DOWN) {
                    previousBounds.y -= turnCompensatorFactor; // turn compensator
                    previousBounds.x -= deModifier; // - same as next direction (closer together)

                }
            } else if (nextDirection == Direction.RIGHT) {
                //x+
                previousBounds.x -= modifier;
                if (previousPartCurrentDirection == Direction.UP) {
                    previousBounds.y += turnCompensatorFactor; // turn compensator
                    previousBounds.x += deModifier; // - same as next direction (closer together)
                } else if (previousPartCurrentDirection == Direction.DOWN) {
                    previousBounds.y -= turnCompensatorFactor; // turn compensator
                    previousBounds.x += deModifier; // - same as next direction (closer together)

                }
            }

            tailPart.setDirection(previousDirection);
            tailPart.setBounds(previousBounds);

            previousBounds = nextBounds;
            previousDirection = nextDirection;
        }
    }
}

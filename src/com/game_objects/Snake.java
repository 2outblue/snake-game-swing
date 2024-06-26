package com.game_objects;

import com.components.snake.SnakeBody;
import com.components.snake.SnakeHead;
import com.constants.Direction;

import java.awt.*;
import java.util.List;

public class Snake {
    private final SnakeHead head;
    private final List<SnakeBody> body;
    private final List<SnakeBody> tail;
    private Direction currentDirectionInput;
    private Direction previousDirectionInput;

    private boolean stopped;

    public Snake(SnakeHead head, List<SnakeBody> body, List<SnakeBody> tail) {
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
    public void grow(SnakeBody b) {
        b.setDirection(body.getLast().getDirection());
        body.addLast(b);
    }

    // Could this cause issues if its not sync?
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

            Rectangle currentBounds = bodyPart.getBounds();
            Direction currentDirection = bodyPart.getDirection();

            bodyPart.setBounds(previousBounds);
            bodyPart.setDirection(previousDirection);


            previousBounds = currentBounds;
            previousDirection = currentDirection;
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

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean moving) {
        this.stopped = moving;
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

            Rectangle currentBounds = tailPart.getBounds();
            Direction currentDirection = tailPart.getDirection();

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
//                deModifier = 0;
//                turnCompensatorFactor = 0;
            } else if (i == 6) {
                modifier = 1;
//                deModifier = 0;
                turnCompensatorFactor = 2;
            } else if (i > 6) {
                modifier = 1;
//                deModifier = 0;
                turnCompensatorFactor = 1;
            }


            if (currentDirection == Direction.DOWN) {
                // applies modifier if previous part is not turning - ie current part moves closer with
                // modifier amount of pixels. If the previous part has a different direction, the current part
                // is moved deModifier amount of pixels in the opposite direction to where its currently going
                // Basically move the parts closer together (always) and move them back apart if the previous part has turned

                // turnCompensatorFactor - the current part moves turnCompensatorFactor amount of pixels in the
                // direction of the previous part (to make the turn more smooth and to overlap with the previous part
                // so the current part is not just a separate circle behind the snake)
                // In summary it if previous part is dir DOWN and current part is dir LEFT, it slightly moves the curr part DOWN (even though its still going left)
                // y+
                previousBounds.y -= modifier;
                if (previousPartCurrentDirection == Direction.LEFT) {
                    previousBounds.y += deModifier; // - same as next direction (closer together)
                    previousBounds.x += turnCompensatorFactor; // turn compensator
                } else if (previousPartCurrentDirection == Direction.RIGHT) {
                    previousBounds.y += deModifier; // - same as next direction (closer together)
                    previousBounds.x -= turnCompensatorFactor; // turn compensator
                }
            } else if (currentDirection == Direction.UP) {
                //y-
                previousBounds.y += modifier;
                if (previousPartCurrentDirection == Direction.LEFT) {
                    previousBounds.y -= deModifier; // - same as next direction (closer together)
                    previousBounds.x += turnCompensatorFactor; // turn compensator
                } else if (previousPartCurrentDirection == Direction.RIGHT) {
                    previousBounds.y -= deModifier; // - same as next direction (closer together)
                    previousBounds.x -= turnCompensatorFactor; // turn compensator
                }
            } else if (currentDirection == Direction.LEFT) {
                //x-
                previousBounds.x += modifier;
                if (previousPartCurrentDirection == Direction.UP) {
                    previousBounds.y += turnCompensatorFactor; // turn compensator
                    previousBounds.x -= deModifier; // - same as next direction (closer together)

                } else if (previousPartCurrentDirection == Direction.DOWN) {
                    previousBounds.y -= turnCompensatorFactor; // turn compensator
                    previousBounds.x -= deModifier; // - same as next direction (closer together)
                }
            } else if (currentDirection == Direction.RIGHT) {
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

            previousBounds = currentBounds;
            previousDirection = currentDirection;
        }
    }
}

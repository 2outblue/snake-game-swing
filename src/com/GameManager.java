package com;

import com.components.ComponentFactory;
import com.components.border.BorderComponent;
import com.components.constants.ComponentConst;
import com.components.food.SmallFood;
import com.components.snake.SnakeBody;
import com.components.snake.SnakeHead;
import com.components.constants.Direction;
import com.components.snake.tail.TailComponent;
import com.components.snake.tail.TailPart;
import com.event_listeners.HeadDirectionChangeListener;
import com.event_listeners.PauseListener;
import com.game_objects.Border;
import com.game_objects.Snake;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {

    private static GameManager instance;
    private ComponentFactory componentFactory;
    private JFrame frame;

    private ThreadGovernor threadGovernor;

    private Snake snake;

    private Border border;

    private JLabel pauseElement;

    private JLabel gameOverElement;

    private boolean gameOver = false;



    private GameManager() {
        componentFactory = ComponentFactory.getInstance();
        this.frame = componentFactory.createFrame();
        createSnake();
        threadGovernor = ThreadGovernor.getInstance();
    }

    public void createAndShowGame(){
        setInitialSnakePosition();
        addEventListeners();
        createBorder();
//        movementThread.start();

        threadGovernor.createMovementThread();
//        threadGovernor.createFoodGenerationThread();

        growSnake();
        growSnake();
        growSnake();
        growSnake();
        growSnake();
        growSnake();
        growSnake();



    }

    private void setInitialSnakePosition(){
        snake.getHead().setBounds(390, 390, ComponentConst.SNAKE_HEAD_20, ComponentConst.SNAKE_HEAD_20);
    }

    private void createSnake() {
        SnakeHead h1 = componentFactory.createHead();
        h1.setDirection(Direction.UP);
//        h1.setBounds(390, 390, ComponentConst.SNAKE_COMPONENT_SIZE, ComponentConst.SNAKE_COMPONENT_SIZE);
        frame.getContentPane().add(h1);
//        frame.setComponentZOrder(h1, 0);
        frame.revalidate();
        frame.repaint();

        SnakeBody body1 = componentFactory.createBody();
        body1.setDirection(Direction.DOWN);
        frame.getContentPane().add(body1);

        List<SnakeBody> bl = new ArrayList<>();
        bl.add(body1);

        List<SnakeBody> tail = new ArrayList<>();
        SnakeBody tailPart12 = componentFactory.createTailPart(12, ComponentConst.TAIL_12);
        frame.getContentPane().add(tailPart12);
        tail.add(tailPart12);
        SnakeBody tailPart = componentFactory.createTailPart(11, ComponentConst.TAIL_11);
        frame.getContentPane().add(tailPart);
        tail.add(tailPart);
        SnakeBody tailPart2 = componentFactory.createTailPart(10, ComponentConst.TAIL_10);
        frame.getContentPane().add(tailPart2);
        tail.add(tailPart2);
        SnakeBody tailPart3 = componentFactory.createTailPart(9, ComponentConst.TAIL_9);
        frame.getContentPane().add(tailPart3);
        tail.add(tailPart3);
        SnakeBody tailPart4 = componentFactory.createTailPart(8, ComponentConst.TAIL_8);
        frame.getContentPane().add(tailPart4);
        tail.add(tailPart4);
        SnakeBody tailPart5 = componentFactory.createTailPart(7, ComponentConst.TAIL_7);
        frame.getContentPane().add(tailPart5);
        tail.add(tailPart5);
        SnakeBody tailPart6 = componentFactory.createTailPart(5, ComponentConst.TAIL_5);
        frame.getContentPane().add(tailPart6);
        tail.add(tailPart6);

        Snake.instantiateSnake(h1, bl, tail);
        snake = Snake.getInstance();
    }


    private void addEventListeners() {
        frame.addKeyListener(new HeadDirectionChangeListener());
        frame.addKeyListener(new PauseListener());
    }

    public synchronized void growSnake() {
        SnakeBody body = componentFactory.createBody();
        frame.getContentPane().add(body);
        SnakeBody body2 = componentFactory.createBody();
        frame.getContentPane().add(body2);
        snake.grow(body);
        snake.grow(body2);
    }

    private void createBorder() {
        this.border = new Border();
        for (int i = 0; i < border.getBorderComponents().length; i++) {
            BorderComponent bc = border.getBorderComponents()[i];
            frame.getContentPane().add(bc);
            bc.setBounds(bc.x, bc.y, bc.getPreferredSize().width, bc.getPreferredSize().height);
        }
    }

    public synchronized void pauseGame() {
        if (pauseElement == null) {
            pauseElement = componentFactory.createPauseLabel();
        }
        if (snake.isPaused()) {
            snake.setPaused(false);
            try {
                frame.setComponentZOrder(pauseElement, 1);
                frame.getContentPane().remove(pauseElement);
                frame.revalidate();
                frame.repaint();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        } else {
            frame.getContentPane().add(pauseElement);
            frame.setComponentZOrder(pauseElement, 0);
            frame.repaint();

            // calculate bounds which will show the label in the center of the window(frame)
            int labelWidth = 300;
            int labelHeight = 140;
            int x = (frame.getWidth() - labelWidth) / 2;
            int y = (frame.getHeight() - labelHeight) / 2;
            pauseElement.setBounds(x, y, labelWidth, labelHeight);
            snake.setPaused(true);
        }
    }

    public synchronized void endGame() {
        if (gameOverElement == null) {
            gameOverElement = componentFactory.createGameOverLabel();
        }

        frame.getContentPane().add(gameOverElement);
        frame.setComponentZOrder(gameOverElement, 0);
        frame.revalidate();
        frame.repaint();

        int labelWidth = 350;
        int labelHeight = 300;
        int x = (frame.getWidth() - labelWidth) / 2;
        int y = (frame.getHeight() - labelHeight) / 2;
        gameOverElement.setBounds(x, y, labelWidth, labelHeight);

        gameOver = true;
        snake.setPaused(true);
    }

    // maybe make a class field SmallFood sf; and use it instead ?
    public void addFood(SmallFood sf) {
        frame.getContentPane().add(sf);
        frame.revalidate();
        frame.repaint();
    }
    public synchronized void removeFood(SmallFood sf) {
        frame.getContentPane().remove(sf);
        frame.revalidate();
        frame.repaint();
    }

    public synchronized boolean gameOver() {
        return gameOver;
    }

    public synchronized void setGameOver(boolean stopGame) {
        this.gameOver = stopGame;
    }

    public synchronized static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

}

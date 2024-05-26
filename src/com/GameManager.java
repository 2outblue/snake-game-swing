package com;

import com.components.ComponentFactory;
import com.components.border.BorderComponent;
import com.components.food.SmallFood;
import com.components.snake.SnakeBody;
import com.components.snake.SnakeHead;
import com.components.constants.Direction;
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
        growSnake();
        growSnake();
        growSnake();
        growSnake();
        growSnake();

    }

    private void setInitialSnakePosition(){
        snake.getHead().setBounds(390, 390, 15, 15);
    }

    private void createSnake() {
        SnakeHead h1 = componentFactory.createHead();
        h1.setDirection(Direction.UP);
        frame.getContentPane().add(h1);

        SnakeBody body1 = componentFactory.createBody();
        body1.setDirection(Direction.DOWN);
        frame.getContentPane().add(body1);

        List<SnakeBody> bl = new ArrayList<>();
        bl.add(body1);

        Snake.instantiateSnake(h1, bl);
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

    public synchronized void addFood(SmallFood sf) {
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

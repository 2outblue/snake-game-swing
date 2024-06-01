package com;

import com.components.ComponentFactory;
import com.components.background.BackgroundComponent;
import com.components.constants.ComponentConst;
import com.components.food.SmallFood;
import com.components.menu.MenuComponent;
import com.components.menu.MenuButton;
import com.components.snake.SnakeBody;
import com.components.snake.SnakeHead;
import com.components.constants.Direction;
import com.event_listeners.HeadDirectionChangeListener;
import com.event_listeners.PauseListener;
import com.event_listeners.menu_events.PlayButtonActionListener;
import com.game_objects.Border;
import com.game_objects.Snake;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static GameManager instance;
    private ComponentFactory componentFactory;
    private JFrame frame;

    private ThreadGovernor threadGovernor;

    private Snake snake;

    private Border border;

    private JLabel pauseElement;

    private JLabel gameOverElement;

    private JComponent background;

    private JLayeredPane layeredPane;

    private boolean gameOver = false;



    private GameManager() {
        componentFactory = ComponentFactory.getInstance();
        this.frame = componentFactory.createFrame();
        createLayeredPane();
    }

    public void createAndShowGame(){
        displayStartScreen();
//        createBackground();
//        createSnake();
//        threadGovernor = ThreadGovernor.getInstance();
//        setInitialSnakePosition();
//        addEventListeners();
//        createBorder();

//        threadGovernor.createMovementThread();
//        threadGovernor.createFoodGenerationThread();

//        growSnake();
//        growSnake();
//        growSnake();
//        growSnake();
//        growSnake();
//        growSnake();
//        growSnake();
    }

    private void displayStartScreen() {
        layeredPane.add(componentFactory.createPlayButton(), JLayeredPane.POPUP_LAYER);

        MenuComponent startMenu = new MenuComponent();
        startMenu.setBounds(0, 0, ComponentConst.FRAME_WIDTH, ComponentConst.FRAME_HEIGHT);
        layeredPane.add(startMenu, JLayeredPane.POPUP_LAYER);

    }

    public void startGame() {

        layeredPane.removeAll();

        createBackground();
        createSnake();
        threadGovernor = ThreadGovernor.getInstance();
        setInitialSnakePosition();
        addEventListeners();
        threadGovernor.createMovementThread();

        frame.requestFocusInWindow();
        growSnake();
        growSnake();
        growSnake();
        growSnake();
        growSnake();
        growSnake();
        growSnake();
    }
    private void restart() {

    }
    private void setInitialSnakePosition(){
        snake.getHead().setBounds(390, 390, ComponentConst.SNAKE_HEAD_20, ComponentConst.SNAKE_HEAD_20);
    }

    private void createBackground() {
        background = new BackgroundComponent();
        background.setBounds(0, 0, 800, 800);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
    }

    private void createLayeredPane() {
        this.layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(frame.getPreferredSize());
        frame.setContentPane(layeredPane);
    }

    private void createSnake() {
        SnakeHead h1 = componentFactory.createHead();
        h1.setDirection(Direction.UP);
//        h1.setBounds(390, 390, ComponentConst.SNAKE_COMPONENT_SIZE, ComponentConst.SNAKE_COMPONENT_SIZE);

//        frame.getContentPane().add(h1);
        layeredPane.add(h1, JLayeredPane.PALETTE_LAYER);

//        frame.setComponentZOrder(h1, 0);
        frame.revalidate();
        frame.repaint();

        SnakeBody body1 = componentFactory.createBody();
        body1.setDirection(Direction.DOWN);

//        frame.getContentPane().add(body1);
        layeredPane.add(body1, JLayeredPane.PALETTE_LAYER);

        List<SnakeBody> bl = new ArrayList<>();
        bl.add(body1);

        List<SnakeBody> tail = new ArrayList<>();
        SnakeBody tailPart12 = componentFactory.createTailPart(12, ComponentConst.TAIL_12);
//        frame.getContentPane().add(tailPart12);
        layeredPane.add(tailPart12, JLayeredPane.PALETTE_LAYER);
        tail.add(tailPart12);
        SnakeBody tailPart = componentFactory.createTailPart(11, ComponentConst.TAIL_11);
//        frame.getContentPane().add(tailPart);
        layeredPane.add(tailPart, JLayeredPane.PALETTE_LAYER);
        tail.add(tailPart);
        SnakeBody tailPart2 = componentFactory.createTailPart(10, ComponentConst.TAIL_10);
//        frame.getContentPane().add(tailPart2);
        layeredPane.add(tailPart2, JLayeredPane.PALETTE_LAYER);
        tail.add(tailPart2);
        SnakeBody tailPart3 = componentFactory.createTailPart(9, ComponentConst.TAIL_9);
//        frame.getContentPane().add(tailPart3);
        layeredPane.add(tailPart3, JLayeredPane.PALETTE_LAYER);
        tail.add(tailPart3);
        SnakeBody tailPart4 = componentFactory.createTailPart(8, ComponentConst.TAIL_8);
//        frame.getContentPane().add(tailPart4);
        layeredPane.add(tailPart4, JLayeredPane.PALETTE_LAYER);
        tail.add(tailPart4);
        SnakeBody tailPart5 = componentFactory.createTailPart(7, ComponentConst.TAIL_7);
//        frame.getContentPane().add(tailPart5);
        layeredPane.add(tailPart5, JLayeredPane.PALETTE_LAYER);
        tail.add(tailPart5);
        SnakeBody tailPart6 = componentFactory.createTailPart(5, ComponentConst.TAIL_5);
//        frame.getContentPane().add(tailPart6);
        layeredPane.add(tailPart6, JLayeredPane.PALETTE_LAYER);
        tail.add(tailPart6);

        Snake.instantiateSnake(h1, bl, tail);
        snake = Snake.getInstance();
    }


    private void addEventListeners() {
        HeadDirectionChangeListener arrowKeysListener = new HeadDirectionChangeListener();
//        frame.addKeyListener(new HeadDirectionChangeListener());
        frame.addKeyListener(arrowKeysListener);
//        System.out.println(Arrays.toString(frame.getKeyListeners()));
        frame.addKeyListener(new PauseListener());
    }

    public synchronized void growSnake() {
        SnakeBody body = componentFactory.createBody();
//        frame.getContentPane().add(body);
        layeredPane.add(body, JLayeredPane.PALETTE_LAYER);
        SnakeBody body2 = componentFactory.createBody();
//        frame.getContentPane().add(body2);
        layeredPane.add(body2, JLayeredPane.PALETTE_LAYER);
        snake.grow(body);
        snake.grow(body2);
    }

//    private void createBorder() {
//        this.border = new Border();
//        for (int i = 0; i < border.getBorderComponents().length; i++) {
//            BorderComponent bc = border.getBorderComponents()[i];
////            frame.getContentPane().add(bc);
//            layeredPane.add(bc, JLayeredPane.PALETTE_LAYER);
//            bc.setBounds(bc.x, bc.y, bc.getPreferredSize().width, bc.getPreferredSize().height);
//        }
//    }

    public synchronized void pauseGame() {
        if (pauseElement == null) {
            pauseElement = componentFactory.createPauseLabel();
        }
        if (snake.isPaused()) {
            snake.setPaused(false);
            try {
//                frame.setComponentZOrder(pauseElement, 1);
//                frame.getContentPane().remove(pauseElement);
                layeredPane.remove(pauseElement);
                frame.revalidate();
                frame.repaint();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        } else {
//            frame.getContentPane().add(pauseElement);
            layeredPane.add(pauseElement, JLayeredPane.POPUP_LAYER);
//            frame.setComponentZOrder(pauseElement, 0);
//            frame.repaint();

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
        layeredPane.add(componentFactory.createGameOverComponent(), JLayeredPane.POPUP_LAYER);

        gameOver = true;
        snake.setPaused(true);
    }

    // maybe make a class field SmallFood sf; and use it instead ?
    public void addFood(SmallFood sf) {
//        frame.getContentPane().add(sf);
        layeredPane.add(sf, JLayeredPane.PALETTE_LAYER);
//        frame.revalidate();
//        frame.repaint();
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

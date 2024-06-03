package com;

import com.components.ComponentFactory;
import com.constants.ComponentConst;
import com.components.menu.DifficultyMark;
import com.game_objects.SmallFood;
import com.components.menu.GamePausedComponent;
import com.components.menu.MenuComponent;
import com.components.snake.SnakeBody;
import com.components.snake.SnakeHead;
import com.constants.Direction;
import com.event_listeners.HeadDirectionChangeListener;
import com.event_listeners.PauseListener;
import com.game_objects.Border;
import com.game_objects.Snake;
import com.game_utility.CoordinateStore;
import com.game_utility.Difficulty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static GameManager instance;
    private ComponentFactory componentFactory;
    private JFrame frame;
    private JLayeredPane layeredPane;
    private ThreadGovernor threadGovernor;

    private ScoreManager scoreManager;
    private Snake snake;

    private Border border;

    private GamePausedComponent pauseElement;

    private JLabel gameOverElement;

    private JComponent background;

    private DifficultyMark difficultyMark;

    private boolean gameOver = false;

    private Difficulty gameDifficulty;

    private JLabel score;

    private JLabel bestScore;

    private GameManager() {
        componentFactory = ComponentFactory.getInstance();
        this.frame = componentFactory.createFrame();
        createLayeredPane();
        gameDifficulty = Difficulty.EASY;
        threadGovernor = ThreadGovernor.getInstance();
        score = componentFactory.createScoreComponent();
        scoreManager = ScoreManager.getInstance();
    }

    public void createAndShowGame(){
        displayStartScreen();
    }

    public void displayStartScreen() {
        createMenu();
        setDifficulty(Difficulty.EASY);
    }

    public void startGame() {
        gameOver = false;
        layeredPane.removeAll();
//        layeredPane.revalidate();
//        layeredPane.repaint();

        scoreManager.resetScore();
        createMap();
        createSnake();
        setInitialSnakePosition();
        addEventListeners();
        displayBestScore();
        displayCurrentScore();
        threadGovernor.createMovementThread();

        layeredPane.requestFocusInWindow();

        growSnake();
        growSnake();
        growSnake();
        growSnake();
        growSnake();
        growSnake();
    }
    public synchronized void restart() {

        layeredPane.removeAll();
        for (KeyListener listener : layeredPane.getKeyListeners()) {
            layeredPane.removeKeyListener(listener);
        }
        threadGovernor.closeAllThreads();
        snake = null;

        displayStartScreen();
    }
    private void setInitialSnakePosition(){
        snake.getHead().setBounds(390, 390, ComponentConst.SNAKE_HEAD_20, ComponentConst.SNAKE_HEAD_20);
    }

    private void createLayeredPane() {
        this.layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(frame.getPreferredSize());
        frame.setContentPane(layeredPane);
    }

    private void createSnake() {
        SnakeHead h1 = componentFactory.createHead();
        h1.setDirection(Direction.UP);
        layeredPane.add(h1, JLayeredPane.PALETTE_LAYER);

        SnakeBody body1 = componentFactory.createBody();
        body1.setDirection(Direction.DOWN);
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

//        Snake.instantiateSnake(h1, bl, tail);
        snake = new Snake(h1, bl, tail);
    }
    private void addEventListeners() {
        layeredPane.addKeyListener(new HeadDirectionChangeListener());
        layeredPane.addKeyListener(new PauseListener());
    }
    public synchronized void growSnake() {
        SnakeBody body = componentFactory.createBody();
        layeredPane.add(body, JLayeredPane.PALETTE_LAYER);

        SnakeBody body2 = componentFactory.createBody();
        layeredPane.add(body2, JLayeredPane.PALETTE_LAYER);

        snake.grow(body);
        snake.grow(body2);
    }
    public synchronized void pauseGame() {
        if (pauseElement == null) {
            pauseElement = componentFactory.createGamePauseComponent();
        }
        if (snake.isPaused()) {
            snake.setPaused(false);
            // TODO: is this try-catch necessary ?
            try {
                layeredPane.remove(pauseElement);

                for (Component comp : layeredPane.getComponentsInLayer(JLayeredPane.POPUP_LAYER)) {
                    layeredPane.remove(comp);
                }
                layeredPane.revalidate();
                layeredPane.repaint();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        } else {
//            frame.getContentPane().add(pauseElement);
            layeredPane.add(componentFactory.createBackToMenuButton(), JLayeredPane.POPUP_LAYER);
            layeredPane.add(pauseElement, JLayeredPane.POPUP_LAYER);

            snake.setPaused(true);
        }
    }
    public synchronized void endGame() {
        layeredPane.add(componentFactory.createBackToMenuButton(), JLayeredPane.POPUP_LAYER);
        layeredPane.add(componentFactory.createGameOverComponent(), JLayeredPane.POPUP_LAYER);

        gameOver = true;
        snake.setPaused(true);
    }

    // maybe make a class field SmallFood sf; and use it instead ?
    public void addFood(SmallFood sf) {
        layeredPane.add(sf, JLayeredPane.PALETTE_LAYER);
    }
    public void removeFood(SmallFood sf) {
        layeredPane.remove(sf);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    private void createMap() {
        layeredPane.revalidate();
        layeredPane.repaint();
        background = componentFactory.createGameBackground(gameDifficulty);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
    }

    public void setDifficulty(Difficulty dif) {
        CoordinateStore.setDifficulty(dif);
        difficultyMark.set(dif);
        gameDifficulty = dif;

    }

    private void createMenu() {
        // event listeners for button clicks are automatically attached in the component factory
        layeredPane.add(componentFactory.createPlayButton(), JLayeredPane.POPUP_LAYER);
        layeredPane.add(componentFactory.createEasyButton(), JLayeredPane.POPUP_LAYER);
        layeredPane.add(componentFactory.createMediumButton(), JLayeredPane.POPUP_LAYER);
        layeredPane.add(componentFactory.createHardButton(), JLayeredPane.POPUP_LAYER);
        layeredPane.add(componentFactory.createQuitButton(), JLayeredPane.POPUP_LAYER);

        difficultyMark = new DifficultyMark();
        difficultyMark.set(Difficulty.EASY);
        layeredPane.add(difficultyMark, JLayeredPane.POPUP_LAYER);

        MenuComponent startMenuBackground = new MenuComponent();
        startMenuBackground.setBounds(0, 0, ComponentConst.FRAME_WIDTH, ComponentConst.FRAME_HEIGHT);
        layeredPane.add(startMenuBackground, JLayeredPane.POPUP_LAYER);
    }

    private void displayBestScore() {
        layeredPane.add(componentFactory.createBestComponent(), JLayeredPane.POPUP_LAYER);
        bestScore = componentFactory.createScoreComponent();
        bestScore.setHorizontalAlignment(SwingConstants.LEFT);
        bestScore.setBounds(150, 15, 100, 50);
        bestScore.setText(scoreManager.getCurrentBest());
        layeredPane.add(bestScore, JLayeredPane.POPUP_LAYER);
    }

    private void displayCurrentScore() {
        layeredPane.add(score, JLayeredPane.PALETTE_LAYER);
    }

    public void updateScore(String value){
        score.setText(value);
    }

    public void updateBestScore(String value) {
        bestScore.setText(value);
    }
    public synchronized boolean gameOver() {
        return gameOver;
    }

    public void quitGame() {
        frame.dispose();
    }

    public synchronized static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public synchronized Snake getSnake() {
        return snake;
    }
}

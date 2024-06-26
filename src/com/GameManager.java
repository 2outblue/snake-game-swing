package com;

import com.components.ComponentFactory;
import com.constants.ComponentBounds;
import com.components.menu.DifficultyMark;
import com.game_objects.SmallFood;
import com.components.snake.SnakeBody;
import com.game_objects.Snake;
import com.game_utility.MapCollisionData;
import com.game_utility.Difficulty;

import javax.swing.*;
import java.awt.*;

public class GameManager {

    private static GameManager instance;
    private final ComponentFactory componentFactory;
    private final JFrame frame;
    private final JLayeredPane layeredPane;
    private final ThreadGovernor threadGovernor;
    private final ScoreManager scoreManager;

    private Snake snake;
    private final JLabel score;
    private JLabel bestScore;

    private final DifficultyMark difficultyMark;
    private boolean inGame = false;
    private Difficulty selectedDifficulty;


    private GameManager() {
        componentFactory = ComponentFactory.getInstance();
        frame = componentFactory.createFrame();
        layeredPane = frame.getLayeredPane();

        selectedDifficulty = Difficulty.EASY;
        difficultyMark = componentFactory.createDifficultyMark();
        threadGovernor = ThreadGovernor.getInstance();
        score = componentFactory.createScoreValueComponent();
        scoreManager = ScoreManager.getInstance();
    }

    public void renderMainMenu() {
        // set the difficulty in the menu since the check mark gets its position data based on
        // which difficulty is currently selected (obviously)
        setDifficulty(Difficulty.EASY);

        // event listeners for button clicks are automatically attached in the component factory
        // component bounds are also set in the factory.
        // The game starts when the play button is pressed -
        layeredPane.add(componentFactory.createPlayButton(), JLayeredPane.POPUP_LAYER);
        layeredPane.add(componentFactory.createEasyButton(), JLayeredPane.POPUP_LAYER);
        layeredPane.add(componentFactory.createMediumButton(), JLayeredPane.POPUP_LAYER);
        layeredPane.add(componentFactory.createHardButton(), JLayeredPane.POPUP_LAYER);
        layeredPane.add(componentFactory.createQuitButton(), JLayeredPane.POPUP_LAYER);

        layeredPane.add(difficultyMark, JLayeredPane.POPUP_LAYER);

        layeredPane.add(componentFactory.createMenuBackground(), JLayeredPane.POPUP_LAYER);
    }

    public void startGame() {
        inGame = true;
        layeredPane.removeAll();
        layeredPane.revalidate();
        layeredPane.repaint();

        scoreManager.resetCurrentScore();
        renderMap();
        // if you don't render the snake, the game crashes (in contrast to everything else with the 'render' prefix)
        renderSnake();
        renderBestScore();
        renderCurrentScore();
        setInitialSnakePosition();

        threadGovernor.startGameLoopThread();

        layeredPane.requestFocusInWindow();

        // initially the snake has only one body part (excluding the tail) - this makes the snake a bit longer when starting a game
        growSnake();
        growSnake();
        growSnake();
        growSnake();
        growSnake();
    }
    public synchronized void restart() {
        layeredPane.removeAll();
        threadGovernor.closeAllThreads();
        snake = null;

        renderMainMenu();
    }

    private void setInitialSnakePosition(){
        if (snake != null) {
            snake.getHead().setBounds(390, 390, ComponentBounds.SNAKE_HEAD_20, ComponentBounds.SNAKE_HEAD_20);
        }
    }
    private void renderSnake() {
        snake = componentFactory.createSnake();

        layeredPane.add(snake.getHead(), JLayeredPane.PALETTE_LAYER);
        // createSnake() adds only one body part to the snake, so no need to loop through the body-list //(prob better with a loop regardless)
        layeredPane.add(snake.getBody().getFirst(), JLayeredPane.PALETTE_LAYER);

        for (SnakeBody tailPart : snake.getTail()) {
            layeredPane.add(tailPart, JLayeredPane.PALETTE_LAYER);
        }
    }
    public synchronized void growSnake() {
        // Ads two body parts per call of this method
        SnakeBody body = componentFactory.createBodyPart();
        layeredPane.add(body, JLayeredPane.PALETTE_LAYER);

        SnakeBody body2 = componentFactory.createBodyPart();
        layeredPane.add(body2, JLayeredPane.PALETTE_LAYER);

        snake.grow(body);
        snake.grow(body2);
    }
    public synchronized void pauseGame() {
        if (snake.isStopped()) {
            snake.setStopped(false);
            // TODO: is this try-catch necessary ?
            try {
                for (Component comp : layeredPane.getComponentsInLayer(JLayeredPane.POPUP_LAYER)) {
                    layeredPane.remove(comp);
                }
                layeredPane.revalidate();
                layeredPane.repaint();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        } else {
            layeredPane.add(componentFactory.createBackToMenuButton(), JLayeredPane.POPUP_LAYER);
            layeredPane.add(componentFactory.createGamePauseComponent(), JLayeredPane.POPUP_LAYER);

            snake.setStopped(true);
        }
    }
    public void endGame() {
        layeredPane.add(componentFactory.createBackToMenuButton(), JLayeredPane.POPUP_LAYER);
        layeredPane.add(componentFactory.createGameOverComponent(), JLayeredPane.POPUP_LAYER);

        inGame = false;
        snake.setStopped(true);
    }

    public void renderFood(SmallFood sf) {
        layeredPane.add(sf, JLayeredPane.PALETTE_LAYER);
    }
    public void removeFood(SmallFood sf) {
        layeredPane.remove(sf);
        layeredPane.revalidate();
        layeredPane.repaint();
    }
    private void renderMap() {
        layeredPane.revalidate();
        layeredPane.repaint();

        layeredPane.add(componentFactory.createMap(selectedDifficulty), JLayeredPane.DEFAULT_LAYER);
    }

    public synchronized void setDifficulty(Difficulty dif) {
        MapCollisionData.setDifficulty(dif);
        difficultyMark.set(dif);
        selectedDifficulty = dif;

    }

    public synchronized Difficulty getDifficulty() {
        return selectedDifficulty;
    }

    // Scores are rendered on the modal layer, since when un-pausing the game, everything on the popup layer is removed
    private void renderBestScore() {
        layeredPane.add(componentFactory.createBestScoreLabel(), JLayeredPane.MODAL_LAYER);

        bestScore = componentFactory.createScoreValueComponent();
        bestScore.setHorizontalAlignment(SwingConstants.LEFT);
        bestScore.setBounds(150, 15, 100, 50);
        bestScore.setText(scoreManager.getCurrentBest());

        layeredPane.add(bestScore, JLayeredPane.MODAL_LAYER);
    }
    public void updateBestScore(String value) {
        bestScore.setText(value);
    }
    private void renderCurrentScore() {
        layeredPane.add(score, JLayeredPane.MODAL_LAYER);
    }
    public void updateCurrentScore(String value){
        score.setText(value);
    }
    public synchronized boolean inGame() {
        return inGame;
    }

    public void quitGame() {
        frame.dispose();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public synchronized Snake getSnake() {
        return snake;
    }
}

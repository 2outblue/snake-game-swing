package com.components;

import com.components.background.BackgroundComponent;
import com.constants.ComponentConst;
import com.game_objects.SmallFood;
import com.components.menu.GameOverComponent;
import com.components.menu.GamePausedComponent;
import com.components.menu.MenuButton;
import com.components.snake.SnakeBody;
import com.components.snake.SnakeHead;
import com.event_listeners.menu_events.BackToMenuListener;
import com.event_listeners.menu_events.ButtonHoverListener;
import com.event_listeners.menu_events.MenuButtonActionListener;
import com.game_utility.Difficulty;

import javax.swing.*;
import java.awt.*;

public class ComponentFactory {

    private static ComponentFactory instance;
    private JFrame frame;

    // below variables are for the snake color pattern
    private String[] pattern;
    private int repeatCounter;
    private final int patternRepeater;
    private int patternPointer;

    // one instance per event listener to attach to all menu buttons

    private MenuButtonActionListener menuButtonLister;
    private ButtonHoverListener buttonHoverListener;
//    private TailComponent snakeTail;

    private ComponentFactory() {
        repeatCounter = 1;
        patternPointer = 0;
        patternRepeater = 2;
        makePattern();
        menuButtonLister = new MenuButtonActionListener();
        buttonHoverListener = new ButtonHoverListener();
    }

    public SnakeBody createTailPart(int size, String path) {
        return new SnakeBody(size, path);

    }

    public SnakeBody createBody() {
        repeatCounter++;

        if (repeatCounter > patternRepeater) {
            repeatCounter = 1;

            patternPointer++;
            if (patternPointer > 4) {patternPointer = 0;}
        }
        return new SnakeBody(ComponentConst.SNAKE_COMPONENT_SIZE, pattern[patternPointer]);
    }

    public SnakeHead createHead() {

        return new SnakeHead(ComponentConst.SNAKE_HEAD_20);

    }

    public JFrame createFrame() {
        if (this.frame == null) {
            JFrame frame = new JFrame("Snake");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setUndecorated(true);
            frame.setLayout(null);

            frame.pack();
            frame.setVisible(true);
            frame.setSize(800, 800);
            frame.setResizable(false);
            //sets the window location in the center of the screen
            frame.setLocationRelativeTo(null);
            this.frame = frame;
        }
        return this.frame;
    }

    public BackgroundComponent createGameBackground(Difficulty dif) {
        BackgroundComponent bc = new BackgroundComponent(ComponentConst.BACKGROUND_1);
        if (dif == Difficulty.MEDIUM) {
            bc = new BackgroundComponent(ComponentConst.BACKGROUND_2);
        } else if (dif == Difficulty.HARD) {
            bc = new BackgroundComponent(ComponentConst.BACKGROUND_3);
        }
        bc.setBounds(0, 0, 800, 800);

        return bc;
    }
    public MenuButton createPlayButton() {
        MenuButton playButton = new MenuButton(ComponentConst.BUTTON_1_PLAY);
        playButton.setBounds(275, 125, 250, 75);
        playButton.setActionCommand("play");
        playButton.addMouseListener(buttonHoverListener);
        playButton.addActionListener(menuButtonLister);
        return playButton;
    }

    public MenuButton createBackToMenuButton() {
        MenuButton button = new MenuButton(ComponentConst.BUTTON_BACK_TO_MENU);
        button.addActionListener(new BackToMenuListener());
        button.setBounds(275, 430, 250, 75);
        button.addMouseListener(buttonHoverListener);
        return button;
    }

    public MenuButton createEasyButton() {
        MenuButton button = new MenuButton(ComponentConst.BUTTON_EASY);
        button.setBounds(275, 336, 250, 75);
        button.setActionCommand("easy");
        button.addActionListener(menuButtonLister);
        button.addMouseListener(buttonHoverListener);
        return button;
    }

    public MenuButton createMediumButton() {
        MenuButton button = new MenuButton(ComponentConst.BUTTON_MEDIUM);
        button.setBounds(275, 451, 250, 75);
        button.setActionCommand("medium");
        button.addActionListener(menuButtonLister);
        button.addMouseListener(buttonHoverListener);
        return button;
    }

    public MenuButton createHardButton() {
        MenuButton button = new MenuButton(ComponentConst.BUTTON_HARD);
        button.setBounds(275, 566, 250, 75);
        button.setActionCommand("hard");
        button.addActionListener(menuButtonLister);
        button.addMouseListener(buttonHoverListener);
        return button;
    }

    public MenuButton createQuitButton() {
        MenuButton button = new MenuButton(ComponentConst.BUTTON_QUIT);
        button.setBounds(275, 670, 250, 75);
        button.setActionCommand("quit");
        button.addActionListener(menuButtonLister);
        button.addMouseListener(buttonHoverListener);
        return button;
    }

    public GamePausedComponent createGamePauseComponent() {
        GamePausedComponent gp = new GamePausedComponent();
        gp.setBounds(170, 325, ComponentConst.GAME_PAUSED_WIDTH, ComponentConst.GAME_PAUSED_HEIGHT);
        return gp;

//        JLabel label = new JLabel("GAME PAUSED", SwingConstants.CENTER);
//        label.setForeground(Color.WHITE);
//        label.setBackground(Color.GREEN);
//        label.setOpaque(true);
//        label.setBorder(new LineBorder(Color.GREEN, 2));
//
//        return label;
    }

//    public JLabel createGameOverLabel() {
//        JLabel label = new JLabel("GAME OVER", SwingConstants.CENTER);
//        label.setForeground(Color.BLACK);
//        label.setBackground(Color.RED);
//        label.setOpaque(true);
//        label.setBorder(new LineBorder(Color.GREEN, 2));
//
//        return label;
//    }

    public GameOverComponent createGameOverComponent() {
        GameOverComponent g = new GameOverComponent();
        g.setBounds(316, 339, ComponentConst.GAME_OVER_WIDTH, ComponentConst.GAME_OVER_HEIGHT);
        return g;
    }

    public SmallFood createSmallFood() {
        return new SmallFood(ComponentConst.FOOD_SIZE, ComponentConst.APPLE_RED_1);
    }

    public JLabel createScoreComponent() {
        JLabel scoreLabel = new JLabel("0");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Luckiest guy", Font.BOLD, 24));
        scoreLabel.setForeground(Color.BLUE);
        scoreLabel.setBounds(350, 15, 100, 50);
        return scoreLabel;
    }
    public static ComponentFactory getInstance() {
            if (instance == null) {
                instance = new ComponentFactory();
            }
            return instance;
    }

    private void makePattern() {
        pattern = new String[5];

        pattern[0] = ComponentConst.BODY_13_GREEN;
        pattern[1] = ComponentConst.BODY_13_RED;
        pattern[2] = ComponentConst.BODY_13_PURPLE;
        pattern[3] = ComponentConst.BODY_13_YELLOW;
        pattern[4] = ComponentConst.BODY_13_BLUE;
    }
}

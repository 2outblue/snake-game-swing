package com.components;

import com.components.background.GameMapComponent;
import com.components.background.BestScoreLabel;
import com.components.menu.*;
import com.components.menu.MenuBackgroundComponent;
import com.constants.ComponentBounds;
import com.constants.Direction;
import com.constants.Resources;
import com.event_listeners.HeadDirectionChangeListener;
import com.event_listeners.PauseListener;
import com.game_objects.SmallFood;
import com.components.snake.SnakeBody;
import com.components.snake.SnakeHead;
import com.event_listeners.menu_events.BackToMenuListener;
import com.event_listeners.menu_events.ButtonHoverListener;
import com.event_listeners.menu_events.MenuButtonActionListener;
import com.game_objects.Snake;
import com.game_utility.Difficulty;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComponentFactory {

    private static ComponentFactory instance;
    // below variables are for the snake color pattern
    private String[] pattern;
    private int repeatCounter;
    private final int patternRepeater;
    private int patternPointer;

    // one instance per event listener to attach to all menu buttons (instead of new event listener for each button)
    private final MenuButtonActionListener menuButtonLister;
    private final ButtonHoverListener buttonHoverListener;

    private ComponentFactory() {
        repeatCounter = 1;
        patternPointer = 0;
        patternRepeater = 2;
        makeSnakeColorPattern();
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
        return new SnakeBody(ComponentBounds.SNAKE_COMPONENT_SIZE, pattern[patternPointer]);
    }

    public SnakeHead createHead() {
        SnakeHead sh = new SnakeHead(ComponentBounds.SNAKE_HEAD_20);
        sh.setDirection(Direction.UP);
        return sh;
    }

    public Snake createSnake() {
        SnakeBody body1 = createBody();
        body1.setDirection(Direction.DOWN);

        List<SnakeBody> bl = new ArrayList<>();
        bl.add(body1);

        List<SnakeBody> tail = new ArrayList<>();

        SnakeBody tailPart12 = createTailPart(12, Resources.TAIL_12);
        tail.add(tailPart12);

        SnakeBody tailPart = createTailPart(11, Resources.TAIL_11);
        tail.add(tailPart);

        SnakeBody tailPart2 = createTailPart(10, Resources.TAIL_10);
        tail.add(tailPart2);

        SnakeBody tailPart3 = createTailPart(9, Resources.TAIL_9);
        tail.add(tailPart3);

        SnakeBody tailPart4 = createTailPart(8, Resources.TAIL_8);
        tail.add(tailPart4);

        SnakeBody tailPart5 = createTailPart(7, Resources.TAIL_7);
        tail.add(tailPart5);

        SnakeBody tailPart6 = createTailPart(5, Resources.TAIL_5);
        tail.add(tailPart6);

        return new Snake(createHead(), bl, tail);
    }

    public JFrame createFrame() {
            JFrame frame = new JFrame("Snake");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setUndecorated(true);
            frame.setLayout(null);

            frame.pack();
            frame.setVisible(true);
            frame.setSize(ComponentBounds.FRAME_WIDTH, ComponentBounds.FRAME_HEIGHT);
            frame.setResizable(false);
            //sets the window location in the center of the screen
            frame.setLocationRelativeTo(null);

            JLayeredPane lp = new JLayeredPane();
            lp.setPreferredSize(frame.getPreferredSize());
            lp.addKeyListener(new HeadDirectionChangeListener());
            lp.addKeyListener(new PauseListener());
            frame.setLayeredPane(lp);

        return frame;
    }

    public MenuBackgroundComponent createMenuBackground() {
        MenuBackgroundComponent mb = new MenuBackgroundComponent();
        mb.setBounds(0, 0, ComponentBounds.FRAME_WIDTH, ComponentBounds.FRAME_HEIGHT);
        return mb;
    }

    public DifficultyMark createDifficultyMark() {
        DifficultyMark dfm = new DifficultyMark();
        dfm.set(Difficulty.EASY);
        return dfm;
    }

    public GameMapComponent createMap(Difficulty dif) {
        GameMapComponent bc = new GameMapComponent(Resources.MAP_EASY);
        if (dif == Difficulty.MEDIUM) {
            bc = new GameMapComponent(Resources.MAP_MEDIUM);
        } else if (dif == Difficulty.HARD) {
            bc = new GameMapComponent(Resources.MAP_HARD);
        }
        bc.setBounds(0, 0, ComponentBounds.FRAME_WIDTH, ComponentBounds.FRAME_HEIGHT);

        return bc;
    }
    public MenuButton createPlayButton() {
        MenuButton button = new MenuButton(Resources.BUTTON_1_PLAY);
        button.setBounds(ComponentBounds.MENU_BUTTON_X, ComponentBounds.BUTTON_PLAY_Y, ComponentBounds.BUTTON_WIDTH, ComponentBounds.BUTTON_HEIGHT);
        button.setActionCommand("play");
        button.addMouseListener(buttonHoverListener);
        button.addActionListener(menuButtonLister);
        return button;
    }

    public MenuButton createEasyButton() {
        MenuButton button = new MenuButton(Resources.BUTTON_EASY);
        button.setBounds(ComponentBounds.MENU_BUTTON_X, ComponentBounds.BUTTON_EASY_Y, ComponentBounds.BUTTON_WIDTH, ComponentBounds.BUTTON_HEIGHT);
        button.setActionCommand("easy");
        button.addActionListener(menuButtonLister);
        button.addMouseListener(buttonHoverListener);
        return button;
    }

    public MenuButton createMediumButton() {
        MenuButton button = new MenuButton(Resources.BUTTON_MEDIUM);
        button.setBounds(ComponentBounds.MENU_BUTTON_X, ComponentBounds.BUTTON_MEDIUM_Y, ComponentBounds.BUTTON_WIDTH, ComponentBounds.BUTTON_HEIGHT);
        button.setActionCommand("medium");
        button.addActionListener(menuButtonLister);
        button.addMouseListener(buttonHoverListener);
        return button;
    }

    public MenuButton createHardButton() {
        MenuButton button = new MenuButton(Resources.BUTTON_HARD);
        button.setBounds(ComponentBounds.MENU_BUTTON_X, ComponentBounds.BUTTON_HARD_Y, ComponentBounds.BUTTON_WIDTH, ComponentBounds.BUTTON_HEIGHT);
        button.setActionCommand("hard");
        button.addActionListener(menuButtonLister);
        button.addMouseListener(buttonHoverListener);
        return button;
    }

    public MenuButton createQuitButton() {
        MenuButton button = new MenuButton(Resources.BUTTON_QUIT);
        button.setBounds(ComponentBounds.MENU_BUTTON_X, ComponentBounds.BUTTON_QUIT_Y, ComponentBounds.BUTTON_WIDTH, ComponentBounds.BUTTON_HEIGHT);
        button.setActionCommand("quit");
        button.addActionListener(menuButtonLister);
        button.addMouseListener(buttonHoverListener);
        return button;
    }

    public MenuButton createBackToMenuButton() {
        MenuButton button = new MenuButton(Resources.BUTTON_BACK_TO_MENU);
        button.addActionListener(new BackToMenuListener());
        button.setBounds(ComponentBounds.BUTTON_BACK_TO_MENU_X, ComponentBounds.BUTTON_BACK_TO_MENU_Y, ComponentBounds.BUTTON_WIDTH, ComponentBounds.BUTTON_HEIGHT);
        button.addMouseListener(buttonHoverListener);
        return button;
    }

    // TODO: MOVE ALL BOUND VALUES TO CONSTANTS
    public GamePausedComponent createGamePauseComponent() {
        GamePausedComponent gp = new GamePausedComponent();
        gp.setBounds(ComponentBounds.GAME_PAUSED_X, ComponentBounds.GAME_PAUSED_Y, ComponentBounds.GAME_PAUSED_WIDTH, ComponentBounds.GAME_PAUSED_HEIGHT);
        return gp;
    }

    public GameOverComponent createGameOverComponent() {
        GameOverComponent g = new GameOverComponent();
        g.setBounds(ComponentBounds.GAME_OVER_X, ComponentBounds.GAME_OVER_Y, ComponentBounds.GAME_OVER_WIDTH, ComponentBounds.GAME_OVER_HEIGHT);
        return g;
    }

    public SmallFood createSmallFood() {
        return new SmallFood(ComponentBounds.FOOD_SIZE, Resources.APPLE_RED_1);
    }

    // creates the label which holds a number to show the score (both current AND best score)
    public JLabel createScoreValueComponent() {
        JLabel scoreLabel = new JLabel("0");
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(Resources.TEXT_FONT)).deriveFont(Font.BOLD, 36);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            scoreLabel.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setForeground(Color.MAGENTA);
        scoreLabel.setBounds(375, 15, 50, 50);
        return scoreLabel;
    }

    // creates the BEST + the apple component - not the actual dynamic best score value
    public BestScoreLabel createBestScoreLabel() {
        BestScoreLabel bc = new BestScoreLabel();
        bc.setBounds(15, 15, bc.getPreferredSize().width, bc.getPreferredSize().height);
        return bc;
    }
    public static ComponentFactory getInstance() {
            if (instance == null) {
                instance = new ComponentFactory();
            }
            return instance;
    }

    private void makeSnakeColorPattern() {
        pattern = new String[5];

        pattern[0] = Resources.BODY_13_GREEN;
        pattern[1] = Resources.BODY_13_RED;
        pattern[2] = Resources.BODY_13_PURPLE;
        pattern[3] = Resources.BODY_13_YELLOW;
        pattern[4] = Resources.BODY_13_BLUE;
    }
}

package com.components;

import com.components.background.BackgroundComponent;
import com.components.background.BestComponent;
import com.components.menu.*;
import com.components.menu.MenuBackgroundComponent;
import com.constants.ComponentConst;
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
    private MenuButtonActionListener menuButtonLister;
    private ButtonHoverListener buttonHoverListener;

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
        return new SnakeBody(ComponentConst.SNAKE_COMPONENT_SIZE, pattern[patternPointer]);
    }

    public SnakeHead createHead() {
        SnakeHead sh = new SnakeHead(ComponentConst.SNAKE_HEAD_20);
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
            frame.setSize(ComponentConst.FRAME_WIDTH, ComponentConst.FRAME_HEIGHT);
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
        mb.setBounds(0, 0, ComponentConst.FRAME_WIDTH, ComponentConst.FRAME_HEIGHT);
        return mb;
    }

    public DifficultyMark createDifficultyMark() {
        DifficultyMark dfm = new DifficultyMark();
        dfm.set(Difficulty.EASY);
        return dfm;
    }

    public BackgroundComponent createGameBackground(Difficulty dif) {
        BackgroundComponent bc = new BackgroundComponent(Resources.BACKGROUND_1);
        if (dif == Difficulty.MEDIUM) {
            bc = new BackgroundComponent(Resources.BACKGROUND_2);
        } else if (dif == Difficulty.HARD) {
            bc = new BackgroundComponent(Resources.BACKGROUND_3);
        }
        bc.setBounds(0, 0, ComponentConst.FRAME_WIDTH, ComponentConst.FRAME_HEIGHT);

        return bc;
    }
    public MenuButton createPlayButton() {
        MenuButton playButton = new MenuButton(Resources.BUTTON_1_PLAY);
        playButton.setBounds(275, 125, 250, 75);
        playButton.setActionCommand("play");
        playButton.addMouseListener(buttonHoverListener);
        playButton.addActionListener(menuButtonLister);
        return playButton;
    }

    public MenuButton createBackToMenuButton() {
        MenuButton button = new MenuButton(Resources.BUTTON_BACK_TO_MENU);
        button.addActionListener(new BackToMenuListener());
        button.setBounds(275, 430, 250, 75);
        button.addMouseListener(buttonHoverListener);
        return button;
    }

    public MenuButton createEasyButton() {
        MenuButton button = new MenuButton(Resources.BUTTON_EASY);
        button.setBounds(275, 336, 250, 75);
        button.setActionCommand("easy");
        button.addActionListener(menuButtonLister);
        button.addMouseListener(buttonHoverListener);
        return button;
    }

    public MenuButton createMediumButton() {
        MenuButton button = new MenuButton(Resources.BUTTON_MEDIUM);
        button.setBounds(275, 451, 250, 75);
        button.setActionCommand("medium");
        button.addActionListener(menuButtonLister);
        button.addMouseListener(buttonHoverListener);
        return button;
    }

    public MenuButton createHardButton() {
        MenuButton button = new MenuButton(Resources.BUTTON_HARD);
        button.setBounds(275, 566, 250, 75);
        button.setActionCommand("hard");
        button.addActionListener(menuButtonLister);
        button.addMouseListener(buttonHoverListener);
        return button;
    }

    public MenuButton createQuitButton() {
        MenuButton button = new MenuButton(Resources.BUTTON_QUIT);
        button.setBounds(275, 670, 250, 75);
        button.setActionCommand("quit");
        button.addActionListener(menuButtonLister);
        button.addMouseListener(buttonHoverListener);
        return button;
    }

    // TODO: MOVE ALL BOUND VALUES TO CONSTANTS
    public GamePausedComponent createGamePauseComponent() {
        GamePausedComponent gp = new GamePausedComponent();
        gp.setBounds(170, 325, ComponentConst.GAME_PAUSED_WIDTH, ComponentConst.GAME_PAUSED_HEIGHT);
        return gp;
    }

    public GameOverComponent createGameOverComponent() {
        GameOverComponent g = new GameOverComponent();
        g.setBounds(316, 339, ComponentConst.GAME_OVER_WIDTH, ComponentConst.GAME_OVER_HEIGHT);
        return g;
    }

    public SmallFood createSmallFood() {
        return new SmallFood(ComponentConst.FOOD_SIZE, Resources.APPLE_RED_1);
    }

    public JLabel createScoreComponent() {
        JLabel scoreLabel = new JLabel("0");
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/font/LuckiestGuy-Regular.ttf")).deriveFont(Font.BOLD, 36);
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

    public BestComponent createBestComponent() {
        BestComponent bc = new BestComponent();
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

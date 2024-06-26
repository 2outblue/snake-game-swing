package com.game_utility;

// Updates the wall/border collision data based on which difficulty (and therefore map) is currently selected
// Note: for the hard map, additional collision data (for the inner walls) is managed in the GameLoopThread
public class MapCollisionData {
    public static int borderMinX;
    public static int borderMaxX;
    public static int borderMinY;
    public static int borderMaxY;

    public static int foodMinX;
    public static int foodMaxX;
    public static int foodMinY;
    public static int foodMaxY;

    public static void setDifficulty(Difficulty dif) {
        if (dif == Difficulty.EASY) {
            borderMinX = 96;
            borderMaxX = 680;
            borderMinY = 126;
            borderMaxY = 660;

            foodMinX = 100;
            foodMaxX = 680;
            foodMinY = 130;
            foodMaxY = 660;
        } else if (dif == Difficulty.MEDIUM) {
            borderMinX = 230;
            borderMaxX = 550;
            borderMinY = 226;
            borderMaxY = 550;

            foodMinX = 232;
            foodMaxX = 546;
            foodMinY = 228;
            foodMaxY = 550;
        } else if (dif == Difficulty.HARD) {
            borderMinX = 110;
            borderMaxX = 670;
            borderMinY = 110;
            borderMaxY = 670;

            foodMinX = 120;
            foodMaxX = 660;
            foodMinY = 112;
            foodMaxY = 670;
        }
    }
}

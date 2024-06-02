package com.game_utility;

// updates coordinate values for where the border is and where the food should spawn
// doesn't update coords of any game objects
public class CoordinateStore {

    public static int borderMinX;
    public static int borderMaxX;
    public static int borderMinY;
    public static int borderMaxY;

    public static int foodMinX;
    public static int foodMaxX;
    public static int foodMinY;
    public static int foodMaxY;

    public static synchronized void setDifficulty(Difficulty dif) {
        if (dif == Difficulty.EASY) {
            borderMinX = 96;
            borderMaxX = 680;
            borderMinY = 78;
            borderMaxY = 660;

            foodMinX = 100;
            foodMaxX = 680;
            foodMinY = 80;
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
            // same values as for medium for now
            borderMinX = 230;
            borderMaxX = 550;
            borderMinY = 226;
            borderMaxY = 550;

            foodMinX = 232;
            foodMaxX = 546;
            foodMinY = 228;
            foodMaxY = 550;
        }
    }
}

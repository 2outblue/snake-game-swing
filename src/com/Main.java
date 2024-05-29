package com;

public class Main {
    public static void main(String[] args) {

        GameManager gameManager = GameManager.getInstance();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gameManager.createAndShowGame();
            }
        });

    }
}

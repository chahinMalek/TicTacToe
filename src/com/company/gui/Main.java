package com.company.gui;

/**
 * User: malek
 * Date: 5/3/2018
 * Time: 9:14 PM
 */

/**
 * Test class to run the <em>GameFrame</em> instance with the game
 */
public class Main {

    /**
     * Main function of this class.
     * @param args
     */
    public static void main(String[] args) {

        GameFrame mainFrame = new GameFrame("Tic-Tac-Toe",400, 200);
        mainFrame.setVisible(true);
    }
}

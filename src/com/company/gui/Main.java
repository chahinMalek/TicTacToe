package com.company.gui;

import java.awt.*;

/**
 * User: malek
 * Date: 5/3/2018
 * Time: 9:14 PM
 */

public class Main {

    public static void main(String[] args) {

        DefaultFrame mainFrame = new DefaultFrame("Tic-Tac-Toe",400, 200);

        mainFrame.add(new MenuPanel(), BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }
}

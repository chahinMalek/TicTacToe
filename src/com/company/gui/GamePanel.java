package com.company.gui;

import javax.swing.*;
import java.awt.*;

/**
 * User: malek
 * Date: 5/15/2018
 * Time: 8:50 PM
 */

public class GamePanel extends JPanel {

    public GamePanel() {
        this(3);
    }

    public GamePanel(int size) {

        setBackground(Color.decode("#181c26"));
        setLayout(new GridLayout(size, size, 5, 5));

        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                add(new MainButton(i + " " + j));
            }
        }

        setVisible(true);
    }
}

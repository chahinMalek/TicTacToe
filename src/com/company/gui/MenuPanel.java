package com.company.gui;

import javax.swing.*;
import java.awt.*;

/**
 * User: malek
 * Date: 5/15/2018
 * Time: 9:25 PM
 */

public class MenuPanel extends JPanel {

    private MainButton button;

    private void initialize() {

        button = new MainButton("New Game");
        button.addActionListener(e -> {
            System.out.println("New Game Button pressed!");
        });

        add(button);

        button = new MainButton("Settings");
        button.addActionListener(e -> {
            System.out.println("Settings Button pressed!");
        });

        add(button);

        button = new MainButton("Quit Game");
        button.addActionListener(e -> {

            if(JOptionPane.showConfirmDialog(getParent(), "Are you sure you want to exit?", "Tic-Tac-Toe", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_NO_OPTION) {
                System.exit(1);
            }
        });

        add(button);
    }

    public MenuPanel() {
        super();

        setBackground(Color.decode("#181c26"));
        setLayout(new GridLayout(3, 1, 20, 20));
        initialize();
    }

}

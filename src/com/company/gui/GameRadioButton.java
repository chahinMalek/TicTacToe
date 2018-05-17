package com.company.gui;

import javax.swing.*;
import java.awt.*;

/**
 * User: malek
 * Date: 5/16/2018
 * Time: 11:22 PM
 */

public class GameRadioButton extends JRadioButton {

    private Font font = new Font("Arial", Font.PLAIN, 30);
    private Color backgroundColor = Color.decode("#181c26");
    private Color foregroundColor = Color.decode("#ffffff");

    public GameRadioButton() {
        this("OK");
    }

    public GameRadioButton(String text) {
        super(text);

        setText(text);
        setFont(font);
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setActionCommand(text);
    }

}

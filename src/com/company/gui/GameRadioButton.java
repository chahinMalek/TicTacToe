package com.company.gui;

import javax.swing.*;
import java.awt.*;

/**
 * User: malek
 * Date: 5/16/2018
 * Time: 11:22 PM
 */

/**
 * Custom <em>JRadioButton</em> class with custom style to be used in the <em>GameFrame</em> class instances.
 */
public class GameRadioButton extends JRadioButton {

    private Font font = new Font("Arial", Font.PLAIN, 30);
    private Color backgroundColor = Color.decode("#181c26");
    private Color foregroundColor = Color.decode("#ffffff");

    /**
     * Creates an instance of this class with the radio button's text set to an empty
     * string and sets the radio button's style.
     */
    public GameRadioButton() {
        this("OK");
    }

    /**
     * Creates an instance of this class with the radio button's text set to the value of the <em>text</em>
     * parameter and sets the radio button's style.
     * @param text
     */
    public GameRadioButton(String text) {
        super(text);

        setText(text);
        setFont(font);
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setActionCommand(text);
    }

}

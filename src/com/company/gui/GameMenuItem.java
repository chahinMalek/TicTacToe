package com.company.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * User: malek
 * Date: 5/15/2018
 * Time: 10:25 AM
 */

/**
 * Custom <em>JMenuItem</em> class with custom style to be used in the <em>GameFrame</em> class instances.
 *This class implements the <em>MouseListener</em> interface so the style of the button looks more
 * user friendly when the mouse cursor hovers over the button's area.
 */
public class GameMenuItem extends JMenuItem implements MouseListener {

    private Font defaultFont = new Font("Arial", Font.PLAIN,15);
    private Color textColor = Color.decode("#000000");
    private Color hoverColor = Color.decode("#ffffff");
    private Color backgroundColor = Color.decode("#000000");

    /**
     * Creates an instance of this class with the menu item's text set to an empty string and sets the menu item's style.
     */
    public GameMenuItem() {
        this("");
    }

    /**
     * Creates an instance of this class with the menu item's text set to the value of the <em>text</em>
     * parameter and sets the menu item's style.
     * @param text
     */
    public GameMenuItem(String text) {

        setFont(defaultFont);
        setText(text);
        setBorder(new LineBorder(Color.WHITE, 3));
        setForeground(textColor);
        setBackground(backgroundColor);
        setFont(defaultFont);
        setOpaque(false);
        addMouseListener(this);
    }

    /**
     * No additional functionalities added in this class.
     * @param e represents the triggered event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * No additional functionalities added in this class.
     * @param e represents the triggered event.
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * No additional functionalities added in this class.
     * @param e represents the triggered event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Changes the background color of the calling instance when the mouse cursor hovers over the button's area.
     * @param e represents the triggered event.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == this) {
            this.setOpaque(true);
            this.setBackground(this.hoverColor);
        }
    }

    /**
     * Sets back the background color of the calling instance when the mouse cursor leaves the button's area.
     * @param e represents the triggered event.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == this) {
            this.setOpaque(false);
        }
    }

}

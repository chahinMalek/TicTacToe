package com.company.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * User: malek
 * Date: 5/15/2018
 * Time: 9:58 AM
 */

/**
 * Custom <em>JMenu</em> class with custom style to be used in the <em>GameFrame</em> class instances.
 * This class implements the <em>MouseListener</em> interface so the style of the button looks more
 * user friendly when the mouse cursor hovers over the button's area.
 */
public class GameMenu extends JMenu implements MouseListener {

    private Font defaultFont = new Font("Arial", Font.PLAIN,18);
    private Color textColor = Color.decode("#ffffff");
    private Color hoverColor = Color.decode("#00aced");
    private Color backgroundColor = Color.decode("#181c26");

    /**
     * Creates an instance of this class with the menu's title set to an empty string and sets the menu's style.
     */
    public GameMenu() {
        this("");
    }

    /**
     * Creates an instance of this class with the menu's title set to the value of the <strong><em>title</em></strong>
     * parameter and sets the menu's style.
     * @param title the value to be set for the menu's title.
     */
    public GameMenu(String title) {
        super(title);

        setFont(defaultFont);
        setForeground(textColor);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setBackground(backgroundColor);
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
            setBackground(backgroundColor);
            this.setOpaque(false);
        }
    }
}

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

public class GameMenu extends JMenu implements MouseListener {

    private Font defaultFont = new Font("Arial", Font.PLAIN,18);
    private Color textColor = Color.decode("#ffffff");
    private Color hoverColor = Color.decode("#00aced");
    private Color backgroundColor = Color.decode("#181c26");

    public GameMenu(String title) {
        super(title);

        setFont(defaultFont);
        setForeground(textColor);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setBackground(backgroundColor);
        setOpaque(false);
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == this) {
            this.setOpaque(true);
            this.setBackground(this.hoverColor);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == this) {
            setBackground(backgroundColor);
            this.setOpaque(false);
        }
    }
}

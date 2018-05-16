package com.company.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * User: malek
 * Date: 5/15/2018
 * Time: 9:20 PM
 */

public class MainButton extends JButton implements MouseListener {

    private Font defaultFont = new Font("Arial", Font.PLAIN,30);
    private Color textColor = Color.decode("#ffffff");
    private Color hoverColor = Color.decode("#00aced");
    private Color backgroundColor = Color.decode("#181c26");

    public MainButton(String text) {

        setFont(defaultFont);
        setText(text);
        setBorder(new LineBorder(Color.WHITE, 3));
        setForeground(textColor);
        setBackground(backgroundColor);
        setFont(defaultFont);
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
            this.setBackground(this.hoverColor);
            this.setOpaque(false);
        }
    }

}

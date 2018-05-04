package com.company.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: malek
 * Date: 5/3/2018
 * Time: 9:14 PM
 */

public class Main {

    private static class TestPanel extends JPanel {

        int x = this.getWidth(), y = this.getHeight();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            x = this.getWidth();
            y = this.getHeight();

            g.drawString("This is a test panel string", x/10, y/10);
        }
    }

    public static void main(String[] args) {

        TestPanel tp = new TestPanel();

        JButton clickMeButton = new JButton("Click me!");
        clickMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(tp, BorderLayout.CENTER);
        container.add(tp, BorderLayout.SOUTH);

        JFrame window = new JFrame("Tic-Tac-Toe");

        window.setContentPane(container);
        window.setSize(500, 500);
        window.setLocation(0, 0);
        window.add(tp, BorderLayout.CENTER);
        window.add(clickMeButton, BorderLayout.SOUTH);
        window.setVisible(true);


//        JOptionPane.showMessageDialog(null, "Hello world!");
    }
}

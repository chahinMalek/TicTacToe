package com.company.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * User: malek
 * Date: 5/9/2018
 * Time: 9:45 AM
 */

public class MenuFrame extends JFrame {

    private class GameButton extends JButton implements MouseListener {

        Font defaultFont = new Font("Arial", Font.PLAIN,30);
        Color textColor = Color.decode("#ffffff");
        Color hoverColor = Color.decode("#00aced");

        public GameButton(String text) {

            setFont(defaultFont);
            setText(text);
            setBorder(new LineBorder(Color.WHITE, 3));
            setForeground(textColor);
            setBackground(getContentPane().getBackground());
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
                this.setOpaque(false);
            }
        }
    }

    public class ButtonContainer extends JPanel {

        private GameButton newGameButton, settingsButton, quitButton;

        private void initialize() {

            newGameButton = new GameButton("New Game");
            newGameButton.addActionListener(e -> {
                System.out.println("New Game Button pressed!");
            });

            add(newGameButton);

            settingsButton = new GameButton("Settings");
            settingsButton.addActionListener(e -> {
                System.out.println("Settings Button pressed!");
            });

            add(settingsButton);

            quitButton = new GameButton("Quit Game");
            quitButton.addActionListener(e -> {
                System.exit(1);
            });

            add(quitButton);
        }

        public ButtonContainer() {
            super();

            setBackground(Color.decode("#181c26"));
            setLayout(new GridLayout(3, 1, 20, 20));
            initialize();
        }
    }

    private ButtonContainer buttonContainer;

    public MenuFrame() {
        this("New Frame");
    }

    public MenuFrame(String text) {
        super(text);

        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_VERT | Frame.MAXIMIZED_HORIZ);
        setResizable(false);

        getContentPane().setBackground(Color.decode("#181c26"));

        setLayout(new BorderLayout());

        // Dimension is used to scale the north and the south empty Box.Filler so that
        // GameButtons have a smaller height
        Dimension d = new Dimension(50, 200);
        add(new Box.Filler(d, d, d), BorderLayout.NORTH);
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(Color.decode("#181c26"));

        // wrapper wraps up the GridLayout buttonContainer
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new GridLayout(1, 3));
        wrapper.add(emptyPanel);

        buttonContainer = new ButtonContainer();
        wrapper.add(buttonContainer);

        emptyPanel = new JPanel();
        emptyPanel.setBackground(Color.decode("#181c26"));
        wrapper.add(emptyPanel);

        add(wrapper, BorderLayout.CENTER);
        add(new Box.Filler(d, d, d), BorderLayout.SOUTH);


        // Adding a game menu
        GameMenu gameMenu = new GameMenu("Game");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> {
            System.out.println("New Game Menu button pressed");
        });
        gameMenu.add(newGame);

        newGame = new JMenuItem("Quit Game");
        newGame.addActionListener(e -> {
            System.exit(1);
        });
        gameMenu.add(newGame);

        JMenuBar gameMenuBar = new JMenuBar();
        gameMenuBar.setBackground(Color.decode("#181c26"));
        gameMenuBar.setBorder(new EmptyBorder(0, 0, 2, 0));
        gameMenuBar.add(gameMenu);

        gameMenu = new GameMenu("Settings");

        newGame = new JMenuItem("Settings");
        newGame.addActionListener(e -> {
            System.out.println("Settings Menu button pressed");
        });
        gameMenu.add(newGame);

        gameMenuBar.add(gameMenu);
        setJMenuBar(gameMenuBar);
    }

}

package com.company.gui;

import com.company.utility.Game;
import com.company.utility.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: malek
 * Date: 5/15/2018
 * Time: 10:15 AM
 */

public class DefaultFrame extends JFrame implements ActionListener {

    private String about = "Tic-Tac-Toe\nMade By: Malek Chahin\n2018\n\nSource code: https://github.com/chahinMalek/TicTacToe\n\n";

    Player player1, player2;
    Game game;

    GameMenuItem resetMenuButton, mainMenuButton;

    CardLayout cl = new CardLayout();

    JPanel panelContainer;
    GamePanel gamePanel;
    MenuPanel menuPanel;

    private Color backgroundColor = Color.decode("#181c26");
    private Color foregroundColor = Color.decode("#ffffff");
    private Font font = new Font("Arial", Font.PLAIN, 30);
    private JMenuBar menuBar;

    public DefaultFrame() {
        this("New Frame", 200, 200);
    }

    public DefaultFrame(String title) {
        this(title, 200, 200);
    }

    public DefaultFrame(int offset) {
        this("New Frame", offset, offset);
    }

    public DefaultFrame(String title, int offset) {
        this(title, offset, offset);
    }

    public DefaultFrame(String title, int leftRight, int topBottom) {
        super(title);

        setTitle(title);
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setResizable(false);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_VERT | JFrame.MAXIMIZED_HORIZ);

        setFont(font);
        setForeground(foregroundColor);
        getContentPane().setBackground(Color.decode("#181c26"));

        setOffset(leftRight, topBottom);
        initializeMenu();

        menuPanel = new MenuPanel();
        gamePanel = new GamePanel();

        panelContainer = new JPanel();
        panelContainer.setLayout(cl);
        panelContainer.add(menuPanel, "1");
        panelContainer.add(gamePanel, "2");
        cl.show(panelContainer, "1");

        add(panelContainer, BorderLayout.CENTER);
    }

    private void setOffset(int leftRight, int topBottom) {

        Dimension d = new Dimension(leftRight, topBottom);
        add(new Box.Filler(d, d, d), BorderLayout.NORTH);
        add(new Box.Filler(d, d, d), BorderLayout.SOUTH);
        add(new Box.Filler(d, d, d), BorderLayout.WEST);
        add(new Box.Filler(d, d, d), BorderLayout.EAST);
    }

    private void initializeMenu() {

        // Adding a game menu
        GameMenu gameMenu = new GameMenu("Game");

        GameMenuItem gameMenuItem = new GameMenuItem("New Game");
        gameMenuItem.setActionCommand("New Game Menu Button");
        gameMenuItem.addActionListener(this);
        gameMenu.add(gameMenuItem);

        resetMenuButton = new GameMenuItem("Reset Game");
        resetMenuButton.setActionCommand("Reset Game Menu Button");
        resetMenuButton.addActionListener(this);
        resetMenuButton.setVisible(false);
        gameMenu.add(resetMenuButton);

        mainMenuButton = new GameMenuItem("Main Menu");
        mainMenuButton.setActionCommand("Main Menu Button");
        mainMenuButton.addActionListener(this);
        mainMenuButton.setVisible(false);
        gameMenu.add(mainMenuButton);

        gameMenuItem = new GameMenuItem("Quit Game");
        gameMenuItem.setActionCommand("Quit Game Menu Button");
        gameMenuItem.addActionListener(this);
        gameMenu.add(gameMenuItem);

        JMenuBar gameMenuBar = new JMenuBar();
        gameMenuBar.setBackground(Color.decode("#181c26"));
        gameMenuBar.setBorder(new EmptyBorder(0, 0, 2, 0));
        gameMenuBar.add(gameMenu);

        // Adding a settings menu
        gameMenu = new GameMenu("Settings");

        gameMenuItem = new GameMenuItem("Game Settings");
        gameMenuItem.addActionListener(this);
        gameMenu.add(gameMenuItem);

        gameMenuBar.add(gameMenu);

        // Adding a help menu
        gameMenu = new GameMenu("Help");

        gameMenuItem = new GameMenuItem("How to play");
        gameMenuItem.addActionListener(this);
        gameMenu.add(gameMenuItem);

        gameMenuBar.add(gameMenu);

        // Adding an about menu
        gameMenu = new GameMenu("About");

        gameMenuItem = new GameMenuItem("About Us");
        gameMenuItem.addActionListener(this);
        gameMenu.add(gameMenuItem);

        gameMenuBar.add(gameMenu);

        // Setting the gameMenuBar as the menu bar of the holding frame
        setJMenuBar(gameMenuBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String sourceActionCommand = e.getActionCommand();

        switch (sourceActionCommand) {

            case "New Game Button":
                resetMenuButton.setVisible(true);
                mainMenuButton.setVisible(true);

                return;

            case "Quit Game Button":

                if(JOptionPane.showConfirmDialog(getParent(), "Are you sure you want to exit?", "Tic-Tac-Toe", JOptionPane.YES_NO_OPTION)
                        == JOptionPane.YES_NO_OPTION) {
                    System.exit(1);
                }

                return;

            case "New Game Menu Button":
                resetMenuButton.setVisible(true);
                mainMenuButton.setVisible(true);

                setOffset(200, 100);
                cl.show(panelContainer, "2");

                return;

            case "Main Menu Button":
                System.out.println(sourceActionCommand + " pressed");
                resetMenuButton.setVisible(false);
                mainMenuButton.setVisible(false);

                setOffset(200, 100);
                cl.show(panelContainer, "1");

                return;

            case "Quit Game Menu Button":

                if(JOptionPane.showConfirmDialog(getParent(), "Are you sure you want to exit?", "Tic-Tac-Toe", JOptionPane.YES_NO_OPTION)
                        == JOptionPane.YES_NO_OPTION) {
                    System.exit(1);
                }

                return;

            case "About Us":
                JOptionPane.showMessageDialog(getParent(), about);
                return;

            default:
                System.out.println(sourceActionCommand + " pressed!");
                break;
        }
    }
}

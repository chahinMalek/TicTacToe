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

public class GameFrame extends JFrame implements ActionListener {

    private String ABOUT = "Tic-Tac-Toe\nMade By: Malek Chahin\n2018\n\nSource code: https://github.com/chahinMalek/TicTacToe\n\n";

    Player player1, player2;
    Game game;

    private GameMenuItem resetMenuButton, mainMenuButton;

    private CardLayout cl = new CardLayout();
    private JPanel panelContainer;

    private Font font = new Font("Arial", Font.PLAIN, 30);
    private Color backgroundColor = Color.decode("#181c26");
    private Color foregroundColor = Color.decode("#ffffff");
    private JMenuBar menuBar;

    /************************************************ INNER CLASSES **************************************************/

    /**
     *
     */
    private class MenuPanel extends JPanel {

        private MainButton button;

        private void initialize() {

            button = new MainButton("New Game");
            button.setActionCommand("New Game Button");
            button.addActionListener(GameFrame.this);

            add(button);

            button = new MainButton("Settings");
            button.setActionCommand("Settings Button");
            button.addActionListener(GameFrame.this);

            add(button);

            button = new MainButton("Quit Game");
            button.setActionCommand("Quit Game Button");
            button.addActionListener(GameFrame.this);

            add(button);
        }

        MenuPanel() {
            super();

            setBackground(backgroundColor);
            setLayout(new GridLayout(3, 1, 20, 20));
            initialize();
        }

    }

    /**
     *
     */
    private class GamePanel extends JPanel {

        private MainButton gameButton;

        GamePanel() {
            this(3);
        }

        GamePanel(int size) {

            setBackground(backgroundColor);
            setLayout(new GridLayout(size, size, 5, 5));

            for(int i=0; i<size; i++) {
                for(int j=0; j<size; j++) {
                    add(gameButton = new MainButton(i + " " + j));
                    gameButton.addActionListener(GameFrame.this);
                }
            }

        }
    }

    private class SettingsPanel extends JPanel implements ActionListener {

        private GameRadioButton gameRadioButton;
        private JPanel aiLevelPanel;

        SettingsPanel() {

            setFont(font);
            setLayout(new GridLayout(1, 3));
            setBackground(backgroundColor);

            initializeBoardSizeMenu();
            initializeDifficultyMenu();
            initializeAILevelMenu();
        }

        private void initializeBoardSizeMenu() {

            JPanel boardSizePanel = new JPanel();
            boardSizePanel.setLayout(new BoxLayout(boardSizePanel, BoxLayout.Y_AXIS));

            boardSizePanel.setBackground(backgroundColor);

            boardSizePanel.setBackground(backgroundColor);
            JLabel label = new JLabel("Choose board size: ");
            label.setFont(font);
            label.setBackground(backgroundColor);
            label.setForeground(foregroundColor);

            boardSizePanel.add(label);

            Dimension d = new Dimension(20, 100);
            boardSizePanel.add(new Box.Filler(d, d, d));

            ButtonGroup boardSizePicked = new ButtonGroup();

            gameRadioButton = new GameRadioButton("3x3");
            gameRadioButton.setSelected(true);

            gameRadioButton.addActionListener(GameFrame.this);

            boardSizePicked.add(gameRadioButton);
            boardSizePanel.add(gameRadioButton);

            gameRadioButton = new GameRadioButton("4x4");

            gameRadioButton.addActionListener(GameFrame.this);

            boardSizePicked.add(gameRadioButton);
            boardSizePanel.add(gameRadioButton);

            add(boardSizePanel);

        }

        private void initializeDifficultyMenu() {

            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

            container.setBackground(backgroundColor);

            container.setBackground(backgroundColor);
            JLabel label = new JLabel("Choose opponent: ");
            label.setFont(font);
            label.setBackground(backgroundColor);
            label.setForeground(foregroundColor);

            Dimension d = new Dimension(50, 150);
            container.add(new Box.Filler(d, d, d));

            container.add(label);

            d = new Dimension(20, 100);
            container.add(new Box.Filler(d, d, d));

            ButtonGroup buttonGroup = new ButtonGroup();

            gameRadioButton = new GameRadioButton("Player");
            gameRadioButton.addActionListener(this);
            gameRadioButton.setSelected(true);
            buttonGroup.add(gameRadioButton);
            container.add(gameRadioButton);

            gameRadioButton = new GameRadioButton("Computer");
            gameRadioButton.addActionListener(this);
            buttonGroup.add(gameRadioButton);
            container.add(gameRadioButton);

            add(container);
        }

        private void initializeAILevelMenu() {

            aiLevelPanel = new JPanel();
            aiLevelPanel.setLayout(new BoxLayout(aiLevelPanel, BoxLayout.Y_AXIS));

            aiLevelPanel.setBackground(backgroundColor);

            aiLevelPanel.setBackground(backgroundColor);
            JLabel label = new JLabel("Choose AI level: ");
            label.setFont(font);
            label.setBackground(backgroundColor);
            label.setForeground(foregroundColor);

            aiLevelPanel.add(label);

            Dimension d = new Dimension(50, 100);
            aiLevelPanel.add(new Box.Filler(d, d, d));

            ButtonGroup buttonGroup = new ButtonGroup();

            gameRadioButton = new GameRadioButton("Easy");
            gameRadioButton.addActionListener(GameFrame.this);
            gameRadioButton.setSelected(true);
            buttonGroup.add(gameRadioButton);
            aiLevelPanel.add(gameRadioButton);

            gameRadioButton = new GameRadioButton("Medium");
            gameRadioButton.addActionListener(GameFrame.this);
            buttonGroup.add(gameRadioButton);
            aiLevelPanel.add(gameRadioButton);

            gameRadioButton = new GameRadioButton("Hard");
            gameRadioButton.addActionListener(GameFrame.this);
            buttonGroup.add(gameRadioButton);
            aiLevelPanel.add(gameRadioButton);

            aiLevelPanel.setVisible(false);
            add(aiLevelPanel);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String actionCommand = e.getActionCommand();

            System.out.println("Settings Menu Panel");

            switch (actionCommand) {
                case "Player":
                    aiLevelPanel.setVisible(false);
                    return;

                case "Computer":
                    aiLevelPanel.setVisible(true);
            }
        }
    }

    /*****************************************************************************************************************/

    public GameFrame() {
        this("New Frame", 200, 200);
    }

    public GameFrame(String title) {
        this(title, 200, 200);
    }

    public GameFrame(int offset) {
        this("New Frame", offset, offset);
    }

    public GameFrame(String title, int offset) {
        this(title, offset, offset);
    }

    public GameFrame(String title, int leftRight, int topBottom) {
        super(title);

        setTitle(title);
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_VERT | JFrame.MAXIMIZED_HORIZ);
        setUndecorated(true);

        setFont(font);
        setForeground(foregroundColor);
        getContentPane().setBackground(backgroundColor);

        setOffset(leftRight, topBottom);
        initializeMenu();

        panelContainer = new JPanel();
        panelContainer.setLayout(cl);
        panelContainer.add(new MenuPanel(), "1");
        panelContainer.add(new GamePanel(), "2");
        panelContainer.add(new SettingsPanel(), "3");
        cl.show(panelContainer, "1");

        add(panelContainer);
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
        gameMenuItem.setActionCommand("Settings Menu Button");
        gameMenuItem.addActionListener(this);
        gameMenu.add(gameMenuItem);

        gameMenuBar.add(gameMenu);

        // Adding a help menu
        gameMenu = new GameMenu("Help");

        gameMenuItem = new GameMenuItem("How to play");
        gameMenuItem.addActionListener(this);
        gameMenu.add(gameMenuItem);

        gameMenuBar.add(gameMenu);

        // Adding an ABOUT menu
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

            case "New Game Menu Button":
            case "New Game Button":

                resetMenuButton.setVisible(true);
                mainMenuButton.setVisible(true);

                setOffset(200, 100);
                cl.show(panelContainer, "2");

                return;

            case "Main Menu Button":
                resetMenuButton.setVisible(false);
                mainMenuButton.setVisible(false);

                setOffset(400, 200);
                cl.show(panelContainer, "1");

                return;

            case "Quit Game Menu Button":
            case "Quit Game Button":

                if(JOptionPane.showConfirmDialog(getParent(), "Are you sure you want to exit?", "Tic-Tac-Toe", JOptionPane.YES_NO_OPTION)
                        == JOptionPane.YES_NO_OPTION) {
                    System.exit(1);
                }

                return;

            case "Settings Button":
            case "Settings Menu Button":

                mainMenuButton.setVisible(true);
                setOffset(200, 100);
                cl.show(panelContainer, "3");
                return;

            case "About Us":
                JOptionPane.showMessageDialog(getParent(), ABOUT);
                return;

            default:
                System.out.println(sourceActionCommand + " pressed!");
                break;
        }
    }
}

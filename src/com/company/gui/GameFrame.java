package com.company.gui;

import com.company.utility.*;

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
    private String HELP = "How To Play\n\n" +
            "Game Options\n" +
            "New Game: Starts a new game with the current game settings\n" +
            "Reset Game: The Reset Game button in the game menu resets the whole game, not just the board's state\n" +
            "Main Menu: Takes you back to the main screen\n" +
            "Quit Game: Quits the whole game process\n\n" +
            "Game Settings\n" +
            "Board size: You decide whether you play on a 3x3 or 4x4 game board\n" +
            "Opponent: You can play vs a friend or the computer\n" +
            "Best of?: Choose the number of games you want to play with your opponent\n" +
            "AI level: Can you beat the HARD AI? I think not!\n";

    private int boardSize = 3, numberOfRounds = 1, sign = 1;
    private Player player1, player2, currentPlayer;
    private Game game;

    private GameMenuItem resetMenuButton, mainMenuButton;

    private CardLayout cl = new CardLayout();
    private JPanel panelContainer;

    private Font font = new Font("Arial", Font.PLAIN, 30);
    private Color backgroundColor = Color.decode("#181c26");
    private Color foregroundColor = Color.decode("#ffffff");

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
    private class GamePanel extends JPanel implements ActionListener {

        private BoardButton gameButton;
        private BoardButton[][] gameButtonMatrix;
        private JLabel player1Score, player2Score;

        GamePanel() {
            this(3);
        }

        GamePanel(int size) {

            setLayout(new BorderLayout());
            setBackground(backgroundColor);

            JPanel board = new JPanel();

            board.setBackground(backgroundColor);
            board.setLayout(new GridLayout(size, size, 5, 5));
            gameButtonMatrix = new BoardButton[size][size];

            for(int i=0; i<size; i++) {
                for(int j=0; j<size; j++) {

                    gameButton = new BoardButton(i+1, j+1);
                    gameButton.setActionCommand((i+1) + " " + (j+1));
                    gameButton.setText("");

                    gameButton.addActionListener(this);
                    gameButtonMatrix[i][j] = gameButton;

                    board.add(gameButton);
                }
            }

            add(board, BorderLayout.CENTER);

            JPanel buttonContainer = new JPanel();
            buttonContainer.setLayout(new FlowLayout());
            buttonContainer.setBackground(backgroundColor);

            player1Score = new JLabel();
            player1Score.setFont(font);
            player1Score.setForeground(foregroundColor);
            player1Score.setText("Player 1: 0");

            Dimension d = new Dimension(100, 20);

            buttonContainer.add(player1Score);
            buttonContainer.add(new Box.Filler(d, d, d));

            player2Score = new JLabel();
            player2Score.setFont(font);
            player2Score.setForeground(foregroundColor);
            player2Score.setText("Player 2: 0");
            buttonContainer.add(player2Score);

            add(buttonContainer, BorderLayout.SOUTH);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            BoardButton button = (BoardButton) e.getSource();

            if(!button.getText().equals("")) {
                return;
            }
            userPlayMove(button);

            if(currentPlayer instanceof AI) {
                aiPlayMove();

                if(currentPlayer instanceof AI) {
                    aiPlayMove();
                }
            }
        }

        private void resetPanel() {

            int size = gameButtonMatrix.length;

            for(int i=0; i<size; i++) {
                for(int j=0; j<size; j++) {

                    gameButtonMatrix[i][j].setText("");
                }
            }
        }

        private BoardButton getButton(int x, int y) {
            return gameButtonMatrix[x][y];
        }

        private void userPlayMove(BoardButton button) {

            sign = currentPlayer.getSign();
            button.setText((sign == 1) ? "X" : "O");

            int moveFlag = currentPlayer.move(game, button.getRow(), button.getColumn());

            switchPlayerAndCheckWin(moveFlag);
        }

        private void aiPlayMove() {

            sign = currentPlayer.getSign();

            int moveFlag = currentPlayer.move(game);

            int x = currentPlayer.getLastMove().getRow(), y = currentPlayer.getLastMove().getColumn();
            getButton(x-1, y-1).setText(currentPlayer.getSign() == 1 ? "X" : "O");

            switchPlayerAndCheckWin(moveFlag);
        }

        private void switchPlayerAndCheckWin(int moveFlag) {

            currentPlayer = (currentPlayer == player1) ? player2 : player1;

            player1Score.setText("Player 1: " + player1.getNumberOfWins());
            player2Score.setText("Player 2: " + player2.getNumberOfWins());

            if(moveFlag == 2 || !game.anyMovesLeft()) {

                if(moveFlag == 2) {
                    JOptionPane.showMessageDialog(getParent(), ((currentPlayer.getSign() == 1) ? "O won" : "X won")
                            + " the round");

                } else {
                    JOptionPane.showMessageDialog(getParent(), "Draw game");
                }

                if(player1.getNumberOfWins() == numberOfRounds/2 + 1 || player2.getNumberOfWins() == numberOfRounds/2 + 1) {

                    String message = ((player1.getNumberOfWins() > player2.getNumberOfWins()) ? "Player 1 " : "Player 2 ") + "won!";

                    if(JOptionPane.showConfirmDialog(getParent(), message + "\n\nNew Game?" , "Tic-Tac-Toe",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {

                        // New Game
                        GameFrame.this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                                "Reset Game Button"));

                    } else {

                        // Return to main screen
                        GameFrame.this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                                "Main Menu Button"));
                    }
                    return;
                }

                resetPanel();
                game.reloadBoard();

                int temp = player2.getSign();
                player2.setSign(player1.getSign());
                player1.setSign(temp);

                currentPlayer = (player1.getSign() == 1) ? player1 : player2;
            }
        }
    }

    /**
     * Settings panel
     */
    private class SettingsPanel extends JPanel implements ActionListener {

        private GameRadioButton gameRadioButton, easyButton;
        private JPanel aiLevelPanel;

        SettingsPanel() {

            setFont(font);
            setLayout(new GridLayout(2, 2));
            setBackground(backgroundColor);

            initializeBoardSizeMenu();
            initializeOpponentMenu();
            initializeNumberOfRoundsMenu();
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

            Dimension d = new Dimension(50, 60);
            boardSizePanel.add(new Box.Filler(d, d, d));

            ButtonGroup boardSizePicked = new ButtonGroup();

            gameRadioButton = new GameRadioButton("3x3");
            gameRadioButton.setSelected(true);

            gameRadioButton.addActionListener(this);

            boardSizePicked.add(gameRadioButton);
            boardSizePanel.add(gameRadioButton);

            gameRadioButton = new GameRadioButton("4x4");

            gameRadioButton.addActionListener(this);

            boardSizePicked.add(gameRadioButton);
            boardSizePanel.add(gameRadioButton);

            add(boardSizePanel);

        }

        private void initializeNumberOfRoundsMenu() {

            JPanel boardSizePanel = new JPanel();
            boardSizePanel.setLayout(new BoxLayout(boardSizePanel, BoxLayout.Y_AXIS));

            boardSizePanel.setBackground(backgroundColor);

            boardSizePanel.setBackground(backgroundColor);
            JLabel label = new JLabel("Best of? ");
            label.setFont(font);
            label.setBackground(backgroundColor);
            label.setForeground(foregroundColor);

            Dimension d = new Dimension(50, 60);
            boardSizePanel.add(new Box.Filler(d, d, d));

            boardSizePanel.add(label);

            boardSizePanel.add(new Box.Filler(d, d, d));

            ButtonGroup boardSizePicked = new ButtonGroup();

            for(int i=1; i<=7; i+=2) {

                gameRadioButton = new GameRadioButton(Integer.toString(i));

                if(i == 1) {
                    gameRadioButton.setSelected(true);
                }

                gameRadioButton.addActionListener(this);

                boardSizePicked.add(gameRadioButton);
                boardSizePanel.add(gameRadioButton);
            }

            add(boardSizePanel);
        }

        private void initializeOpponentMenu() {

            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

            container.setBackground(backgroundColor);

            container.setBackground(backgroundColor);
            JLabel label = new JLabel("Choose opponent: ");
            label.setFont(font);
            label.setBackground(backgroundColor);
            label.setForeground(foregroundColor);

            container.add(label);

            Dimension d = new Dimension(50, 60);
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

            Dimension d = new Dimension(50, 60);
            aiLevelPanel.add(new Box.Filler(d, d, d));

            aiLevelPanel.add(label);

            aiLevelPanel.add(new Box.Filler(d, d, d));

            ButtonGroup buttonGroup = new ButtonGroup();

            easyButton = gameRadioButton = new GameRadioButton("Easy");
            gameRadioButton.addActionListener(this);
            gameRadioButton.setSelected(true);
            buttonGroup.add(gameRadioButton);
            aiLevelPanel.add(gameRadioButton);

            gameRadioButton = new GameRadioButton("Medium");
            gameRadioButton.addActionListener(this);
            buttonGroup.add(gameRadioButton);
            aiLevelPanel.add(gameRadioButton);

            gameRadioButton = new GameRadioButton("Hard");
            gameRadioButton.addActionListener(this);
            buttonGroup.add(gameRadioButton);
            aiLevelPanel.add(gameRadioButton);

            aiLevelPanel.setVisible(false);
            add(aiLevelPanel);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String actionCommand = e.getActionCommand();

            switch (actionCommand) {

                case "3x3":
                    boardSize = 3;
                    return;

                case "4x4":
                    boardSize = 4;
                    return;

                case "1":
                case "3":
                case "5":
                case "7":
                    numberOfRounds = Integer.parseInt(actionCommand);
                    return;

                case "Player":
                    aiLevelPanel.setVisible(false);
                    easyButton.setSelected(true);
                    player2 = new User();
                    return;

                case "Computer":
                    aiLevelPanel.setVisible(true);
                    player2 = new EasyAI();

                    return;

                case "Easy":
                    player2 = new EasyAI();
                    return;

                case "Medium":
                    player2 = new MediumAI();
                    return;

                case "Hard":
                    player2 = new HardAI();
                    return;
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

        player1 = new User();

        player2 = new User();
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

                setOffset(200, 50);

                this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Reset Game Button"));
                return;

            case "Reset Game Button":
            case "Reset Game Menu Button":

                panelContainer.add(new GamePanel(boardSize), "2");
                cl.show(panelContainer, "2");

                player1.setNumberOfWins(0);
                player1.setSign(1);
                player2.setNumberOfWins(0);
                player2.setSign(-1);

                game = new Game(player1, player2, boardSize, numberOfRounds);

                currentPlayer = player1;
                return;

            case "Main Menu Button":
                resetMenuButton.setVisible(false);
                mainMenuButton.setVisible(false);

                setOffset(400, 200);
                cl.show(panelContainer, "1");

                return;

            case "Quit Game Menu Button":
            case "Quit Game Button":

                if(JOptionPane.showConfirmDialog(getParent(), "Are you sure you want to exit?", "Tic-Tac-Toe",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                    System.exit(1);
                }

                return;

            case "Settings Button":
            case "Settings Menu Button":
                mainMenuButton.setVisible(true);
                resetMenuButton.setVisible(false);
                setOffset(200, 100);
                cl.show(panelContainer, "3");
                return;

            case "About Us":
                JOptionPane.showMessageDialog(getParent(), ABOUT);
                return;

            case "How to play":
                JOptionPane.showMessageDialog(getParent(), HELP);
                return;

            default:
                System.out.println(sourceActionCommand + " pressed!");
                break;
        }
    }
}

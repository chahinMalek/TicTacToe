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

/**
 * Custom <em>JFrame</em> class that holds the whole game user interface and functionalities.
 * This class implements the <em>ActionListener</em> interface so all the ActionEvents fired inside this class
 * will be handled inside of this class (continue reading).
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

    /**
     * Custom <em>JPanel</em> class that holds the user interface of the game's main screen.
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

        /**
         * Creates an instance of this class with custom style and three buttons.
         * <strong><em>New Game Button</em></strong> creates a new game with two players set by default to <em>User</em>
         * class instances.
         * <strong><em>Settings Button</em></strong> creates a settings instance which will be explained later.
         * <strong><em>Quit Game Button</em></strong> creates a dialog for the user to choose whether he wants to stop
         * the created game process or not.
         */
        public MenuPanel() {
            super();

            setBackground(backgroundColor);
            setLayout(new GridLayout(3, 1, 20, 20));
            initialize();
        }

    }

    /**
     * Custom <em>JPanel</em> class that is used in this class to create a user interface for the game. This class
     * implements the <em>ActionListener</em> interface so every <em>ActionEvent</em> fired inside of this class will
     * be handled inside of this class.
     */
    private class GamePanel extends JPanel implements ActionListener {

        private BoardButton gameButton;
        private BoardButton[][] gameButtonMatrix;
        private JLabel player1Score, player2Score;

        /**
         * Creates a new instance of this class with the game board size set to 3x3 (by default).
         */
        GamePanel() {
            this(3);
        }

        /**
         * Creates a new instance of this class with the game board size set to the value of the <em>size</em> parameter.
         * Instances of this class will have a custom style.
         * @param size the value to be set for the size of the game board.
         */
        GamePanel(int size) {

            setLayout(new BorderLayout());
            setBackground(backgroundColor);

            // BoardButton container
            JPanel board = new JPanel();

            board.setBackground(backgroundColor);
            board.setLayout(new GridLayout(size, size, 5, 5));

            // Needed to store references to buttons
            gameButtonMatrix = new BoardButton[size][size];

            // Panel button initialization
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

            // Creating a container panel for the labels that will represent the current number of wins for each player
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

        /**
         * Event handler for this class. Every event of type <em>ActionEvent</em> fired inside of this class will
         * be handled in this method.
         * @param e an <em>ActionEvent</em> instance.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            BoardButton button = (BoardButton) e.getSource();

            // If the button is already clicked then it's value must not be changed again (until the next round)
            if(!button.getText().equals("")) {
                return;
            }

            // Handles user's move
            userPlayMove(button);

            // Handles the computer's move
            if(currentPlayer instanceof AI) {
                aiPlayMove();

                // If the computer plays last in a game and has to play first in the next game, that case is handled
                // this way.
                if(currentPlayer instanceof AI) {
                    aiPlayMove();
                }
            }
        }

        /**
         * Sets the text values for every instance of the <em>BoardButton</em> class available in this game to
         * an empty string.
         */
        private void resetPanel() {

            int size = gameButtonMatrix.length;

            for(int i=0; i<size; i++) {
                for(int j=0; j<size; j++) {

                    gameButtonMatrix[i][j].setText("");
                }
            }
        }

        /**
         * Mutator method to get a reference to the <em>BoardButton</em> object with the row and column indexes set
         * by the <em>row</em> and <em>column</em> parameters respectively.
         * @param row the row value to be set for the calling object's <em>row</em> value.
         * @param column the column value to be set for the calling object's <em>column</em> value.
         * @return a reference to a <em>BoardButton</em> object.
         */
        private BoardButton getButton(int row, int column) {
            return gameButtonMatrix[row][column];
        }

        /**
         * Handles the user's move. Also switches the move to the next player and checks if the game is finished after
         * this move (resulting a win or a draw).
         * @param button represents a <em>BoardButton</em> reference to the button which the user pressed.
         */
        private void userPlayMove(BoardButton button) {

            sign = currentPlayer.getSign();
            button.setText((sign == 1) ? "X" : "O");

            int moveFlag = currentPlayer.move(game, button.getRow(), button.getColumn());

            // Switches the player to the next player and checks if the game is finished after the move is played
            // (which is done in the lines above)
            switchPlayerAndCheckWin(moveFlag);
        }

        /**
         * Handles the user's move. Also switches the move to the next player and checks if the game is finished after
         * this move (resulting a win or a draw).
         */
        private void aiPlayMove() {

            sign = currentPlayer.getSign();

            int moveFlag = currentPlayer.move(game);

            int x = currentPlayer.getLastMove().getRow(), y = currentPlayer.getLastMove().getColumn();
            getButton(x-1, y-1).setText(currentPlayer.getSign() == 1 ? "X" : "O");

            // Switches the player to the next player and checks if the game is finished after the move is played
            // (which is done in the lines above)
            switchPlayerAndCheckWin(moveFlag);
        }

        /**
         * Switches the turn of the players. Sets the text values for the game score labels as the values the players
         * have for their number of wins.
         *
         * Prompts a dialog in which the user can choose whether he wants a new game or not
         * (only prompt at the end of a game). If a user chooses to play a new game then an <em>ActionEvent</em> is fired
         * and handled inside this class, resetting the game with the same players.
         * Vice versa, an <em>ActionEvent</em> is again fired that will also be handled
         * inside this class, taking the user back to the main screen.
         *
         * If a game is finished with a draw then no changes will be made for the user's number of wins attribute.
         *
         * If an end of a game nor a round is reached then only a change of player turns is made.
         *
         * @param moveFlag
         */
        private void switchPlayerAndCheckWin(int moveFlag) {

            currentPlayer = (currentPlayer == player1) ? player2 : player1;

            player1Score.setText("Player 1: " + player1.getNumberOfWins());
            player2Score.setText("Player 2: " + player2.getNumberOfWins());

            if(moveFlag == 2 || !game.anyMovesLeft()) {

                // If the last move was a winning one (for the round)
                if(moveFlag == 2) {

                    // Prompts a dialog to inform the user that an end of the current round is reached
                    JOptionPane.showMessageDialog(getParent(), ((currentPlayer.getSign() == 1) ? "O won" : "X won")
                            + " the round");

                // If the last move resulted a draw round (no moves left on the game board)
                } else if(!game.anyMovesLeft()) {
                    JOptionPane.showMessageDialog(getParent(), "Draw game");
                }

                // If any of the players reached the winning amount of game rounds
                if(player1.getNumberOfWins() == numberOfRounds/2 + 1 || player2.getNumberOfWins() == numberOfRounds/2 + 1) {

                    // Generate a win message
                    String message = ((player1.getNumberOfWins() > player2.getNumberOfWins()) ? "Player 1 " : "Player 2 ") + "won!";

                    // Prompt a dialog for the user to choose whether he wants to play a new game or not
                    if(JOptionPane.showConfirmDialog(getParent(), message + "\n\nNew Game?" , "Tic-Tac-Toe",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {

                        // New Game event fired
                        GameFrame.this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                                "Reset Game Button"));

                    } else {

                        // Return to main screen event fired
                        GameFrame.this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                                "Main Menu Button"));
                    }
                    return;
                }

                // WILL BE EXECUTED ONLY IF THE END OF THE GAME IS NOT REACHED YET
                // Reset the game's state
                resetPanel();
                game.reloadBoard();

                // Switch player turns (will be executed only if the end of the game is not reached)
                int temp = player2.getSign();
                player2.setSign(player1.getSign());
                player1.setSign(temp);

                currentPlayer = (player1.getSign() == 1) ? player1 : player2;
            }
        }
    }

    /**
     * Custom <em>JPanel</em> class that is used in this class to create a user interface for the game settings.
     * This class implements the <em>ActionListener</em> interface so every <em>ActionEvent</em>
     * fired inside of this class will be handled inside of this class.
     */
    private class SettingsPanel extends JPanel implements ActionListener {

        private GameRadioButton gameRadioButton, easyButton;
        private JPanel aiLevelPanel;

        /**
         * Creates an instance of this class with custom style and four options.
         * <strong><em>Choosing the size of the board</em></strong>, a panel is created with a radio button group
         * to choose between a 3x3 board size or 4x4 board size.
         * <strong><em>Choosing the opponent</em></strong>, a panel is created with a radio button group to choose
         * between a human opponent or the AI opponent.
         * <strong><em>Choosing the number of rounds</em></strong>, a panel is created with a radio button group to
         * choose a number of rounds for the game to last.
         * <strong><em>Choosing the difficulty level of the AI</em></strong>, a panel is created with a radio button
         * group to choose a difficulty level for the AI opponent to play against. This panel will appear only if the
         * AI is chosen as the opposing player.
         */
        SettingsPanel() {

            setFont(font);
            setLayout(new GridLayout(2, 2));
            setBackground(backgroundColor);

            initializeBoardSizeMenu();
            initializeOpponentMenu();
            initializeNumberOfRoundsMenu();
            initializeAILevelMenu();
        }

        /**
         * Initializes a panel to choose the board size.
         */
        private void initializeBoardSizeMenu() {

            JPanel boardSizePanel = new JPanel();
            boardSizePanel.setLayout(new BoxLayout(boardSizePanel, BoxLayout.Y_AXIS));

            boardSizePanel.setBackground(backgroundColor);

            // Adding a label for the user to know what he is choosing the value for.
            JLabel label = new JLabel("Choose board size: ");
            label.setFont(font);
            label.setBackground(backgroundColor);
            label.setForeground(foregroundColor);
            boardSizePanel.add(label);

            // Creating a gap between the label and the radio group.
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

        /**
         * Initializes a panel to choose the number of rounds for the game.
         */
        private void initializeNumberOfRoundsMenu() {

            JPanel boardSizePanel = new JPanel();
            boardSizePanel.setLayout(new BoxLayout(boardSizePanel, BoxLayout.Y_AXIS));

            boardSizePanel.setBackground(backgroundColor);

            // Adding a label for the user to know what he is choosing the value for.
            JLabel label = new JLabel("Best of? ");
            label.setFont(font);
            label.setBackground(backgroundColor);
            label.setForeground(foregroundColor);

            Dimension d = new Dimension(50, 60);
            boardSizePanel.add(new Box.Filler(d, d, d));

            boardSizePanel.add(label);
            boardSizePanel.add(new Box.Filler(d, d, d));

            ButtonGroup boardSizePicked = new ButtonGroup();

            // Adding options to choose between the values 1, 3, 5 and 7 for the number of rounds for the game
            for(int i=1; i<=7; i+=2) {

                gameRadioButton = new GameRadioButton(Integer.toString(i));

                if(i == 1) gameRadioButton.setSelected(true);

                gameRadioButton.addActionListener(this);
                boardSizePicked.add(gameRadioButton);
                boardSizePanel.add(gameRadioButton);
            }

            add(boardSizePanel);
        }

        /**
         * Initializes a panel to choose the opponent.
         */
        private void initializeOpponentMenu() {

            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

            container.setBackground(backgroundColor);

            // Adding a label for the user to know what he is choosing the value for.
            JLabel label = new JLabel("Choose opponent: ");
            label.setFont(font);
            label.setBackground(backgroundColor);
            label.setForeground(foregroundColor);

            container.add(label);

            Dimension d = new Dimension(50, 60);
            container.add(new Box.Filler(d, d, d));

            ButtonGroup buttonGroup = new ButtonGroup();

            // Adding buttons to choose between a human opponent player or the AI opponent.
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

        /**
         * Initializes a panel to choose the level of the AI opponent.
         */
        private void initializeAILevelMenu() {

            aiLevelPanel = new JPanel();
            aiLevelPanel.setLayout(new BoxLayout(aiLevelPanel, BoxLayout.Y_AXIS));

            aiLevelPanel.setBackground(backgroundColor);

            // Adding a label for the user to know what he is choosing the value for.
            JLabel label = new JLabel("Choose AI level: ");
            label.setFont(font);
            label.setBackground(backgroundColor);
            label.setForeground(foregroundColor);

            Dimension d = new Dimension(50, 60);
            aiLevelPanel.add(new Box.Filler(d, d, d));

            aiLevelPanel.add(label);

            aiLevelPanel.add(new Box.Filler(d, d, d));

            ButtonGroup buttonGroup = new ButtonGroup();

            // Adding buttons to choose between an easy, medium or hard level of the AI opponent.
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

            // Disabling the visibility of this panel until the user selects the computer as the
            // opposing player.
            aiLevelPanel.setVisible(false);
            add(aiLevelPanel);
        }

        /**
         * Event handler for this class. Every event of type <em>ActionEvent</em> fired inside of this class will
         * be handled in this method.
         * @param e an <em>ActionEvent</em> instance.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            String actionCommand = e.getActionCommand();

            switch (actionCommand) {

                // Setting the board size value to 3x3.
                case "3x3":
                    boardSize = 3;
                    break;

                // Setting the board size value to 4x4.
                case "4x4":
                    boardSize = 4;
                    break;

                // Setting the number of rounds value to on of the cases added below.
                case "1":
                case "3":
                case "5":
                case "7":
                    numberOfRounds = Integer.parseInt(actionCommand);
                    break;

                // Setting the opposing player of human type.
                case "Player":
                    aiLevelPanel.setVisible(false);
                    easyButton.setSelected(true);
                    player2 = new User();
                    break;

                // Setting the opposing player of computer type.
                case "Computer":
                    aiLevelPanel.setVisible(true);
                    player2 = new EasyAI();
                    break;

                // Setting the AI level to easy.
                case "Easy":
                    player2 = new EasyAI();
                    break;

                // Setting the AI level to medium.
                case "Medium":
                    player2 = new MediumAI();
                    break;

                // Setting the AI level to hard.
                case "Hard":
                    player2 = new HardAI();
                    break;

                default:
                    System.out.println(actionCommand);
                    break;
            }
        }
    }

    /**
     * Creates a new instance of this class with the frame title set to a default value and a default
     * gap from the screen edges.
     */
    public GameFrame() {
        this("New Frame", 200, 200);
    }

    /**
     * Creates a new instance of this class with the frame title set to the value of <em>title</em> parameter
     * and a default gap from the screen edges.
     * @param title the value to be set as the frame title.
     */
    public GameFrame(String title) {
        this(title, 200, 200);
    }

    /**
     * Creates a new instance of this class with the frame title set to a default value and a gap from
     * the screen edges sized with the value of the <em>offset</em> parameter.
     * @param offset the value for the gap size.
     */
    public GameFrame(int offset) {
        this("New Frame", offset, offset);
    }

    /**
     * Creates a new instance of this class with the frame title set to the value of <em>title</em> parameter
     * and a gap from the screen edges sized with the value of the <em>offset</em> parameter.
     * @param title the value to be set as the frame title.
     * @param offset the value for the gap size.
     */
    public GameFrame(String title, int offset) {
        this(title, offset, offset);
    }

    /**
     * Creates a new instance of this class with the frame title set to the value of <em>title</em> parameter
     * and a gap from the screen edges sized with the values of the <em>leftRight</em> and <em>topBottom</em> parameters.
     * @param title the value to be set as the frame title.
     * @param leftRight the value for the gap size from the left/right edge of the screen.
     * @param topBottom the value for the gap size from the top/bottom edge of the screen.
     */
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

        // Setting the custom offset that the user set the values for and initializing the game menu.
        setOffset(leftRight, topBottom);
        initializeMenu();

        panelContainer = new JPanel();

        // Setting the layout as a card layout because the center part of this frame is changeable.
        panelContainer.setLayout(cl);

        // Adding panels to be set in the center of this frame in the panelContainer and adding their constraint id
        panelContainer.add(new MenuPanel(), "1");
        panelContainer.add(new GamePanel(), "2");
        panelContainer.add(new SettingsPanel(), "3");
        cl.show(panelContainer, "1");

        add(panelContainer);

        // Default player declarations.
        player1 = new User();
        player2 = new User();
    }

    /**
     * Event handler for this class. Every event of type <em>ActionEvent</em> fired inside of this class will
     * be handled in this method.
     * @param e an <em>ActionEvent</em> instance.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        String sourceActionCommand = e.getActionCommand();

        switch (sourceActionCommand) {

            // Creates a new Game instance and setting the main menu and reset game menu buttons to visible so
            // the user can leave or restart the current game at any time.
            case "New Game Menu Button":
            case "New Game Button":
                resetMenuButton.setVisible(true);
                mainMenuButton.setVisible(true);
                setOffset(200, 50);

                // Firing an event because for the reset game because the rest of this case is identical to
                // handling the reset game event handling.
                this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Reset Game Button"));
                break;

            // Handling the event fired when the user clicks the reset game buttons
            case "Reset Game Button":
            case "Reset Game Menu Button":

                // Makes the GamePanel added in the constructor to the panelContainer and setting it to be visible to
                // the user.
                panelContainer.add(new GamePanel(boardSize), "2");
                cl.show(panelContainer, "2");

                // Setting default values for the player attributes,
                player1.setNumberOfWins(0);
                player1.setSign(1);
                player2.setNumberOfWins(0);
                player2.setSign(-1);

                // Creating a new Game instance so a new game is started.
                game = new Game(player1, player2, boardSize, numberOfRounds);

                currentPlayer = player1;
                break;

            // Handles the event fired when the user pressed the menu button.
            case "Main Menu Button":

                // Setting the next two buttons to not visible because they should only be visible in mid-game.
                resetMenuButton.setVisible(false);
                mainMenuButton.setVisible(false);

                setOffset(400, 200);

                // Setting the main screen visible to the user again.
                cl.show(panelContainer, "1");
                break;

            // Handles the event fired when the user clicks to quit the game.
            case "Quit Game Menu Button":
            case "Quit Game Button":

                // Prompts a confirmation dialog to the user so the user has to confirm that he wants to stop the
                // game process.
                if(JOptionPane.showConfirmDialog(getParent(), "Are you sure you want to exit?", "Tic-Tac-Toe",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                    System.exit(1);
                }
                break;

            // Handles the event fired when the user clicks to enter the game settings screen.
            case "Settings Button":
            case "Settings Menu Button":

                mainMenuButton.setVisible(true);
                resetMenuButton.setVisible(false);
                setOffset(200, 100);
                cl.show(panelContainer, "3");
                break;

            // Handles the event fired when the user clicks to see the 'About Us' section.
            case "About Us":
                // Prompting a dialog to the screen so the user can read the ABOUT variable's value.
                JOptionPane.showMessageDialog(getParent(), ABOUT);
                break;

            // Handles the event fired when the user clicks to see the game instructions.
            case "How to play":
                // Prompting a dialog to the screen so the user can read the HELP variable's value.
                JOptionPane.showMessageDialog(getParent(), HELP);
                break;

            default:
                System.out.println(sourceActionCommand + " pressed!");
                break;
        }
    }

    /**
     * Accessor method to set the values for the gaps from the edges of the screen to the content in the
     * center of this frame.
     * @param leftRight value to be set as the gap size from the left/right screen edges.
     * @param topBottom value to be set as the gap size from the top/bottom screen edges.
     */
    private void setOffset(int leftRight, int topBottom) {

        Dimension d = new Dimension(leftRight, topBottom);
        add(new Box.Filler(d, d, d), BorderLayout.NORTH);
        add(new Box.Filler(d, d, d), BorderLayout.SOUTH);
        add(new Box.Filler(d, d, d), BorderLayout.WEST);
        add(new Box.Filler(d, d, d), BorderLayout.EAST);
    }

    /**
     * Initializes the game menu bar.
     */
    private void initializeMenu() {

        // Adding a game menu
        GameMenu gameMenu = new GameMenu("Game");

        // Adding options for a new game, resetting the current game, returning to the main screen or stopping
        // the game process and leaving the app.
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

        // Creating a custom styled JMenuBar instance
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
}

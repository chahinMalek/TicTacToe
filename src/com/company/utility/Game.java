package com.company.utility;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Holds information that every game possesses
 */
public class Game {

    int[][] board;
    LinkedList<Short> free;
    private int boardSize;
    private int moveCounter;
    private Player player1, player2;

    private final int ROUNDS;

    /**
     * Initializing game which means: setting the board size to the @param boardSize, creating the board with such
     * dimension, setting (by default) the turn to the player (user) and reloading the board.
     *
     * Precondition: Receiving a parameter of type short
     * Postcondition: Created object of type Game with a board of n row n dimension (n = boardSize)
     *
     * @param player1 first player to join the game (represents a user)
     * @param player2 second player to join the game (represents a second user or a computer opponent)
     * @param boardSize value used to initialize the dimensions of the game board
     * @param rounds value used to initialize the number of rounds the game should last
     */
    public Game(Player player1, Player player2, int boardSize, int rounds) {

        ROUNDS = rounds;

        this.player1 = player1;
        this.player2 = player2;

        // FIXME player 1 should have the value 1
        this.player1.setSign(-1);
        this.player2.setSign(1);

        this.boardSize = boardSize;
        board = new int[boardSize+2][boardSize+2];
        free = new LinkedList<>();
        reloadBoard();
    }

    /**
     * Resets the game boards state
     */
    public void reloadBoard() {

        while(!free.isEmpty()) free.removeFirst();

        for(short i=0; i<boardSize*boardSize; i++) free.add(i);
        moveCounter = 0;

        for(int i=0; i<boardSize+2; i++) {

            for(int j=0; j<boardSize+2; j++) {
                board[i][j] = 0;
            }
        }
    }

    /**
     * Prints out the game board's state
     */
    public void printBoard() {

        System.out.println();
        System.out.println();

        for(int i=1; i<this.boardSize+1; i++) {

            for(int j=1; j<this.boardSize+1; j++) {

                if(this.board[i][j] == 1) {
                    System.out.print("| X |");
                } else if(this.board[i][j] == -1) {
                    System.out.print("| O |");
                } else {
                    System.out.print("|   |");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Checks if there are any moves left on the board to play
     * @return <em>true</em> if there are any moves left, <em>false</em> otherwise
     */
    public boolean anyMovesLeft() {
        return (free.size() != 0);
    }

    // TODO check if needed

    /**
     * Returns the first (user) player
     * @return a clone object of the user player
     */
    public Player getPlayer1() {
        return player1.clone();
    }

    /**
     * Returns the opponent (user or computer)
     * @return a clone object of the opponent player
     */
    public Player getPlayer2() {
        return player2.clone();
    }

    /**
     * Returns an integer value that represents the dimension of the board
     * @return value that represents the current boards's dimensions
     */
    public int getBoardSize() {
        return boardSize;
    }

    boolean isWinningMove(Player player, int x, int y) {
        short point = player.getSign();

        this.board[0][y] += point;
        if(Math.abs(this.board[0][y]) == this.boardSize) return true;

        this.board[x][0] += point;
        if(Math.abs(this.board[x][0]) == this.boardSize) return true;

        if(x == y) {
            this.board[0][0] += point;
        }
        if(Math.abs(this.board[0][0]) == this.boardSize) return true;

        if(this.boardSize - x + 1 == y) {
            this.board[boardSize + 1][0] += point;
        }
        if(Math.abs(this.board[this.boardSize + 1][0]) == this.boardSize) return true;

        return false;
    }

    void incrementMoveCounter() {
        moveCounter++;
    }

    void removeField(int element) {
        Iterator<Short> it = free.listIterator();

        while(it.hasNext()) {

            if(it.next().intValue() == element) {
                it.remove();
                break;
            }
        }
    }

}

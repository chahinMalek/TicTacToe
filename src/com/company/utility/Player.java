package com.company.utility;

/**
 * Represents every type of a player in this game
 */
public abstract class Player {

    private Move lastMove = null;

    /**
     * Represents every type of a move in this game
     */
    public static class Move {

        /**
         * Values that represent a <em>Move's</em> instance row and column
         */
        int row, column;


        /**
         * Default constructor
         */
        public Move() {

        }


        /**
         * Used to instantiate objects of type <em>Move</em> with defined values for <em>row</em>
         * and <em>column</em> attributes
         *
         * @param row value that represents the row where the move will be placed
         * @param column value that represents the column where the move will be placed
         */
        public Move(int row, int column) {
            this.row = row;
            this.column = column;
        }

        /**
         * Mutator method to get the calling <em>Move</em> object's <em>row</em> value
         * @return <em>int</em> value that represents the <em>Move</em> object's row on the game board
         */
        public int getRow() {
            return row;
        }

        /**
         * Mutator method to set the calling <em>Move</em> object's <em>row</em> value
         * @param row value that represents the row where the move will be placed
         */
        public void setRow(int row) {
            this.row = row;
        }

        /**
         * Mutator method to get the calling <em>Move</em> object's <em>column</em> value
         * @return <em>int</em> value that represents the <em>Move</em> object's column on the game board
         */
        public int getColumn() {
            return column;
        }

        /**
         * Mutator method to set the calling <em>Move</em> object's <em>column</em> value
         * @param column value that represents the column where the move will be placed
         */
        public void setColumn(int column) {
            this.column = column;
        }
    }

    private short numberOfWins;
    private short sign;

    /**
     * Sets the number of wins of a player to 0
     */
    public Player() {
        this.numberOfWins = 0;
    }

    /**
     * Copy constructor
     * @param player object whose values will be copied to the calling <em>Player</em> instance
     */
    public Player(Player player) {

        if(player == null) {
            System.out.println("Constructor argument must not be null!");
            System.exit(1);
        }

        this.numberOfWins = player.numberOfWins;
        this.sign = player.sign;
    }

    /**
     * Useful in inheritance
     * @return copy of <em>Player</em> object in the same hierarchy level the calling <em>Player</em> instance is
     */
    @Override
    public Player clone() {
        return this;
    }

    /**
     * Mutator method to get the calling <em>Player</em> object's <em>number of wins</em> value
     * @return <em>int</em> value that represents the calling <em>Player</em> object's number of wins in the current game
     */
    public short getNumberOfWins() {
        return numberOfWins;
    }

    /**
     * Accessor method to set the calling <em>Player</em> object's <em>number of wins</em> value
     * @param numberOfWins the value to be set
     */
    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = (short) numberOfWins;
    }

    /**
     * Mutator method to get the calling <em>Move</em> object's <em>sign</em>
     * @return <em>int</em> value that represents the calling <em>Player</em> object's sign
     */
    public short getSign() {
        return sign;
    }

    /**
     * Mutator method to set the calling <em>Player</em> object's sign
     * @param sign value used to set the calling <em>Player</em> object's sign
     */
    public void setSign(int sign) {
        this.sign = (short) sign;
    }

    /**
     * Method that is used by a <em>Player</em> instance (no matter what level in the hierarchy)
     * to play a move in the current game
     *
     * @param game represents the current game
     * @return <em>short</em> value 2 if the last move resulted a win, otherwise 1 if the move was legal
     */
    public Short move(Game game) {

        Move move = lastMove = findOptimalMove(game);
        int x = move.getRow(), y = move.getColumn();

        game.board[x][y] += (getSign() == 1) ? (short) 1 : -1;

        if(game.isWinningMove(this, x, y)) {

            increaseNumberOfWins();
            game.reloadBoard();
            return 2;
        }
        return 1;
    }

    /**
     * Method that is used by a <em>Player</em> instance (mainly made for the user class)
     * to play a move in the current game
     *
     * @param game represents the current game
     * @param x represents the <em>Player</em> move's row index
     * @param y represents the <em>Player</em> move's column index
     * @return <em>short</em> value 2 if the last move resulted a win, otherwise 1 if the move was legal
     */
    public Short move(Game game, int x, int y) {

        // Will be used in the gui package
        lastMove = getMove((short) x, y);

        game.board[x][y] += (getSign() == 1) ? (short) 1 : -1;

        game.removeField((x-1)*game.getBoardSize() + (y-1));

        if(game.isWinningMove(this, x, y)) {

            increaseNumberOfWins();
            game.reloadBoard();
            return 2;
        }
        return 1;

    }

    /**
     * Accessor method to get the current last move in the game.
     * @return a value that represents the last move in the game of type <em>Move</em>
     */
    public Move getLastMove() {
        return lastMove;
    }

    /**
     * Method that is used by the <em>move(Game)</em> method to decide which is the move that the calling <em>Player</em>
     * instance will choose to play next
     *
     * @param game represents the current game
     * @return a move that the calling <em>Player</em> instance will choose to play next
     */
    protected abstract Move findOptimalMove(Game game);

    /**
     * Static method used to get a <em>Move</em> instance based on a <em>Short</em> value and the current game's board size
     * @param index value that represents the index of a move
     * @param boardSize current game's board size
     *
     * @return a new <em>Move</em> instance which <em>row</em> and <em>column</em> value are defined
     * by the <strong><em>index</em></strong> parameter
     */
    protected static Move getMove(Short index, int boardSize) {
        int x = index/boardSize + 1;
        return new Move(x, index - (x-1)*boardSize + 1);
    }

    /**
     * Static method used to get a <em>short</em> index value out of a <em>Move</em> instance
     * @param move a <em>Move</em> instance which will be converted to a <em>short</em> value
     * @param boardSize current game's board size
     *
     * @return a new <em>short</em> value which is calculated by the passed <strong><em>move</em></strong> value
     */
    protected static Short getIndex(Move move, int boardSize) {
        return (short)((move.row -1) * boardSize + (move.column -1));
    }

    private void increaseNumberOfWins() {
        this.numberOfWins++;
    }
}

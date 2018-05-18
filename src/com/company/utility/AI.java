package com.company.utility;

import java.util.LinkedList;
import java.util.Random;

/**
 * Abstract class that will represent an instance of a AI player
 */
public abstract class AI extends Player {

    /**
     * Method that is used by the calling <em>Player</em> object's <em>move(Game)</em> method to decide
     * which move is best to play next
     *
     * @param game represents the current game
     * @return returns the best move the AI agent should play
     */
    @Override
    public Move findOptimalMove(Game game) {

        // The AI agent is always the maximizer

        int minimaxValue;
        Move bestMove = new Move(), temp;

        // Moves with priority
        LinkedList<Short> priorities = getPriorityMoves(game);

        if(game.free.size() == Math.pow(game.getBoardSize(), 2)) {

            int boardSize = game.getBoardSize(), cornerSize = (boardSize % 2 == 0) ? 2 * boardSize : 2 * boardSize-1;

            int[] corners = new int[cornerSize];
            --cornerSize;

            for(int i = 0, j = boardSize-1; i < boardSize; i++, j--) {
                corners[cornerSize--] = i * boardSize + i;

                if(i * boardSize + i != j * boardSize + i) {
                    corners[cornerSize--] = j * boardSize + i;
                }
            }

            bestMove = getMove((short) corners[new Random().nextInt(corners.length)], game.getBoardSize());

            game.removeField(getIndex(bestMove, game.getBoardSize()));
            return bestMove;
        }

        // Temporarily declared variable moves
        LinkedList<Short> moves = game.free;

        int counter = moves.size();
        Short minimaxMove, priorityMove = priorities.poll();

        // Setting nodeValue to -infinity because the agent is always the maximizer at the root node
        int nodeValue = Integer.MIN_VALUE;

        for(int i=0; i<counter; i++) {

            minimaxMove = moves.poll();

            // Check if the minimaxMove variable value is stored in the priorityMove collection
            if(priorityMove != null && !priorityMove.equals(minimaxMove)) {
                moves.add(minimaxMove);
                continue;
            }

            // Variables priorityMove and minimaxMove have the same value
            priorityMove = priorities.poll();

            // Polling the first free move and saving move in temp variable
            temp = getMove(minimaxMove, game.getBoardSize());

            // Playing the move
            playMove(game, temp, getSign());

            // Calculating the value of the move
            minimaxValue = minimax(game, 0, true, temp);

            // Redo playing the move
            revertMove(game, temp, getSign());

            // Adding the polled first element back to the list of free moves
            moves.add(minimaxMove);

            // Saving the optimal move
            if(minimaxValue > nodeValue) {
                nodeValue = minimaxValue;
                bestMove.setRow(temp.getRow());
                bestMove.setColumn(temp.getColumn());
            }
        }

        // Removing the optimal move from thee free-move list
        game.removeField(getIndex(bestMove, game.getBoardSize()));

        // Returning the optimal move to the Player.move(Game) method
        return bestMove;
    }

    /**
     * Abstract method that will serve to make an Easy, Medium, Hard game scaling
     * @param game represents the current game
     * @return a list of moves that the AI agent will consider to look at as options for the next move
     */
    protected abstract LinkedList<Short> getPriorityMoves(Game game);

    /**
     * Algorithm used to create a game tree and use DFS traversal to check the game stages
     * @param game represents the current game
     * @param depth <em>int</em> value used to control the method's recursive calls
     *
     * @param isMaximizer <em>boolean</em> value used as a flag to determine if the calling <em>AI</em> instance
     * is a maximizer or the minimizer in the method's recursive calls (in the DFS traversal)
     *
     * @param move represents the last made move on the game board
     * @return an <em>int</em> value that will represent the value of a move to play in the <em>findOptimalMove(Game)</em> method
     */
    protected int minimax(Game game, int depth, boolean isMaximizer, Move move) {

        // Checking if the move is a winning one
        if(isWinningMove(game, move)) {
            if(isMaximizer) {
                return 10;

            } return -10;
        }

        if(depth == game.getBoardSize() * game.getBoardSize()) {
            return 0;
        }

        boolean flag = isMaximizer;
        isMaximizer = !isMaximizer;
        depth++;

        LinkedList<Short> priorities = getPriorityMoves(game);
        LinkedList<Short> moves = game.free;

        // Results a draw
        if(moves.size() == 0) {
            return 0;
        }

        int freeMoveCounter = moves.size();

        Short minimaxMove, priorityMove = priorities.poll();

        int nodeValue = (isMaximizer) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for(int i=0; i<freeMoveCounter; i++) {

            // Polling first free move
            minimaxMove = moves.poll();

            if(!minimaxMove.equals(priorityMove)) {
                moves.add(minimaxMove);
                continue;
            }

            priorityMove = priorities.poll();

            Move tMove = getMove(minimaxMove, game.getBoardSize());
            playMove(game, tMove, (isMaximizer) ? this.getSign() : -this.getSign());

            if(isMaximizer) {
                nodeValue = Math.max(minimax(game, depth, isMaximizer, tMove), nodeValue);

            } else {
                nodeValue = Math.min(minimax(game, depth, isMaximizer, tMove), nodeValue);
            }

            moves.add(minimaxMove);
            revertMove(game, tMove, (isMaximizer) ? this.getSign() : -this.getSign());
        }

        // Makes the AI a bit smarter when choosing which move to play
        // It will choose the move which will lead him to an optimal result in the fastest way
        return nodeValue + ((flag) ? (-depth) : depth);
    }

    /**
     * Utility function for the minimax algorithm to check if the last played move was a winning one
     * @param game represents the current game
     * @param move represents the last played move on the game board
     * @return <em>true</em> if the passed <strong><em>move</em></strong> parameter was a winning move, <em>false</em> otherwise
     */
    protected boolean isWinningMove(Game game, Move move) {

        int boardSize = game.getBoardSize();

        if(Math.abs(game.board[move.row][0]) == boardSize) {
            return true;
        }

        if(Math.abs(game.board[0][move.column]) == boardSize) {
            return true;
        }

        if(move.row == move.column && Math.abs(game.board[0][0]) == boardSize) {
            return true;
        }

        if(move.row + move.column == boardSize + 1 && Math.abs(game.board[boardSize+1][0]) == boardSize) {
            return true;
        }

        return false;
    }

    /**
     * Function that simulates playing a move
     * @param game represents the current game
     * @param move represents the last played move on the game board
     * @param sign value that represents the sign that is placed in the game board at the <em>move</em> place
     */
    protected void playMove(Game game, Move move, int sign) {

        game.board[move.row][move.column] = sign;

        game.board[move.row][0] += sign;
        game.board[0][move.column] += sign;

        if(move.row == move.column) {
            game.board[0][0] += sign;
        }

        if(move.row + move.column == game.board.length - 1) {
            game.board[game.board.length-1][0] += sign;
        }
    }

    /**
     * Counts the move's score based on sum of the scores from the column, row (and diagonals) score the move is placed on
     * @param game represents the current game
     * @param move represents the last played move on the game board
     *
     * @return the move's score calculated as stated in the method description
     */
    protected int moveScore(Game game, Move move) {

        int priority = 0;

        priority += Math.abs(game.board[0][move.column]);
        priority += Math.abs(game.board[move.row][0]);

        if(move.row == move.column) {
            priority += Math.abs(game.board[0][0]);
        }

        if(move.row + move.column == game.board.length - 1) {
            priority += Math.abs(game.board[game.board[0].length-1][0]);
        }

        return priority;
    }

    /**
     * Function that simulates reverting a move
     * @param game represents the current game
     * @param move represents the last played move on the game board
     * @param sign value that represents the sign that is placed in the game board at the <em>move</em> place
     */
    protected void revertMove(Game game, Move move, int sign) {

        playMove(game, move, -sign);
        game.board[move.row][move.column] = 0;
    }

}

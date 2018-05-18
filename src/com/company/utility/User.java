package com.company.utility;

import java.util.Scanner;

/**
 * Class which objects represent user players of the game
 */
public final class User extends Player {

    private String nickname;

    public User() {
    }

    /**
     * Used to instantiate an object of type User and initializing the private field nickname to the
     * <strong><em>nickname</em></strong> argument value
     *
     * @param nickname value that will be used as the name of the user player
     */
    public User(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns a new <em>User</em> player of type with the same values as the calling object
     *
     * @return Returns a new instance of type <em>User</em> which has all the private values as the calling object
     */
    @Override
    public User clone() {
        return new User(this);
    }

    /**
      * Invoking this method results making a new move by a <em>User</em> player
      * @param game instance of the <em>Game</em> class that represents the current game
      * @return index of the move made by the <em>User</em> player if the move is valid, -1 otherwise
      */
    @Override
    public final Short move(Game game) {
        try {
            return super.move(game);

        } catch (NullPointerException e) {
            return -1;
        }
    }

    /**
     * Handling <em>User</em> player's input moves
     * @param game instance of the <em>Game</em> class that represents the current game
     * @return object of type <em>Move</em> that represents the <em>User</em> player's input move
     */
    @Override
    protected final Move findOptimalMove(Game game) {

        Scanner keyboard = new Scanner(System.in);

        short x = keyboard.nextShort(), y = keyboard.nextShort();

        if (x < 1 || x > game.getBoardSize() || y < 1 || y > game.getBoardSize() || game.board[x][y] != 0) {
            return null;
        }

        game.removeField((x-1)*game.getBoardSize() + (y-1));

        return new Move(x, y);
    }

    private User(User user) {
        super(user);
        this.nickname = user.nickname;
    }
}

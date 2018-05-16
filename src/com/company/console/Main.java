package com.company.console;

import com.company.utility.*;

import java.util.Scanner;

import static com.company.console.InputHandler.checkInput;

/**
 * Initializing a game for two players (user or computer opponent)
 */
public class Main {

    /**
     * Method that checks if <strong><em>game</em></strong> has ended
     * @param game represents a current game object
     * @param player represents a player that made the last move
     *
     * @param flag value returned by the <em>move</em> method from the <em>Player</em> class that
     * will be used to check if the <strong><em>player</em></strong> won.
     *
     * @return <em>true</em> if the game has ended in either way, <em>false</em> otherwise
     */
    private static boolean isEnd(Game game, Player player, short flag) {

        if(flag == 2) {
            String sign = (player.getSign() == 1) ? "X" : "O";
            System.out.println(sign + " won!");
            game.printBoard();
            System.out.println("\nCurrent score: " + game.getPlayer1().getNumberOfWins() + " : " + game.getPlayer2().getNumberOfWins() + "\n");
            return true;
        }

        if(!game.anyMovesLeft()) {
            System.out.println("\n DRAW \n");
            game.printBoard();
            return true;
        }

        return false;
    }

    /**
     * Used to instantiate a game
     */
    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);

        // Variable initialization.
        int decision, dimension, numberOfRounds;
        Player player1, player2;

        // Username input
        System.out.print("Enter nickname (Player 1): ");
        player1 = new User(keyboard.nextLine());

        // Choosing game size and round number. Adding 2 to the value of the variable dimension because we choose 1 to get the
        //  3x3 dimension (3 will be the value of the dimension variable), we choose 2 to get the 4x4 dimension etc.
        dimension = checkInput("Choose game size:\n1. 3x3\n2. 4x4", new Integer[] {1, 2});
        dimension += 2;

        // We choose 1 if we want to play one game, 2 if we want to play best of 3 games, 3 if we want to play best of 5 games etc.
        // This is why we assign the value 2*numberOfRounds - 1 as the final value to the numberOfRounds variable
        numberOfRounds = checkInput("Best of?:\n1. 1\n2. 3\n3. 5\n4. 7", new Integer[] {1, 2, 3, 4});
        numberOfRounds = 2 * numberOfRounds - 1;

        // Choosing game mode
        decision = checkInput("Choose your opponent: (Press 1 or 2 to choose):\n1. Player\n2. Computer", new Integer[] {1, 2});


        if(decision == 1) {
            // Entering username for opponent user player

            System.out.print("Enter nickname (Player 2): ");
            player2 = new User(keyboard.nextLine());

        } else {

            // Choosing AI level
            decision = checkInput("Choose AI level: (Press 1, 2 or 3 to choose):\n1. Easy\n2. Medium\n3. Hard", new Integer[] {1, 2, 3});

            // Instantiating opponent as computer with chosen AI level.
            if(decision == 1) {
                player2 = new EasyAI();

            } else if(decision == 2) {
                player2 = new MediumAI();

            } else player2 = new HardAI();
        }

        // Game created. Each player got a sign (X or O)
        Game game = new Game(player1, player2, dimension, (short) numberOfRounds);

        // Game loop
        while(player1.getNumberOfWins() < (numberOfRounds/2 + 1) && player2.getNumberOfWins() < (numberOfRounds/2 + 1)) {

            // Sign changed every round
            Short temp = player1.getSign();
            player1.setSign(player2.getSign());
            player2.setSign(temp);

            System.out.println(player1.getSign());

            // Game loop. Can end with a WIN or a DRAW. After each case the board gets reset.
            while(true) {

                short flag;

                game.printBoard();
                System.out.println("X is on the move.");

                // Move input handling for player with sign X
                flag = (player1.getSign() == 1) ? player1.move(game) : player2.move(game);
                while(flag == -1) {
                    System.out.println("Illegal move! Try again.");
                    flag = (player1.getSign() == 1) ? player1.move(game) : player2.move(game);
                }

                // Checking if the round ended after the move
                if(isEnd(game, (player1.getSign() == 1) ? player1 : player2, flag)) {
                    System.out.println("Enter anything to continue.");
                    keyboard.nextLine();
                    break;
                }

                game.printBoard();
                System.out.println("O is on the move.");

                // Move input handling for player with sign X
                flag = (player1.getSign() == -1) ? player1.move(game) : player2.move(game);
                while(flag == -1) {
                    System.out.println("Illegal move! Try again.");
                    flag = (player1.getSign() == -1) ? player1.move(game) : player2.move(game);
                }

                // Checking if the round ended after the move
                if(isEnd(game, (player1.getSign() == -1) ? player1 : player2, flag)) {
                    System.out.println("Enter anything to continue.");
                    keyboard.nextLine();
                    break;
                }
            }
            game.reloadBoard();
        }
    }
}

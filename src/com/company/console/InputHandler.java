package com.company.console;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Used to handle the user input in this game
 */
public interface InputHandler {

    /**
     * @param <T> generic data type
     *
     * @param input represents a generic data value that the function checks if it's equal to any in the parameter
     * <strong><em>values</em></strong>
     *
     * @param values represents array of values that <strong><em>input</em></strong> has to match one of them
     * @return true if <strong><em>input</em></strong> matches at least one value in <strong><em>values</em></strong>,
     * false otherwise
     */
    static <T> boolean matchValues(T input, Object[] values) {

        for(Object value : values) {
            if(input.equals(value)) return true;
        }
        return false;
    }

    /**
     * @param message output message the user should see
     * @param values values that should be matched by the user in order for the input to be valid
     * @return a user's valid input response
     */
    static int checkInput(String message, Integer[] values) {

        Scanner input = new Scanner(System.in);

        int decision;
        boolean flag = false;

        do {

            if(flag) {
                System.out.println("Invalid input! Try again.");
            }

            try {
                System.out.println(message);
                decision = input.nextInt();

            } catch (InputMismatchException e) {
                decision = -1;
                input.nextLine();
            }

        } while(flag = !matchValues(decision, values));

        input.nextLine();
        return decision;

    }

}

package com.company.gui;

/**
 * User: malek
 * Date: 5/17/2018
 * Time: 1:22 PM
 */

/**
 * Custom <em>MainButton</em> class with style defined by the <em>MainButton</em> superclass but
 * with few more added functionalities.
 * Instances of this class will be used in the <em>GameFrame</em> class to be a part of the game board.
 */
public class BoardButton extends MainButton {

    private int row, column;

    /**
     * Used to create a <em>BoardButton</em> instance with row and column values set with the
     * <em>row</em> and <em>column</em> parameter values.
     * @param row the row index value to be set.
     * @param column the column index value to be set.
     */
    public BoardButton(int row, int column) {
        super();

        this.row = row;
        this.column = column;

        setVisible(true);
    }

    /**
     *
     * @return
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Mutator method to get the calling <em>BoardButton</em> object's <em>column</em> value
     * @return
     */
    public int getColumn() {
        return this.column;
    }

}

package com.company.gui;

/**
 * User: malek
 * Date: 5/17/2018
 * Time: 1:22 PM
 */

public class BoardButton extends MainButton {

    private int row, column;

    public BoardButton(int row, int column) {
        super();

        this.row = row;
        this.column = column;

        setVisible(true);
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

}

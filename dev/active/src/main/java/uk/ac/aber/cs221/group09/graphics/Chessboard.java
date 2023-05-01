/*
    * @(GP9) Chessboard.java 0.5 2023/04/26
    *
    * Copyright (c) 2021 Aberystywth University
    * All rights reserved
    *
    */

package uk.ac.aber.cs221.group09.graphics;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

/**
 * Chessboard - A class that controls and displays the chessboard gridpane
 *
 * The class is used to produce the graphical representation of the board, including updating
 * tile graphics to represent a particular Forsyth Edwards Notation or to highlight tile buttons
 * to indicate valid move, checked pieces, or available pieces.
 *
 * @author Gwion Hughes, Ciaran Smith
 * @version 0.6 draft
 * @see Tile
 */
public class Chessboard {
    private TileGraphicsLoader graphicsLoader;
    private Tile[][] tiles;
    private GridPane chessBoard;
    private PlayScreen playScreen;

    /**
     * Class constructor. Takes a scene containing class as a paramater to display the chessboard on.
     * Calls the a function that sets up the chessboard gridpane.
     * @param playScreen - The class containing the scene displaying the chessboard to the user.
     */
    public Chessboard(PlayScreen playScreen) {
        chessBoard();
        this.playScreen = playScreen;
    }

    /**
     * Getter for the Chessboard gridpane
     * @return - Chessboard gridpane instance
     */
    public GridPane getChessBoard() {
        return chessBoard;
    }

    /**
     * Initialises the chessboard.
     */
    private void chessBoard() {
        //The graphics loader contains the images for the chess pieces.
        this.graphicsLoader = new TileGraphicsLoader();

        //Initialise the FX data struct containing the buttons and an indexable 2d array that share ownership
        //of the tiles.
        this.chessBoard = new GridPane();
        this.tiles = new Tile[8][8];

        //Make sure the gridpane has no padding so it doesn't repel other objects
        this.chessBoard.setPadding(new Insets(0, 0, 0, 0));


        //A simple numerical variable used to itterate black and white tile placement one after another later in the function
        int check = 0;

        for (int row = 0; row < 8; row++) {

            //Every row should start on an alternating colour
            check++;
            for (int column = 0; column < 8; column++) {

                //Every column should start with an alternating colour
                check++;
                if (check % 2 == 1) {

                    //Create a new black tile. Add its button to the gridpane and the class to the 2d array
                    Tile tile = new Tile(column, row, false, this);
                    this.tiles[column][row] = tile;
                    this.chessBoard.add(tile.getButton(), column, row);
                } else {

                    //Create a new white tile. Add its button to the gridpane and the class to the 2d array
                    Tile tile = new Tile(column, row, true, this);
                    this.tiles[column][row] = tile;
                    this.chessBoard.add(tile.getButton(), column, row);
                }
            }
        }
    }

    /**
     * This function is called by a tile's button. It passes the column and the row on the chessboard where
     * the user has clicked forwards to the chessboard. The pressed tile's styleclass is changed until the next
     * event when the board is "refreshed". The column and row are passed then to the playscreen to pass onwards.
     * @param column - The file or vertical column of the pressed tile.
     * @param row - The rank or horizontal row of the pressed tile.
     */
    public void click(int column, int row) {
        //Debugging output
        System.out.println(column);
        System.out.println(row);

        //Alert the playscreen of incident and set tile to visually indicate it has been selected.
        this.playScreen.alertPressedTile(column, row);
        tiles[row][column].setStyleClass("selected-tile");

        //Cieran proof of concept highlight valid move tile.
        highlightValidTiles(tempCalcValidPawn(column, row));
    }

    /**
     * Function called to return each tile's button to its default styleclass, i.e. no longer visually indicate it is selected
     * or a valid location to move. The function also clears the graphics of the tile's button. Called to refresh the board.
     */
    private void clearChessBoard() {
        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 8; row++) {
                tiles[column][row].clearTile();
            }
        }
    }

    /**
     * Called on the chessboard to update the board with a new visual representation. Usually called after a move or
     * to set a board up in its default position. Passes an imageview to each tile to display depending on a board
     * notation.
     * @param boardNotation - A Forsyth Edwards notation written in string representing the state of the board.
     */
    public void updateBoard(String boardNotation) {
        //Clear of the board of any graphics or highlighted tiles
        clearChessBoard();

        //sets starting positions to access the array from.
        int column = 0;
        int row = 0;

        //iterate through the Forsyth Edwards Notation string.
        for (int readHead = 0; readHead < boardNotation.length(); readHead++) {
            //if we have arrived at a space then we no longer need to read from the string, as the information from this point on is not relevant to this method
            if (boardNotation.charAt(readHead) == ' ') {
                return;
            }

            //if we have arrived at a '/', this is the marker for going down a rank, so we decrement the rank and reset the file to the first file.
            if (boardNotation.charAt(readHead) == '/') {
                column = 0;
                row++;
                continue;
            }

            //if we have arrived at a digit this is the marker for n amount of empty squares on the rank in a row before we find a piece. so we add this digit to out file variable.
            if (Character.isDigit(boardNotation.charAt(readHead))) {
                column += Character.getNumericValue(boardNotation.charAt(readHead));
                continue;
            }


            //if the character representing the piece is upper case then it is a white piece, else it is a black piece.
            tiles[column][row].setGraphics(graphicsLoader.fetchTilePieceGraphic(boardNotation.charAt(readHead)));
            //increment the file for the next position on the board.
            column++;
        }
        return;
    }

    /**
     * Unimplemented function. Pass is three arrays, possibly empty, of coordinates of buttons to highlights in different
     * styles.
     * @param validT - ArrayList of coordinates of valid tiles.
     * @param checkT - ArrayList of coordinates of checked pieces
     * @param attackT - ArrayList of coordinates containing attacking pieces.
     */
    public void highlightTiles(ArrayList validT, ArrayList checkT, ArrayList attackT) {
        //Helper function on Valid Tiles
        //Helper function on checked tiles
        //Helper function on Attacking tiles
    }

    private String[][] testChessBoard;

    /**
     * A function to test the funcionatlity of highlighting tiles. Given a position that would hold a pawn in
     * its default start board state, if clicked it should display valid moves from that position.
     * @param selectedColumn - The file or vertical column of the pressed tile.
     * @param selectedRow - The rank or horizontal row of the pressed tile.
     * @return A string array containing the char numbers of the coordinates to highlight.
     */
    private ArrayList tempCalcValidPawn(int selectedColumn, int selectedRow) {
        testChessBoard = new String[8][8];
        ArrayList pawnMoves = new ArrayList();
        pawnMoves.clear();
        //Assigning Pawns for both players
        for (int i = 0; i < 8; i++) {
            testChessBoard[1][i] = "BP";
            testChessBoard[6][i] = "WP";
        }
        if (testChessBoard[selectedColumn][selectedRow] == "WP") {
            pawnMoves.add(selectedRow);
            pawnMoves.add(selectedColumn - 1);
        } else if (testChessBoard[selectedColumn][selectedRow] == "BP") {
            pawnMoves.add(selectedRow);
            pawnMoves.add(selectedColumn + 1);
        }
        System.out.println(testChessBoard[selectedColumn][selectedRow]);
        return pawnMoves;
    }

    private void highlightValidTiles(ArrayList validT) {
        //go through list of valid tile coordinates and
        //tiles[0][0].setStyleClass("valid-tile");
        tiles[(int) validT.get(0)][(int) validT.get(1)].setStyleClass("valid-tile");
    }

    private void highlightCheckTile(ArrayList checkT) {
        tiles[0][0].setStyleClass("check-tile");
    }

    private void highlightAttackingTiles(ArrayList attackT) {
        tiles[0][0].setStyleClass("attacking-tile");
    }

}

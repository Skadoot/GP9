package uk.ac.aber.cs221.group09.graphics;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import uk.ac.aber.cs221.group09.logic.Game;
import uk.ac.aber.cs221.group09.logic.vector.Vector2;

import java.util.ArrayList;

/**
 * This is a controller class for the chessboard displayed on the PlayScreen
 */
public class Chessboard {
    private TileGraphicsLoader graphicsLoader;
    private Tile[][] tiles;
    private GridPane chessBoard;
    private PlayScreen playScreen;

    public Chessboard(PlayScreen playScreen) {
        chessBoard();
        this.playScreen = playScreen;
    }

    private void chessBoard() {
        this.graphicsLoader = new TileGraphicsLoader();
        //Initialise a gridpane that will be a grid containing out buttons that behave as tiles
        this.chessBoard = new GridPane();
        //Make sure the gridpane has no padding so it doesn't repel other objects
        this.chessBoard.setPadding(new Insets(0, 0, 0, 0));
        //Initialise our 2d array of buttons that will contain pointers it shares with the gridpane for ease of index
        this.tiles = new Tile[8][8];

        //A simple numerical variable used to itterate black and white tile placement one after another later in the function
        int check = 0;

        for (int row = 0; row < 8; row++) {

            //Every row should start on an alternating colour
            check++;
            for (int column = 0; column < 8; column++) {

                check++;
                if (check % 2 == 1) {
                    Tile tile = new Tile(column, row, false, this);
                    this.tiles[column][row] = tile;
                    this.chessBoard.add(tile.getButton(), column, row);
                } else {
                    Tile tile = new Tile(column, row, true, this);
                    this.tiles[column][row] = tile;
                    this.chessBoard.add(tile.getButton(), column, row);
                }
            }
        }
    }

    public void click(int column, int row) {
        System.out.println(column);
        System.out.println(row);
        this.playScreen.alertPressedTile(column, row);
        tiles[row][column].setStyleClass("selected-tile");

        //highlightValidTiles(tempCalcValidPawn(column, row));
    }

    private void clearChessBoard() {
        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 8; row++) {
                tiles[column][row].clearTile();
            }
        }
    }

    public void updateBoard(String boardNotation) {
        //Clear of the board of any graphics first
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
    }

    public GridPane getChessBoard() {
        return chessBoard;
    }

    public void highlightTiles(ArrayList validT, ArrayList checkT, ArrayList attackT) {
        //Helper function on Valid Tiles
        //Helper function on checked tiles
        //Helper function on Attacking tiles
    }

    private String[][] testChessBoard;

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

    public void highlightValidTiles(ArrayList<Vector2> validT) {
        //go through list of valid tile coordinates and
        //tiles[0][0].setStyleClass("valid-tile");
        for(int i =0; i< validT.size();i++){
            int x = validT.get(i).x;
            int y = validT.get(i).y;
            System.out.println(x +":" + y);
            tiles[x][y].setStyleClass("valid-tile");
        }

    }

    private void highlightCheckTile(ArrayList checkT) {
        tiles[0][0].setStyleClass("check-tile");
    }

    private void highlightAttackingTiles(ArrayList attackT) {
        tiles[0][0].setStyleClass("attacking-tile");
    }

}

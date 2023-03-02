import pieces.piece;
import vector.vector2;

/**
 * a class that stores the logical representation of a chess board.
 *
 * this class stores the position of each piece on the board.
 *
 * @version 1.0 initial development.
 * @version 1.1 can now calculate legal moves for all pieces of a particular color.
 * @version 1.2 can now find legal moves of all pieces taking into account if the moves will put the player in check.
 * @version 1.3 functionality for calculating the legal moves for a player's piece has been moved to moveCalculator.
 *
 * @see moveCalculator
 *
 * @author shr27@aber.ac.uk.
 */

public class board {

    //board size.
    private static final int BOARD_SIZE = 8;


    //the logical representation of the board.
    private final piece[][] board;


    //forsyth edwards notation of the board for saving and loading board states.
    private String boardState;


    //both king positions.
    private vector2 whiteKingPosition;
    private vector2 blackKingPosition;

    /**
     * constructor for board.
     * initializes the board array.
     */
    public board() {
        board = new piece[BOARD_SIZE][BOARD_SIZE];
    }

    /**
     * initializes the pieces in the board based on the Forsyth Edwards Notation string given.
     *
     * @param initializingBoardState This is the Forsyth Edwards Notation string.
     */
    public void initializeBoardState(String initializingBoardState) {
        //sets the board state string to the given Forsyth Edwards Notation.
        this.boardState = initializingBoardState;

        //sets starting positions to access the array from.
        int file = 0;
        int rank = 7;

        //iterate through the Forsyth Edwards Notation string.
        for (int positionInString = 0; positionInString < boardState.length(); positionInString++) {
            //if we have arrived at a space then we no longer need to read from the string, as the information from this point on is not relevant to this method
            if (boardState.charAt(positionInString) == ' ') {return;}

            //if we have arrived at a '/', this is the marker for going down a rank, so we decrement the rank and reset the file to the first file.
            if (boardState.charAt(positionInString) == '/') {file = 0; rank--; continue;}

            //if we have arrived at a digit this is the marker for n amount of empty squares on the rank in a row before we find a piece. so we add this digit to out file variable.
            if (Character.isDigit(boardState.charAt(positionInString))) { file += Character.getNumericValue(boardState.charAt(positionInString)); continue;}

            //if we arrive here we need to add a piece to the board. at the position file, rank.
            vector2 boardPosition = new vector2(file, rank);

            //if the character representing the piece is upper case then it is a white piece, else it is a black piece.
            if (Character.isUpperCase(boardState.charAt(positionInString))) {
                //create a new white piece that is of the same type as the current character in the string, with the position of boardPosition.
                piece piece = new piece('w', boardPosition, Character.toLowerCase(boardState.charAt(positionInString)));
                setPiece(boardPosition, piece);

                //if the piece is a king, set the position of the white king to this piece's position.
                if (Character.toLowerCase(boardState.charAt(positionInString)) == 'k'){
                    whiteKingPosition = boardPosition;
                }
            } else {
                //create a new black piece that is of the same type as the current character in the string, with the position of boardPosition.
                piece piece = new piece('b', boardPosition, boardState.charAt(positionInString));
                setPiece(boardPosition, piece);

                //if the piece is a king, set the position of the black king to this piece's position.
                if (boardState.charAt(positionInString) == 'k'){
                    blackKingPosition = boardPosition;
                }
            }
            //increment the file for the next position on the board.
            file++;
        }
    }

    /**
     * A method to move a piece on the board.
     *
     * @param selectedPiece the selected piece to move.
     * @param coordinate the vector2 coordinate to move the piece to.
     */
    public void movePiece(piece selectedPiece, vector2 coordinate) {
        //sets the square that the piece is currently on to null.
        setPiece(selectedPiece.getPosition(), null);

        //set the new square to be the piece.
        setPiece(coordinate, selectedPiece);

        //update the piece's position.
        selectedPiece.setPosition(coordinate);

        //if we move a king we need to update the position of the king to track of its position.
        if (selectedPiece.getType() == 'k') {
            //check if the king is moving more than one square so that we can 

            //if the piece's color is white update the white kings position, else update the black kings position.
            if (selectedPiece.getColor() == 'w') {
                whiteKingPosition = coordinate;
            } else {
                blackKingPosition = coordinate;
            }
        }

        //get the new board state string.
        updateBoardStateString();
    }

    /**
     * A method to update the Forsyth Edwards Notation based on the current state of the board array.
     */
    private void updateBoardStateString() {
        //initializing the new board state string.
        String newBoardState;

        //read the board and update the string to represent the board.
        newBoardState = "t";

        //set the bard state.
        boardState = newBoardState;
    }

    /**
     * A method to return the Forsyth Edwards notation board state string variable.
     *
     * @return the Forsyth Edwards notation board state string variable.
     */
    public String getBoardStateString() {
        return boardState;
    }

    /**
     * A method to return a piece on the board in a particular index. represented by a vector2 object.
     *
     * @param coordinate this is the vector2 which represents a position in the board array to access.
     *
     * @return A piece on the board at position coordinate.x, coordinate.y in the board array.
     */
    public piece getPiece(vector2 coordinate) {
        return board[coordinate.x][coordinate.y];
    }

    /**
     * A method which sets a piece on the board to a new piece.
     *
     * @param piecePosition the position of the piece you want to set.
     * @param piece the piece that you want to set it to.
     */
    private void setPiece(vector2 piecePosition, piece piece) {
        board[piecePosition.x][piecePosition.y] = piece;
    }

    /**
     *  A method to return the position of the white king.
     *
     * @return the white kings position in a vector2.
     */
    public vector2 getWhiteKingPosition() {
        return whiteKingPosition;
    }

    /**
     * A method to return the position of the black king.
     *
     * @return the black kings position in a vector2.
     */
    public vector2 getBlackKingPosition() {
        return blackKingPosition;
    }

    /**
     * A method to print out the board in text form to the console window.
     */
    public void printBoardStateToConsole() {
        //loop through the array in reverse so that the board is printed in the correct orientation.
        for (int rank = 7; rank > -1; rank--) {
            for (int file = 0; file < 8; file++) {

                //create the vector2 for the position of the current piece to be printed.
                vector2 boardPosition = new vector2(file, rank);

                //get the piece in the current board position.
                piece piece = getPiece(boardPosition);

                //if the piece is null print a '/' character and continue the loop.
                if (piece == null) {System.out.print("  /  "); continue;}

                //if the piece's color is white print the piece's type in uppercase. else in lower case.
                if (piece.getColor() == 'w') {
                    System.out.print("  " + Character.toUpperCase(piece.getType()) + "  ");
                } else {
                    System.out.print("  " + piece.getType() + "  ");
                }
            }
            //new line to separate each rank.
            System.out.print("\n\n");
        }
    }
}
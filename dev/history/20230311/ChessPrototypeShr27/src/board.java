import pieces.piece;
import vector.vector2;

/**
 * a class that stores the logical representation of a chess board.
 *
 * this class stores the position of each piece on the board.
 *
 * @version 0.1 initial development.
 * @version 0.2 can now calculate legal moves for all pieces of a particular color.
 * @version 0.3 can now find legal moves of all pieces taking into account if the moves will put the player in check.
 * @version 0.4 functionality for calculating the legal moves for a player's piece has been moved to moveCalculator.
 * @version 0.5 functionality for moving all pieces added, including en passant and castling.
 * @version 0.6 functionality for updating the FEN string after a move is made added.
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
    private String forsythEdwardsBoardNotation;

    //array which contains the forsyth edwards notation split up into sections.
    private final String[] forsythEdwardsBoardNotationArray;

    //both king positions.
    private vector2 whiteKingPosition;
    private vector2 blackKingPosition;

    /**
     * constructor for board.
     * initializes the board array.
     */
    public board(String initializingBoardState) {
        board = new piece[BOARD_SIZE][BOARD_SIZE];

        //sets the board state string to the given Forsyth Edwards Notation.
        this.forsythEdwardsBoardNotation = initializingBoardState;

        //splits up the string.
        forsythEdwardsBoardNotationArray = forsythEdwardsBoardNotation.split(" ", 6);

        //initialize the board to the current state represented in the string.
        initializeBoardState();
    }

    /*
     * initializes the pieces in the board based on the Forsyth Edwards Notation string given.
     */
    private void initializeBoardState() {
        //sets starting positions to access the array from.
        int file = 0;
        int rank = 7;

        //get the section of the edwards forsyth notation string which represents the state of the board.
        String boardRepresentation = forsythEdwardsBoardNotationArray[0];

        //iterate through the state of the board string.
        for (int positionInString = 0; positionInString < boardRepresentation.length(); positionInString++) {
            //if we have arrived at a '/', this is the marker for going down a rank, so we decrement the rank and reset the file to the first file.
            if (boardRepresentation.charAt(positionInString) == '/') {file = 0; rank--; continue;}

            //if we have arrived at a digit this is the marker for n amount of empty squares on the rank in a row before we find a piece. so we add this digit to out file variable.
            if (Character.isDigit(boardRepresentation.charAt(positionInString))) { file += Character.getNumericValue(boardRepresentation.charAt(positionInString)); continue;}

            //if we arrive here we need to add a piece to the board. at the position file, rank.
            vector2 boardPosition = new vector2(file, rank);

            //if the character representing the piece is upper case then it is a white piece, else it is a black piece.
            if (Character.isUpperCase(boardRepresentation.charAt(positionInString))) {
                //create a new white piece that is of the same type as the current character in the string, with the position of boardPosition.
                piece piece = new piece('w', boardPosition, Character.toLowerCase(boardRepresentation.charAt(positionInString)));
                setPiece(boardPosition, piece);

                //if the piece is a king, set the position of the white king to this piece's position.
                if (Character.toLowerCase(boardRepresentation.charAt(positionInString)) == 'k'){
                    whiteKingPosition = boardPosition;
                }
            } else {
                //create a new black piece that is of the same type as the current character in the string, with the position of boardPosition.
                piece piece = new piece('b', boardPosition, boardRepresentation.charAt(positionInString));
                setPiece(boardPosition, piece);

                //if the piece is a king, set the position of the black king to this piece's position.
                if (boardRepresentation.charAt(positionInString) == 'k'){
                    blackKingPosition = boardPosition;
                }
            }
            //increment the file for the next position on the board.
            file++;
        }
    }


    /**
     * A method to move a piece on the board, while taking into account special moves.
     *
     * @param selectedPiece the selected piece to move.
     * @param move the vector2 coordinate to move the piece to.
     */
    public void movePiece(piece selectedPiece, vector2 move) {
        //if the piece is a pawn, need to do checking for "en passant".
        if (selectedPiece.getType() == 'p') {
            movePawn(selectedPiece, move);
        }
        //if the piece is a king, need to do checking for castling and make sure we keep track of the king position.
        else if (selectedPiece.getType() == 'k') {
            moveKing(selectedPiece, move);
            //set the en passant capture square to "-".
            forsythEdwardsBoardNotationArray[3] = "-";
        }
        //if a rook is moving for the first time, need to update the castling notation.
        else if (selectedPiece.getType() == 'r' && !selectedPiece.hasMoved()) {
            moveRook(selectedPiece, move);
            //set the en passant capture square to "-".
            forsythEdwardsBoardNotationArray[3] = "-";
        }
        //if the piece is neither a king nor a pawn there are no special moves to take into account, so, just move it.
        else {
            updatePiecePositionInArray(selectedPiece, move);
            //set the en passant capture square to "-".
            forsythEdwardsBoardNotationArray[3] = "-";
        }

        //get the new board state string, after the move has been played.
        updateForsythEdwardsBoardNotation();
    }

    /*
    / Since the pawn has 2 unique moves, en passant, and promotion.
     */
    private void movePawn(piece pawn, vector2 move) {
        //get the section of the forsyth edwards string notation for en passant.
        String enPassantString = forsythEdwardsBoardNotationArray[3];

        //if the move that the pawn is taking is to the square represented by the string then, capture the pawn under/above it.
        if (move.getVector2AsBoardNotation().equals(enPassantString)) {
            //if the pawn is white capture the pawn below it.
            if (pawn.getColor() == 'w') {
                capturePiece(new vector2(move.x,move.y - 1));
            }
            //if the pawn is black capture the pawn above it.
            else {
                capturePiece(new vector2(move.x,move.y + 1));
            }
            forsythEdwardsBoardNotationArray[3] = "-";
            updatePiecePositionInArray(pawn, move);
            return;
        }

        //if a white pawn is moving 2 squares
        if (move.y - pawn.getPosition().y == 2) {
            enPassantString = new vector2(move.x, move.y - 1).getVector2AsBoardNotation();
            forsythEdwardsBoardNotationArray[3] = enPassantString;
        }
        // if a black pawn is moving 2 squares
        else if (move.y - pawn.getPosition().y == -2) {
            enPassantString = new vector2(move.x, move.y + 1).getVector2AsBoardNotation();
            forsythEdwardsBoardNotationArray[3] = enPassantString;
        }
        // if the pawn is not moving 2 squares.
        else {
            forsythEdwardsBoardNotationArray[3] = "-";
        }

        //move the pawn.
        updatePiecePositionInArray(pawn, move);
    }

    /*
    / Since the king has a unique move, castling.
    */
    private void moveKing(piece king, vector2 move) {
        //get the castling notation.
        String castlingNotation = forsythEdwardsBoardNotationArray[2];

        //if the king is moving more than one square it is castling, move the rook, on the correct side.
        if(move.x > king.getPosition().x + 1 || move.x < king.getPosition().x - 1) {
            //move king side rook.
            piece rook;
            vector2 newRookPosition;

            //find out which rook needs to move, and to where.
            if (move.x > king.getPosition().x + 1) {
                //get the king side rook if the king is moving to the right.
                rook = getPiece(new vector2(0, 7));

                //get the new rook position.
                newRookPosition = new vector2(king.getPosition().x + 1, king.getPosition().y);
            } else {
                //get the queen side rook if the king is moving to the left.
                rook = getPiece(new vector2(0, 0));

                //get the new rook position.
                newRookPosition = new vector2(king.getPosition().x - 1, king.getPosition().y);
            }

            //update the rook's position.
            if (rook != null) {
                updatePiecePositionInArray(rook, newRookPosition);
            }
        }

        //update the king's position.
        updatePiecePositionInArray(king, move);

        //if the piece's color is white update the white kings position, else update the black kings position.
        if (king.getColor() == 'w') {
            whiteKingPosition = move;
            //update the castling notation for white.
            forsythEdwardsBoardNotationArray[2] = castlingNotation.replaceAll("[A-Z]", "");
        } else {
            blackKingPosition = move;
            //update the castling notation for black.
            forsythEdwardsBoardNotationArray[2] = castlingNotation.replaceAll("[a-z]", "");
        }
    }

    /*
    / method for moving rooks since we need to update the castling notation when a rook moves.
    */
    private void moveRook(piece rook, vector2 move) {
        //get the castling notation.
        String castlingNotation = forsythEdwardsBoardNotationArray[2];

        //if it's a white rook need to remove capital letters in the castling notation.
        if (rook.getColor() == 'w') {
            //if it's the queen side rook moving.
            if (rook.getPosition().x == 0) {
                forsythEdwardsBoardNotationArray[2] = castlingNotation.replaceAll("Q", "");
            }
            //if it's the king side rook moving.
            else {
                forsythEdwardsBoardNotationArray[2] = castlingNotation.replaceAll("K", "");
            }
        } else {
            //if it's the queen side rook moving.
            if (rook.getPosition().x == 0) {
                forsythEdwardsBoardNotationArray[2] = castlingNotation.replaceAll("q", "");
            }
            //if it's the king side rook moving.
            else {
                forsythEdwardsBoardNotationArray[2] = castlingNotation.replaceAll("k", "");
            }
        }

        //move the rook.
        updatePiecePositionInArray(rook, move);
    }


    /*
    / method to essentially move a piece from one array index to another.
     */
    private void updatePiecePositionInArray(piece piece,vector2 newPiecePosition) {
        //set the old position in the array to null.
        setPiece(piece.getPosition(), null);

        //if there is a piece on the square that the piece is moving to then it is capturing it.
        if (getPiece(newPiecePosition) != null) {capturePiece(newPiecePosition);}

        //move the piece in the array.
        setPiece(newPiecePosition, piece);

        //update the piece's internal position value.
        piece.setPosition(newPiecePosition);

        //say that the piece has now moved.
        piece.setHasMoved(true);
    }

    /*
    / method for capturing pieces.
     */
    private void capturePiece(vector2 piecePosition) {
        //play sound for capturing?

        //update captured pieces array/string.
        setPiece(piecePosition, null);
    }


    /*
     * A method to update the Forsyth Edwards Notation based on the current state of the board array, and game.
     */
    private void updateForsythEdwardsBoardNotation() {
        //update the board string. represented by forsythEdwardsBoardNotationArray[0].
        //create a new string builder.
        StringBuilder newBoardRepresentationString = new StringBuilder("");
        //variable to keep track of how many empty spaces there have been.
        int skippedPieces = 0;
        //loop through the array backwards.
        for (int rank = 7; rank > -1; rank--) {
            if (skippedPieces > 0) {
                newBoardRepresentationString.append(skippedPieces);
                skippedPieces = 0;
            }
            if (rank != 7) {
                newBoardRepresentationString.append("/");
            }
            for (int file = 0; file < BOARD_SIZE; file++) {
                piece currentPiece = getPiece(new vector2(file, rank));
                if (currentPiece == null) {
                    skippedPieces += 1;
                    continue;
                } else if (skippedPieces > 0) {
                    newBoardRepresentationString.append(skippedPieces);
                    skippedPieces = 0;
                }
                if (currentPiece.getColor() == 'w') {
                    newBoardRepresentationString.append(Character.toUpperCase(currentPiece.getType()));
                } else {
                    newBoardRepresentationString.append(currentPiece.getType());
                }
            }
        }
        forsythEdwardsBoardNotationArray[0] = newBoardRepresentationString.toString();

        //update the current player string. represented by forsythEdwardsBoardNotationArray[1].
        if (forsythEdwardsBoardNotationArray[1].equals("w")) {
            forsythEdwardsBoardNotationArray[1] = "b";
        } else {
            forsythEdwardsBoardNotationArray[1] = "w";
        }

        //if the castling notation is empty, forsythEdwardsBoardNotationArray[2], then replace it with the '-' character.
        if (forsythEdwardsBoardNotationArray[2].equals("")) {
            forsythEdwardsBoardNotationArray[2] = "-";
        }

        //en passant represented by forsythEdwardsBoardNotationArray[3], should already be updated by this point.

        //the half move clock represented by forsythEdwardsBoardNotationArray[4].

        //update the full move number represented by forsythEdwardsBoardNotationArray[5], by incrementing it by 1.
        forsythEdwardsBoardNotationArray[5] = Integer.toString(Integer.parseInt(forsythEdwardsBoardNotationArray[5]) + 1);

        //initializing the new board state stringBuilder.
        StringBuilder newBoardState = new StringBuilder();

        //loop over the forsythEdwardsBoardNotationArray and add each part separated by a " " to the string builder.
        for (int i = 0; i < forsythEdwardsBoardNotationArray.length; i++) {
            //add the section of the forsythEdwardsBoardNotationArray.
            newBoardState.append(forsythEdwardsBoardNotationArray[i]);

            //if we are not at the end of the array then separate each part with a " " character.
            if (i != forsythEdwardsBoardNotationArray.length - 1) {
                newBoardState.append(" ");
            }
        }
        //set the board state.
        forsythEdwardsBoardNotation = newBoardState.toString();
        //System.out.println(forsythEdwardsBoardNotation);
    }


    /**
     * A method to return the boards current forsyth edwards board notation.
     *
     * @return forsythEdwardsBoardNotation.
     */
    public String getForsythEdwardsBoardNotation() {
        return forsythEdwardsBoardNotation;
    }

    /**
     * A method to return a section of the Forsyth Edwards notation board state string variable.
     *
     * @param index which section of the string.
     * @return the section of the string.
     */
    public String getForsythEdwardsBoardNotationArrayIndex(int index) {
        return forsythEdwardsBoardNotationArray[index];
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

    /*
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
import pieces.piece;
import vector.vector2;
import java.util.HashMap;
import java.util.Map;

/**
 * a class that stores the logical representation of a chess board.
 *
 * this class stores the position of each piece on the board.
 * this class calculates the legal moves of all the pieces on the board.
 *
 * @version 1.0 initial development.
 * @version 1.1 can now calculate legal moves for all pieces of a particular color.
 * @version 1.2 can now find legal moves of all pieces taking into account if the moves will put the player in check.
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
                setPieceOnBoard(boardPosition, piece);

                //if the piece is a king, set the position of the white king to this piece's position.
                if (Character.toLowerCase(boardState.charAt(positionInString)) == 'k'){
                    whiteKingPosition = boardPosition;
                }
            } else {
                //create a new black piece that is of the same type as the current character in the string, with the position of boardPosition.
                piece piece = new piece('b', boardPosition, boardState.charAt(positionInString));
                setPieceOnBoard(boardPosition, piece);

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
    private void setPieceOnBoard(vector2 piecePosition, piece piece) {
        board[piecePosition.x][piecePosition.y] = piece;
    }

    /**
     * A method to move a piece on the board.
     *
     * @param selectedPiece the selected piece to move.
     * @param coordinate the vector2 coordinate to move the piece to.
     */
    public void movePiece(piece selectedPiece, vector2 coordinate) {
        //sets the square that the piece is currently on to null.
        setPieceOnBoard(selectedPiece.getPosition(), null);

        //set the new square to be the piece.
        setPieceOnBoard(coordinate, selectedPiece);

        //update the piece's position.
        selectedPiece.setPosition(coordinate);

        //if we move a king we need to update the position of the king to track of its position.
        if (selectedPiece.getType() == 'k') {

            //if the piece's color is white update the white kings position, else update the black kings position.
            if (selectedPiece.getColor() == 'w') {
                whiteKingPosition = coordinate;
            } else {
                blackKingPosition = coordinate;
            }
        }

        //get the new board state string.
        updateBoardState();
    }

    /**
     * A method to update the Forsyth Edwards Notation based on the current state of the board array.
     */
    private void updateBoardState() {
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
    public String getBoardState() {
        return boardState;
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

    /**
     * A method which finds the legal moves for each piece of a given color.
     *
     * @param player the player's color, 'w' for white, 'b' for black.
     */
    public void findLegalMovesForPlayer(char player) {
        //loop over piece in the board array.
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                //create the current board position vector2.
                vector2 piecePosition = new vector2(file, rank);

                //get the piece at the current board position.
                piece piece = getPiece(piecePosition);

                //if the piece is null, skip and look at the next piece.
                if (piece == null) {continue;}

                //if the piece is not the correct color, skip and look at the next piece.
                if (piece.getColor() != player) {continue;}

                //check what the piece's type is: 'p' for pawn, 'n' for knight, 'r' for rook, 'b' for bishop, 'q' for queen, 'k' for king.
                switch (piece.getType()) {
                    case 'p':
                        System.out.println("looking for pawn moves");
                        //calculate the legal moves for the pawn.
                        getPawnLegalMoves(piece, false);
                        break;
                    case 'n':
                        System.out.println("looking for knight moves");
                        //calculate the legal moves for the knight.
                        getKnightLegalMoves(piece, false);
                        break;
                    case 'b':
                        System.out.println("looking for bishop moves");
                        //calculate the legal moves for the bishop.
                        getBishopLegalMoves(piece, false);
                        break;
                    case 'r':
                        System.out.println("looking for rook moves");
                        //calculate the legal moves for the rook.
                        getRookLegalMoves(piece, false);
                        break;
                    case 'q':
                        System.out.println("looking for queen moves");
                        //calculate the legal moves for the queen.
                        getQueenLegalMoves(piece, false);
                        break;
                    case 'k':
                        System.out.println("looking for king moves");
                        //calculate the legal moves for the king.
                        getKingLegalMoves(piece, false);
                        break;
                }
            }
        }
    }

    /**
     * This method calculates and sets the legal moves of any given pawn on the board.
     *
     * @param pawn the pawn to calculate the moves for.
     * @param isForCheckMap boolean to see if the method is being used to create a check map, if so we do not need to check if the moves put the player in check.
     */
    private void getPawnLegalMoves(piece pawn, boolean isForCheckMap) {
        //calculate the single advance square position. the y should be positive for white pieces and negative for black pieces.
        vector2 singleAdvance = new vector2(pawn.getPosition().x, ((pawn.getColor() == 'w') ? pawn.getPosition().y + 1 : pawn.getPosition().y - 1));

        //calculate the double advance square position. the y should be positive for white pieces and negative for black pieces.
        vector2 doubleAdvance = new vector2(pawn.getPosition().x, ((pawn.getColor() == 'w') ? singleAdvance.y + 1 : singleAdvance.y - 1));

        //calculate left and right attack square positions.
        vector2 leftAttack = new vector2(singleAdvance.x - 1, singleAdvance.y);
        vector2 rightAttack = new vector2(singleAdvance.x + 1, singleAdvance.y);

        //check if the single advance is a valid position on the board. and that there are no pieces occupying it.
        if (singleAdvance.x <= 7 && singleAdvance.y <= 7 && singleAdvance.x >= 0 && singleAdvance.y >= 0 && getPiece(singleAdvance) == null) {
            addPieceLegalMove(pawn, singleAdvance, isForCheckMap);

            //check if there is a piece on the double advance square and that the pawn has not moved before.
            if (getPiece(doubleAdvance) == null && !pawn.has_moved()) {
                addPieceLegalMove(pawn, doubleAdvance, isForCheckMap);
            }
        }

        //check if the left attack is a valid position on the board.
        if (leftAttack.x >= 0 && leftAttack.x <= 7) {

            //check if the piece on the left attack square is not the same color as the pawn.
            if (getPiece(leftAttack) != null && getPiece(leftAttack).getColor() != pawn.getColor()) {
                addPieceLegalMove(pawn, leftAttack, isForCheckMap);
            }
        }

        //check if the right attack is a valid position on the board.
        if (rightAttack.x >= 0 && rightAttack.x <= 7) {

            //check if the piece on the left attack square is not the same color as the pawn.
            if (getPiece(rightAttack) == null && getPiece(rightAttack).getColor() != pawn.getColor()) {
                addPieceLegalMove(pawn, rightAttack, isForCheckMap);
            }
        }
    }

    /**
     * This method calculates and sets the legal moves of any given knight on the board.
     *
     * @param knight the knight to calculate the  moves for.
     * @param isForCheckMap boolean to see if the method is being used to create a check map, if so we do not need to check if the moves put the player in check.
     */
    private void getKnightLegalMoves(piece knight, boolean isForCheckMap) {
        //these are the knights possible jumps, in vector2s relative to the knights position. positive values add to the knights current x or y, negative subtracts.
        vector2[] knightDirections = { new vector2(2, 1),  new vector2(1, 2), new vector2(-2, 1), new vector2(-1, 2), new vector2(2, -1), new vector2(1, -2), new vector2(-2, -1), new vector2(-1, -2)};

        //for each of the directions in the knight's directions.
        for (vector2 d : knightDirections) {

            //find what the move square is.
            vector2 move = new vector2(knight.getPosition().x + d.x, knight.getPosition().y + d.y);

            //if the move square is outside the board, skip to the next direction.
            if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {continue;}

            //if the move square is empty (null) or if there is an opponents piece on that square, check if that move will put the player in check.
            if (getPiece(move) == null || getPiece(move).getColor() != knight.getColor()) {
                addPieceLegalMove(knight, move, isForCheckMap);
            }
        }
    }

    /**
     * This method calculates and sets the legal moves of any given bishop on the board.
     *
     * @param bishop the bishop to calculate the moves for.
     * @param isForCheckMap boolean to see if the method is being used to create a check map, if so we do not need to check if the moves put the player in check.
     */
    private void getBishopLegalMoves(piece bishop, boolean isForCheckMap) {
        //the bishop's directions: north-west, north-east, south-west, south-east.
        vector2[] bishopDirections = { new vector2(1, 1), new vector2(-1, -1), new vector2(1, -1), new vector2(-1, 1)};

        //for every direction in the bishop's directions.
        for (vector2 d : bishopDirections) {

            //for n squares in that direction until the bishop is blocked. maximum 8 squares.
            for (int n = 1; n < BOARD_SIZE; n++) {

                //find out which square the move is.
                vector2 move = new vector2(bishop.getPosition().x + (d.x * n), bishop.getPosition().y + (d.y * n) );

                //if the move square is outside the board, skip to the next direction.
                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {break;}

                if (getPiece(move) == null) {
                    addPieceLegalMove(bishop, move, isForCheckMap);
                } else if (!(getPiece(move).getColor() == bishop.getColor())) {
                    addPieceLegalMove(bishop, move, isForCheckMap);
                    break;
                } else if ((getPiece(move).getColor() == bishop.getColor())) {
                    break;
                }
            }
        }
    }

    /**
     * This method calculates and sets the legal moves of any given rook on the board.
     *
     * @param rook the rook to calculate the moves for.
     * @param isForCheckMap boolean to see if the method is being used to create a check map, if so we do not need to check if the moves put the player in check.
     */
    private void getRookLegalMoves(piece rook, boolean isForCheckMap) {
        //the rook's directions: north, east, west, south.
        vector2[] rookDirections = {new vector2(0, 1),  new vector2(0, -1), new vector2(1, 0), new vector2(-1, 0)};

        //for every direction for the rook.
        for (vector2 d : rookDirections) {

            //for n squares in that direction until the rook is blocked. maximum 8 squares.
            for (int n = 1; n < BOARD_SIZE; n++) {

                //find out which square the move is.
                vector2 move = new vector2(rook.getPosition().x + (d.x * n), rook.getPosition().y + (d.y * n));

                //if the move square is outside the board, skip to the next direction.
                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {break;}

                if (getPiece(move) == null) {
                    addPieceLegalMove(rook, move, isForCheckMap);
                } else if (!(getPiece(move).getColor() == rook.getColor())) {
                    addPieceLegalMove(rook, move, isForCheckMap);
                    break;
                } else if ((getPiece(move).getColor() == rook.getColor())) {
                    break;
                }
            }
        }
    }

    /**
     * This method calculates and sets the legal moves of any given queen on the board.
     *
     * @param queen the queen to calculate ethe moves for.
     * @param isForCheckMap boolean to see if the method is being used to create a check map, if so we do not need to check if the moves put the player in check.
     */
    private void getQueenLegalMoves(piece queen, boolean isForCheckMap) {
        //the queen's directions: north-west, north-east, south-west, south-east, north, south, east, west.
        vector2[] queenDirections = {new vector2(0, 1),  new vector2(0, -1), new vector2(1, 0), new vector2(-1, 0), new vector2(1, 1), new vector2(-1, -1), new vector2(1, -1), new vector2(-1, 1)};

        //for each direction in the queen's directions.
        for (vector2 d : queenDirections) {

            //for n squares in that direction until the queen is blocked. maximum 8 squares.
            for (int n = 1; n < BOARD_SIZE; n++) {

                //find out which square the move is.
                vector2 move = new vector2(queen.getPosition().x + (d.x * n), queen.getPosition().y + (d.y * n));

                //if the move square is outside the board, skip to the next direction.
                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {break;}

                if (getPiece(move) == null) {
                    addPieceLegalMove(queen, move, isForCheckMap);
                } else if (!(getPiece(move).getColor() == queen.getColor())) {
                    addPieceLegalMove(queen, move, isForCheckMap);
                    break;
                } else if ((getPiece(move).getColor() == queen.getColor())) {
                    break;
                }
            }
        }
    }

    /**
     * This method calculates and sets the legal moves of any given king on the board.
     *
     * @param king the king to calculate the moves for
     * @param isForCheckMap boolean to see if the method is being used to create a check map, if so we do not need to check if the moves put the player in check.
     */
    private void getKingLegalMoves(piece king, boolean isForCheckMap) {
        //the king's directions: north-west, north-east, south-west, south-east, north, south, east, west.
        vector2[] kingDirections = {new vector2(0, 1),  new vector2(0, -1), new vector2(1, 0), new vector2(-1, 0), new vector2(1, 1), new vector2(-1, -1), new vector2(1, -1), new vector2(-1, 1)};

        //for each direction in the king's directions'
        for (vector2 d : kingDirections) {

            //find out which square the move is.
            vector2 move = new vector2(king.getPosition().x + (d.x), king.getPosition().y + (d.y));

            //if the move square is outside the board, skip to the next direction.
            if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {continue;}

            if (getPiece(move) == null) {
                addPieceLegalMove(king, move, isForCheckMap);
            } else if (!(getPiece(move).getColor() == king.getColor())) {
                addPieceLegalMove(king, move, isForCheckMap);
            }
        }
    }

    /**
     *
     * @param p
     * @param move
     * @param check_map
     */
    private void addPieceLegalMove(piece p, vector2 move, boolean check_map) {
        if (check_map) {
            p.add_move(move);
        } else if (isMoveSafe(p, move)) {
            p.add_move(move);
        }
    }

    /**
     *
     * @param piece
     * @param move
     *
     * @return
     */
    private boolean isMoveSafe(piece piece, vector2 move) {
        board projectionOfMove = new board();
        projectionOfMove.initializeBoardState(this.getBoardState());
        projectionOfMove.movePiece(projectionOfMove.getPiece(piece.getPosition()), move);
        return !projectionOfMove.isPlayerInCheck(piece.getColor());
    }

    /**
     *
     * @param player
     *
     * @return
     */
    private boolean isPlayerInCheck(char player) {
        Map<vector2, vector2> checkMap = createCheckMap(player);
        if (player == 'w' && checkMap.containsValue(whiteKingPosition)) {
            return true;
        } else return player == 'b' && checkMap.containsValue(blackKingPosition);
    }

    /**
     *
     * @param player
     *
     * @return
     */
    private Map<vector2, vector2> createCheckMap(char player) {
        //initializing the map.
        Map<vector2, vector2> checkMap = new HashMap<>();

        //create the check map.
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                if (getPiece(new vector2(file, rank)) == null) {continue;}
                piece piece = getPiece(new vector2(file, rank));
                if (piece.getColor() == player) {continue;}

                //System.out.println("looking for piece attacking squares (" + piece.get_type() + ").");

                switch (piece.getType()) {
                    case 'p' :
                        getPawnLegalMoves(piece, true);
                        break;
                    case 'n' :
                        getKnightLegalMoves(piece, true);
                        break;
                    case 'b' :
                        getBishopLegalMoves(piece, true);
                        break;
                    case 'r' :
                        getRookLegalMoves(piece, true);
                        break;
                    case 'q' :
                        getQueenLegalMoves(piece, true);
                        break;
                    case 'k' :
                        getKingLegalMoves(piece, true);
                }
                //add the piece's attacked squares to the check map.
                for (vector2 attackedSquare : piece.getPossibleMoves()) {
                    checkMap.put(attackedSquare, attackedSquare);
                }
            }
        }
        //update the threatened squares.
        return checkMap;
    }
}
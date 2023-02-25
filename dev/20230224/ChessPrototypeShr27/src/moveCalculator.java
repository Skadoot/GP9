import pieces.piece;
import vector.vector2;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a class that calculates the legal moves for the all the pieces of a particular color for a particular board position.
 *
 * @version 1.0 can calculate moves for all the pieces of a given board and color.
 *
 * @see board
 *
 * @author shr27@aber.ac.uk
 */
public class moveCalculator {

    //the player that you want to calculate the moves for.
    private final char player;

    //the board that you want to calculate the moves for.
    private final board board;

    /**
     * constructor for moveCalculator
     *
     * @param player the player that you wish to calculate the moves for.
     * @param board the board that you want to calculate the moves for.
     */
    public moveCalculator(char player, board board) {
        this.player = player;
        this.board = board;
    }

    /**
     * A method which finds the legal moves for each piece for the player making the move.
     *
     */
    public void findLegalMovesForPlayer() {
        //loop over every piece in the board array.
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                //create the current board position vector2.
                vector2 piecePosition = new vector2(file, rank);

                //get the piece at the current board position.
                piece piece = board.getPiece(piecePosition);

                //if the piece is null, skip and look at the next piece.
                if (piece == null) {continue;}

                //if the piece is not the correct color, skip and look at the next piece.
                if (piece.getColor() != player) {continue;}

                //check what the piece's type is and calculate its legal moves: 'p' for pawn, 'n' for knight, 'r' for rook, 'b' for bishop, 'q' for queen, 'k' for king.
                switch (piece.getType()) {
                    case 'p':
                        getPawnLegalMoves(piece, false);
                        break;
                    case 'n':
                        getKnightLegalMoves(piece, false);
                        break;
                    case 'b':
                        getBishopLegalMoves(piece, false);
                        break;
                    case 'r':
                        getRookLegalMoves(piece, false);
                        break;
                    case 'q':
                        getQueenLegalMoves(piece, false);
                        break;
                    case 'k':
                        getKingLegalMoves(piece, false);
                        break;
                }
            }
        }
    }

    /**
     * A method which creates a map of all unsafe squares for the player.
     *
     * @return a Map of all attacked squares.
     */
    private Map<vector2, vector2> createCheckMap() {
        //initializing the map.
        Map<vector2, vector2> checkMap = new HashMap<>();

        //loop over every piece in the board.
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                //create the current board position vector2.
                vector2 piecePosition = new vector2(file, rank);

                //get the piece at the current board position.
                piece piece = board.getPiece(piecePosition);

                //if the piece is null, skip and look at the next piece.
                if (piece == null) {continue;}

                //if the piece is not the correct color, skip and look at the next piece.
                if (piece.getColor() == player) {continue;}

                //check what the piece's type is: 'p' for pawn, 'n' for knight, 'r' for rook, 'b' for bishop, 'q' for queen, 'k' for king.
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
        if (singleAdvance.x <= 7 && singleAdvance.y <= 7 && singleAdvance.x >= 0 && singleAdvance.y >= 0 && board.getPiece(singleAdvance) == null) {
            addPieceLegalMove(pawn, singleAdvance, isForCheckMap);

            //check if there is a piece on the double advance square and that the pawn has not moved before.
            if (board.getPiece(doubleAdvance) == null && !pawn.hasMoved()) {
                addPieceLegalMove(pawn, doubleAdvance, isForCheckMap);
            }
        }

        //check if the left attack is a valid position on the board.
        if (leftAttack.x >= 0 && leftAttack.x <= 7) {

            //check if the piece on the left attack square is not the same color as the pawn.
            if (board.getPiece(leftAttack) != null && board.getPiece(leftAttack).getColor() != pawn.getColor()) {
                addPieceLegalMove(pawn, leftAttack, isForCheckMap);
            }
        }

        //check if the right attack is a valid position on the board.
        if (rightAttack.x >= 0 && rightAttack.x <= 7 && !(board.getPiece(rightAttack) == null)) {

            //check if the piece on the left attack square is not the same color as the pawn.
            if (board.getPiece(rightAttack).getColor() != pawn.getColor()) {
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
            if (board.getPiece(move) == null || board.getPiece(move).getColor() != knight.getColor()) {
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
            for (int n = 1; n < 8; n++) {

                //find out which square the move is.
                vector2 move = new vector2(bishop.getPosition().x + (d.x * n), bishop.getPosition().y + (d.y * n) );

                //if the move square is outside the board, skip to the next direction.
                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {break;}

                if (board.getPiece(move) == null) {
                    addPieceLegalMove(bishop, move, isForCheckMap);
                } else if (!(board.getPiece(move).getColor() == bishop.getColor())) {
                    addPieceLegalMove(bishop, move, isForCheckMap);
                    break;
                } else if ((board.getPiece(move).getColor() == bishop.getColor())) {
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

        //for every direction in the rook's directions.
        for (vector2 d : rookDirections) {

            //for n squares in that direction until the rook is blocked. maximum 8 squares.
            for (int n = 1; n < 8; n++) {

                //find out which square the move is.
                vector2 move = new vector2(rook.getPosition().x + (d.x * n), rook.getPosition().y + (d.y * n));

                //if the move square is outside the board, skip to the next direction.
                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {break;}

                if (board.getPiece(move) == null) {
                    addPieceLegalMove(rook, move, isForCheckMap);
                } else if (!(board.getPiece(move).getColor() == rook.getColor())) {
                    addPieceLegalMove(rook, move, isForCheckMap);
                    break;
                } else if ((board.getPiece(move).getColor() == rook.getColor())) {
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
            for (int n = 1; n < 8; n++) {

                //find out which square the move is.
                vector2 move = new vector2(queen.getPosition().x + (d.x * n), queen.getPosition().y + (d.y * n));

                //if the move square is outside the board, skip to the next direction.
                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {break;}

                if (board.getPiece(move) == null) {
                    addPieceLegalMove(queen, move, isForCheckMap);
                } else if (!(board.getPiece(move).getColor() == queen.getColor())) {
                    addPieceLegalMove(queen, move, isForCheckMap);
                    break;
                } else if ((board.getPiece(move).getColor() == queen.getColor())) {
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

        //for each direction in the king's directions.
        for (vector2 d : kingDirections) {

            //find out which square the move is.
            vector2 move = new vector2(king.getPosition().x + (d.x), king.getPosition().y + (d.y));

            //if the move square is outside the board, skip to the next direction.
            if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {continue;}

            if (board.getPiece(move) == null) {
                addPieceLegalMove(king, move, isForCheckMap);
            } else if (!(board.getPiece(move).getColor() == king.getColor())) {
                addPieceLegalMove(king, move, isForCheckMap);
            }
        }
    }

    /**
     * A method which adds a single move to the legal move arrayList of a piece.
     *
     * @param piece the piece we want to add the move to
     * @param move the move that we want to add
     * @param isForCheckMap if this is true then we can ignore weather or not the move will leave us in check or not since we are creating the check map.
     */
    private void addPieceLegalMove(piece piece, vector2 move, boolean isForCheckMap) {
        if (isForCheckMap) {
            piece.addMove(move);
        } else if (isMoveSafe(piece, move)) {
            piece.addMove(move);
        }
    }

    /**
     * this is a method that determines if a move would leave the player in check or not.
     *
     * @param piece the piece to move.
     * @param move the move to assess.
     *
     * @return a boolean which determines if the move will leave the player in check or not.
     */
    private boolean isMoveSafe(piece piece, vector2 move) {
        board projectionOfMove = new board();
        projectionOfMove.initializeBoardState(board.getBoardStateString());
        projectionOfMove.movePiece(projectionOfMove.getPiece(piece.getPosition()), move);

        moveCalculator moveCalculator = new moveCalculator(piece.getColor(), projectionOfMove);
        return !moveCalculator.isPlayerInCheck(piece.getColor());
    }

    /**
     * a method which determines if the player is in check in the board position or not.
     *
     * @param player the player that we want to check if they are in check or not.
     *
     * @return a boolean which determines if the player is in check in the board position or not.
     */
    private boolean isPlayerInCheck(char player) {
        Map<vector2, vector2> checkMap = createCheckMap();
        if (player == 'w' && checkMap.containsValue(board.getWhiteKingPosition())) {
            return true;
        } else return player == 'b' && checkMap.containsValue(board.getBlackKingPosition());
    }
}

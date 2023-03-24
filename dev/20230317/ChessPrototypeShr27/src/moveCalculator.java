import pieces.piece;
import vector.vector2;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This is a class that calculates the legal moves for the all the pieces of a particular color for a particular board position.
 *
 * @version 0.1 can calculate moves for all the pieces of a given board and color.
 * @version 0.2 added castling to king moves.
 *
 * @see board
 *
 * @author shr27@aber.ac.uk
 */
public class moveCalculator {
    //the player that you want to calculate the moves for.
    private final char currentPlayer;

    //the board that you want to calculate the moves for.
    private final board board;

    //can white castle king side?
    private boolean canWhiteCastleKingSide;

    //can white castle queen side?
    private boolean canWhiteCastleQueenSide;

    //can black castle king side?
    private boolean canBlackCastleKingSide;

    //can black castle queen side?
    private boolean canBlackCastleQueenSide;

    //the map of threatened squares
    private final ArrayList<vector2> checkMap = new ArrayList<>();

    /**
     * constructor for moveCalculator
     *
     * @param player the player that you wish to calculate the moves for.
     * @param board the board that you want to calculate the moves for.
     */
    public moveCalculator(char player, board board) {
        this.currentPlayer = player;
        this.board = board;

        //determine who can castle, so we can take this into account when calculating the legal moves.
        determineWhoCanCastle();
    }



    /**
     * A method which finds the legal moves for each piece for the player making the move.
     */
    public void findLegalMovesForPlayer(boolean opponentPlayer) {
        //loop over every piece in the board array.
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                //create the current board position vector2.
                vector2 piecePosition = new vector2(file, rank);

                //get the piece at the current board position.
                piece piece = board.getPiece(piecePosition);

                //if the piece is null, skip and look at the next piece.
                if (piece == null) {continue;}

                //if the piece is not the color of the current player, then we are using this piece's moves to create the list of all unsafe squares.
                if (piece.getColor() == currentPlayer && opponentPlayer) {continue;}
                if (piece.getColor() != currentPlayer && !opponentPlayer) {continue;}

                //check what the piece's type is and calculate its legal moves: 'p' for pawn, 'n' for knight, 'r' for rook, 'b' for bishop, 'q' for queen, 'k' for king.
                switch (piece.getType()) {
                    case 'p':
                        getPawnLegalMoves(piece, opponentPlayer);
                        break;
                    case 'n':
                        getKnightLegalMoves(piece, opponentPlayer);
                        break;
                    case 'b':
                        getBishopLegalMoves(piece, opponentPlayer);
                        break;
                    case 'r':
                        getRookLegalMoves(piece, opponentPlayer);
                        break;
                    case 'q':
                        getQueenLegalMoves(piece, opponentPlayer);
                        break;
                    case 'k':
                        getKingLegalMoves(piece, opponentPlayer);
                        break;
                }
                //if we used the piece to find unsafe squares add the piece's legal moves to the checkMap.
                if (opponentPlayer) {
                    checkMap.addAll(piece.getPossibleMoves());
                }
            }
        }
    }



    /*
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
        if (!isForCheckMap) {
            if (singleAdvance.x <= 7 && singleAdvance.y <= 7 && singleAdvance.x >= 0 && singleAdvance.y >= 0 && board.getPiece(singleAdvance) == null) {
                addPieceLegalMove(pawn, singleAdvance, false);

                //check if there is a piece on the double advance square and that the pawn has not moved before.
                if (board.getPiece(doubleAdvance) == null && !pawn.hasMoved()) {
                    addPieceLegalMove(pawn, doubleAdvance, false);
                }
            }
        }

        //check if the left attack is a valid position on the board.
        if ((leftAttack.x > -1)) {
            if (board.getPiece(leftAttack) != null || isForCheckMap) {
                if (isForCheckMap) {
                    addPieceLegalMove(pawn, leftAttack, true);
                }
                //check if the piece on the left attack square is not the same color as the pawn.
                else if (board.getPiece(leftAttack).getColor() != pawn.getColor() || leftAttack.getVector2AsBoardNotation().equals(board.getForsythEdwardsBoardNotationArrayIndex(3))) {
                    addPieceLegalMove(pawn, leftAttack, false);
                }
            }
        }

        //check if the right attack is a valid position on the board.
        if ((rightAttack.x < 8)) {
            if (board.getPiece(rightAttack) != null || isForCheckMap) {
                if (isForCheckMap) {
                    addPieceLegalMove(pawn, rightAttack, true);
                }
                //check if the piece on the left attack square is not the same color as the pawn.
                else if (board.getPiece(rightAttack).getColor() != pawn.getColor() || rightAttack.getVector2AsBoardNotation().equals(board.getForsythEdwardsBoardNotationArrayIndex(3))) {
                    addPieceLegalMove(pawn, rightAttack, false);
                }
            }
        }

        if (isForCheckMap) {return;}
        System.out.print("legal moves for " + pawn.getColor() + " " + "pawn" + " at " + pawn.getPosition().getVector2AsBoardNotation());
        System.out.print(" | ");
        for (vector2 square: pawn.getPossibleMoves()) {
            System.out.print(square.getVector2AsBoardNotation() + " ");
        }
        System.out.println();
    }

    /*
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
        if (isForCheckMap) {return;}
        System.out.print("legal moves for " + knight.getColor() + " " + "knight" + " at " + knight.getPosition().getVector2AsBoardNotation());
        System.out.print(" | ");
        for (vector2 square: knight.getPossibleMoves()) {
            System.out.print(square.getVector2AsBoardNotation() + " ");
        }
        System.out.println();
    }

    /*
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
                vector2 move = new vector2(bishop.getPosition().x + (d.x * n), bishop.getPosition().y + (d.y * n));

                //if the move square is outside the board, skip to the next direction.
                if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {break;}

                if (board.getPiece(move) == null) {
                    addPieceLegalMove(bishop, move, isForCheckMap);
                } else if (!(board.getPiece(move).getColor() == bishop.getColor())) {
                    addPieceLegalMove(bishop, move, isForCheckMap);
                    break;
                } else if (board.getPiece(move).getColor() == bishop.getColor()) {break;}
            }
        }
        if (isForCheckMap) {return;}
        System.out.print("legal moves for " + bishop.getColor() + " " + "bishop" + " at " + bishop.getPosition().getVector2AsBoardNotation());
        System.out.print(" | ");
        for (vector2 square: bishop.getPossibleMoves()) {
            System.out.print(square.getVector2AsBoardNotation() + " ");
        }
        System.out.println();
    }

    /*
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
        if (isForCheckMap) {return;}
        System.out.print("legal moves for " + rook.getColor() + " " + "rook" + " at " + rook.getPosition().getVector2AsBoardNotation());
        System.out.print(" | ");
        for (vector2 square: rook.getPossibleMoves()) {
            System.out.print(square.getVector2AsBoardNotation() + " ");
        }
        System.out.println();
    }

    /*
     * This method calculates and sets the legal moves of any given queen on the board.
     *
     * @param queen the queen to calculate the moves for.
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
        if (isForCheckMap) {return;}
        System.out.print("legal moves for " + queen.getColor() + " " + "queen" + " at " + queen.getPosition().getVector2AsBoardNotation());
        System.out.print(" | ");
        for (vector2 square: queen.getPossibleMoves()) {
            System.out.print(square.getVector2AsBoardNotation() + " ");
        }
        System.out.println();
    }

    /*
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

        //check if castling king side is legal.
        if (canPlayerCastleKingSide(king.getColor())) {
            for (int i = 1; i < 8; i++) {
                vector2 move = new vector2(king.getPosition().x + i, king.getPosition().y);
                if (board.getPiece(move) == null) {continue;}
                if (board.getPiece(move).getType() != 'r') {break;}

                //if we find a rook in the direction.
                addPieceLegalMove(king, new vector2(move.x - 1, move.y), isForCheckMap);
                break;
            }
        }

        //check if castling queen side is legal.
        if (canPlayerCastleQueenSide(king.getColor())) {
            for (int i = 1; i < 8; i++) {
                vector2 move = new vector2(king.getPosition().x - i, king.getPosition().y);
                if (board.getPiece(move) == null) {continue;}
                if (board.getPiece(move).getType() != 'r') {break;}

                //if we find a rook in the direction.
                addPieceLegalMove(king, new vector2(move.x + 2, move.y), isForCheckMap);
                break;
            }
        }
        if (isForCheckMap) {return;}
        System.out.print("legal moves for " + king.getColor() + " " + "king" + " at " + king.getPosition().getVector2AsBoardNotation());
        System.out.print(" | ");
        for (vector2 square: king.getPossibleMoves()) {
            System.out.print(square.getVector2AsBoardNotation() + " ");
        }
        System.out.println();
    }



    /*
     * A method to determine which players can castle and in which direction. using the board's forsyth edwards string notation.
     */
    private void determineWhoCanCastle() {
        //start off assuming that we cannot castle.
        canWhiteCastleKingSide = false;
        canWhiteCastleQueenSide = false;
        canBlackCastleKingSide = false;
        canBlackCastleQueenSide = false;

        //split the forsyth edwards notation string and acquire the 3rd section of it, which is the section which stores if the players can castle or not.
        String castlingNotation = board.getForsythEdwardsBoardNotationArrayIndex(2);

        //if this section only consists of the '-' character then neither side can castle, then return.
        if (castlingNotation.equals("-")) {return;}

        //otherwise, loop through the string.
        for (int i = 0; i < castlingNotation.length(); i++) {
            //if a 'k' is found.
            if (castlingNotation.charAt(i) == 'k') {
                //check if its uppercase, if so then it is referring to the ability for white to castle on the king side.
                if (Character.isUpperCase(castlingNotation.charAt(i))) {
                    canWhiteCastleKingSide = true;
                    continue;
                }

                //here it must be a black king.
                canBlackCastleKingSide = true;
            }
            //if a 'q' is found.
            else if (castlingNotation.charAt(i) == 'q') {
                //check if its uppercase, if so then it is referring to the ability for white to castle on the queen side.
                if (Character.isUpperCase(castlingNotation.charAt(i))) {
                    canWhiteCastleQueenSide = true;
                    continue;
                }

                //here it must be a black queen.
                canBlackCastleQueenSide = true;
            }
        }
    }

    /*
     * A method to determine if a player can castle king side.
     *
     * param player, the players color.
     */
    private boolean canPlayerCastleKingSide(char player) {
        if (player == 'w') {
            return canWhiteCastleKingSide;
        } else {
            return canBlackCastleKingSide;
        }
    }

    /*
     * A method to determine if a player can castle queen side.
     *
     * param player, the players color.
     */
    private boolean canPlayerCastleQueenSide(char player) {
        if (player == 'w') {
            return canWhiteCastleQueenSide;
        } else {
            return canBlackCastleQueenSide;
        }
    }


    /*
     * A method which adds a single move to the legal move arrayList of a piece.
     *
     * @param piece the piece we want to add the move to
     * @param move the move that we want to add
     * @param isForCheckMap if this is true then we can ignore weather or not the move will leave us in check or not since we are creating the check map.
     */
    private void addPieceLegalMove(piece piece, vector2 move, boolean isForCheckMap) {
        if (isForCheckMap) {
            piece.addMove(move);
        } else if (!isMoveSafe(piece, move)) {
            piece.addMove(move);
        }
    }

    /*
     * this is a method that determines if a move would leave the player in check or not.
     *
     * @param piece the piece to move.
     * @param move the move to assess.
     *
     * @return a boolean which determines if the move will leave the player in check or not.
     */
    private boolean isMoveSafe(piece piece, vector2 move) {
        board projectionOfMove = new board(board.getForsythEdwardsBoardNotation());
        projectionOfMove.movePiece(projectionOfMove.getPiece(piece.getPosition()), move);

        moveCalculator moveCalculator = new moveCalculator(piece.getColor(), projectionOfMove);
        moveCalculator.findLegalMovesForPlayer(true);

        return moveCalculator.isPlayerInCheck();
    }

    /**
     * a method which determines if the player is in check in the board position or not.
     * the player that we want to check if they are in check or not.
     *
     * @return a boolean which determines if the player is in check in the board position or not.
     */
    public boolean isPlayerInCheck() {
        if (currentPlayer == 'w') {
            for (vector2 attackedSquare: checkMap) {
                if (board.getWhiteKingPosition().getVector2AsBoardNotation().equals(attackedSquare.getVector2AsBoardNotation())) {
                    return true;
                }
            }
        } else {
            for (vector2 attackedSquare: checkMap) {
                if (board.getBlackKingPosition().getVector2AsBoardNotation().equals(attackedSquare.getVector2AsBoardNotation())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A method which prints out to the console every square which is currently being attacked by the opponent's pieces.
     */
    public void printCheckMap() {
        System.out.print("unsafe squares for player " + currentPlayer + " : ");
        for (vector2 attackedSquare : checkMap) {
           System.out.print(attackedSquare.getVector2AsBoardNotation() + " ");
        }
        System.out.println();
    }
}

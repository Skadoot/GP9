/*
 * @(#) Game.java 0.1 2023-03-16
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic;

import uk.ac.aber.cs221.group09.logic.pieces.Piece;
import uk.ac.aber.cs221.group09.logic.vector.Vector2;

import java.util.ArrayList;

/**
 * Game - The primary logic controller of the game.
 * <p>
 * This class contains the instance of the game board on which the moves are performed,
 * maintains track of which player is currently selected,
 * and contains the Log object that records the game.
 *
 * @author Shaun Royle
 * @version 0.6 (draft)
 * @see uk.ac.aber.cs221.group09.logic.MoveCalculator
 */
public class Game {
    //this is the order of operations that will happen every turn
    private final Board gameBoard;

    //this is the player taking the current move.
    private char attackingPlayer;

    //keep track of which move it is.
    private int moveCount;

    //is it the first move?
    boolean firstMove = true;
    private MoveCalculator moveCalculator;
    public Log log;
    private Vector2 selectedPiece;
    private boolean isMovesCalculated = false;


    /**
     * Constructor for game.
     *
     * @param boardState the initial board state string for the game (Forsyth Edwards Notation).
     */
    public Game(String boardState, String fileName, boolean load) {
        if (!load) {
            gameBoard = new Board(boardState);
            this.log = new Log(fileName, false);
        } else {
            gameBoard = new Board(boardState);
            this.log = new Log(fileName, true);
        }
        this.selectedPiece = new Vector2();
    }



    /**
     * A method which outlines the general loop of the game.
     */
    public void move(int row, int column) {
        //If the moves have been calculated the first turn, don't calculate them again.
        if (!isMovesCalculated && firstMove) {
            //calculate the legal moves for the board. with the current player.
            calculateMoves();

            firstMove = false;
        }

        //wait for the UI to give us a selected piece, here we would set it to be the coordinate that the ui passes back to us.
        Vector2 selectedBoardCoordinate = new Vector2(column, row);

        //Get a list of all the legal moves for the chessboard
        ArrayList<Vector2> currentLegalMoves = gameBoard.getPiece(selectedPiece).getPossibleMoves();

        //Check the selected coordinates are a legal move and the current selected piece is the attacking player's piece.
        if(currentLegalMoves.contains(selectedBoardCoordinate) && gameBoard.getPiece(selectedPiece).getColor() == attackingPlayer) {

            gameBoard.movePiece(gameBoard.getPiece(selectedPiece), selectedBoardCoordinate);
            selectedPiece = selectedBoardCoordinate;
            moveCount += (attackingPlayer == 'b') ? 1 : 0;
            isMovesCalculated = false;
            gameBoard.clearMoves();

            System.out.println("Moved piece to " + selectedBoardCoordinate.getVector2AsBoardNotation() + "\n");

            calculateMoves();

        } else if (gameBoard.getPiece(selectedBoardCoordinate) != null) {
            if (gameBoard.getPiece(selectedBoardCoordinate).getColor() == attackingPlayer) {

                selectedPiece = selectedBoardCoordinate;
                System.out.println("selected piece is : " + selectedPiece.getVector2AsBoardNotation() + ",");
                if (gameBoard.getPiece(selectedPiece).getPossibleMoves().isEmpty()) {
                    System.out.println("Did not find legal move.");
                }
            }
        }
    }

    public void calculateMoves() {
        //determine the player making the current move.
        determineCurrentPlayer();

        moveCalculator = new MoveCalculator(attackingPlayer, gameBoard);

        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        isMovesCalculated = true;

        moveCalculator.printCheckMap();

        System.out.println("\nis " + attackingPlayer + " in check = " + moveCalculator.isPlayerInCheck());
        System.out.println("\nis " + attackingPlayer + " in checkmate = " + moveCalculator.isPlayerInCheckMate());

        System.out.println("\n" + gameBoard.getForsythEdwardsBoardNotation() + "\n");
    }

    public String gameNotation() {
        return gameBoard.getForsythEdwardsBoardNotation();
    }

    /**
     * A method which determines which player's move it is.
     * using the board's Forsyth Edwards Notation string.
     */
    private void determineCurrentPlayer() {
        //check the board state string to find which player's turn it is.
        attackingPlayer = gameBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
    }

    public ArrayList<int[]> validTiles() {
        Piece piece = gameBoard.getPiece(selectedPiece);
        ArrayList<int[]> res = new ArrayList<int[]>();
        if(piece.getColor() != attackingPlayer) {
            return res;
        }
        ArrayList<Vector2> tiles = piece.getPossibleMoves();
        for (Vector2 vTiles: tiles) {
            int coords[] = new int[2];
            coords[0] = vTiles.y;
            coords[1] = vTiles.x;
            res.add(coords);
        }
        return res;
    }

    public ArrayList<int[]> checkedKing() {
        ArrayList<int[]> res = new ArrayList<int[]>();
        MoveCalculator checkCheck = new MoveCalculator(attackingPlayer, gameBoard);
        if (checkCheck.isPlayerInCheck()) {
            int[] coords = new int[2];
            if(attackingPlayer == 'w') {
                Vector2 wKPos = gameBoard.getWhiteKingPosition();
                coords[0] = wKPos.y;
                coords[1] = wKPos.x;
            } else {
                Vector2 wKPos = gameBoard.getBlackKingPosition();
                coords[0] = wKPos.y;
                coords[1] = wKPos.x;
            }
            res.add(coords);
        }
        return res;
    }

    /**
     * Tell the backend to promote a piece.
     * @param n
     */
    public void promote(int n) {
        gameBoard.piecePromotion(n);
    }

    /**
     * Checks whether a promotion is available. Called after every move to update the class field.
     * @return boolean - Whether the player can promote a pawn.
     */
    public boolean isPromotionAvailable() {
        if(gameBoard.canWhitePromote() || gameBoard.canBlackPromote()) {
            return true;
        } else {
            return false;
        }
    }
}

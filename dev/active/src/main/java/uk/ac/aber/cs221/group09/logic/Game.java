/*
 * @(GP9) Game.java 1.0 2023/05/02
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
 * @version 1.0 (Release)
 * @see uk.ac.aber.cs221.group09.logic.MoveCalculator
 */
public class Game {

   private Board gameBoard;
   public Log log;
   // Whether this is the first move of the game
   boolean firstMove = true;
   // This is the player taking the current move.
   private char attackingPlayer;
   // Keep track of which move it is.
   private int moveCount;
   private MoveCalculator moveCalculator;
   private Vector2 selectedPiece;
   private boolean isMovesCalculated = false;

   private Vector2 selectedBoardCoordinate;

   private boolean isMoveMade = false;

   /**
    * Constructor for game.
    * Simple constructor for game.
    *
    * @param boardState the initial board state string for the game (Forsyth Edwards Notation).
    */
   public Game(String boardState, String fileName, boolean load) {
      gameBoard = new Board(boardState);
      this.log = new Log(fileName);
      this.selectedPiece = new Vector2();
   }

   public Game(){
       this.log = new Log();
       this.selectedPiece = new Vector2();
   }

   public void createGame(String fileName) {
      log.setFileName(fileName);
      gameBoard = new Board(log.readLog(log.getNumberOfLines()-1));
   }

   /**
    * Outlines the general loop of the game.
    */
   public void move(int row, int column) {
      // If the moves have been calculated the first turn, don't calculate them again.
      if (!isMovesCalculated && firstMove) {
         // Calculate the legal moves for the board using the current player.
         calculateMoves();

         firstMove = false;
      }

      // Wait for the UI to return a selected piece, here we would set it to be the coordinate that the ui passes back to us.
      selectedBoardCoordinate = new Vector2(column, row);

      // Get a list of all the legal moves for the chessboard
      ArrayList<Vector2> currentLegalMoves = gameBoard.getPiece(selectedPiece).getPossibleMoves();

      // Check the selected coordinates are a legal move and the current selected piece is the attacking player's piece.
      if (currentLegalMoves.contains(selectedBoardCoordinate) && gameBoard.getPiece(selectedPiece).getColor() == attackingPlayer) {
         isMoveMade = true;
         gameBoard.movePiece(gameBoard.getPiece(selectedPiece), selectedBoardCoordinate);
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

   /**
    * this method updates the board.
    */
   public void updateBoard() {
      log.updateLog(gameBoard.getForsythEdwardsBoardNotation()); //should update the log after a move is made
      selectedPiece = selectedBoardCoordinate;
      moveCount += (attackingPlayer == 'b') ? 1 : 0;
      isMovesCalculated = false;
      gameBoard.clearMoves();

      //print debugging
      System.out.println("Moved piece to " + selectedBoardCoordinate.getVector2AsBoardNotation());
      System.out.println("\n         ,....,----------------------------------------------------\n" +
         "      ,::::::<-----------------------------------------------------\n" +
         "     ,::/^\\\"``.----------------------------------------------------\n" +
         "    ,::/, `   e`.--------------------------------------------------\n" +
         "   ,::; |        '.------------------------------------------------\n" +
         "   ,::|  \\___,-.  c)-----------------------------------------------\n" +
         "   ;::|     \\   '-'------------------------------------------------\n" +
         "   ;::|      \\-----------------------------------------------------\n" +
         "   ;::|   _.=`\\----------------------------------------------------\n" +
         "   `;:|.=` _.=`\\---------------------------------------------------\n" +
         "     '|_.=`   __\\--------------------------------------------------\n" +
         "     `\\_..==`` /---------------------------------------------------\n" +
         "      .'.___.-'.---------------------------------------------------\n" +
         "     /          \\--------------------------------------------------\n" +
         "    ('--......--')-------------------------------------------------\n" +
         "    /'--......--'\\-------------------------------------------------\n" +
         "    `\"--......--\"--------------------------------------------------\n");

      calculateMoves();
      isMoveMade = false;
   }

   public boolean isMoveMade() {
      return isMoveMade;
   }

   public void calculateMoves() {
      // Determine the player making the current move.
      determineCurrentPlayer();

      // Calculate the legal moves for the board, using the current player.
      MoveCalculator moveCalculator = new MoveCalculator(attackingPlayer, gameBoard);

      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      isMovesCalculated = true;

      System.out.print("\n");

      moveCalculator.printCheckMap();

      System.out.println("\nis " + attackingPlayer + " in check = " + moveCalculator.isPlayerInCheck());
      System.out.println("is " + attackingPlayer + " in checkmate = " + moveCalculator.isPlayerInCheckMate());

      System.out.println("\n" + gameBoard.getForsythEdwardsBoardNotation() + "\n");
   }

   public String gameNotation() {
      return gameBoard.getForsythEdwardsBoardNotation();
   }

   /**
    * Determines which player's turn it is, using the board's Forsyth Edwards Notation string.
    */
   private void determineCurrentPlayer() {
      // Check the board state string to find which player's turn it is.
      attackingPlayer = gameBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
   }

   public ArrayList<int[]> validTiles() {
      Piece piece = gameBoard.getPiece(selectedPiece);
      ArrayList<int[]> res = new ArrayList<int[]>();
      if (piece.getColor() != attackingPlayer) {
         return res;
      }
      ArrayList<Vector2> tiles = piece.getPossibleMoves();
      for (Vector2 vTiles : tiles) {
         int[] coords = new int[2];
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
         if (attackingPlayer == 'w') {
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
    * Requests that the board promotes a piece.
    *
    * @param n
    */
   public void promote(int n) {
      gameBoard.piecePromotion(n);
      gameBoard.clearMoves();
      MoveCalculator promotionCheck = new MoveCalculator(attackingPlayer, gameBoard);
      promotionCheck.findLegalMovesForPlayer(true);
      promotionCheck.findLegalMovesForPlayer(false);
   }

   /**
    * Checks whether a promotion is available. Called after every move to update the class field.
    *
    * @return boolean whether the player can promote a pawn.
    */
   public boolean isPromotionAvailable() {
      if (gameBoard.canWhitePromote() || gameBoard.canBlackPromote()) {
         return true;
      } else {
         return false;
      }
   }
}

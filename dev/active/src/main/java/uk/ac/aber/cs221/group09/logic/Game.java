/*
 * @(GP9) Game.java 1.1 2023/05/02
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic;

import uk.ac.aber.cs221.group09.logic.pieces.Piece;
import uk.ac.aber.cs221.group09.util.Vector2;

import java.util.ArrayList;

/**
 * Game - The primary logic controller of the game.
 * <p>
 * This class contains the instance of the game board on which the moves are performed,
 * maintains track of which player is currently selected,
 * and contains the Log object that records the game.
 *
 * @author Shaun Royle
 * @version 1.1 (Release)
 * @see MoveCalculator
 */
public class Game {

   public Log log;
   // Whether this is the first move of the game
   boolean firstMove = true;
   private Board gameBoard;
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
    * Default constructor for game.
    *
    * @param boardState the initial board state string for the game (Forsyth Edwards Notation).
    */
   public Game(String boardState, String fileName, boolean load) {
      gameBoard = new Board(boardState);
      this.log = new Log(fileName);
      this.selectedPiece = new Vector2();
   }

   /**
    * Simple constructor.
    */
   public Game() {
      this.log = new Log();
      this.selectedPiece = new Vector2();
   }

   /**
   *  method to create a game giving a file name, and weather or not the game has not finished
   *
   * @param fileName the file name
   * @param isFinished is the game finished?
    */
   public void createGame(String fileName, boolean isFinished) {
      log.setFinishedGame(isFinished);
      log.setFileName(fileName);
      gameBoard = new Board(log.readLog(log.getNumberOfLines() - 1));
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
      ArrayList<Vector2> currentLegalMoves = new ArrayList<Vector2>();
      if(gameBoard.getPiece(selectedPiece) != null) {
         currentLegalMoves = gameBoard.getPiece(selectedPiece).getPossibleMoves();
      }
      // Check the selected coordinates are a legal move and the current selected piece is the attacking player's piece.
      if (currentLegalMoves.contains(selectedBoardCoordinate) && gameBoard.getPiece(selectedPiece).getColor() == attackingPlayer) {
         isMoveMade = true;
         gameBoard.movePiece(gameBoard.getPiece(selectedPiece), selectedBoardCoordinate);
      } else {
         selectedPiece = selectedBoardCoordinate;
      }
   }

   /**
    * Updates the active log file after every move.
    * Updates the logical positioning of the pieces on the board.
    */
   public void updateBoard() {
      log.updateLog(gameBoard.getForsythEdwardsBoardNotation()); // Should update the log after a move is made
      selectedPiece = selectedBoardCoordinate;
      moveCount += (attackingPlayer == 'b') ? 1 : 0;
      isMovesCalculated = false;
      gameBoard.clearMoves();

      // Print debugging
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

      if (isGameOverByCheckMate()) {
         determineCurrentPlayer();
         String winningPlayer = "b";
         if (attackingPlayer == 'b') {
            winningPlayer = "w";
         }

         gameBoard.updateFENStringWhenCheckMate(winningPlayer);
         log.updateLog(gameBoard.getForsythEdwardsBoardNotation());
         log.moveFileToFinishedGamesDir();
      }
      System.out.println("\n" + gameBoard.getForsythEdwardsBoardNotation() + "\n");
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

   public boolean isGameOverByCheckMate() {
      MoveCalculator moveCalculator = new MoveCalculator(attackingPlayer, gameBoard);

      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      return moveCalculator.isPlayerInCheckMate();
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

   /**
    * Creates and ArrayList containing the coordinates of valid tiles to display on the front end.
    * @return res - An ArrayList of int pairs.
    */
   public ArrayList<Vector2> validTiles() {
      Piece piece = gameBoard.getPiece(selectedPiece);
      ArrayList<Vector2> res = new ArrayList<>();
      if(gameBoard.getPiece(selectedPiece) == null) {
         return res;
      }
      if (piece.getColor() != attackingPlayer) {
         return res;
      }
      res = piece.getPossibleMoves();
      return res;
   }

   /**
    * Return the position of any king in check in order to display on the front end.
    * @return res - ArrayList containing an int pair resembling a coordinate.
    */
   public ArrayList<Vector2> checkedKing() {
      ArrayList<Vector2> res = new ArrayList<Vector2>();
      MoveCalculator checkCheck = new MoveCalculator(attackingPlayer, gameBoard);
      checkCheck.findLegalMovesForPlayer(true);
      checkCheck.findLegalMovesForPlayer(false);
      if (checkCheck.isPlayerInCheck()) {
         Vector2 kCheck = new Vector2();
         if (attackingPlayer == 'w') {
            kCheck = gameBoard.getWhiteKingPosition();
         } else {
            kCheck = gameBoard.getBlackKingPosition();
         }
         res.add(kCheck);
      }
      return res;
   }




   /**
    * Requests that the board promotes a piece.
    *
    * @param n the unique identifier of the piece to promote.
    */
   public void promote(int n) {
      gameBoard.promotePawn(n);
      log.replaceLine(gameBoard.getTurnNumber(), gameBoard.getForsythEdwardsBoardNotation());
      gameBoard.clearMoves();
      MoveCalculator promotionCheck = new MoveCalculator(attackingPlayer, gameBoard);
      promotionCheck.findLegalMovesForPlayer(true);
      promotionCheck.findLegalMovesForPlayer(false);
   }





   /**
    * Checks whether a promotion is available.
    * Called after every move to update the class field.
    *
    * @return boolean whether the player can promote a pawn.
    */
   public boolean isPromotionAvailable() {
      return gameBoard.canWhitePromote() || gameBoard.canBlackPromote();
   }

   /**
    * method that checks if the game is over by checkmate
    * @param c the player.
    */
   public void endGame(char c) {
      String winningPlayer = Character.toString(c);
      gameBoard.updateFENStringWhenCheckMate(winningPlayer);
      log.updateLog(gameBoard.getForsythEdwardsBoardNotation());
      log.moveFileToFinishedGamesDir();

      System.out.println("\n" + gameBoard.getForsythEdwardsBoardNotation() + "\n");
   }

   public Board getGameBoard() {
      return gameBoard;
   }
}
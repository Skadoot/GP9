/*
 * @(#) Game.java 0.1 2023-03-16
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic;

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
   private Board gameBoard;

   //this is the player taking the current move.
   private char attackingPlayer;

   //keep track of which move it is.
   private int moveCount;

   public Log log;
   private Vector2 selectedPiece;
   private boolean isMovesCalculated = false;

   /**
    * Constructor for game.
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
    * A method which outlines the general loop of the game.
    */
   public void move(int row, int column) {
      //determine the player making the current move.
      determineCurrentPlayer();

      //calculate the legal moves for the board. with the current player.
      MoveCalculator moveCalculator = new MoveCalculator(attackingPlayer, gameBoard);

      if (!isMovesCalculated) {
         moveCalculator.findLegalMovesForPlayer(true);
         moveCalculator.findLegalMovesForPlayer(false);
         moveCalculator.printCheckMap();

         isMovesCalculated = true;

         System.out.println("is " + attackingPlayer + " in check = " + moveCalculator.isPlayerInCheck());
      }

      //print the board to the console.
      gameBoard.printBoardStateToConsole();


      //wait for the UI to give us a selected piece, here we would set it to be the coordinate that the ui passes back to us.
      Vector2 selectedBoardCoordinate = new Vector2(column, row);


      //Get a list of all the legal moves for the chessboard
      ArrayList<Vector2> currentLegalMoves = gameBoard.getPiece(selectedPiece).getPossibleMoves();
      if (currentLegalMoves.contains(selectedBoardCoordinate) && gameBoard.getPiece(selectedPiece).getColor() == attackingPlayer) {
         System.out.println("Moved piece.");
         gameBoard.movePiece(gameBoard.getPiece(selectedPiece), selectedBoardCoordinate); //can use this to record movements in UI
         log.updateLog(gameBoard.getForsythEdwardsBoardNotation()); //should update the log after a move is made
         selectedPiece = selectedBoardCoordinate;
         moveCount += (attackingPlayer == 'b') ? 1 : 0;
         isMovesCalculated = false;
         gameBoard.clearMoves();

      } else if (gameBoard.getPiece(selectedBoardCoordinate) != null) {
         if (gameBoard.getPiece(selectedBoardCoordinate).getColor() == attackingPlayer) {
            System.out.println("Did not find legal move.");
            selectedPiece = selectedBoardCoordinate;
         }
      }
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
}

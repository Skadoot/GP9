/*
 * @(GP9) LogTest.java 1.0 2023-05-02
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.group09.logic.pieces.Piece;
import uk.ac.aber.cs221.group09.util.Coordinate;

public class LogTest {
   /**
    * A method to test whether the saved log is read properly
    *
    * @param testGame     The setup of a game with the default starting position
    * @param pieceToMove  The position of the white pawn on the A1 square
    * @param testPosition The position for the selected piece to move to
    */
   @Test
   //FR11 tests
   public void testReadLog() {
      Game testGame = new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", "testGame", false);
      Piece pieceToMove = testGame.getGameBoard().getPiece(new Coordinate(0, 1));
      testGame.calculateMoves();
      Coordinate testPosition = new Coordinate(0, 3);
      for (Coordinate currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getCoordinateAsBoardNotation().equals(testPosition.getCoordinateAsBoardNotation())) {
            testGame.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      testGame.log.updateLog(testGame.getGameBoard().getForsythEdwardsBoardNotation());

      System.out.println(testGame.log.getNumberOfLines());

      Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR b KQkq a3 1 1", testGame.log.readLog(1));
   }

   /**
    * A method to test whether the number of lines in a saved log is read properly
    *
    * @param testGame     The setup of a game with the default starting position
    * @param pieceToMove  The position of the white pawn on the A1 square
    * @param testPosition The position for the selected piece to move to
    */
   @Test
   //FR11 Tests
   public void testGetNumberOfLines() {
      Game testGame = new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", "testGame", false);
      Piece pieceToMove = testGame.getGameBoard().getPiece(new Coordinate(0, 1));
      testGame.calculateMoves();
      Coordinate testPosition = new Coordinate(0, 3);
      for (Coordinate currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getCoordinateAsBoardNotation().equals(testPosition.getCoordinateAsBoardNotation())) {
            testGame.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      testGame.log.updateLog(testGame.getGameBoard().getForsythEdwardsBoardNotation());

      pieceToMove = testGame.getGameBoard().getPiece(new Coordinate(1, 1));
      testGame.calculateMoves();
      testPosition = new Coordinate(1, 3);
      for (Coordinate currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getCoordinateAsBoardNotation().equals(testPosition.getCoordinateAsBoardNotation())) {
            testGame.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      testGame.log.updateLog(testGame.getGameBoard().getForsythEdwardsBoardNotation());


      Assertions.assertEquals(3, testGame.log.getNumberOfLines());
   }

   /**
    * A method to test whether the logs are updated properly during a game
    *
    * @param testGame     The setup of a game with the default starting position
    * @param pieceToMove  The position of the white pawn on the A1 square
    * @param testPosition The position for the selected piece to move to
    */
   @Test
   //FR11 Tests
   public void testUpdateLog() {
      Game testGame = new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", "testGame", false);
      Piece pieceToMove = testGame.getGameBoard().getPiece(new Coordinate(0, 1));
      testGame.calculateMoves();
      Coordinate testPosition = new Coordinate(0, 3);
      for (Coordinate currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getCoordinateAsBoardNotation().equals(testPosition.getCoordinateAsBoardNotation())) {
            testGame.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      testGame.log.updateLog(testGame.getGameBoard().getForsythEdwardsBoardNotation());
      Assertions.assertEquals(2, testGame.log.getNumberOfLines());
   }

   /**
    * A method to test whether the filename is the same as the actual game
    *
    * @param testGame The setup of a new game with a starting board
    */
   @Test
   //FR11 Tests
   public void testsetFilename() {
      //creates a new game with a starting board
      Game testGame = new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", "testGame", false);

      //check if the log for the filename is the same as the following game
      testGame.log.updateLog(testGame.getGameBoard().getForsythEdwardsBoardNotation());
      Assertions.assertEquals(2, testGame.log.getNumberOfLines());

   }

   /**
    * A method to test whether the finished game is applied to the log
    *
    * @param testGame     The setup of a new game with a starting board
    * @param pieceToMove  the white pawn
    * @param testPosition position for the white pawn to move to
    */
   @Test
   //FR11 Tests
   public void testsetFinishedGame() {
      //creates a new game with a starting board
      Game testGame = new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", "testGame", false);
      Piece pieceToMove = testGame.getGameBoard().getPiece(new Vector2(0, 1));
      testGame.calculateMoves();
      Vector2 testPosition = new Vector2(0, 3);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testGame.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      testGame.log.updateLog(testGame.getGameBoard().getForsythEdwardsBoardNotation());
      Assertions.assertEquals(2, testGame.log.getNumberOfLines());

      //checks whether finished games are saved correctly
      testGame.log.setFinishedGame(true);
   }

   /**
    * A method to test whether the deleted file or game is removed from the log
    *
    * @param testGame     The setup of a new game with a starting board
    * @param pieceToMove  the white pawn
    * @param testPosition position for the white pawn to move to
    */
   @Test
   //FR11 Tests
   public void testdeleteFile() {
      //creates a new game with a starting board
      Game testGame = new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", "testGame", false);
      Piece pieceToMove = testGame.getGameBoard().getPiece(new Vector2(0, 1));
      testGame.calculateMoves();
      Vector2 testPosition = new Vector2(0, 3);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testGame.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      testGame.log.updateLog(testGame.getGameBoard().getForsythEdwardsBoardNotation());
      Assertions.assertEquals(2, testGame.log.getNumberOfLines());

      //checks whether deleted files are removed
      testGame.log.deleteFile();

   }

   /**
    * A method to test whether the lines are replaced if a new game is created
    *
    * @param testGame The setup of a new game with a starting board
    */
   @Test
   //FR11 Tests
   public void testReplaceLine() {
      //creates a new game with a starting board
      Game testGame = new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", "testGame", false);

      //replaces line of the log if check whether it has been updated
      testGame.log.replaceLine(0, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1");
      Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1", testGame.log.readLog(0));
   }
}
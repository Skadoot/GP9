/*
 * @(GP9) LogTest.java 1.0 2023-05-02
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.group09.logic.pieces.Piece;
import uk.ac.aber.cs221.group09.util.Vector2;

public class LogTest {
   @Test
   //FR11 tests
   /**
    * A method to test whether the saved log is read properly
    *
    * @param testGame The setup of a game with the default starting position
    * @param pieceToMove The position of the white pawn on the A1 square
    * @param testPosition The position for the selected piece to move to
    */
   public void testReadLog() {
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

      System.out.println(testGame.log.getNumberOfLines());

      Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR b KQkq a3 1 1", testGame.log.readLog(1));
   }

   @Test
   //FR11 Tests
   /**
    * A method to test whether the number of lines in a saved log is read properly
    *
    * @param testGame The setup of a game with the default starting position
    * @param pieceToMove The position of the white pawn on the A1 square
    * @param testPosition The position for the selected piece to move to
    */
   public void testGetNumberOfLines() {
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

      pieceToMove = testGame.getGameBoard().getPiece(new Vector2(1, 1));
      testGame.calculateMoves();
      testPosition = new Vector2(1, 3);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testGame.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      testGame.log.updateLog(testGame.getGameBoard().getForsythEdwardsBoardNotation());


      Assertions.assertEquals(3, testGame.log.getNumberOfLines());
   }

   @Test
   //FR11 Tests
   /**
    * A method to test whether the logs are updated properly during a game
    *
    * @param testGame The setup of a game with the default starting position
    * @param pieceToMove The position of the white pawn on the A1 square
    * @param testPosition The position for the selected piece to move to
    */
   public void testUpdateLog() {
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
   }

   @Test
   public void testsetFilename() {
      Game testGame = new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", "testGame", false);
      testGame.log.updateLog(testGame.getGameBoard().getForsythEdwardsBoardNotation());
      Assertions.assertEquals(2, testGame.log.getNumberOfLines());

   }

   @Test
   public void testsetFinishedGame() {
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
      testGame.log.setFinishedGame(true);


   }

   @Test
   public void testdeleteFile() {
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
      testGame.log.deleteFile();

   }

   @Test
   public void testReplaceLine() {
      Game testGame = new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", "testGame", false);
      testGame.log.replaceLine(0, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1");
      Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1", testGame.log.readLog(0));
   }
}
/*
 * @(#) LogTest.java 0.1 2023-05-02
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
   public void testReadLog(){
      Game testGame = new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", "testGame", false);
      Piece pieceToMove = testGame.getGameBoard().getPiece(new Vector2(0,1));
      testGame.calculateMoves();
      Vector2 testPosition = new Vector2(0, 3);
      for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
         {
            testGame.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      testGame.log.updateLog(testGame.getGameBoard().getForsythEdwardsBoardNotation());

      System.out.println(testGame.log.getNumberOfLines());

      Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR b KQkq a3 1 1", testGame.log.readLog(1));
   }

   @Test
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

      pieceToMove = testGame.getGameBoard().getPiece(new Vector2(1,1));
      testGame.calculateMoves();
      testPosition = new Vector2(1,3);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testGame.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      testGame.log.updateLog(testGame.getGameBoard().getForsythEdwardsBoardNotation());


      Assertions.assertEquals(3, testGame.log.getNumberOfLines());
   }

   @Test
   public void testUpdateLog(){
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

}

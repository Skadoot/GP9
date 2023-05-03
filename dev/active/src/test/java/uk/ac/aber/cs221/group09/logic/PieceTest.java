/*
 * @(#) BoardTest.java 0.1 2023-05-02
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.group09.logic.pieces.Piece;
import uk.ac.aber.cs221.group09.logic.vector.Vector2;

/**
 * PieceTest - Testing class for the Piece Class
 * <p>
 * This class stores the testing related to the status of the pieces on the board
 *
 * @author Craymon Chan
 * @author Jim Brown
 * @author Sean Hobson
 * @version 0.1 (draft)
 * @see uk.ac.aber.cs221.group09.logic.PieceTest
 */

class PieceTest{

   @Test
   public void testGetWhiteKingPosition() {
      //creates a new board with a move available for the white king
      Board testBoard = new Board("rnbqkbnr/pppp1ppp/4p3/8/8/4P3/PPPP1PPP/RNBQKBNR w KQkq - 0 1");

      //sets the white king as a testing piece to move
      Vector2 testPosition = new Vector2(4, 0);
      Piece pieceToMove = testBoard.getPiece(testPosition);

      //moves the white king
      Vector2 testPosition2 = new Vector2(4,1);
      testBoard.movePiece(pieceToMove, testPosition2);

      //checks if the white king's position is returned correctly
      Assertions.assertEquals(testPosition2,testBoard.getWhiteKingPosition());
   }

   @Test
   public void testGetBlackKingPosition() {
      //creates a new board with a move available for the black king
      Board testBoard = new Board("rnbqkbnr/pppp1ppp/4p3/8/8/4P3/PPPPKPPP/RNBQ1BNR b kq - 0 1");

      //sets the black king as a testing piece to move
      Vector2 testPosition = new Vector2(4, 7);
      Piece pieceToMove = testBoard.getPiece(testPosition);

      //moves the black king
      Vector2 testPosition2 = new Vector2(4,6);
      testBoard.movePiece(pieceToMove, testPosition2);

      //checks if the black king's position is returned correctly
      Assertions.assertEquals(testPosition2,testBoard.getBlackKingPosition());
   }

   @Test
   //FR2 Tests
   public void testGetPieceColor() {
      //creates a new board
      Board testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      //selects the white rook on a1
      Vector2 testPosition = new Vector2(0, 0);

      //check if the expected piece color is same as actual piece color - which should be white
      Assertions.assertEquals('w',testBoard.getPiece(testPosition).getColor());
   }

   @Test
   //FR2 Tests
   public void testGetPieceType() {
      //creates a new board
      Board testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      //selects the knight on b1
      Vector2 testPosition = new Vector2(1, 0);

      //check if the expected piece type is the same as actual piece type - which should be a knight (n)
      Assertions.assertEquals('n',testBoard.getPiece(testPosition).getType());
   }


   @Test
   //FR2 Tests
   public void testGetPiecePosition() {
      //create a new board
      Board testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      //selects the bishop on f1
      Vector2 testPosition = new Vector2(5, 0);

      //check if the expected piece position is the same as actual piece position - which should be 5,0
      Assertions.assertEquals(5, testPosition.x);
      Assertions.assertEquals(0, testPosition.y);

   }


}
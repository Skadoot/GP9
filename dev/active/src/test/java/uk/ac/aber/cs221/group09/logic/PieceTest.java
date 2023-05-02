/*
 * @(#) PieceTest.java 0.1 2023-05-02
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
   /**
    * A method to check the white's king position
    *
    * @param Board setup of a new board with a white king where it is allowed to move
    * @param testPosition setup a white king
    * @param pieceToMove testing piece attempt to move
    */
   public void testGetWhiteKingPosition() {
      // Create a new board with a white king where it is allowed to move
      Board testBoard = new Board("rnbqkbnr/pppp1ppp/4p3/8/8/4P3/PPPP1PPP/RNBQKBNR w KQkq - 0 1");

      // initial position of white king and sets a test piece to move
      Vector2 testPosition = new Vector2(4, 0);
      Piece pieceToMove = testBoard.getPiece(testPosition);

      // Move king into another position
      Vector2 testPosition2 = new Vector2(4,1);
      testBoard.movePiece(pieceToMove, testPosition2);

      //check if the white king's position is returned correctly
      Assertions.assertEquals(testPosition2,testBoard.getWhiteKingPosition());
   }

   @Test
   /**
    * A method to check the black's king position
    *
    * @param Board setup of a new board with a black king where it is allowed to move
    * @param testPosition setup a white king
    * @param pieceToMove testing piece attempt to move
    */
   public void testGetBlackKingPosition() {
      // Create a new board with a black king where it is allowed to move
      Board testBoard = new Board("rnbqkbnr/pppp1ppp/4p3/8/8/4P3/PPPPKPPP/RNBQ1BNR b kq - 0 1");

      // initial position of black king and sets a test piece to move
      Vector2 testPosition = new Vector2(4, 7);
      Piece pieceToMove = testBoard.getPiece(testPosition);

      // Move king into another position
      Vector2 testPosition2 = new Vector2(4,6);
      testBoard.movePiece(pieceToMove, testPosition2);

      //check if the black king's position is returned correctly
      Assertions.assertEquals(testPosition2,testBoard.getBlackKingPosition());
   }

   @Test
   //FR2 Tests
   /**
    * A method to check the piece's color
    *
    * @param Board setup of a new starting board
    * @param testPosition setup a white pawn
    */
   public void testGetPieceColor() {
      //create a new board
      Board testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      //Get the color of a specific chess piece
      Vector2 testPosition = new Vector2(0, 0);

      //check if the expected piece color is same as actual piece color
      Assertions.assertEquals('w',testBoard.getPiece(testPosition).getColor());
   }

   @Test
   //FR2 Tests
   /**
    * A method to check the piece's type
    *
    * @param Board setup of a new starting board
    * @param testPosition setup a knight piece
    */
   public void testGetPieceType() {
      //create a new board
      Board testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      //Get the type of a specific chess piece
      Vector2 testPosition = new Vector2(1, 0);

      //check if the expected piece type is the same as actual piece type
      Assertions.assertEquals('n',testBoard.getPiece(testPosition).getType());
   }


   @Test
   //FR2 Tests
   /**
    * A method to check the piece's position
    *
    * @param Board setup of a new starting board
    * @param testPosition check the position of a specific piece
    */
   public void testGetPiecePosition() {
      //create a new board
      Board testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      //Get the position of a specific chess piece
      Vector2 testPosition = new Vector2(5, 0);

      //check if the expected piece position is the same as actual piece position
      Assertions.assertEquals(5, testPosition.x);
      Assertions.assertEquals(0, testPosition.y);
   }

}
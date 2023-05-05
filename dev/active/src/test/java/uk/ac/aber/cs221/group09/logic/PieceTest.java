/*
 * @(GP9) PieceTest.java 1.0 2023-05-02
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.group09.logic.pieces.Piece;
import uk.ac.aber.cs221.group09.util.Coordinate;

/**
 * PieceTest - Testing class for the Piece Class
 * <p>
 * This class stores the testing related to the status of the pieces on the board
 *
 * @author Craymon Chan
 * @author Jim Brown
 * @author Sean Hobson
 * @version 1.0 (Release)
 * @see PieceTest
 */
class PieceTest {
   @Test
   public void testGetWhiteKingPosition() {
      //creates a new board with a move available for the white king
      Board testBoard = new Board("rnbqkbnr/pppp1ppp/4p3/8/8/4P3/PPPP1PPP/RNBQKBNR w KQkq - 0 1");

      //sets the white king as a testing piece to move
      Coordinate testPosition = new Coordinate(4, 0);
      Piece pieceToMove = testBoard.getPiece(testPosition);

      //moves the white king
      Coordinate testPosition2 = new Coordinate(4, 1);
      testBoard.movePiece(pieceToMove, testPosition2);

      //checks if the white king's position is returned correctly
      Assertions.assertEquals(testPosition2, testBoard.getWhiteKingPosition());
   }

   @Test
   public void testGetBlackKingPosition() {
      //creates a new board with a move available for the black king
      Board testBoard = new Board("rnbqkbnr/pppp1ppp/4p3/8/8/4P3/PPPPKPPP/RNBQ1BNR b kq - 0 1");

      //sets the black king as a testing piece to move
      Coordinate testPosition = new Coordinate(4, 7);
      Piece pieceToMove = testBoard.getPiece(testPosition);

      //moves the black king
      Coordinate testPosition2 = new Coordinate(4, 6);
      testBoard.movePiece(pieceToMove, testPosition2);

      //checks if the black king's position is returned correctly
      Assertions.assertEquals(testPosition2, testBoard.getBlackKingPosition());
   }

   @Test
   //FR2 Tests
   public void testGetPieceColor() {
      //creates a new board
      Board testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      //selects the white rook on a1
      Coordinate testPosition = new Coordinate(0, 0);

      //check if the expected piece color is same as actual piece color - which should be white
      Assertions.assertEquals('w', testBoard.getPiece(testPosition).getColor());
   }

   @Test
   //FR2 Tests
   public void testGetPieceType() {
      //creates a new board
      Board testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      //selects the knight on b1
      Coordinate testPosition = new Coordinate(1, 0);

      //check if the expected piece type is the same as actual piece type - which should be a knight (n)
      Assertions.assertEquals('n', testBoard.getPiece(testPosition).getType());
   }


   @Test
   //FR2 Tests
   public void testGetPiecePosition() {
      //selects the f1 square
      Coordinate testPosition = new Coordinate(5, 0);

      //checks if the expected piece position is the same as actual piece position - which should be 5,0
      Assertions.assertEquals(5, testPosition.x);
      Assertions.assertEquals(0, testPosition.y);
   }

   @Test
   public void promoteWhiteQueen() {
      //creates a new game with a white pawn available to be promoted
      Game game = new Game("rnbqkbnP/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNR b KQq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn on h8 to a queen
      game.promote(0);

      //checks that the piece on h8 is now a white queen
      Coordinate testPosition = new Coordinate(7, 7);
      Assertions.assertEquals('q', game.getGameBoard().getPiece(testPosition).getType());
      Assertions.assertEquals('w', game.getGameBoard().getPiece(testPosition).getColor());
   }

   @Test
   public void promoteWhiteRook() {
      //creates a new game with a white pawn available to be promoted
      Game game = new Game("rnbqkbnP/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNR b KQq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn to a rook
      game.promote(1);

      //checks that the piece on h8 is now a white rook
      Coordinate testPosition = new Coordinate(7, 7);
      Assertions.assertEquals('r', game.getGameBoard().getPiece(testPosition).getType());
      Assertions.assertEquals('w', game.getGameBoard().getPiece(testPosition).getColor());
   }

   @Test
   public void promoteWhiteBishop() {
      //creates a new game with a white pawn available to be promoted
      Game game = new Game("rnbqkbnP/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNR b KQq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn to a bishop
      game.promote(2);

      //checks that the piece on h8 is now a white bishop
      Coordinate testPosition = new Coordinate(7, 7);
      Assertions.assertEquals('b', game.getGameBoard().getPiece(testPosition).getType());
      Assertions.assertEquals('w', game.getGameBoard().getPiece(testPosition).getColor());
   }

   @Test
   public void promoteWhiteKnight() {
      //creates a new game with a white pawn available to be promoted
      Game game = new Game("rnbqkbnP/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNR b KQq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn to a knight
      game.promote(3);

      //checks that the piece on h8 is now a white knight
      Coordinate testPosition = new Coordinate(7, 7);
      Assertions.assertEquals('n', game.getGameBoard().getPiece(testPosition).getType());
      Assertions.assertEquals('w', game.getGameBoard().getPiece(testPosition).getColor());
   }


   @Test
   public void promoteBlackQueen() {
      //creates a new game with a black pawn available to be promoted
      Game game = new Game("rnbqkbnr/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNp b Qq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn to a queen
      game.promote(0);

      //checks that the piece on h1 is now a black queen
      Coordinate testPosition = new Coordinate(7, 0);
      Assertions.assertEquals('q', game.getGameBoard().getPiece(testPosition).getType());
      Assertions.assertEquals('b', game.getGameBoard().getPiece(testPosition).getColor());
   }

   @Test
   public void promoteBlackRook() {
      //creates a new game with a black pawn available to be promoted
      Game game = new Game("rnbqkbnr/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNp b Qq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn to a rook
      game.promote(1);

      //checks that the piece on h1 is now a black rook
      Coordinate testPosition = new Coordinate(7, 0);
      Assertions.assertEquals('r', game.getGameBoard().getPiece(testPosition).getType());
      Assertions.assertEquals('b', game.getGameBoard().getPiece(testPosition).getColor());
   }

   @Test
   public void promoteBlackBishop() {
      //creates a new game with a black pawn available to be promoted
      Game game = new Game("rnbqkbnr/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNp b Qq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn to a bishop
      game.promote(2);

      //checks that the piece on h1 is now a black bishop
      Coordinate testPosition = new Coordinate(7, 0);
      Assertions.assertEquals('b', game.getGameBoard().getPiece(testPosition).getType());
      Assertions.assertEquals('b', game.getGameBoard().getPiece(testPosition).getColor());
   }

   @Test
   public void promoteBlackKnight() {
      //creates a new game with a black pawn available to be promoted
      Game game = new Game("rnbqkbnr/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNp b Qq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn to a bishop
      game.promote(3);

      //checks that the piece on h1 is now a black bishop
      Coordinate testPosition = new Coordinate(7, 0);
      Assertions.assertEquals('n', game.getGameBoard().getPiece(testPosition).getType());
      Assertions.assertEquals('b', game.getGameBoard().getPiece(testPosition).getColor());
   }

}
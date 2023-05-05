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
public class PieceTest {
   /**
    * A method to test whether the white king's position is properly tracked
    *
    * @param testBoard     The setup of a board with a move available for the white king
    * @param testPosition  The initial position of the white king
    * @param pieceToMove   The white king piece
    * @param testPosition2 The position for the white king to move to
    */
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

   /**
    * A method to test whether the black king's position is properly tracked
    *
    * @param testBoard     The setup of a board with a move available for the black king
    * @param testPosition  The initial position of the black king
    * @param pieceToMove   The black king piece
    * @param testPosition2 The position for the black king to move to
    */
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

   /**
    * A method to test whether a piece's colour is stored correctly
    *
    * @param testBoard    The setup of a board in the starting position
    * @param testPosition The position of the white rook on the a1 square
    */
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

   /**
    * A method to test whether a piece's type is stored correctly
    *
    * @param testBoard    The setup of a board in the starting position
    * @param testPosition The position of the white knight on the b1 square
    */
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

   /**
    * A method to test whether a piece's position is stored correctly
    *
    * @param testPosition The F1 square of the board
    */
   @Test
   //FR2 Tests
   public void testGetPiecePosition() {
      //selects the f1 square
      Coordinate testPosition = new Coordinate(5, 0);

      //checks if the expected piece position is the same as actual piece position - which should be 5,0
      Assertions.assertEquals(5, testPosition.x);
      Assertions.assertEquals(0, testPosition.y);
   }

   /**
    * A method to test that white is able to promote a pawn to a queen
    *
    * @param game         The setup of a new game with a white pawn on the 8th rank, ready for promotion
    * @param testPosition The position of the white pawn on the H8 square
    */
   @Test
   //FR5 Tests
   public void testPromoteWhiteQueen() {
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

   /**
    * A method to test that white is able to promote a pawn to a rook
    *
    * @param game         The setup of a new game with a white pawn on the 8th rank, ready for promotion
    * @param testPosition The position of the white pawn on the H8 square
    */
   @Test
   //FR5 Tests
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

   /**
    * A method to test that white is able to promote a pawn to a bishop
    *
    * @param game         The setup of a new game with a white pawn on the 8th rank, ready for promotion
    * @param testPosition The position of the white pawn on the H8 square
    */
   @Test
   //FR5 Tests
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

   /**
    * A method to test that white is able to promote a pawn to a knight
    *
    * @param game         The setup of a new game with a white pawn on the 8th rank, ready for promotion
    * @param testPosition The position of the white pawn on the H8 square
    */
   @Test
   //FR5 Tests
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

   /**
    * A method to test that black is able to promote a pawn to a queen
    *
    * @param game         The setup of a new game with a black pawn on the 1st rank, ready for promotion
    * @param testPosition The position of the black pawn on the H1 square
    */
   @Test
   //FR5 Tests
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

   /**
    * A method to test that black is able to promote a pawn to a rook
    *
    * @param game         The setup of a new game with a black pawn on the 1st rank, ready for promotion
    * @param testPosition The position of the black pawn on the H1 square
    */
   @Test
   //FR5 Tests
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

   /**
    * A method to test that black is able to promote a pawn to a bishop
    *
    * @param game         The setup of a new game with a black pawn on the 1st rank, ready for promotion
    * @param testPosition The position of the black pawn on the H1 square
    */
   @Test
   //FR5 Tests
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

   /**
    * A method to test that black is able to promote a pawn to a knight
    *
    * @param game         The setup of a new game with a black pawn on the 1st rank, ready for promotion
    * @param testPosition The position of the black pawn on the H1 square
    */
   @Test
   //FR5 Tests
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

   /**
    * A method to test that a white queen moves correctly after promotion
    *
    * @param game         The setup of a new game with a white pawn on the 8th rank, ready for promotion
    * @param pieceToMove  The promoted white queen piece
    * @param testPosition The position for the selected piece to move to
    */
   @Test
   //FR5 Tests
   public void testMoveAfterPromoteWhiteQueen() {
      //creates a new game with a white pawn available to be promoted
      Game game = new Game("rnbqkbnP/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNR b KQq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn on h8 to a queen
      game.promote(0);

      //sets the promoted piece as a test piece to move
      Piece pieceToMove = game.getGameBoard().getPiece(new Coordinate(7, 7));
      Coordinate testPosition = new Coordinate(7, 3);

      for (Coordinate currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getCoordinateAsBoardNotation().equals(testPosition.getCoordinateAsBoardNotation())) {
            game.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      Assertions.assertEquals("rnbqkbn1/ppppppp1/8/8/7Q/8/PPPPPPP1/RNBQKBNR", game.getGameBoard().getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to test that a white rook moves correctly after promotion
    *
    * @param game         The setup of a new game with a white pawn on the 8th rank, ready for promotion
    * @param pieceToMove  The promoted white rook piece
    * @param testPosition The position for the selected piece to move to
    */
   @Test
   //FR5 Tests
   public void testMoveAfterPromoteWhiteRook() {
      //creates a new game with a white pawn available to be promoted
      Game game = new Game("rnbqkbnP/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNR b KQq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn on h8 to a rook
      game.promote(1);

      //sets the promoted piece as a test piece to move
      Piece pieceToMove = game.getGameBoard().getPiece(new Coordinate(7, 7));
      Coordinate testPosition = new Coordinate(7, 3);

      for (Coordinate currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getCoordinateAsBoardNotation().equals(testPosition.getCoordinateAsBoardNotation())) {
            game.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      Assertions.assertEquals("rnbqkbn1/ppppppp1/8/8/7R/8/PPPPPPP1/RNBQKBNR", game.getGameBoard().getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to test that a white bishop moves correctly after promotion
    *
    * @param game         The setup of a new game with a white pawn on the 8th rank, ready for promotion
    * @param pieceToMove  The promoted white bishop piece
    * @param testPosition The position for the selected piece to move to
    */
   @Test
   //FR5 Tests
   public void testMoveAfterPromoteWhiteBishop() {
      //creates a new game with a white pawn available to be promoted
      Game game = new Game("rnbqkbnP/pppppp2/8/8/8/8/PPPPPPP1/RNBQKBNR b KQq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn on h8 to a bishop
      game.promote(2);

      //sets the promoted piece as a test piece to move
      Piece pieceToMove = game.getGameBoard().getPiece(new Coordinate(7, 7));
      Coordinate testPosition = new Coordinate(3, 3);

      for (Coordinate currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getCoordinateAsBoardNotation().equals(testPosition.getCoordinateAsBoardNotation())) {
            game.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      Assertions.assertEquals("rnbqkbn1/pppppp2/8/8/3B4/8/PPPPPPP1/RNBQKBNR", game.getGameBoard().getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to test that a white knight moves correctly after promotion
    *
    * @param game         The setup of a new game with a white pawn on the 8th rank, ready for promotion
    * @param pieceToMove  The promoted white knight piece
    * @param testPosition The position for the selected piece to move to
    */
   @Test
   //FR5 Tests
   public void testMoveAfterPromoteWhiteKnight() {
      //creates a new game with a white pawn available to be promoted
      Game game = new Game("rnbqkbnP/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNR b KQq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn on h8 to a knight
      game.promote(3);

      //sets the promoted piece as a test piece to move
      Piece pieceToMove = game.getGameBoard().getPiece(new Coordinate(7, 7));
      Coordinate testPosition = new Coordinate(6, 5);

      for (Coordinate currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getCoordinateAsBoardNotation().equals(testPosition.getCoordinateAsBoardNotation())) {
            game.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      Assertions.assertEquals("rnbqkbn1/ppppppp1/6N1/8/8/8/PPPPPPP1/RNBQKBNR", game.getGameBoard().getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to test that a black queen moves correctly after promotion
    *
    * @param game         The setup of a new game with a black pawn on the 1st rank, ready for promotion
    * @param pieceToMove  The promoted black queen piece
    * @param testPosition The position for the selected piece to move to
    */
   @Test
   //FR5 Tests
   public void testMoveAfterPromoteBlackQueen() {
      //creates a new game with a black pawn available to be promoted
      Game game = new Game("rnbqkbnr/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNp b Qq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn on h1 to a queen
      game.promote(0);

      //sets the promoted piece as a piece to move
      Piece pieceToMove = game.getGameBoard().getPiece(new Coordinate(7, 0));
      Coordinate testPosition = new Coordinate(7, 5);

      for (Coordinate currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getCoordinateAsBoardNotation().equals(testPosition.getCoordinateAsBoardNotation())) {
            game.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      Assertions.assertEquals("rnbqkbnr/ppppppp1/7q/8/8/8/PPPPPPP1/RNBQKBN1", game.getGameBoard().getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to test that a black rook moves correctly after promotion
    *
    * @param game         The setup of a new game with a black pawn on the 1st rank, ready for promotion
    * @param pieceToMove  The promoted black rook piece
    * @param testPosition The position for the selected piece to move to
    */
   @Test
   //FR5 Tests
   public void testMoveAfterPromoteBlackRook() {
      //creates a new game with a black pawn available to be promoted
      Game game = new Game("rnbqkbnr/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNp b Qq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn on h1 to a rook
      game.promote(1);

      //sets the promoted piece as a piece to move
      Piece pieceToMove = game.getGameBoard().getPiece(new Coordinate(7, 0));
      Coordinate testPosition = new Coordinate(7, 5);

      for (Coordinate currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getCoordinateAsBoardNotation().equals(testPosition.getCoordinateAsBoardNotation())) {
            game.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      Assertions.assertEquals("rnbqkbnr/ppppppp1/7r/8/8/8/PPPPPPP1/RNBQKBN1", game.getGameBoard().getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to test that a black bishop moves correctly after promotion
    *
    * @param game         The setup of a new game with a black pawn on the 1st rank, ready for promotion
    * @param pieceToMove  The promoted black bishop piece
    * @param testPosition The position for the selected piece to move to
    */
   @Test
   //FR5 Tests
   public void testMoveAfterPromoteBlackBishop() {
      //creates a new game with a black pawn available to be promoted
      Game game = new Game("rnbqkbnr/ppppppp1/8/8/8/8/PPPPPP2/RNBQKBNp b Qq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn on h1 to a bishop
      game.promote(2);

      //sets the promoted piece as a piece to move
      Piece pieceToMove = game.getGameBoard().getPiece(new Coordinate(7, 0));
      Coordinate testPosition = new Coordinate(4, 3);

      for (Coordinate currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getCoordinateAsBoardNotation().equals(testPosition.getCoordinateAsBoardNotation())) {
            game.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      Assertions.assertEquals("rnbqkbnr/ppppppp1/8/8/4b3/8/PPPPPP2/RNBQKBN1", game.getGameBoard().getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to test that a black knight moves correctly after promotion
    *
    * @param game         The setup of a new game with a black pawn on the 1st rank, ready for promotion
    * @param pieceToMove  The promoted black knight piece
    * @param testPosition The position for the selected piece to move to
    */
   @Test
   //FR5 Tests
   public void testMoveAfterPromoteBlackKnight() {
      //creates a new game with a black pawn available to be promoted
      Game game = new Game("rnbqkbnr/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNp b Qq - 0 1", "test", false);

      //checks if there is a promotion available
      game.isPromotionAvailable();
      //promotes the pawn on h1 to a knight
      game.promote(3);

      //sets the promoted piece as a piece to move
      Piece pieceToMove = game.getGameBoard().getPiece(new Coordinate(7, 0));
      Coordinate testPosition = new Coordinate(6, 2);

      for (Coordinate currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getCoordinateAsBoardNotation().equals(testPosition.getCoordinateAsBoardNotation())) {
            game.getGameBoard().movePiece(pieceToMove, testPosition);
         }
      }
      Assertions.assertEquals("rnbqkbnr/ppppppp1/8/8/8/6n1/PPPPPPP1/RNBQKBN1", game.getGameBoard().getForsythEdwardsBoardNotationArrayIndex(0));
   }

}
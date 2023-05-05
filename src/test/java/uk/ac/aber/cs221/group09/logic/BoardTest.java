/*
 * @(GP9) BoardTest.java 1.0 2023-05-02
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.group09.logic.pieces.Piece;
import uk.ac.aber.cs221.group09.util.Vector2;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * BoardTest - Testing class for the Board Class
 * <p>
 * This class stores the testing related to the movements of the pieces on the board
 *
 * @author Craymon Chan
 * @author Jim Brown
 * @author Sean Hobson
 * @version 1.0 (Release)
 * @see BoardTest
 */
public class BoardTest {
   private static Board testBoard;

   /**
    * A method to test whether the FEN string is returned correctly
    *
    * @param testBoard the setup of a starting board
    */
   @Test
   // FR3 Tests
   public void testGetForsythEdwardsNotation() {
      // Creates a new board
      testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      // Checks whether the function returns notation correctly
      assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", testBoard.getForsythEdwardsBoardNotation());
   }

   /**
    * A method to test whether the FEN string's index is returned correctly
    *
    * @param testBoard the setup of a starting board
    */
   @Test
   // FR3 Tests
   public void testGetForsythEdwardsBoardNotationArrayIndex() {
      // Creates a new board
      Board testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      // Checks whether the function returns notation index correctly
      String expected = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
      String result = testBoard.getForsythEdwardsBoardNotationArrayIndex(0);

      // Checks if the expected index is returned correctly
      assertEquals(expected, result);
   }

   /**
    * A method to check the current player
    *
    * @param testBoard the setup of a starting board
    */
   @Test
   // FR2 Tests
   public void testDetermineCurrentPlayer() {
      // Creates a new board
      testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      // Checks the current player
      Assertions.assertEquals("w", testBoard.getForsythEdwardsBoardNotationArrayIndex(1));
   }

   /**
    * A method to check whether piece movement is working correctly
    *
    * @param testBoard    the setup of a starting board
    * @param pieceToMove  the white rook
    * @param testPosition position for the white rook to move to
    */
   @Test
   // FR5 Tests
   public void testMakeLegalMove() {
      // Creates a new board
      testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      // Initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      // Calculates all the legal moves for the given player
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      // Sets a test piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(0, 1));

      // Moves piece into test position to check whether the legal moves are valid
      Vector2 testPosition = new Vector2(0, 3);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         // Checks if the expected board is the same as actual board
      }
      Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether piece moved will allow king into check
    *
    * @param testBoard    the setup of a board where the white king will be checked if a white pawn is moved
    * @param pieceToMove  the white pawn
    * @param testPosition position for the white pawn to move to
    */
   @Test
   // FR5 and FR6 Tests
   public void testMoveIntoCheck() {
      // Creates a new board with the pawn at e2 pinned to the white king
      testBoard = new Board("rnb1kbnr/pppp1ppp/4p3/8/7q/PP6/2PPPPPP/RNBQKBNR w KQkq - 0 1");

      // Initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      // Calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      // Sets the pawn at e2 as a test piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(5, 1));

      // Attempts to move the pinned pawn, which is an illegal move
      Vector2 testPosition = new Vector2(5, 2);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         // Check if the expected board is the same as actual board - the pawn should not have moved
      }
      Assertions.assertEquals("rnb1kbnr/pppp1ppp/4p3/8/7q/PP6/2PPPPPP/RNBQKBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check as illegal piece movement is not allowed
    *
    * @param testBoard    the setup of a starting board
    * @param pieceToMove  the white rook
    * @param testPosition position for the white rook to move to
    */
   @Test
   // FR5 Tests
   public void testMakeIllegalMove() {
      // Creates a new board
      testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      // Initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      // Calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      // Sets the pawn at a2 as a test piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(0, 1));

      // Attempts to move the pawn at a2 to a6, which is an illegal move
      Vector2 testPosition = new Vector2(0, 5);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         // Check if the expected board is the same as actual board - the pawn should not have moved
      }
      Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check as illegal piece movement for king to be in check is not allowed
    *
    * @param testBoard    the setup of a board where the white king is in check
    * @param pieceToMove  the white pawn
    * @param testPosition position for the white pawn to move to
    */
   @Test
   // FR5 Tests
   public void testMakeIllegalMoveInCheck() {
      // Creates a new board with the white king in check
      testBoard = new Board("4k3/8/8/8/8/8/P7/4K2r w - - 0 1");

      // Initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      // Calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      // Sets the pawn at a2 as a test piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(0, 1));

      // Attempts to move the pawn at a2, which is an illegal move
      Vector2 testPosition = new Vector2(0, 2);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         // Check if the expected board is the same as actual board - the pawn should not have moved
      }
      Assertions.assertEquals("4k3/8/8/8/8/8/P7/4K2r", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether the castling function is working correctly
    *
    * @param testBoard    the setup of a board where the white king side knight and bishop is missing
    * @param pieceToMove  the white king
    * @param testPosition position for the white king to move to
    */
   @Test
   // FR5 Tests
   public void testCastling() {
      // Creates a new board with the white king side knight and bishop missing, so that white can castle
      testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQK2R w KQkq - 0 1");

      // Initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      // Calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      // Sets the white king as a test piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(4, 0));

      // Castles the white king by moving it two spaces to the right
      Vector2 testPosition = new Vector2(6, 0);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
      }
      // Check if the expected board is the same as actual board - the white king should be castled
      Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQ1RK1", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether the castling function is not allowed when the king is blocked
    *
    * @param testBoard    the setup of a starting board
    * @param pieceToMove  the white king
    * @param testPosition position for the white king to move to
    */
   @Test
   //FR5 Tests
   public void testCastlingBlocked() {
      // Create a new board with the initial setup of a chess game
      testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      // Initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      // Calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      // Sets the white king as a test piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(4, 0));

      // Attempts to castle the white king by moving it two spaces to the right, which is an illegal move
      Vector2 testPosition = new Vector2(6, 0);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
      }
      // Check if the expected board is the same as actual board - the pieces should not have moved
      Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether castling on the white king's side is not allowed
    *
    * @param testBoard    the setup of a board where only white king and white rooks are available on the first line
    * @param pieceToMove  the white king
    * @param testPosition position for the white king to move to
    */
   @Test
   // FR5 Tests
   public void testCastlingIllegalWhiteKingSide() {
      // Creates a new board without any bishops, knights, or queens. White is unable to castle on the king side
      testBoard = new Board("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w Qkq - 0 1");

      // Initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      // Calculates all the legal moves white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      // Sets the white king as a test piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(4, 0));

      // Attempts to castle the white king on the king side, by moving it two spaces to the right
      Vector2 testPosition = new Vector2(6, 0);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
      }
      // Check if the expected board is the same as actual board - the king should not have moved
      Assertions.assertEquals("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether castling on the white queen's side is not allowed
    *
    * @param testBoard    the setup of a board where only white king and white rooks are available on the first line
    * @param pieceToMove  the white king
    * @param testPosition position for the white king to move to
    */
   @Test
   // FR5 Tests
   public void testCastlingIllegalWhiteQueenSide() {
      // Creates a new board without any bishops, knights, or queens. White is unable to castle on the queen side
      testBoard = new Board("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w Kkq - 0 1");

      // Initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      // Calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      // Sets the white king as a test piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(4, 0));

      // Attempts to castle the white king on the queen side, by moving it two spaces to the left
      Vector2 testPosition = new Vector2(2, 0);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
      }
      // Check if the expected board is the same as actual board - the king should not have moved
      Assertions.assertEquals("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether castling on the black king's side is not allowed
    *
    * @param testBoard    the setup of a board where only black king and black rooks are available on the first line
    * @param pieceToMove  the black king
    * @param testPosition position for the black king to move to
    */
   @Test
   // FR5 Tests
   public void testCastlingIllegalBlackKingSide() {
      // Creates a new board without any bishops, knights, or queens. Black is unable to castle on the king side
      testBoard = new Board("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQq - 0 1");

      // Initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      // Calculates all the legal moves for black
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      // Sets the black king as a test piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(4, 7));

      // Attempts to castle the black king on the king side, by moving it two spaces to the right
      Vector2 testPosition = new Vector2(6, 7);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
      }
      //check if the expected board is the same as actual board - the king should not have moved
      Assertions.assertEquals("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether castling on the black queen's side is not allowed
    *
    * @param testBoard    the setup of a board where only black king and black rooks are available on the first line
    * @param pieceToMove  the black king
    * @param testPosition position for the black king to move to
    */
   @Test
   //FR5 Tests
   public void testCastlingIllegalBlackQueenSide() {
      //creates a new board without any bishops, knights, or queens. Black is unable to castle on the queen side
      testBoard = new Board("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQk - 0 1");

      //initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      //calculates all the legal moves for black
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //sets the black king as a test piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(4, 7));

      //attempts to castle the black king on the queen side, by moving it two spaces to the left
      Vector2 testPosition = new Vector2(2, 7);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
      }
      //check if the expected board is the same as actual board - the king should not have moved
      Assertions.assertEquals("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether the En Passant function is working correctly
    *
    * @param testBoard    the setup of a board where white pawn is at(4,4) while black pawn is at(3,4)
    * @param pieceToMove  the white pawn
    * @param testPosition position for the white pawn to move to
    */
   @Test
   //FR5 Tests
   public void testEnPassant() {
      //creates a new board with an en passant move available for the white pawn at e5
      testBoard = new Board("rnbqkbnr/ppp1p1pp/5p2/3pP3/8/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 1");


      //initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      //calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //sets the pawn at e5 as a test piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(4, 4));

      //moves the pawn at e5 to d6, capturing the black pawn at d5 in the process
      Vector2 testPosition = new Vector2(3, 5);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         //check if the expected board is the same as actual board
      }
      Assertions.assertEquals("rnbqkbnr/ppp1p1pp/3P1p2/8/8/8/PPPP1PPP/RNBQKBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether the Pawn Pieces are moving correctly
    *
    * @param testBoard    the setup of a starting board
    * @param pieceToMove  the white pawn
    * @param testPosition position for the white pawn to move to
    */
   @Test
   //FR5 Tests
   public void testMakeLegalMovePawn() {
      //creates a new board
      testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      //initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      //calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //sets the pawn at a2 as a test piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(0, 1));

      //moves the pawn at a2 forward two spaces
      Vector2 testPosition = new Vector2(0, 3);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         //check if the expected board is the same as actual board - the pawn should have moved
      }
      Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether the Knight Pieces are moving correctly
    *
    * @param testBoard    the setup of a starting board
    * @param pieceToMove  the white knight
    * @param testPosition position for the white knight to move to
    */
   @Test
   //FR5 Tests
   public void testMakeLegalMoveKnight() {
      //creates a new board
      testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

      //initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      //calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //sets the white knight at b1 as a test piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(1, 0));

      //moves the knight from b1 to c3
      Vector2 testPosition = new Vector2(2, 2);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         //check if the expected board is the same as actual board - the knight should be on the c3 square
      }
      Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/2N5/PPPPPPPP/R1BQKBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether the Rook Pieces are moving correctly
    *
    * @param testBoard    the setup of a board allowing rook pieces to move
    * @param pieceToMove  the white rook
    * @param testPosition position for the white rook to move to
    */
   @Test
   //FR5 Tests
   public void testMakeLegalMoveRook() {
      //creates a new board without a white bishop or knight on b1 and c1
      testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/R2QKBNR w KQkq - 0 1");

      //initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      //calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //sets the rook at a1 as a testing piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(0, 0));

      //moves the rook from a1 to c1
      Vector2 testPosition = new Vector2(2, 0);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         //check if the expected board is the same as actual board - the rook should be on the c1 square
      }
      Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/2RQKBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether the Bishop Pieces are moving correctly
    *
    * @param testBoard    the setup of a board allowing bishop pieces to move
    * @param pieceToMove  the white bishop
    * @param testPosition position for the white bishop to move to
    */
   @Test
   //FR5 Tests
   public void testMakeLegalMoveBishop() {
      //creates a new board with legal moves available for the white bishop at f1
      testBoard = new Board("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1");

      //initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      //calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //sets the white bishop at f1 as a testing piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(5, 0));

      //moves the bishop from f1 to c4
      Vector2 testPosition = new Vector2(2, 3);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         //check if the expected board is the same as actual board - the bishop should be on the c4 square
      }
      Assertions.assertEquals("rnbqkbnr/pppp1ppp/8/4p3/2B1P3/8/PPPP1PPP/RNBQK1NR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether the Queen Piece is moving correctly
    *
    * @param testBoard    the setup of a board allowing queen piece to move
    * @param pieceToMove  the white queen
    * @param testPosition position for the white queen to move to
    */
   @Test
   //FR5 Tests
   public void testMakeLegalMoveQueen() {
      //creates a new board with a legal move for the white queen
      testBoard = new Board("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 1");

      //initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      //calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //sets the white queen as a testing piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(3, 0));

      //moves the queen from d1 to g4
      Vector2 testPosition = new Vector2(6, 3);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         //check if the expected board is the same as actual board - the queen should be on the g4 square
      }
      Assertions.assertEquals("rnbqkbnr/pppp1ppp/8/4p3/4P1Q1/8/PPPP1PPP/RNB1KBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether the King Piece is moving correctly
    *
    * @param testBoard    the setup of a board allowing king piece to move
    * @param pieceToMove  the white king
    * @param testPosition position for the white king to move to
    */
   @Test
   //FR5 Tests
   public void testMakeLegalMoveKing() {
      //creates a new board with a legal move for the white king
      testBoard = new Board("rnbqkbnr/ppp2ppp/3p4/4p3/4P1Q1/8/PPPP1PPP/RNB1KBNR w KQkq - 0 1");

      //initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      //calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //sets the white king as a testing piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(4, 0));

      //moves the king from e1 to d1
      Vector2 testPosition = new Vector2(3, 0);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         //check if the expected board is the same as actual board - the king should be on the d1 square
      }
      Assertions.assertEquals("rnbqkbnr/ppp2ppp/3p4/4p3/4P1Q1/8/PPPP1PPP/RNBK1BNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether capturing as pawn is working correctly
    *
    * @param testBoard    the setup of a board allowing pawn piece to capture
    * @param pieceToMove  the white pawn
    * @param testPosition position for the white pawn to capture and move to
    */
   @Test
   //FR5 Tests
   public void testCapturePawn() {
      //creates a new board with a white pawn able to make a capture
      testBoard = new Board("rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1");

      //initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      //calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //sets the pawn at e4 as a testing piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(4, 3));

      //moves the pawn from e4 to d5, capturing the black pawn which was previously there
      Vector2 testPosition = new Vector2(3, 4);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         //check if the expected board is the same as actual board - the pawn should be on d5, and the black pawn should be captured
      }
      Assertions.assertEquals("rnbqkbnr/ppp1pppp/8/3P4/8/8/PPPP1PPP/RNBQKBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether capturing as pieces to avoid king in check
    *
    * @param testBoard    the setup of a board allowing pawn piece to capture other pieces to avoid king getting in check
    * @param pieceToMove  the white pawn
    * @param testPosition position for the white pawn to capture and move to
    */
   @Test
   //FR5 Tests
   public void testCaptureInCheck() {
      //creates a new board with the white king in check from the bishop at b4, and a white pawn at a3 which can capture the bishop
      testBoard = new Board("rnbqk1nr/pp1p1ppp/2p1p3/8/1b6/P2P4/1PP1PPPP/RNBQKBNR w KQkq - 0 1");

      //initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      //calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //sets the pawn at a3 as a testing piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(0, 2));

      //moves the pawn to capture the bishop at b4, stopping the check
      Vector2 testPosition = new Vector2(1, 3);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         //check if the expected board is the same as actual board - the pawn should be on b4, and the black bishop should be captured
      }
      Assertions.assertEquals("rnbqk1nr/pp1p1ppp/2p1p3/8/1P6/3P4/1PP1PPPP/RNBQKBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether capturing as knight is working correctly
    *
    * @param testBoard    the setup of a board allowing knight piece to capture
    * @param pieceToMove  the white knight
    * @param testPosition position for the white knight to capture and move to
    */
   @Test
   //FR5 Tests
   public void testCaptureKnight() {
      //creates a new board with a white knight able to make a capture
      testBoard = new Board("rnbqkbnr/ppp1pppp/8/3p4/8/2N5/PPPPPPPP/R1BQKBNR w KQkq - 0 1");

      //initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      //calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //sets the knight at c3 as a testing piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(2, 2));

      //moves the knight to capture the pawn at d5
      Vector2 testPosition = new Vector2(3, 4);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         //check if the expected board is the same as the actual board - the knight should be on d5, and the black pawn should be captured
      }
      Assertions.assertEquals("rnbqkbnr/ppp1pppp/8/3N4/8/8/PPPPPPPP/R1BQKBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether capturing as bishop is working correctly
    *
    * @param testBoard    the setup of a board allowing bishop piece to capture
    * @param pieceToMove  the white bishop
    * @param testPosition position for the white bishop to capture and move to
    */
   @Test
   //FR5 Tests
   public void testCaptureBishop() {
      //creates a new board with a white bishop able to make a capture
      testBoard = new Board("rnbqkbnr/pppppp1p/8/6p1/8/3P4/PPP1PPPP/RNBQKBNR w KQkq - 0 1");

      //initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      //calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //sets the bishop at c1 as a testing piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(2, 0));

      //moves the bishop to capture the pawn at g5
      Vector2 testPosition = new Vector2(6, 4);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         //check if the expected board is the same as the actual board - the bishop should be on g5, and the black pawn should be captured
      }
      Assertions.assertEquals("rnbqkbnr/pppppp1p/8/6B1/8/3P4/PPP1PPPP/RN1QKBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether capturing as rook is working correctly
    *
    * @param testBoard    the setup of a board allowing rook piece to capture
    * @param pieceToMove  the white rook
    * @param testPosition position for the white rook to capture and move to
    */
   @Test
   //FR5 Tests
   public void testCaptureRook() {
      //creates a new board with a white rook able to make a capture
      testBoard = new Board("rnbqkbnr/ppp2ppp/3p4/P7/R3p3/8/1PPPPPPP/1NBQKBNR w Kkq - 0 4");

      //initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      //calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //sets the rook at a4 as a testing piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(0, 3));

      //moves the rook to capture the pawn at e4
      Vector2 testPosition = new Vector2(4, 3);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         //check if the expected board is the same as the actual board - the rook should be on e4, and the black pawn should be captured
      }
      Assertions.assertEquals("rnbqkbnr/ppp2ppp/3p4/P7/4R3/8/1PPPPPPP/1NBQKBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether capturing as queen is working correctly
    *
    * @param testBoard    the setup of a board allowing queen piece to capture
    * @param pieceToMove  the white queen
    * @param testPosition position for the white queen to capture and move to
    */
   @Test
   //FR5 Tests
   public void testCaptureQueen() {
      //creates a new board with the white queen able to make a capture
      testBoard = new Board("rnbqkbnr/ppppppp1/8/7p/4P3/8/PPPP1PPP/RNBQKBNR w KQkq h6 0 2");

      //initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      //calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //sets the white queen as a testing piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(3, 0));

      //moves the queen to capture the pawn at h5
      Vector2 testPosition = new Vector2(7, 4);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         //check if the expected board is the same as the actual board - the queen should be on h5, and the black pawn should be captured
      }
      Assertions.assertEquals("rnbqkbnr/ppppppp1/8/7Q/4P3/8/PPPP1PPP/RNB1KBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether capturing as king is working correctly
    *
    * @param testBoard    the setup of a board allowing king piece to capture
    * @param pieceToMove  the white king
    * @param testPosition position for the white king to capture and move to
    */
   @Test
   //FR5 Tests
   public void testCaptureKing() {
      //creates a new board with the white king able to make a capture
      testBoard = new Board("rnbqkbnr/pppp2pp/4p3/8/4Pp2/5K2/PPPP1PPP/RNBQ1BNR w kq - 0 4");

      //initialises an attacking player
      char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
      MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

      //calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //sets the white king as a testing piece to move
      Piece pieceToMove = testBoard.getPiece(new Vector2(5, 2));

      //moves the king to capture the pawn at f4
      Vector2 testPosition = new Vector2(5, 3);
      for (Vector2 currentMove : pieceToMove.getPossibleMoves()) {
         if (currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation())) {
            testBoard.movePiece(pieceToMove, testPosition);
         }
         //check if the expected board is the same as the actual board - the king should be on f4, and the black pawn should be captured
      }
      Assertions.assertEquals("rnbqkbnr/pppp2pp/4p3/8/4PK2/8/PPPP1PPP/RNBQ1BNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
   }

   /**
    * A method to check whether white side promotion is working correctly
    *
    * @param testBoard the setup of a board allowing white pawn to be promoted
    */
   @Test
   //FR5 Tests
   public void testCanWhitePromote() {
      //creates a new board where the white pawn is allowed to be promoted
      testBoard = new Board("rnbqkbnP/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNR b KQq - 0 1");

      //checks if white pawn meets the criteria to be promoted
      Assertions.assertEquals(true, testBoard.canWhitePromote());
   }

   /**
    * A method to check whether black side promotion is working correctly
    *
    * @param testBoard the setup of a board allowing black pawn to be promoted
    */
   @Test
   //FR5 Tests
   public void testCanBlackPromote() {
      //creates a new board where the black pawn is allowed to be promoted
      testBoard = new Board("rnbqkbnr/ppppppp1/8/8/8/8/PPPPPPP1/RNBQKBNp w Qq - 0 1");

      //checks if black pawn meets the criteria to be promoted
      Assertions.assertEquals(true, testBoard.canBlackPromote());
   }


}




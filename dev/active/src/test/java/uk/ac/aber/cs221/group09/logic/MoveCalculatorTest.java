/*
 * @(GP9) MoveCalculator.java 1.0 2023-05-02
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * MoveCalculatorTest - Testing class for the MoveCalculator Class
 * <p>
 * This class stores the testing related to calculating the possible moves for pieces on the board
 *
 * @author Craymon Chan
 * @author Jim Brown
 * @author Sean Hobson
 * @version 1.0 (Release)
 * @see MoveCalculatorTest
 */
public class MoveCalculatorTest {
   private static Board testBoard;
   private static MoveCalculator moveCalculator;
   //Methods to catch console outputs as a variable for comparison purposes
   private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

   @BeforeEach
   public void setUpStream() {
      System.setOut(new PrintStream(outContent));
   }

   @AfterEach
   public void restoreStream() {
      System.setOut(null);
   }

   @Test
   //FR6 Tests
   /**
    * A method to test whether the white side's king is in check
    *
    * @param testBoard The setup of a board with the white king in check
    * @param moveCalculator Determine current player's turn and inserting Board to calculate moves
    */
   public void testisWhiteInCheck() {
      //creates a new board with the white king in check
      testBoard = new Board("rnb1kbnr/pppp1ppp/4p3/8/7q/4PP2/PPPP2PP/RNBQKBNR w KQkq - 1 3");
      moveCalculator = new MoveCalculator('w', testBoard);

      //calculates all the legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //returns true if white is in check
      Assertions.assertEquals(true, moveCalculator.isPlayerInCheck());
   }

   @Test
   //FR6 Tests
   /**
    * A method to test whether the black side's king is in check
    *
    * @param testBoard the setup of a board with the black king in check
    * @param moveCalculator Determine current player's turn and inserting Board to calculate moves
    */
   public void testisBlackInCheck() {
      //creates a new board with the white king in check
      testBoard = new Board("rnbqkbnr/ppppp1pp/5p2/7Q/4P3/8/PPPP1PPP/RNB1KBNR b KQkq - 1 2");
      moveCalculator = new MoveCalculator('b', testBoard);

      //calculates all the legal moves for black
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //returns true if black is in check
      Assertions.assertEquals(true, moveCalculator.isPlayerInCheck());
   }

   @Test
   //FR5 Tests
   /**
    * A method to test whether all legal moves for the pieces are calculated
    *
    * @param testBoard the setup of a board with the black king in check
    * @param moveCalculator determine player's possible moves to avoid the check
    */
   public void testFindLegalMoves() {
      //creates a new board with black in check
      testBoard = new Board("rnbqkbnr/ppppp1pp/5p2/7Q/4P3/8/PPPP1PPP/RNB1KBNR b KQkq - 1 2");
      moveCalculator = new MoveCalculator('b', testBoard);

      //calculates all the legal moves for black
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //Checks whether legal moves for the given player is returned correctly
      Assertions.assertEquals("legal moves for b pawn at f6 | \r\n" +
            "legal moves for b pawn at a7 | \r\n" +
            "legal moves for b pawn at b7 | \r\n" +
            "legal moves for b pawn at c7 | \r\n" +
            "legal moves for b pawn at d7 | \r\n" +
            "legal moves for b pawn at e7 | \r\n" +
            "legal moves for b pawn at g7 | g6 \r\n" +
            "legal moves for b pawn at h7 | \r\n" +
            "legal moves for b rook at a8 | \r\n" +
            "legal moves for b knight at b8 | \r\n" +
            "legal moves for b bishop at c8 | \r\n" +
            "legal moves for b queen at d8 | \r\n" +
            "legal moves for b king at e8 | \r\n" +
            "legal moves for b bishop at f8 | \r\n" +
            "legal moves for b knight at g8 | \r\n" +
            "legal moves for b rook at h8 | \r\n", outContent.toString());
   }

   @Test
   //FR7 Tests
   /**
    * A method to test whether the current player is in checkmate
    *
    * @param testBoard the setup of a checkmate board state
    * @param moveCalculator determine player's available moves
    */
   public void testCheckmate() {
      //creates a new board with white in checkmate
      testBoard = new Board("rnb1kbnr/pppp1ppp/4p3/8/6Pq/5P2/PPPPP2P/RNBQKBNR w KQkq - 0 1");
      moveCalculator = new MoveCalculator('w', testBoard);

      //calculates legal moves for white
      moveCalculator.findLegalMovesForPlayer(true);
      moveCalculator.findLegalMovesForPlayer(false);

      //checks that white is in checkmate
      Assertions.assertEquals(true, moveCalculator.isPlayerInCheckMate());
   }
}
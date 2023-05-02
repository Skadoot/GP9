/*
 * @(#) MoveCalculator.java 0.1 2023-05-02
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

/**
 * MoveCalculatorTest - Testing class for the MoveCalculator Class
 * <p>
 * This class stores the testing related to calculating the possible moves for pieces on the board
 *
 * @author Craymon Chan
 * @author Jim Brown
 * @author Sean Hobson
 * @version 0.1 (draft)
 * @see uk.ac.aber.cs221.group09.logic.MoveCalculatorTest
 */

class MoveCalculatorTest {

    /**
     * Methods to catch console outputs as a variable for comparison purposes
     */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @BeforeAll
    public void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }
    @AfterEach
    public void restoreStream() {
        System.setOut(null);
    }


    private static Board testBoard;
    private static MoveCalculator moveCalculator;

    @Test
    //FR6 Tests
    /**
     * A method to test whether the white side's king is in checked state
     *
     * @param board the setup of board for white king to be checked
     * @param MoveCalculator determine player's turn and inserting Board to compare board status
     */
    public void testisWhiteInCheck() {
        //setup the board as white side king is in checked state
        testBoard = new Board("rnb1kbnr/pppp1ppp/4p3/8/7q/4PP2/PPPP2PP/RNBQKBNR w KQkq - 1 3");
        moveCalculator = new MoveCalculator('w', testBoard);

        //calculates all the legal moves for the given player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //If the white side player's king is in check, returns true
        Assertions.assertEquals(true, moveCalculator.isPlayerInCheck());
    }

    @Test
    //FR6 Tests
    /**
     * A method to test whether the black side's king is in checked state
     *
     * @param board the setup of board for black side's king to be checked
     * @param MoveCalculator determine player's turn and inserting Board to compare board status
     */
    public void testisBlackInCheck() {
        //setup the board as black side king is in checked state
        testBoard = new Board("rnbqkbnr/ppppp1pp/5p2/7Q/4P3/8/PPPP1PPP/RNB1KBNR b KQkq - 1 2");
        moveCalculator = new MoveCalculator('b', testBoard);

        //calculates all the legal moves for the given player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //If the black side player's king is in check, returns true
        Assertions.assertEquals(true, moveCalculator.isPlayerInCheck());
    }

    @Test
    //FR5 Tests
    /**
     * A method to test whether all legal moves for the pieces are eligible
     *
     * @param board the setup of a chessboard with the black king in check
     * @param MoveCalculator determine player's possible moves to avoid the check
     */
    public void testFindLegalMoves() {
        //setup the board chessboard with the black king in check
        testBoard = new Board("rnbqkbnr/ppppp1pp/5p2/7Q/4P3/8/PPPP1PPP/RNB1KBNR b KQkq - 1 2");
        moveCalculator = new MoveCalculator('b', testBoard);

        //calculate the legal moves for the current player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //Checking whether legal moves for the given player is returned correctly
        Assertions.assertEquals("legal moves for b pawn at f5 | \r\n" +
                "legal moves for b pawn at a6 | \r\n" +
                "legal moves for b pawn at b6 | \r\n" +
                "legal moves for b pawn at c6 | \r\n" +
                "legal moves for b pawn at d6 | \r\n" +
                "legal moves for b pawn at e6 | \r\n" +
                "legal moves for b pawn at g6 | g5 \r\n" +
                "legal moves for b pawn at h6 | \r\n" +
                "legal moves for b rook at a7 | \r\n" +
                "legal moves for b knight at b7 | \r\n" +
                "legal moves for b bishop at c7 | \r\n" +
                "legal moves for b queen at d7 | \r\n" +
                "legal moves for b king at e7 | \r\n" +
                "legal moves for b bishop at f7 | \r\n" +
                "legal moves for b knight at g7 | \r\n" +
                "legal moves for b rook at h7 | \r\n", outContent.toString());
    }

    @Test
    //FR7 Tests
    /**
     * A method to test whether any moves are available in checkmate
     *
     * @param board the setup of a checkmate board state
     * @param MoveCalculator determine player's available moves
     */
    public void testCheckmate(){
        //setup the chessboard with white in checkmate
        testBoard = new Board("rnb1kbnr/pppp1ppp/4p3/8/6Pq/5P2/PPPPP2P/RNBQKBNR w KQkq - 0 1");
        moveCalculator = new MoveCalculator('w', testBoard);

        //calculate legal moves for white
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //check that white has no legal moves
        Assertions.assertEquals("legal moves for w rook at a0 | \r\n" +
              "legal moves for w knight at b0 | \r\n" +
              "legal moves for w bishop at c0 | \r\n" +
              "legal moves for w queen at d0 | \r\n" +
              "legal moves for w king at e0 | \r\n" +
              "legal moves for w bishop at f0 | \r\n" +
              "legal moves for w knight at g0 | \r\n" +
              "legal moves for w rook at h0 | \r\n" +
              "legal moves for w pawn at a1 | \r\n" +
              "legal moves for w pawn at b1 | \r\n" +
              "legal moves for w pawn at c1 | \r\n" +
              "legal moves for w pawn at d1 | \r\n" +
              "legal moves for w pawn at e1 | \r\n" +
              "legal moves for w pawn at h1 | \r\n" +
              "legal moves for w pawn at f2 | \r\n" +
              "legal moves for w pawn at g3 | \r\n", outContent.toString());
    }





}
package uk.ac.aber.cs221.group09.logic;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MoveCalculatorTest {

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
    public void testFindLegalMoves() {
        //setup the board as invalid chessboard
        testBoard = new Board("rnbqkbnr/ppppp1pp/5p2/7Q/4P3/8/PPPP1PPP/RNB1KBNR b KQkq - 1 2");
        moveCalculator = new MoveCalculator('b', testBoard);

        //declare the legal moves for the given player as false
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




}
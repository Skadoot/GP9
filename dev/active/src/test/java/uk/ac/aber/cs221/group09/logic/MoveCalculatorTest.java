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
    //SE-F-052
    public void testisWhiteInCheck() {
        testBoard = new Board("rnb1kbnr/pppp1ppp/4p3/8/7q/4PP2/PPPP2PP/RNBQKBNR w KQkq - 1 3");
        moveCalculator = new MoveCalculator('w', testBoard);
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);
        Assertions.assertEquals(true, moveCalculator.isPlayerInCheck());
    }

    @Test
    //SE-F-052
    public void testisBlackInCheck() {
        testBoard = new Board("rnbqkbnr/ppppp1pp/5p2/7Q/4P3/8/PPPP1PPP/RNB1KBNR b KQkq - 1 2");
        moveCalculator = new MoveCalculator('b', testBoard);
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);
        Assertions.assertEquals(true, moveCalculator.isPlayerInCheck());
    }

    @Test
    //FR5 Tests
    public void testFindLegalMoves() {
        testBoard = new Board("rnbqkbnr/ppppp1pp/5p2/7Q/4P3/8/PPPP1PPP/RNB1KBNR b KQkq - 1 2");
        moveCalculator = new MoveCalculator('b', testBoard);
        moveCalculator.findLegalMovesForPlayer(false);

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
package testing;

import static org.junit.jupiter.api.Assertions.*;
import main.board;
import main.moveCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pieces.piece;
import vector.vector2;

class boardTest {
    private static board testBoard;

    @Test
    public void testGetForsythEdwardsNotation() {
        testBoard = new board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", testBoard.getForsythEdwardsBoardNotation());
    }

    @Test
    public void testGetForsythEdwardsBoardNotationArrayIndex() {
        board testBoard = new board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        String expected = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        String result = testBoard.getForsythEdwardsBoardNotationArrayIndex(0);
        assertEquals(expected, result);
    }

    @Test
    //SE-F-010
    public void testDetermineCurrentPlayer() {
        testBoard = new board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        Assertions.assertEquals("w",testBoard.getForsythEdwardsBoardNotationArrayIndex(1));
    }

    @Test
    //SE-F-007
    public void testGetPieceColor() {
        //create a new board
        board testBoard = new board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        vector2 testPosition = new vector2(0, 0);
        Assertions.assertEquals('w',testBoard.getPiece(testPosition).getColor());
    }


    @Test
    public void testGetPieceType() {
        //create a new board
        board testBoard = new board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        vector2 testPosition = new vector2(1, 0);
        Assertions.assertEquals('n',testBoard.getPiece(testPosition).getType());
    }

    @Test
    public void testGetPiecePosition() {
        //create a new board
        board testBoard = new board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        vector2 testPosition = new vector2(5, 0);
        System.out.println(testBoard.getPiece(testPosition).getPosition().x);
        System.out.println(testBoard.getPiece(testPosition).getPosition().y);
    }

    @Test
    public void testGetPiecePossibleMoves() {
        //create a new board
        board testBoard = new board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        vector2 testPosition = new vector2(0, 1);
        System.out.println(testBoard.getPiece(testPosition).getPosition().x);
        Assertions.assertEquals('n',testBoard.getPiece(testPosition).getType());
    }

    @Test
    public void testGetWhiteKingPosition() {
        // Create a new board with a white king at (4,0)
        board testBoard = new board("rnbqkbnr/pppp1ppp/4p3/8/8/4P3/PPPP1PPP/RNBQKBNR w KQkq - 0 1");
        vector2 testPosition = new vector2(4, 0); // initial position of white king

        piece pieceToMove = testBoard.getPiece(testPosition);

        // Move King into another position
        vector2 testPosition2 = new vector2(4,1);
        testBoard.movePiece(pieceToMove, testPosition2);

        Assertions.assertEquals(testPosition2,testBoard.getWhiteKingPosition());
    }

    @Test
    public void testGetBlackKingPosition() {
        // Create a new board with a black king at (4,7)
        board testBoard = new board("rnbqkbnr/pppp1ppp/4p3/8/8/4P3/PPPPKPPP/RNBQ1BNR b kq - 0 1");
        vector2 testPosition = new vector2(4, 7); // initial position of black king

        piece pieceToMove = testBoard.getPiece(testPosition);

        // Move King into another position
        vector2 testPosition2 = new vector2(4,6);
        testBoard.movePiece(pieceToMove, testPosition2);

        Assertions.assertEquals(testPosition2,testBoard.getBlackKingPosition());
    }

    @Test
    //FR5 Tests
    public void testMakeLegalMove() {
        testBoard = new board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        moveCalculator moveCalculator = new moveCalculator(attacking_player, testBoard);
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        piece pieceToMove = testBoard.getPiece(new vector2(0,1));


        vector2 testPosition = new vector2(0, 3);
        for(vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    //FR5 and FR6 Tests
    public void testMoveIntoCheck() {
        testBoard = new board("rnb1kbnr/pppp1ppp/4p3/8/7q/PP6/2PPPPPP/RNBQKBNR w KQkq - 0 1");

        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        moveCalculator moveCalculator = new moveCalculator(attacking_player, testBoard);

        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        piece pieceToMove = testBoard.getPiece(new vector2(5,1));


        vector2 testPosition = new vector2(5, 2);
        for(vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("rnb1kbnr/pppp1ppp/4p3/8/7q/PP6/2PPPPPP/RNBQKBN",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    //FR5 Tests
    public void testMakeIllegalMove() {
        testBoard = new board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        moveCalculator moveCalculator = new moveCalculator(attacking_player, testBoard);

        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        piece pieceToMove = testBoard.getPiece(new vector2(0,1));

        vector2 testPosition = new vector2(0, 5);
        for(vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    ////SE-F-052 and FR5 Tests
    public void testMakeIllegalMoveInCheck() {
        testBoard = new board("4k3/8/8/8/8/8/P7/4K2r w - - 0 1");

        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        moveCalculator moveCalculator = new moveCalculator(attacking_player, testBoard);

        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        piece pieceToMove = testBoard.getPiece(new vector2(0,1));

        vector2 testPosition = new vector2(0, 2);
        for(vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("4k3/8/8/8/8/8/P7/4K2r",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }


}
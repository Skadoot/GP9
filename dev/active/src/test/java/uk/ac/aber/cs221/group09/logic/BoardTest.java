package uk.ac.aber.cs221.group09.logic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.group09.logic.pieces.Piece;
import uk.ac.aber.cs221.group09.logic.vector.Vector2;

class BoardTest {
    private static Board testBoard;

    @Test
    //FR3 Tests
    public void testGetForsythEdwardsNotation() {
        //create a new board
        testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        //check whether the function returns notation correctly
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", testBoard.getForsythEdwardsBoardNotation());
    }

    @Test
    //FR3 Tests
    public void testGetForsythEdwardsBoardNotationArrayIndex() {
        //create a new board
        Board testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        //check whether the function returns notation index correctly
        String expected = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        String result = testBoard.getForsythEdwardsBoardNotationArrayIndex(0);
        assertEquals(expected, result);
    }

    @Test
    //FR2 Tests
    public void testDetermineCurrentPlayer() {
        //create a new board
        testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        //Check the current player
        Assertions.assertEquals("w",testBoard.getForsythEdwardsBoardNotationArrayIndex(1));
    }

    @Test
    //FR2 Tests
    public void testGetPieceColor() {
        //create a new board
        Board testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        //Get the color of a specific chess piece
        Vector2 testPosition = new Vector2(0, 0);
        Assertions.assertEquals('w',testBoard.getPiece(testPosition).getColor());
    }


    @Test
    //FR2 Tests
    public void testGetPieceType() {
        //create a new board
        Board testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        //Get the type of a specific chess piece
        Vector2 testPosition = new Vector2(1, 0);
        Assertions.assertEquals('n',testBoard.getPiece(testPosition).getType());
    }

    @Test
    //FR2 Tests
    public void testGetPiecePosition() {
        //create a new board
        Board testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        //Get the position of a specific chess piece
        Vector2 testPosition = new Vector2(5, 0);
        System.out.println(testBoard.getPiece(testPosition).getPosition().x);
        System.out.println(testBoard.getPiece(testPosition).getPosition().y);
    }

    @Test
    public void testGetPiecePossibleMoves() {
        //create a new board
        Board testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        //Check for possible moves for a single chess piece
        Vector2 testPosition = new Vector2(0, 1);
        System.out.println(testBoard.getPiece(testPosition).getPosition().x);
        Assertions.assertEquals('n',testBoard.getPiece(testPosition).getType());
    }

    @Test
    public void testGetWhiteKingPosition() {
        // Create a new board with a white king
        Board testBoard = new Board("rnbqkbnr/pppp1ppp/4p3/8/8/4P3/PPPP1PPP/RNBQKBNR w KQkq - 0 1");

        // initial position of white king and sets a test piece to move
        Vector2 testPosition = new Vector2(4, 0);
        Piece pieceToMove = testBoard.getPiece(testPosition);

        // Move king into another position
        Vector2 testPosition2 = new Vector2(4,1);
        testBoard.movePiece(pieceToMove, testPosition2);

        Assertions.assertEquals(testPosition2,testBoard.getWhiteKingPosition());
    }

    @Test
    public void testGetBlackKingPosition() {
        // Create a new board with a black king
        Board testBoard = new Board("rnbqkbnr/pppp1ppp/4p3/8/8/4P3/PPPPKPPP/RNBQ1BNR b kq - 0 1");

        // initial position of black king and sets a test piece to move
        Vector2 testPosition = new Vector2(4, 7);
        Piece pieceToMove = testBoard.getPiece(testPosition);

        // Move king into another position
        Vector2 testPosition2 = new Vector2(4,6);
        testBoard.movePiece(pieceToMove, testPosition2);

        Assertions.assertEquals(testPosition2,testBoard.getBlackKingPosition());
    }

    @Test
    //FR5 Tests
    public void testMakeLegalMove() {
        //create a new board
        testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        //Initialize an attacking player
        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

        //calculates all the legal moves for the given player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //Sets a test piece to move
        Piece pieceToMove = testBoard.getPiece(new Vector2(0,1));

        //Moves piece into test position to check whether the legal moves are valid
        Vector2 testPosition = new Vector2(0, 3);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    //FR5 and FR6 Tests
    public void testMoveIntoCheck() {
        //create a new board of black side in the checked state
        testBoard = new Board("rnb1kbnr/pppp1ppp/4p3/8/7q/PP6/2PPPPPP/RNBQKBNR w KQkq - 0 1");

        //Initialize an attacking player
        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

        //calculates all the legal moves for the given player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //Sets a test piece to move
        Piece pieceToMove = testBoard.getPiece(new Vector2(5,1));

        //Moves piece into test position to check whether the moves are going into checked state
        Vector2 testPosition = new Vector2(5, 2);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("rnb1kbnr/pppp1ppp/4p3/8/7q/PP6/2PPPPPP/RNBQKBNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    //FR5 Tests
    public void testMakeIllegalMove() {
        //create a new board
        testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        //Initialize an attacking player
        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

        //calculates all the legal moves for the given player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //Sets a test piece to move
        Piece pieceToMove = testBoard.getPiece(new Vector2(0,1));

        //Moves piece into test position to check whether the moves are illegal
        Vector2 testPosition = new Vector2(0, 5);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    //FR5 Tests
    public void testMakeIllegalMoveInCheck() {
        //create a new board where the
        testBoard = new Board("4k3/8/8/8/8/8/P7/4K2r w - - 0 1");

        //Initialize an attacking player
        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

        //calculates all the legal moves for the given player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //Sets a test piece to move
        Piece pieceToMove = testBoard.getPiece(new Vector2(0,1));

        //Moves piece into test position to check whether the illegal moves would go into checked state
        Vector2 testPosition = new Vector2(0, 2);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("4k3/8/8/8/8/8/P7/4K2r",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    //FR5 Tests
    public void testCastlingLegal(){
        //create a new board
        testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQK2R w KQkq - 0 1");

        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        Piece pieceToMove = testBoard.getPiece(new Vector2(4,0));

        Vector2 testPosition = new Vector2(6, 0);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQ1RK1",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    public void testCastlingIllegal(){

    }

    @Test
    public void CheckMate(){

    }
    @Test
    //FR5 Tests
    public void testEnPassant() {
        //create a new board
        testBoard = new Board("rnbqkbnr/ppppp1pp/5p2/3P4/8/8/PPP1PPPP/RNBQKBNR w KQkq - 0 3");

        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        Piece pieceToMove = testBoard.getPiece(new Vector2(4,4));

        Vector2 testPosition = new Vector2(3, 5);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("rnbqkbnr/ppppp1pp/8/8/3Pp3/8/PPP1PPPP/RNBQKBNR b KQkq - 0 3",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    //FR5 Tests
    public void testMakeLegalMovePawn() {
        testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        Piece pieceToMove = testBoard.getPiece(new Vector2(0,1));


        Vector2 testPosition = new Vector2(0, 3);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    public void testMakeLegalMoveKnight() {
        testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        Piece pieceToMove = testBoard.getPiece(new Vector2(1,0));


        Vector2 testPosition = new Vector2(2, 2);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/2N5/PPPPPPPP/R1BQKBNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    public void testMakeLegalMoveRook() {
        testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/R2QKBNR w KQkq - 0 1");

        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        Piece pieceToMove = testBoard.getPiece(new Vector2(0,0));


        Vector2 testPosition = new Vector2(2, 0);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/2RQKBNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    public void testMakeLegalMoveBishop() {
        testBoard = new Board("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1");

        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        Piece pieceToMove = testBoard.getPiece(new Vector2(5,0));


        Vector2 testPosition = new Vector2(2, 3);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("rnbqkbnr/pppp1ppp/8/4p3/2B1P3/8/PPPP1PPP/RNBQK1NR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    public void testMakeLegalMoveQueen() {
        testBoard = new Board("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 1");

        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        Piece pieceToMove = testBoard.getPiece(new Vector2(3,0));


        Vector2 testPosition = new Vector2(6, 3);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("rnbqkbnr/pppp1ppp/8/4p3/4P1Q1/8/PPPP1PPP/RNB1KBNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    public void testMakeLegalMoveKing() {
        testBoard = new Board("rnbqkbnr/ppp2ppp/3p4/4p3/4P1Q1/8/PPPP1PPP/RNB1KBNR w KQkq - 0 1");

        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        Piece pieceToMove = testBoard.getPiece(new Vector2(4,0));


        Vector2 testPosition = new Vector2(3, 0);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }Assertions.assertEquals("rnbqkbnr/ppp2ppp/3p4/4p3/4P1Q1/8/PPPP1PPP/RNBK1BNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }







}




/*
 * @(#) BoardTest.java 0.1 2023-05-02
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
 * BoardTest - Testing class for the Board Class
 * <p>
 * This class stores the testing related to the movements of the pieces on the board
 *
 * @author Craymon Chan
 * @author Jim Brown
 * @author Sean Hobson
 * @version 0.1 (draft)
 * @see uk.ac.aber.cs221.group09.logic.BoardTest
 */
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

        //check if the expected index is returned correctly
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
            //check if the expected board is the same as actual board
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
            //check if the expected board is the same as actual board
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
            //check if the expected board is the same as actual board
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
            //check if the expected board is the same as actual board
        }Assertions.assertEquals("4k3/8/8/8/8/8/P7/4K2r",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    //FR5 Tests
    public void testCastling(){
        //create a new board
        testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQK2R w KQkq - 0 1");

        //initializing an attacking player
        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

        //calculates all the legal moves for the given player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //Sets up a king piece and a rook piece to move
        Piece pieceToMove = testBoard.getPiece(new Vector2(4,0));
        Piece pieceToMove2 = testBoard.getPiece(new Vector2(7,0));

        //Move pieces into test positions to check whether the castling function is working correctly
        Vector2 testPosition = new Vector2(6, 0);
        Vector2 testPosition2 = new Vector2(5,0);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove2, testPosition2);
            }
        }
        //check if the expected board is the same as actual board
        Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQ1RK1",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    public void testCastlingIllegal(){
        //create a new board
        testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        //initializing an attacking player
        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

        //calculates all the legal moves for the given player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //Sets up a king piece and a rook piece to move
        Piece pieceToMove = testBoard.getPiece(new Vector2(4,0));
        Piece pieceToMove2 = testBoard.getPiece(new Vector2(7,0));

        //Attempt to move pieces into test positions to check whether the castling function is working correctly
        Vector2 testPosition = new Vector2(6, 0);
        Vector2 testPosition2 = new Vector2(5,0);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
        }
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove2, testPosition2);
            }
        }
        //check if the expected board is the same as actual board - the pieces should not have moved
        Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }
    @Test
    //FR5 Tests
    public void testEnPassant() {
        //create a new board
        testBoard = new Board("rnbqkbnr/ppppp2pp/4p3/4Pp2/8/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1");


        //initializing an attacking player
        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

        //calculates all the legal moves for the given player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //Sets up testing pawn pieces to move
    Piece pieceToMove = testBoard.getPiece(new Vector2(4,4));

        //Moves pieces into test positions to check whether the En Passant function is working correctly
    Vector2 testPosition = new Vector2(3, 5);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
        if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
        {
            testBoard.movePiece(pieceToMove, testPosition);
        }
            //check if the expected board is the same as actual board

    }Assertions.assertEquals("rnbqkbnr/ppppp2pp/4Pp2/8/8/8/PPPP1PPP/RNBQKBNR", testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
}

    @Test
    //FR5 Tests
    public void testMakeLegalMovePawn() {
        //create a new board
        testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        //initializing an attacking player
        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

        //calculates all the legal moves for the given player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //Sets up a testing pawn piece to move
        Piece pieceToMove = testBoard.getPiece(new Vector2(0,1));

        //Moves pawn piece into test position to check whether it is moving correctly
        Vector2 testPosition = new Vector2(0, 3);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
            //check if the expected board is the same as actual board
        }Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    //FR5 Tests
    public void testMakeLegalMoveKnight() {
        //create a new board
        testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        //initializing an attacking player
        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

        //calculates all the legal moves for the given player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //Sets up a testing knight piece to move
        Piece pieceToMove = testBoard.getPiece(new Vector2(1,0));

        //Moves knight piece into test position to check whether it is moving correctly
        Vector2 testPosition = new Vector2(2, 2);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
            //check if the expected board is the same as actual board
        }Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/2N5/PPPPPPPP/R1BQKBNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    //FR5 Tests
    public void testMakeLegalMoveRook() {
        //create a new board
        testBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/R2QKBNR w KQkq - 0 1");

        //initializing an attacking player
        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

        //calculates all the legal moves for the given player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //Sets up a testing rook piece to move
        Piece pieceToMove = testBoard.getPiece(new Vector2(0,0));

        //Moves rook piece into test position to check whether it is moving correctly
        Vector2 testPosition = new Vector2(2, 0);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
            //check if the expected board is the same as actual board
        }Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/2RQKBNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    //FR5 Tests
    public void testMakeLegalMoveBishop() {
        //create a new board
        testBoard = new Board("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1");

        //initializing an attacking player
        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

        //calculates all the legal moves for the given player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //Sets up a testing bishop piece to move
        Piece pieceToMove = testBoard.getPiece(new Vector2(5,0));

        //Moves rook piece into test position to check whether it is moving correctly
        Vector2 testPosition = new Vector2(2, 3);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
            //check if the expected board is the same as actual board
        }Assertions.assertEquals("rnbqkbnr/pppp1ppp/8/4p3/2B1P3/8/PPPP1PPP/RNBQK1NR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    //FR5 Tests
    public void testMakeLegalMoveQueen() {
        //create a new board
        testBoard = new Board("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 1");

        //initializing an attacking player
        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

        //calculates all the legal moves for the given player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //Sets up a testing queen piece to move
        Piece pieceToMove = testBoard.getPiece(new Vector2(3,0));

        //Moves queen piece into test position to check whether it is moving correctly
        Vector2 testPosition = new Vector2(6, 3);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
            //check if the expected board is the same as actual board
        }Assertions.assertEquals("rnbqkbnr/pppp1ppp/8/4p3/4P1Q1/8/PPPP1PPP/RNB1KBNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }

    @Test
    //FR5 Tests
    public void testMakeLegalMoveKing() {
        //create a new board
        testBoard = new Board("rnbqkbnr/ppp2ppp/3p4/4p3/4P1Q1/8/PPPP1PPP/RNB1KBNR w KQkq - 0 1");

        //initializing an attacking player
        char attacking_player = testBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
        MoveCalculator moveCalculator = new MoveCalculator(attacking_player, testBoard);

        //calculates all the legal moves for the given player
        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //Sets up a testing king piece to move
        Piece pieceToMove = testBoard.getPiece(new Vector2(4,0));

        //Moves king piece into test position to check whether it is moving correctly
        Vector2 testPosition = new Vector2(3, 0);
        for(Vector2 currentMove : pieceToMove.getPossibleMoves()) {
            if(currentMove.getVector2AsBoardNotation().equals(testPosition.getVector2AsBoardNotation()))
            {
                testBoard.movePiece(pieceToMove, testPosition);
            }
            //check if the expected board is the same as actual board
        }Assertions.assertEquals("rnbqkbnr/ppp2ppp/3p4/4p3/4P1Q1/8/PPPP1PPP/RNBK1BNR",testBoard.getForsythEdwardsBoardNotationArrayIndex(0));
    }










}




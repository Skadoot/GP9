/*
 * @(GP9) MoveCalculator.java 1.1 2023/03/16
 *
 * Copyright (c) 2023 Aberystwyth University
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic;

import uk.ac.aber.cs221.group09.logic.pieces.Piece;
import uk.ac.aber.cs221.group09.util.Coordinate;

import java.util.ArrayList;

/**
 * MoveCalculator - Calculates legal moves for each piece for a player.
 * <p>
 * This is a class that calculates the legal moves for the all the pieces of a particular color for a particular board position.
 *
 * @author Shaun Royle
 * @version 1.1 (Release)
 * @see Board
 */
public class MoveCalculator {
   private final char currentPlayer; // The player that you want to calculate the moves for.
   private final Board board; // The board that you want to calculate the moves for.
   private final ArrayList<Coordinate> checkMap = new ArrayList<>(); // The map of threatened squares
   private boolean canWhiteCastleKingSide; // Can white castle king side?
   private boolean canWhiteCastleQueenSide; // Can white castle queen side?
   private boolean canBlackCastleKingSide; // Can black castle king side?
   private boolean canBlackCastleQueenSide; // Can black castle queen side?

   /**
    * Simple constructor for moveCalculator.
    *
    * @param player the player that you wish to calculate the moves for.
    * @param board  the board on which to calculate the moves.
    */
   public MoveCalculator(char player, Board board) {
      this.currentPlayer = player;
      this.board = board;

      // Determine who can castle, so we can take this into account when calculating the legal moves.
      determineWhoCanCastle();
   }

   /**
    * Calculates and returns the legal moves for a given chess piece.
    * The legal moves are determined based on the type of the piece and the opponent player's turn.
    *
    * @param piece          The chess piece for which the legal moves are to be determined.
    * @param opponentPlayer A boolean value representing whether it's the opponent player's turn or not.
    */
   public void getLegalMoveForPiece(Piece piece, boolean opponentPlayer) {

      // Check what the piece's type is and calculate its legal moves: 'p' for pawn, 'n' for knight, 'r' for rook, 'b' for bishop, 'q' for queen, 'k' for king.
      switch (piece.getType()) {
         case 'p':
            getPawnLegalMoves(piece, opponentPlayer);
            break;
         case 'n':
            getKnightLegalMoves(piece, opponentPlayer);
            break;
         case 'b':
            getBishopLegalMoves(piece, opponentPlayer);
            break;
         case 'r':
            getRookLegalMoves(piece, opponentPlayer);
            break;
         case 'q':
            getQueenLegalMoves(piece, opponentPlayer);
            break;
         case 'k':
            getKingLegalMoves(piece, opponentPlayer);
            break;
      }
   }


   /**
    * Finds the legal moves for each piece for the player making the move.
    */
   public void findLegalMovesForPlayer(boolean opponentPlayer) {
      // Loop over every piece in the board array.
      for (int rank = 0; rank < 8; rank++) {
         for (int file = 0; file < 8; file++) {
            // Create the current board position vector2.
            Coordinate piecePosition = new Coordinate(file, rank);

            // Get the piece at the current board position.
            Piece piece = board.getPiece(piecePosition);

            // If the piece is null, skip and look at the next piece.
            if (piece == null) {
               continue;
            }

            // If the piece is not the color of the current player, then we are using this piece's moves to create the list of all unsafe squares.
            if (piece.getColor() == currentPlayer && opponentPlayer) {
               continue;
            }
            if (piece.getColor() != currentPlayer && !opponentPlayer) {
               continue;
            }

            // Check what the piece's type is and calculate its legal moves: 'p' for pawn, 'n' for knight, 'r' for rook, 'b' for bishop, 'q' for queen, 'k' for king.
            switch (piece.getType()) {
               case 'p':
                  getPawnLegalMoves(piece, opponentPlayer);
                  break;
               case 'n':
                  getKnightLegalMoves(piece, opponentPlayer);
                  break;
               case 'b':
                  getBishopLegalMoves(piece, opponentPlayer);
                  break;
               case 'r':
                  getRookLegalMoves(piece, opponentPlayer);
                  break;
               case 'q':
                  getQueenLegalMoves(piece, opponentPlayer);
                  break;
               case 'k':
                  getKingLegalMoves(piece, opponentPlayer);
                  break;
            }
            // If we used the piece to find unsafe squares add the piece's legal moves to the checkMap.
            if (opponentPlayer) {
               checkMap.addAll(piece.getPossibleMoves());
            }
         }
      }
   }

   /**
    * Calculates and sets the legal moves of any given pawn on the board.
    *
    * @param pawn          the pawn to calculate the moves for.
    * @param isForCheckMap boolean to see if the method is being used to create a check map, if so we do not need to check if the moves put the player in check.
    */
   private void getPawnLegalMoves(Piece pawn, boolean isForCheckMap) {
      // Calculate the single advance square position. the y should be positive for white pieces and negative for black pieces.
      Coordinate singleAdvance = new Coordinate(pawn.getPosition().x, ((pawn.getColor() == 'w') ? pawn.getPosition().y + 1 : pawn.getPosition().y - 1));

      // Calculate the double advance square position. the y should be positive for white pieces and negative for black pieces.
      Coordinate doubleAdvance = new Coordinate(pawn.getPosition().x, ((pawn.getColor() == 'w') ? singleAdvance.y + 1 : singleAdvance.y - 1));

      // Calculate left and right attack square positions.
      Coordinate leftAttack = new Coordinate(singleAdvance.x - 1, singleAdvance.y);
      Coordinate rightAttack = new Coordinate(singleAdvance.x + 1, singleAdvance.y);

      // Check if the single advance is a valid position on the board. and that there are no pieces occupying it.
      if (!isForCheckMap) {
         if (singleAdvance.x <= 7 && singleAdvance.y <= 7 && singleAdvance.x >= 0 && singleAdvance.y >= 0 && board.getPiece(singleAdvance) == null) {
            addPieceLegalMove(pawn, singleAdvance, false);

            // Check if there is a piece on the double advance square and that the pawn has not moved before.

            if (doubleAdvance.x <= 7 && doubleAdvance.y <= 7 && doubleAdvance.x >= 0 && doubleAdvance.y >= 0) {
               if (board.getPiece(doubleAdvance) == null && !pawn.hasMoved()) {
                  addPieceLegalMove(pawn, doubleAdvance, false);
               }
            }
         }
      }

      // Check if the left attack is a valid position on the board.
      if ((leftAttack.x > -1) && (leftAttack.y > -1 && leftAttack.y < 8)) {
         canPawnAttack(pawn, isForCheckMap, leftAttack);
      }

      // Check if the right attack is a valid position on the board.
      if ((rightAttack.x < 8) && (rightAttack.y > -1 && rightAttack.y < 8)) {
         canPawnAttack(pawn, isForCheckMap, rightAttack);
      }

      if (isForCheckMap) {
         return;
      }
      System.out.print("legal moves for " + pawn.getColor() + " " + "pawn" + " at " + pawn.getPosition().getVector2AsBoardNotation());
      System.out.print(" | ");
      for (Coordinate square : pawn.getPossibleMoves()) {
         System.out.print(square.getVector2AsBoardNotation() + " ");
      }
      System.out.println();
   }

   /*
   /  Checks if a pawn can attack in a particular direction.
    */
   private void canPawnAttack(Piece pawn, boolean isForCheckMap, Coordinate rightAttack) {
      if (board.getPiece(rightAttack) != null || isForCheckMap) {
         if (isForCheckMap) {
            addPieceLegalMove(pawn, rightAttack, true);
         }
         // Check if the piece on the left attack square is not the same color as the pawn.
         else if (board.getPiece(rightAttack).getColor() != pawn.getColor()) {
            addPieceLegalMove(pawn, rightAttack, false);
         }
      } else if (board.getPiece(rightAttack) == null || isForCheckMap) {
         if (rightAttack.getVector2AsBoardNotation().equals(board.getForsythEdwardsBoardNotationArrayIndex(3))) {
            addPieceLegalMove(pawn, rightAttack, isForCheckMap);
         }
      }
   }

   /**
    * Calculates and sets the legal moves of any given knight on the board.
    *
    * @param knight        the knight to calculate the moves for.
    * @param isForCheckMap boolean to see if the method is being used to create a check map, if so we do not need to check if the moves put the player in check.
    */
   private void getKnightLegalMoves(Piece knight, boolean isForCheckMap) {
      // These are the knights possible jumps, in Vector2's relative to the knights position. positive values add to the knights current x or y, negative subtracts.
      Coordinate[] knightDirections = {new Coordinate(2, 1), new Coordinate(1, 2), new Coordinate(-2, 1), new Coordinate(-1, 2), new Coordinate(2, -1), new Coordinate(1, -2), new Coordinate(-2, -1), new Coordinate(-1, -2)};

      // For each of the directions in the knight's directions.
      for (Coordinate d : knightDirections) {

         // Find what the move square is.
         Coordinate move = new Coordinate(knight.getPosition().x + d.x, knight.getPosition().y + d.y);

         // If the move square is outside the board, skip to the next direction.
         if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {
            continue;
         }

         // If the move square is empty (null) or if there is an opponents piece on that square, check if that move will put the player in check.
         if (board.getPiece(move) == null || board.getPiece(move).getColor() != knight.getColor()) {
            addPieceLegalMove(knight, move, isForCheckMap);
         }
      }

      if (isForCheckMap) {
         return;
      }

      System.out.print("legal moves for " + knight.getColor() + " " + "knight" + " at " + knight.getPosition().getVector2AsBoardNotation());
      System.out.print(" | ");
      for (Coordinate square : knight.getPossibleMoves()) {
         System.out.print(square.getVector2AsBoardNotation() + " ");
      }
      System.out.println();
   }

   /**
    * Calculates and sets the legal moves of any given bishop on the board.
    *
    * @param bishop        the bishop to calculate the moves for.
    * @param isForCheckMap boolean to see if the method is being used to create a check map, if so we do not need to check if the moves put the player in check.
    */
   private void getBishopLegalMoves(Piece bishop, boolean isForCheckMap) {
      // The bishop's directions: north-west, north-east, south-west, south-east.
      Coordinate[] bishopDirections = {new Coordinate(1, 1), new Coordinate(-1, -1), new Coordinate(1, -1), new Coordinate(-1, 1)};

      // For every direction in the bishop's directions.
      for (Coordinate d : bishopDirections) {

         // For n squares in that direction until the bishop is blocked. maximum 8 squares.
         for (int n = 1; n < 8; n++) {

            // Find out which square the move is.
            Coordinate move = new Coordinate(bishop.getPosition().x + (d.x * n), bishop.getPosition().y + (d.y * n));

            // If the move square is outside the board, skip to the next direction.
            if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {
               break;
            }

            if (board.getPiece(move) == null) {
               addPieceLegalMove(bishop, move, isForCheckMap);
            } else if (!(board.getPiece(move).getColor() == bishop.getColor())) {
               addPieceLegalMove(bishop, move, isForCheckMap);
               break;
            } else if (board.getPiece(move).getColor() == bishop.getColor()) {
               break;
            }
         }
      }

      if (isForCheckMap) {
         return;
      }

      System.out.print("legal moves for " + bishop.getColor() + " " + "bishop" + " at " + bishop.getPosition().getVector2AsBoardNotation());
      System.out.print(" | ");

      for (Coordinate square : bishop.getPossibleMoves()) {
         System.out.print(square.getVector2AsBoardNotation() + " ");
      }
      System.out.println();
   }

   /**
    * Calculates and sets the legal moves of any given rook on the board.
    *
    * @param rook          the rook to calculate the moves for.
    * @param isForCheckMap boolean to see if the method is being used to create a check map, if so we do not need to check if the moves put the player in check.
    */
   private void getRookLegalMoves(Piece rook, boolean isForCheckMap) {
      // The rook's directions: north, east, west, south.
      Coordinate[] rookDirections = {new Coordinate(0, 1), new Coordinate(0, -1), new Coordinate(1, 0), new Coordinate(-1, 0)};

      // For every direction in the rook's directions.
      for (Coordinate d : rookDirections) {

         // For n squares in that direction until the rook is blocked. maximum 8 squares.
         for (int n = 1; n < 8; n++) {

            // Find out which square the move is.
            Coordinate move = new Coordinate(rook.getPosition().x + (d.x * n), rook.getPosition().y + (d.y * n));

            // If the move square is outside the board, skip to the next direction.
            if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {
               break;
            }

            if (board.getPiece(move) == null) {
               addPieceLegalMove(rook, move, isForCheckMap);
            } else if (!(board.getPiece(move).getColor() == rook.getColor())) {
               addPieceLegalMove(rook, move, isForCheckMap);
               break;
            } else if ((board.getPiece(move).getColor() == rook.getColor())) {
               break;
            }
         }
      }

      if (isForCheckMap) {
         return;
      }

      System.out.print("legal moves for " + rook.getColor() + " " + "rook" + " at " + rook.getPosition().getVector2AsBoardNotation());
      System.out.print(" | ");

      for (Coordinate square : rook.getPossibleMoves()) {
         System.out.print(square.getVector2AsBoardNotation() + " ");
      }

      System.out.println();
   }

   /**
    * Calculates and sets the legal moves of any given queen on the board.
    *
    * @param queen         the queen to calculate the moves for.
    * @param isForCheckMap boolean to see if the method is being used to create a check map, if so we do not need to check if the moves put the player in check.
    */
   private void getQueenLegalMoves(Piece queen, boolean isForCheckMap) {
      // The queen's directions: north-west, north-east, south-west, south-east, north, south, east, west.
      Coordinate[] queenDirections = {new Coordinate(0, 1), new Coordinate(0, -1), new Coordinate(1, 0), new Coordinate(-1, 0), new Coordinate(1, 1), new Coordinate(-1, -1), new Coordinate(1, -1), new Coordinate(-1, 1)};

      // For each direction in the queen's directions.
      for (Coordinate d : queenDirections) {

         // For n squares in that direction until the queen is blocked. maximum 8 squares.
         for (int n = 1; n < 8; n++) {

            // Find out which square the move is.
            Coordinate move = new Coordinate(queen.getPosition().x + (d.x * n), queen.getPosition().y + (d.y * n));

            // If the move square is outside the board, skip to the next direction.
            if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {
               break;
            }

            if (board.getPiece(move) == null) {
               addPieceLegalMove(queen, move, isForCheckMap);
            } else if (!(board.getPiece(move).getColor() == queen.getColor())) {
               addPieceLegalMove(queen, move, isForCheckMap);
               break;
            } else if ((board.getPiece(move).getColor() == queen.getColor())) {
               break;
            }
         }
      }

      if (isForCheckMap) {
         return;
      }

      System.out.print("legal moves for " + queen.getColor() + " " + "queen" + " at " + queen.getPosition().getVector2AsBoardNotation());
      System.out.print(" | ");

      for (Coordinate square : queen.getPossibleMoves()) {
         System.out.print(square.getVector2AsBoardNotation() + " ");
      }

      System.out.println();
   }

   /**
    * Calculates and sets the legal moves of any given king on the board.
    *
    * @param king          the king to calculate the moves for
    * @param isForCheckMap boolean to see if the method is being used to create a check map, if so we do not need to check if the moves put the player in check.
    */
   private void getKingLegalMoves(Piece king, boolean isForCheckMap) {
      // The king's directions: north-west, north-east, south-west, south-east, north, south, east, west.
      Coordinate[] kingDirections = {new Coordinate(0, 1), new Coordinate(0, -1), new Coordinate(1, 0), new Coordinate(-1, 0), new Coordinate(1, 1), new Coordinate(-1, -1), new Coordinate(1, -1), new Coordinate(-1, 1)};

      // For each direction in the king's directions.
      for (Coordinate d : kingDirections) {

         // Find out which square the move is.
         Coordinate move = new Coordinate(king.getPosition().x + (d.x), king.getPosition().y + (d.y));

         // If the move square is outside the board, skip to the next direction.
         if (move.x > 7 || move.y > 7 || move.x < 0 || move.y < 0) {
            continue;
         }

         if (board.getPiece(move) == null) {
            addPieceLegalMove(king, move, isForCheckMap);
         } else if (!(board.getPiece(move).getColor() == king.getColor())) {
            addPieceLegalMove(king, move, isForCheckMap);
         }
      }

      // Check if castling king side is legal.
      if (canPlayerCastleKingSide(king.getColor())) {
         for (int i = 1; i < 8; i++) {
            Coordinate move = new Coordinate(king.getPosition().x + i, king.getPosition().y);
            if (board.getPiece(move) == null) {
               continue;
            }
            if (board.getPiece(move).getType() != 'r') {
               break;
            }

            // If we find a rook in the direction.
            addPieceLegalMove(king, new Coordinate(move.x - 1, move.y), isForCheckMap);
            break;
         }
      }

      // Check if castling queen side is legal.
      if (canPlayerCastleQueenSide(king.getColor())) {
         for (int i = 1; i < 8; i++) {
            Coordinate move = new Coordinate(king.getPosition().x - i, king.getPosition().y);
            if (board.getPiece(move) == null) {
               continue;
            }
            if (board.getPiece(move).getType() != 'r') {
               break;
            }

            // If we find a rook in the direction.
            addPieceLegalMove(king, new Coordinate(move.x + 2, move.y), isForCheckMap);
            break;
         }
      }

      if (isForCheckMap) {
         return;
      }

      System.out.print("legal moves for " + king.getColor() + " " + "king" + " at " + king.getPosition().getVector2AsBoardNotation());
      System.out.print(" | ");

      for (Coordinate square : king.getPossibleMoves()) {
         System.out.print(square.getVector2AsBoardNotation() + " ");
      }

      System.out.println();
   }


   /**
    * Determines which players can castle and in which direction. using the board's forsyth edwards string notation.
    */
   private void determineWhoCanCastle() {
      // Start off assuming that we cannot castle.
      canWhiteCastleKingSide = false;
      canWhiteCastleQueenSide = false;
      canBlackCastleKingSide = false;
      canBlackCastleQueenSide = false;

      // Split the forsyth edwards notation string and acquire the 3rd section of it, which is the section which stores if the players can castle or not.
      String castlingNotation = board.getForsythEdwardsBoardNotationArrayIndex(2);

      // If this section only consists of the '-' character then neither side can castle, then return.
      if (castlingNotation.equals("-")) {
         return;
      }

      // Otherwise, loop through the string.
      for (int i = 0; i < castlingNotation.length(); i++) {
         // If a 'k' is found.
         if (castlingNotation.charAt(i) == 'k' || castlingNotation.charAt(i) == 'K') {
            // Check if its uppercase, if so then it is referring to the ability for white to castle on the king side.
            if (Character.isUpperCase(castlingNotation.charAt(i))) {
               // White can castle king side.
               canWhiteCastleKingSide = true;
            } else {
               // Black can castle king side.
               canBlackCastleKingSide = true;
            }
         }
         // If a 'q' is found.
         else if (castlingNotation.charAt(i) == 'q' || castlingNotation.charAt(i) == 'Q') {
            // Check if its uppercase, if so then it is referring to the ability for white to castle on the queen side.
            if (Character.isUpperCase(castlingNotation.charAt(i))) {
               // White can castle queen side.
               canWhiteCastleQueenSide = true;
            } else {
               // Black can castle queen side.
               canBlackCastleQueenSide = true;
            }
         }
      }
   }

   /**
    * Determines if a player can castle king side.
    *
    * @param player the players color.
    */
   private boolean canPlayerCastleKingSide(char player) {
      if (player == 'w') {
         return canWhiteCastleKingSide;
      } else {
         return canBlackCastleKingSide;
      }
   }

   /**
    * Determines if a player can castle queen side.
    *
    * @param player the players color.
    */
   private boolean canPlayerCastleQueenSide(char player) {
      if (player == 'w') {
         return canWhiteCastleQueenSide;
      } else {
         return canBlackCastleQueenSide;
      }
   }

   /**
    * Adds a single move to the legal move arrayList of a piece.
    *
    * @param piece         the piece we want to add the move to
    * @param move          the move that we want to add
    * @param isForCheckMap if this is true then we can ignore weather or not the move will leave us in check or not since we are creating the check map.
    */
   private void addPieceLegalMove(Piece piece, Coordinate move, boolean isForCheckMap) {
      if (isForCheckMap) {
         piece.addMove(move);
      } else if (!isMoveSafe(piece, move)) {
         piece.addMove(move);
      }
   }

   /**
    * Determines if a move would leave the player in check or not.
    *
    * @param piece the piece to move.
    * @param move  the move to assess.
    * @return a boolean which determines if the move will leave the player in check or not.
    */
   private boolean isMoveSafe(Piece piece, Coordinate move) {
      Board projectionOfMove = new Board(board.getForsythEdwardsBoardNotation());
      projectionOfMove.movePiece(projectionOfMove.getPiece(piece.getPosition()), move);

      MoveCalculator moveCalculator = new MoveCalculator(piece.getColor(), projectionOfMove);
      moveCalculator.findLegalMovesForPlayer(true);

      return moveCalculator.isPlayerInCheck();
   }

   /**
    * Determines if the player is in check.
    *
    * @return boolean whether the player is in check or not.
    */
   public boolean isPlayerInCheck() {
      if (currentPlayer == 'w') {
         for (Coordinate attackedSquare : checkMap) {
            if (board.getWhiteKingPosition().getVector2AsBoardNotation().equals(attackedSquare.getVector2AsBoardNotation())) {
               return true;
            }
         }
      } else {
         for (Coordinate attackedSquare : checkMap) {
            if (board.getBlackKingPosition().getVector2AsBoardNotation().equals(attackedSquare.getVector2AsBoardNotation())) {
               return true;
            }
         }
      }
      return false;
   }

   /**
    * Determines if the player is in checkmate.
    *
    * @return boolean whether the player is in checkmate or not.
    */
   public boolean isPlayerInCheckMate() {
      // Check if there are any moves.
      boolean playerHasMoves = false;

      for (int rank = 0; rank < 8; rank++) {
         for (int file = 0; file < 8; file++) {
            if (board.getPiece((new Coordinate(rank, file))) != null) {
               if (!board.getPiece(new Coordinate(rank, file)).getPossibleMoves().isEmpty() && board.getPiece(new Coordinate(rank, file)).getColor() == currentPlayer) {
                  playerHasMoves = true;
               }
            }
         }
      }

      // If the player is in check and there are no moves, return true for checkmate.
      return isPlayerInCheck() && !playerHasMoves;
   }

   /**
    * Prints to console every square that is currently being attacked by the opponent's pieces.
    */
   public void printCheckMap() {
      System.out.print("unsafe squares for player " + currentPlayer + " : ");
      for (Coordinate attackedSquare : checkMap) {
         System.out.print(attackedSquare.getVector2AsBoardNotation() + " ");
      }
      System.out.println();
   }
}

/*
 * @(GP9) Board.java 1.0 2023-05-02
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic;

import uk.ac.aber.cs221.group09.logic.pieces.Piece;
import uk.ac.aber.cs221.group09.logic.vector.Vector2;

/**
 * Board - Stores the logical representation of a chess board.
 * <p>
 * This class stores the position of each piece on the board.
 *
 * @author Shaun Royle
 * @version 1.0 (Release)
 * @see uk.ac.aber.cs221.group09.logic.MoveCalculator
 */
public class Board {
   // Board size.
   private static final int BOARD_SIZE = 8;

   // The logical representation of the board.
   private final Piece[][] board;
   // Array which contains the forsyth edwards notation split up into sections.
   private final String[] forsythEdwardsBoardNotationArray;
   // Forsyth edwards notation of the board for saving and loading board states.
   private String forsythEdwardsBoardNotation;
   // Both king positions.
   private Vector2 whiteKingPosition;
   private Vector2 blackKingPosition;
   private Vector2 availablePromotion;

   /**
    * A simple constructor for the Board class.
    * Initializes the board to a beginning state using the given forsyth edwards notation.
    *
    * @param initializingBoardState the string representation to initialize the board to.
    */
   public Board(String initializingBoardState) {
      board = new Piece[BOARD_SIZE][BOARD_SIZE];

      // Sets the board state string to the given Forsyth Edwards Notation.
      this.forsythEdwardsBoardNotation = initializingBoardState;

      // Splits up the string using a regex
      forsythEdwardsBoardNotationArray = forsythEdwardsBoardNotation.split(" ", 6);

      // Initialize the board to the current state represented in the string.
      initializeBoardState();
   }


   /**
    * Get the coordinates of the available pawn promotion.
    *
    * @return Vector2 position of the piece eligible for promotion.
    */
   public Vector2 getAvailablePromotion() {
      return availablePromotion;
   }

   /**
    * Set the coordinates of the available pawn promotion to pawn ready for promotion or null.
    *
    * @param position Vector2 position of a pawn ready for promotion.
    * @return
    */
   public void setAvailablePromotion(Vector2 position) {
      this.availablePromotion = position;
   }

   /**
    * Initializes the pieces on the board based on the Forsyth Edwards Notation string given.
    */
   private void initializeBoardState() {
      // Gets starting positions to access the array from.
      int file = 0;
      int rank = 7;

      // Get the section of the edwards forsyth notation string which represents the state of the board.
      String boardRepresentation = forsythEdwardsBoardNotationArray[0];

      // Iterate through the state of the board string.
      for (int positionInString = 0; positionInString < boardRepresentation.length(); positionInString++) {
         // If we have arrived at a '/', this is the marker for going down a rank, so we decrement the rank and reset the file to the first file.
         if (boardRepresentation.charAt(positionInString) == '/') {
            file = 0;
            rank--;
            continue;
         }

         // If we have arrived at a digit, this is the marker for n amount of empty squares on the rank in a row before we find a piece. Add this digit to out file variable.
         if (Character.isDigit(boardRepresentation.charAt(positionInString))) {
            file += Character.getNumericValue(boardRepresentation.charAt(positionInString));
            continue;
         }

         // If we arrive here we need to add a piece to the board. Crate the new boardPosition using the Vector2 object.
         Vector2 boardPosition = new Vector2(file, rank);

         // If the character representing the piece is upper case then it is a white piece, else it is a black piece.
         if (Character.isUpperCase(boardRepresentation.charAt(positionInString))) {
            // Create a new white piece that is of the same type as the current character in the string, with the position of boardPosition.
            Piece piece = new Piece('w', boardPosition, Character.toLowerCase(boardRepresentation.charAt(positionInString)));
            setPiece(boardPosition, piece);

            // If the piece is a king, set the position of the white king to this piece's position.
            if (Character.toLowerCase(boardRepresentation.charAt(positionInString)) == 'k') {
               whiteKingPosition = boardPosition;
            }
         } else {
            // Create a new black piece that is of the same type as the current character in the string, with the position of boardPosition.
            Piece piece = new Piece('b', boardPosition, boardRepresentation.charAt(positionInString));
            setPiece(boardPosition, piece);

            // If the piece is a king, set the position of the black king to this piece's position.
            if (boardRepresentation.charAt(positionInString) == 'k') {
               blackKingPosition = boardPosition;
            }
         }
         // Increment the file for the next position on the board.
         file++;
      }
   }

   /**
    * Moves a piece on the board, while taking into account special moves.
    *
    * @param selectedPiece the selected piece to move.
    * @param move          the vector2 coordinate to move the piece to.
    */
   public void movePiece(Piece selectedPiece, Vector2 move) {
      // If the piece is a pawn, need to do checking for "en passant".
      if (selectedPiece.getType() == 'p') {
         movePawn(selectedPiece, move);
      }
      // If the piece is a king, need to do checking for castling and make sure we keep track of the king position.
      else if (selectedPiece.getType() == 'k') {
         moveKing(selectedPiece, move);
         // Set the en passant capture square to "-".
         forsythEdwardsBoardNotationArray[3] = "-";
      }
      // If a rook is moving for the first time, need to update the castling notation.
      else if (selectedPiece.getType() == 'r' && !selectedPiece.hasMoved()) {
         moveRook(selectedPiece, move);
         // Set the en passant capture square to "-".
         forsythEdwardsBoardNotationArray[3] = "-";
      }
      // If the piece is neither a king nor a pawn there are no special moves to take into account, so just move it.
      else {
         updatePiecePositionInArray(selectedPiece, move);
         // Set the en passant capture square to "-".
         forsythEdwardsBoardNotationArray[3] = "-";
      }

      // Get the new board state string, after the move has been played.
      updateForsythEdwardsBoardNotation(true);
   }

   /**
    * Handles specific movements for pawn pieces, e.g. En Passant and promotion.
    */
   private void movePawn(Piece pawn, Vector2 move) {
      // Get the section of the forsyth edwards string notation for en passant.
      String enPassantString = forsythEdwardsBoardNotationArray[3];

      // If the move that the pawn is taking is to the square represented by the string then, capture the pawn under/above it.
      if (move.getVector2AsBoardNotation().equals(enPassantString)) {
         // If the pawn is white capture the pawn below it.
         if (pawn.getColor() == 'w') {
            capturePiece(new Vector2(move.x, move.y - 1));
         }
         // If the pawn is black capture the pawn above it.
         else {
            capturePiece(new Vector2(move.x, move.y + 1));
         }
         forsythEdwardsBoardNotationArray[3] = "-";
         updatePiecePositionInArray(pawn, move);
         return;
      }

      // If a white pawn is moving two squares, for allowing En Passant
      if (move.y - pawn.getPosition().y == 2) {
         enPassantString = new Vector2(move.x, move.y - 1).getVector2AsBoardNotation();
         forsythEdwardsBoardNotationArray[3] = enPassantString;
      }
      // If a black pawn is moving two squares, for allowing En Passant
      else if (move.y - pawn.getPosition().y == -2) {
         enPassantString = new Vector2(move.x, move.y + 1).getVector2AsBoardNotation();
         forsythEdwardsBoardNotationArray[3] = enPassantString;
      }
      // If the pawn is not moving 2 squares.
      else {
         forsythEdwardsBoardNotationArray[3] = "-";
      }

      // Move the pawn.
      updatePiecePositionInArray(pawn, move);
   }

   /**
    * Handles specific movements for pawn pieces, e.g. Castling.
    */
   private void moveKing(Piece king, Vector2 move) {
      // Get the castling notation.
      String castlingNotation = forsythEdwardsBoardNotationArray[2];

      // If the king is moving more than one square it is castling, move the rook, on the correct side.
      if (move.x > king.getPosition().x + 1 || move.x < king.getPosition().x - 1) {
         // Move king's side rook.
         Piece rook;
         Vector2 newRookPosition;

         // Find out which rook needs to move, and to where.
         if (move.x > king.getPosition().x + 1) {
            // Get the king's side rook if the king is moving to the right.
            rook = getPiece(new Vector2(7, king.getPosition().y));

            // Get the new rook position.
            newRookPosition = new Vector2(king.getPosition().x + 1, king.getPosition().y);
         } else {
            // Get the queen side rook if the king is moving to the left.
            rook = getPiece(new Vector2(0, king.getPosition().y));

            // Get the new rook position.
            newRookPosition = new Vector2(king.getPosition().x - 1, king.getPosition().y);
         }

         // Update the rook's position.
         updatePiecePositionInArray(rook, newRookPosition);
      }

      // Update the king's position.
      updatePiecePositionInArray(king, move);

      // If the piece's color is white, update the white king's position, else update the black king's position.
      if (king.getColor() == 'w') {
         whiteKingPosition = move;
         // Update the castling notation for white.
         forsythEdwardsBoardNotationArray[2] = castlingNotation.replaceAll("[A-Z]", "");
      } else {
         blackKingPosition = move;
         // Update the castling notation for black.
         forsythEdwardsBoardNotationArray[2] = castlingNotation.replaceAll("[a-z]", "");
      }
   }

   /**
    * Handles specific movements for rook pieces, e.g. Castling.
    */
   private void moveRook(Piece rook, Vector2 move) {
      // Get the castling notation.
      String castlingNotation = forsythEdwardsBoardNotationArray[2];

      // If it's a white rook, need to remove capital letters in the castling notation.
      if (rook.getColor() == 'w') {
         // If it's the queen side rook moving.
         if (rook.getPosition().x == 0) {
            forsythEdwardsBoardNotationArray[2] = castlingNotation.replaceAll("Q", "");
         }
         //if it's the king side rook moving.
         else {
            forsythEdwardsBoardNotationArray[2] = castlingNotation.replaceAll("K", "");
         }
      } else {
         // If it's the queen side rook moving.
         if (rook.getPosition().x == 0) {
            forsythEdwardsBoardNotationArray[2] = castlingNotation.replaceAll("q", "");
         }
         // If it's the king side rook moving.
         else {
            forsythEdwardsBoardNotationArray[2] = castlingNotation.replaceAll("k", "");
         }
      }

      // Move the rook.
      updatePiecePositionInArray(rook, move);
   }

   /**
    * Moves a piece from one array index to another
    */
   private void updatePiecePositionInArray(Piece piece, Vector2 newPiecePosition) {
      // Set the old position in the array to null.
      setPiece(piece.getPosition(), null);

      // If there is a piece on the square that the piece is moving to then it is capturing it.
      if (getPiece(newPiecePosition) != null) {
         capturePiece(newPiecePosition);
      }

      // Move the piece in the array.
      setPiece(newPiecePosition, piece);

      // Update the piece's internal position value.
      piece.setPosition(newPiecePosition);

      // Say that the piece has now moved.
      piece.setHasMoved(true);
   }

   /**
    * Method for capturing a piece
    */
   private void capturePiece(Vector2 piecePosition) {
      // Update captured pieces array/string.
      setPiece(piecePosition, null);
   }

   /**
    * Method to update the Forsyth Edwards Notation based on the current state of the board array, and game.
    */
   private void updateForsythEdwardsBoardNotation(boolean newTurn) {
      // Update the board string. represented by forsythEdwardsBoardNotationArray[0].
      // Create a new string builder.
      StringBuilder newBoardRepresentationString = new StringBuilder();
      // Variable to keep track of how many empty spaces there have been.
      int skippedPieces = 0;
      // Loop through the array backwards.
      for (int rank = 7; rank > -1; rank--) {
         if (skippedPieces > 0) {
            newBoardRepresentationString.append(skippedPieces);
            skippedPieces = 0;
         }
         if (rank != 7) {
            newBoardRepresentationString.append("/");
         }
         for (int file = 0; file < BOARD_SIZE; file++) {
            Piece currentPiece = getPiece(new Vector2(file, rank));
            if (currentPiece == null) {
               skippedPieces += 1;
               if (rank == 0 && skippedPieces > 0) {
                  newBoardRepresentationString.append(skippedPieces);
                  skippedPieces = 0;
               }
               continue;
            } else if (skippedPieces > 0) {
               newBoardRepresentationString.append(skippedPieces);
               skippedPieces = 0;
            }
            if (currentPiece.getColor() == 'w') {
               newBoardRepresentationString.append(Character.toUpperCase(currentPiece.getType()));
            } else {
               newBoardRepresentationString.append(currentPiece.getType());
            }
         }
      }
      forsythEdwardsBoardNotationArray[0] = newBoardRepresentationString.toString();

      // Update the current player string. represented by forsythEdwardsBoardNotationArray[1].
      if(newTurn) {
         if (forsythEdwardsBoardNotationArray[1].equals("w")) {
            forsythEdwardsBoardNotationArray[1] = "b";
         } else {
            forsythEdwardsBoardNotationArray[1] = "w";
         }
      }

      // If the castling notation is empty, forsythEdwardsBoardNotationArray[2], then replace it with the '-' character.
      if (forsythEdwardsBoardNotationArray[2].equals("")) {
         forsythEdwardsBoardNotationArray[2] = "-";
      }

      // En Passant represented by forsythEdwardsBoardNotationArray[3], should already be updated by this point.
      // The half move clock represented by forsythEdwardsBoardNotationArray[4].

      // Update the full move number represented by forsythEdwardsBoardNotationArray[5], by incrementing it by 1.
      if(newTurn) {
         forsythEdwardsBoardNotationArray[5] = Integer.toString(Integer.parseInt(forsythEdwardsBoardNotationArray[5]) + 1);
      }

      // Initializing the new board state stringBuilder.
      StringBuilder newBoardState = new StringBuilder();

      // Loop over the forsythEdwardsBoardNotationArray and add each part separated by a " " to the string builder.
      for (int i = 0; i < forsythEdwardsBoardNotationArray.length; i++) {
         // Add the section of the forsythEdwardsBoardNotationArray.
         newBoardState.append(forsythEdwardsBoardNotationArray[i]);

         // If we are not at the end of the array then separate each part with a " " character.
         if (i != forsythEdwardsBoardNotationArray.length - 1) {
            newBoardState.append(" ");
         }
      }
      // Set the board state.
      forsythEdwardsBoardNotation = newBoardState.toString();
   }

   /**
    * Returns the boards current forsyth edwards board notation.
    *
    * @return String containing board state as FEN.
    */
   public String getForsythEdwardsBoardNotation() {
      return forsythEdwardsBoardNotation;
   }

   /**
    * Returns a section of the Forsyth Edwards notation board state string variable.
    *
    * @param index which section of the string to return.
    * @return String containing the specified index.
    */
   public String getForsythEdwardsBoardNotationArrayIndex(int index) {
      return forsythEdwardsBoardNotationArray[index];
   }

   /**
    * Returns a piece on the board from a particular index, represented by a Vector2 object.
    *
    * @param coordinate the Vector2 coordinate to retrieve from the board.
    * @return Piece object from the specified position on the board.
    */
   public Piece getPiece(Vector2 coordinate) {
      return board[coordinate.x][coordinate.y];
   }

   /**
    * Sets a piece on the board to the specified piece.
    */
   private void setPiece(Vector2 piecePosition, Piece piece) {
      board[piecePosition.x][piecePosition.y] = piece;
   }

   /**
    * Returns the position of the white king.
    *
    * @return Vector2 containing the white kings position.
    */
   public Vector2 getWhiteKingPosition() {
      return whiteKingPosition;
   }

   /**
    * Returns the position of the black king.
    *
    * @return Vector2 containing the black kings position.
    */
   public Vector2 getBlackKingPosition() {
      return blackKingPosition;
   }

   /**
    * Prints the board in text form to the console window.
    */
   public void printBoardStateToConsole() {
      // Loop through the array in reverse so that the board is printed in the correct orientation.
      for (int rank = 7; rank > -1; rank--) {
         for (int file = 0; file < 8; file++) {

            // Create the vector2 for the position of the current piece to be printed.
            Vector2 boardPosition = new Vector2(file, rank);

            // Get the piece at the current board position.
            Piece piece = getPiece(boardPosition);

            // If the piece is null, print a '/' character and continue the loop.
            if (piece == null) {
               System.out.print("  /  ");
               continue;
            }

            // If the piece's color is white, print the piece's type in uppercase, else in lower case.
            if (piece.getColor() == 'w') {
               System.out.print("  " + Character.toUpperCase(piece.getType()) + "  ");
            } else {
               System.out.print("  " + piece.getType() + "  ");
            }
         }
         // New line to separate each rank.
         System.out.print("\n\n");
      }
   }

   public void clearMoves() {
      for (int row = 0; row < 8; row++) {
         for (int column = 0; column < 8; column++) {
            if (board[row][column] != null) {
               board[row][column].clearMoves();
            }
         }
      }
   }

   /**
    * Returns whether the black player can promote. Searches the white camp to find a black pawn.
    *
    * @return boolean - Whether the black player can promote a pawn
    */
   public boolean canBlackPromote() {
      for (int column = 0; column < 8; column++) {
         Vector2 coordinate = new Vector2(column, 0);
         if (getPiece(coordinate) != null && getPiece(coordinate).getType() == 'p') {
            setAvailablePromotion(coordinate);
            return true;
         }
      }
      return false;
   }

   /**
    * Returns whether the white player can promote. Searches the black camp to find a white pawn.
    *
    * @return boolean - Whether the white player can promote a pawn
    */
   public boolean canWhitePromote() {
      for (int column = 0; column < 8; column++) {
         Vector2 coordinate = new Vector2(column, 7);
         if (getPiece(coordinate) != null && getPiece(coordinate).getType() == 'p') {
            setAvailablePromotion(coordinate);
            return true;
         }
      }
      return false;
   }

   /**
    * Promote an available piece to a queen piece.
    *
    * @param n abstract number representing desired promotion.
    */
   public void piecePromotion(int n) {
      switch (n) {
         case (0):
            getPiece(getAvailablePromotion()).setType('q');
            break;
         case (1):
            getPiece(getAvailablePromotion()).setType('r');
            break;
         case (2):
            getPiece(getAvailablePromotion()).setType('b');
            break;
         case (3):
            getPiece(getAvailablePromotion()).setType('n');
            break;
      }
      updateForsythEdwardsBoardNotation(false);
      availablePromotion = null;
   }
}
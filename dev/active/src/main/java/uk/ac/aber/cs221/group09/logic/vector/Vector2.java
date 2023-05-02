/*
 * @(#) Vector2.java 0.1 2023-03-07
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.logic.vector;

/**
 * Vector2 - A class to represent a chess piece.
 * <p>
 * How the class is used
 *
 * @author Shaun Royle
 * @version 0.1 (draft)
 * @see uk.ac.aber.cs221.group09.logic.pieces.Piece
 */
public class Vector2 {
   //board notation.
   private final char[] boardNotation = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
   //x coordinate for the board.
   public int x;
   //y coordinate for the board.
   public int y;

   //default constructor with no parameters.
   public Vector2() {
      this.x = 0;
      this.y = 0;
   }

   //constructor with parameters.
   public Vector2(int x, int y) {
      this.x = x;
      this.y = y;
   }

   /**
    * A method which returns the position represented by this vector2 as a chess board notation.
    *
    * @return the vector2 as a chess board position notation String.
    */
   public String getVector2AsBoardNotation() {
      return boardNotation[x] + Integer.toString(y + 1);
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof Vector2)) {
         return false;
      }
      Vector2 compare = (Vector2) o;
      return this.x == compare.x && this.y == compare.y;
   }
}
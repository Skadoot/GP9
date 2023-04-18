package uk.ac.aber.cs221.group09.logic.vector;

public class Vector2 {
    //x coordinate for the board.
    public int x;

    //y coordinate for the board.
    public int y;

    //board notation.
    private final char[] boardNotation = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

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
        return boardNotation[x] + Integer.toString(y);
    }
}
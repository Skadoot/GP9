import vector.vector2;
import java.util.ArrayList;

/**
 * A class which stores general game information.
 *
 * @version 1.0 general outline of the game loop.
 *
 * @author shr27@aber.ac.uk
 */
public class game {
    //this is the order of operations that will happen every turn
    private final board gameBoard;

    //this is the player taking the current move.
    private char attackingPlayer;

    //keep track of which move it is.
    private int moveCount;

    public Log log;

    /**
     * Constructor for game.
     *
     * @param boardState the initial board state string for the game (Forsyth Edwards Notation).
     */
    public game(String boardState, String fileName, boolean load) {
        if (!load){
            gameBoard = new board(boardState);
            this.log = new Log(fileName, false);
            move();
        } else {
            gameBoard = new board(boardState);
            this.log = new Log(fileName, true);
            move();
        }
    }

    /**
     * A method which outlines the general loop of the game.
     */
    public void move() {
        //determine the player making the current move.
        determineCurrentPlayer();

        //calculate the legal moves for the board. with the current player.
        moveCalculator moveCalculator = new moveCalculator(attackingPlayer, gameBoard);

        moveCalculator.findLegalMovesForPlayer(true);
        moveCalculator.findLegalMovesForPlayer(false);

        //print the board to the console.
        gameBoard.printBoardStateToConsole();
        moveCalculator.printCheckMap();
        System.out.println("is " + attackingPlayer + " in check = " + moveCalculator.isPlayerInCheck());

        //wait for the UI to give us a selected piece, here we would set it to be the coordinate that the ui passes back to us.
        vector2 selectedBoardCoordinate = new vector2();
        while (gameBoard.getPiece(selectedBoardCoordinate) == null || gameBoard.getPiece(selectedBoardCoordinate).getColor() != attackingPlayer) {
            selectedBoardCoordinate = new vector2();
        }

        //if a piece is selected return its legal moves to the UI. then the UI draws its legal moves.
        ArrayList<vector2> selectedPieceLegalMoves = gameBoard.getPiece(selectedBoardCoordinate).getPossibleMoves();

        //UI waits for the player to select a move square.
        vector2 moveSquare = new vector2();
        boolean legalMoveMade = false;

        //loop while a legal move has not been selected
        while (!legalMoveMade) {
            moveSquare = new vector2();

            //if the selected piece's legal move list contains the selected move square a legal move has been selected.
            if (gameBoard.getPiece(selectedBoardCoordinate).getPossibleMoves().contains(moveSquare)) {
                legalMoveMade = true;
                gameBoard.movePiece(gameBoard.getPiece(selectedBoardCoordinate), moveSquare);
                moveCount += (attackingPlayer == 'b') ? 1 : 0;
            } else if (gameBoard.getPiece(moveSquare).getColor() == attackingPlayer) {
                selectedBoardCoordinate = gameBoard.getPiece(moveSquare).getPosition();
            }
        }
    }

    /**
     * A method which determines which player's move it is.
     * using the board's Forsyth Edwards Notation string.
     */
    private void determineCurrentPlayer() {
        //check the board state string to find which player's turn it is.
        attackingPlayer = gameBoard.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0];
    }


}

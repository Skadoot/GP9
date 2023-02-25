import vector.vector2;

import java.util.ArrayList;

public class game
{
    //this is the order of operations that will happen every turn
    private board gameBoard;

    //this is the player taking the current move.
    private char attackingPlayer;

    //keep track of which move it is.
    private int moveCount;

    /**
     *
     * @param boardState
     */
    public game(String boardState) {
        gameBoard = new board();
        gameBoard.initializeBoardState(boardState);
        move();
    }

    /**
     *
     */
    public void move() {
        //determine the player making the current move.
        determine_current_player();

        //filters the moves for every piece of the current player.
        gameBoard.findLegalMovesForPlayer(attackingPlayer);
        gameBoard.printBoardStateToConsole();

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

        while (!legalMoveMade) {
            moveSquare = new vector2();

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
     *
     */
    private void determine_current_player() {
        String boardState = gameBoard.getBoardState();
        //check the board state string to find which player's turn it is.
        for (int i = 0; i < boardState.length(); i++) {
            if (boardState.charAt(i) == ' ') {
                attackingPlayer = boardState.charAt(i + 1);
                return;
            }
        }
    }
}

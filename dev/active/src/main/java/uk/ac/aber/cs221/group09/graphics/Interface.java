package uk.ac.aber.cs221.group09.graphics;

import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.aber.cs221.group09.logic.pieces.Piece;
import uk.ac.aber.cs221.group09.logic.Board;
import uk.ac.aber.cs221.group09.logic.MoveCalculator;
import uk.ac.aber.cs221.group09.logic.vector.Vector2;

import java.io.IOException;
import java.util.ArrayList;

public class Interface extends Application {
   private Stage primaryStage;
   private PlayerNameScreen playerNameScreen;
   private PlayScreen playScreen;
   private StartScreen startScreen;
   private LoadScreen loadScreen;
   private Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
   private MoveCalculator moveCalc = new MoveCalculator(board.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0], board);
   private Piece pieceToMove;

   @Override
   public void start(Stage stage) throws IOException {
      primaryStage = stage;

      //Create instances of the screen handling classes and attach this interface instance to those classes.
      playerNameScreen = new PlayerNameScreen(this);
      playScreen = new PlayScreen(this);
      startScreen = new StartScreen(this);
      loadScreen = new LoadScreen(this);


      stage.setTitle("Gorpu Chess!");
      stage.setScene(startScreen.getStartScreen());
      stage.show();
   }

   public static void main(String[] args) {
      launch();
   }

   public Stage getStage() {
      return primaryStage;
   }

   /**
    * Intended to send the coordinates of the clicked tile to the backend. Currently the playground to test chessboard
    * features.
    *
    * @param column
    * @param row
    */
   public void click(int column, int row) {
      playScreen.updatePlayScreen(board.getForsythEdwardsBoardNotation());
      ArrayList<Vector2> validTiles;
      Vector2 selectedTile = new Vector2(row, column);
      //System.out.print(board.getPiece(selectedTile));
      pieceToMove = board.getPiece(selectedTile);
      moveCalc.getLegalMoveForPiece(pieceToMove, false);

      validTiles = (pieceToMove.getPossibleMoves());


      playScreen.highlightPossibleMoves(validTiles);
   }

   public void toMenu() {
      primaryStage.setScene(startScreen.getStartScreen());
   }

   public void toPNScreen() {
      primaryStage.setScene(playerNameScreen.getScene());
   }

   public void toChessboard() {
      primaryStage.setScene(playScreen.getScene());
   }

   public void toNewChessboard(String whiteName, String blackName) {
      playScreen.setWhitePlayerName(whiteName);
      playScreen.setBlackPlayerName(blackName);
      playScreen.updatePlayScreen(board.getForsythEdwardsBoardNotation());
      primaryStage.setScene(playScreen.getScene());
   }

   /**
    * Switch scene displayed to a scene that displays all the finished games to view through.
    */
   public void loadFGames() {
      //loadScreen.populateButtonBar(list of finished games);
      loadScreen.setLabel("Finished Games:");
      primaryStage.setScene(loadScreen.getScene());
   }

   /**
    * Switch the scene displayed to a scene that displays all the unfinished games to pick back up from.
    */
   public void loadUFGames() {
      //loadScreen.populateButtonBar(list of unfinished games);
      loadScreen.setLabel("Unfinished Games:");
      primaryStage.setScene(loadScreen.getScene());
   }
}
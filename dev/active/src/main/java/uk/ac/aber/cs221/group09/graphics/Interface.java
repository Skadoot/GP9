/*
 * @(GP9) Interface.java 0.5 2023/04/27
 *
 * Copyright (c) 2021 Aberystywth University
 * All rights reserved
 *
 */

package uk.ac.aber.cs221.group09.graphics;

import javafx.application.Application;
import javafx.stage.Stage;

import uk.ac.aber.cs221.group09.logic.pieces.Piece;
import uk.ac.aber.cs221.group09.logic.Board;
import uk.ac.aber.cs221.group09.logic.MoveCalculator;
import uk.ac.aber.cs221.group09.logic.vector.Vector2;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface - The class contains the primary stage for displaying the chess tutor application
 *
 * The class is used to initialise the application. It's the top level of the system and messages information
 * to the backend. Information from the backend is then sent through to the GUI through this interface class.
 *
 * @author Gwion Hughes, Ciaran Smith
 * @version 0.9 draft
 * @see StartScreen
 */
public class Interface extends Application {
   private Stage primaryStage;
   private PlayerNameScreen playerNameScreen;
   private PlayScreen playScreen;
   private StartScreen startScreen;
   private LoadScreen loadScreen;
   private Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
   private MoveCalculator moveCalc = new MoveCalculator(board.getForsythEdwardsBoardNotationArrayIndex(1).toCharArray()[0], board);
   private Piece pieceToMove;
   boolean firstPieceClick = true;
   ArrayList<Vector2> movesToCompare;

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

      Vector2 selectedTile = new Vector2(row, column);
      playScreen.updatePlayScreen(board.getForsythEdwardsBoardNotation());
      ArrayList<Vector2> validTiles;

      if (firstPieceClick) {

         do {

            //System.out.print(board.getPiece(selectedTile));
            pieceToMove = board.getPiece(selectedTile);
            moveCalc.getLegalMoveForPiece(pieceToMove, false);

            validTiles = (pieceToMove.getPossibleMoves());
            movesToCompare = validTiles;
            playScreen.highlightPossibleMoves(validTiles);

         } while (board.getPiece(selectedTile) == null);
         firstPieceClick = false;
      }else{

      System.out.println("second click");
      for (int i = 0; i < movesToCompare.size(); i++) {
         if(selectedTile.x == movesToCompare.get(i).x && selectedTile.y == movesToCompare.get(i).y){
            System.out.println("Valid Move");
            board.movePiece(pieceToMove,selectedTile);
            playScreen.updatePlayScreen(board.getForsythEdwardsBoardNotation());

         }
      }
      movesToCompare.clear();
      firstPieceClick = true;
   }

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
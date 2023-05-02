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

import uk.ac.aber.cs221.group09.logic.Game;
import uk.ac.aber.cs221.group09.logic.pieces.Piece;
import uk.ac.aber.cs221.group09.logic.vector.Vector2;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface - The class contains the primary stage for displaying the chess tutor application
 * <p>
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
   private Piece pieceToMove;
   boolean firstPieceClick = true;
   private Game game;
   //setting a default value for currentTurn to view previous moves of a game
   public int currentTurn = -1;
   //setting a default value for startedViewing for viewing previous moves of a game
   boolean startedViewing = false;
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

      game.move(row, column);
      playScreen.updatePlayScreen(game.gameNotation());
      startedViewing = false;
   }


   public int getTurnNumber() {
      String gameInfo[] = game.gameNotation().split(" ", 6);

      int turn = Integer.parseInt(gameInfo[5]);
      return turn;
   }



   public String replayFEN(boolean reverse) {
      //depending on which direction button is pressed the function operates in reverse through the log file
      if (reverse) {
         //checks to see if the move view buttons have been pressed,
         //will be irrelevant for the finished game view
         if (!startedViewing) {
            //sets the index for navigation through the played moves to be the last move played
            currentTurn = (getTurnNumber() - 2);
            //sets the boolean to true, so we can iterate through the moves without causing errors
            startedViewing = true;
            //if we've already started looking at our played moves we just continue to iterate backwards
            // from the last turn we looked at
         } else {
            //makes sure we don't go beyond the array boundaries
            if (currentTurn > 0) {
               currentTurn--;
            }
         }
         return game.log.readLog(currentTurn);
         //if we've clicked the forwards button the else will run
      } else {
         //checks to see if the move view buttons have been pressed,
         //will be irrelevant for the finished game view
         if (!startedViewing) {
            //sets the index for navigation through the played moves to be the move just played
            currentTurn = (getTurnNumber() - 1);
            //sets the boolean to true, so we can iterate through the moves without causing errors
            startedViewing = true;
         }
         //making sure we're not pushing past the boundaries of the played moves and ensuring we stop at the latest move
         // and no errors are caused
         if (!(currentTurn > getTurnNumber() - 2)) {
            //increases the turn we are looking at by 1
            currentTurn++;
            return game.log.readLog(currentTurn);
         }
      }
      return game.gameNotation();
   }

   public void toMenu() {
      primaryStage.setScene(startScreen.getStartScreen());
   }

   public void toPNScreen() {
      primaryStage.setScene(playerNameScreen.getScene());
   }

   public void toChessboard() {
      //make game with existing file name. Can we use toNewChessboard?

   }

   public void toNewChessboard(String whiteName, String blackName, String filename) {
      game = new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", filename, false);
      //set the game.log.filename
      playScreen.setWhitePlayerName(whiteName);
      playScreen.setBlackPlayerName(blackName);
      playScreen.updatePlayScreen(game.gameNotation());
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
      game = new Game();
      loadScreen.setLabel("Unfinished Games:");

      //creates an array list from the log function to display every game saved locally
      ArrayList<String> existingGamesList = game.log.displayExistingGameFiles();
      //creates an array for conversion to the correct format for the button bar
      String[] existingGamesArray = new String[existingGamesList.size()];
      //iterates through the games list and assigns each to its own index in the array
      for (int i = 0; i < existingGamesList.size(); i++) {
         existingGamesArray[i] = existingGamesList.get(i);
      }
      //the array is sent to populate the scrollpane's button bar
      loadScreen.populateButtonBar(existingGamesArray);
      primaryStage.setScene(loadScreen.getScene());
   }

   public void setGameFromSave(String filename) {
      //game.createBoard(filename);
      //playScreen.updatePlayScreen(game.gameNotation());
      //primaryStage.setScene(playScreen.getScene());
   }
}
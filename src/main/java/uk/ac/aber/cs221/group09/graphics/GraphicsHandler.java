/*
 * @(GP9) GraphicsHandler.java 0.5 2023/04/27
 *
 * Copyright (c) 2021 Aberystywth University
 * All rights reserved
 *
 */

package uk.ac.aber.cs221.group09.graphics;

import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.aber.cs221.group09.logic.Game;

import java.io.IOException;
import java.util.ArrayList;

/**
 * GraphicsHandler - The class contains the primary stage for displaying the chess tutor application
 * <p>
 * The class is used to initialise the application. It's the top level of the system and messages information
 * to the backend. Information from the backend is then sent through to the GUI through this GraphicsHandler class.
 *
 * @author Gwion Hughes, Ciaran Smith
 * @version 0.9 draft
 * @see StartScreen
 */
public class GraphicsHandler extends Application {
   private Stage primaryStage;
   private PlayerNameScreen playerNameScreen;
   private PlayScreen playScreen;
   private StartScreen startScreen;
   private LoadScreen loadScreen;
   private Game game;

   public static void main(String[] args) {
      launch();
   }

   @Override
   public void start(Stage stage) throws IOException {
      primaryStage = stage;

      //Create instances of the screen handling classes and attach this graphics handler instance to those classes.
      playerNameScreen = new PlayerNameScreen(this);
      playScreen = new PlayScreen(this);
      startScreen = new StartScreen(this);
      loadScreen = new LoadScreen(this);


      stage.setTitle("Gorpu Chess!");
      stage.setScene(startScreen.getStartScreen());
      stage.show();
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

      if (game.isPromotionAvailable()) {
         playScreen.offerPromotion();
      }

      if (game.isMoveMade()) {
         game.updateBoard();
      }

      playScreen.updatePlayScreen(game.gameNotation());
      playScreen.highlightTiles(game.validTiles(), game.checkedKing()); //might need to comment this out
   }


   public int getTurnNumber() {
      String[] gameInfo = game.gameNotation().split(" ", 7);
      int turn = Integer.parseInt(gameInfo[4]);
      return turn;
   }

   public String getPreviousFEN(int turn) {
      return game.log.readLog(turn);
   }

   public void toMenu() {
      primaryStage.setScene(startScreen.getStartScreen());

   }

   public void toPNScreen() {
      primaryStage.setScene(playerNameScreen.getScene());
   }

   public void toNewChessboard(String whiteName, String blackName, String filename) {
      game = new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 -", filename, false);
      //set the game.log.filename

      playScreen = new PlayScreen(this);

      playScreen.setWhitePlayerName(whiteName);
      playScreen.setBlackPlayerName(blackName);
      playScreen.updatePlayScreen(game.gameNotation());
      primaryStage.setScene(playScreen.getScene());
   }

   /**
    * Switch scene displayed to a scene that displays all the finished games to view through.
    */
   public void loadFGames() {
      game = new Game();

      loadScreen.setLabel("Finished Games:");
      // Set the log to look for games in the finished games directory
      game.log.setFinishedGame(true);
      // Creates an array list from the log function to display every game saved locally
      ArrayList<String> existingGamesList = game.log.displayExistingGameFiles();
      // Creates an array for conversion to the correct format for the button bar
      String[] existingGamesArray = new String[existingGamesList.size()];
      // Iterates through the games list and assigns each to its own index in the array
      for (int i = 0; i < existingGamesList.size(); i++) {
         existingGamesArray[i] = existingGamesList.get(i);
      }
      // The array is sent to populate the scrollpane's button bar
      loadScreen.populateButtonBar(existingGamesArray, true);
      primaryStage.setScene(loadScreen.getScene());
   }

   /**
    * Switch the scene displayed to a scene that displays all the unfinished games to pick back up from.
    */
   public void loadUFGames() {
      game = new Game();
      loadScreen.setLabel("Unfinished Games:");
      // Set the log to look for games in the unfinished games directory
      game.log.setFinishedGame(false);
      // Creates an array list from the log function to display every unfinished game saved locally
      ArrayList<String> existingGamesList = game.log.displayExistingGameFiles();
      // Creates an array for conversion to the correct format for the button bar
      String[] existingGamesArray = new String[existingGamesList.size()];
      // Iterates through the games list and assigns each to its own index in the array
      for (int i = 0; i < existingGamesList.size(); i++) {
         existingGamesArray[i] = existingGamesList.get(i);
      }
      // The array is sent to populate the scrollpane's button bar
      loadScreen.populateButtonBar(existingGamesArray, false);
      primaryStage.setScene(loadScreen.getScene());
   }

   /**
    * This function is called to set up the board on an empty game based on a save's FEN string.
    * It passes the filename of the save to the game in order set up the unfinished/finished game to play/view.
    *
    * @param filename Name of save.
    */
   public void setGameFromSave(String filename, boolean isFinished) {
      game.createGame(filename, isFinished);
      // Make a whole new playScreen.
      playScreen = new PlayScreen(this);
      playScreen.updatePlayScreen(game.gameNotation());
      if (isFinished) {
         playScreen.setGameFinished();
      }
      primaryStage.setScene(playScreen.getScene());
   }

   public void requestPromotion(int n) {
      game.promote(n);
      playScreen.updatePlayScreen(game.gameNotation());
      playScreen.highlightTiles(game.validTiles(), game.checkedKing());
   }

   public void updateGameOver(char c) {
      game.endGame(c);
   }

   public void deleteGame() {
      game.log.deleteFile();
   }
}
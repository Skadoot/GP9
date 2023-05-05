/*
 * @(GP9) StartScreen.java 1.0 2023/05/01
 *
 * Copyright (c) 2023 Aberystwyth University
 * All rights reserved.
 */

package uk.ac.aber.cs221.group09.graphics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * StartScreen - This class contains the first scene the user is greeted by upon launching the application.
 * <p>
 * This class contains the menu scene and all the scenes of application are navigated to from or back to this scene.
 *
 * @author Gwion Hughes
 * @version 1.0 (Release)
 * @see GraphicsHandler
 */
public class StartScreen {
   private final GraphicsHandler anGraphicsHandler;
   private Scene startScreen;
   private ImageView title;

   /**
    * Simple constructor for the StartScreen class. Takes an GraphicsHandler object as a parameter.
    *
    * @param anGraphicsHandler - Class containing the application window.
    */
   public StartScreen(GraphicsHandler anGraphicsHandler) {
      this.anGraphicsHandler = anGraphicsHandler;
      title = new ImageView(new Image("/images/gorupchess.png"));
      constructScreen();
   }

   public Scene getStartScreen() {
      return startScreen;
   }

   /**
    * Creates a scene with a vertical box as its root with three buttons for navigating to loading finished games,
    * unfinished games, or starting a new game.
    */
   private void constructScreen() {
      VBox rootMenu = new VBox();

      VBox btnSelection = new VBox();
      Button newGameButton = new Button();
      Button continueGameButton = new Button();
      Button viewGameButton = new Button();

      // This button takes the user to the PlayerName scene.
      newGameButton.setText("Start New Game");
      newGameButton.setOnAction(actionEvent -> requestNewGame());

      // This button takes the user to the LoadGame scene with a selection of unfinished games to pick back up
      continueGameButton.setText("Load Unfinished Game");
      continueGameButton.setOnAction(actionEvent -> requestToUnfinishedGames());

      // This button takes the user to the LoadGame scene with a selection of finished games to view.
      viewGameButton.setText("View Finished Game");
      viewGameButton.setOnAction(actionEvent -> requestToViewFinishedGames());

      // The below code places the buttons.
      btnSelection.setPadding(new Insets(5, 5, 5, 5));
      btnSelection.setSpacing(12);

      btnSelection.getChildren().add(newGameButton);
      btnSelection.getChildren().add(continueGameButton);
      btnSelection.getChildren().add(viewGameButton);

      //Add title and buttons to start menu.
      rootMenu.getChildren().addAll(title,btnSelection);
      rootMenu.setAlignment(Pos.CENTER);
      rootMenu.setSpacing(24);

      btnSelection.setAlignment(Pos.CENTER);

      Scene menu = new Scene(rootMenu, 1280, 720);
      menu.getStylesheets().add(StartScreen.class.getResource("/css/StartScreenStyleSheet.css").toExternalForm());

      this.startScreen = menu;
   }

   private void requestNewGame() {
      anGraphicsHandler.toPNScreen();
   }

   private void requestToViewFinishedGames() {
      anGraphicsHandler.loadFGames();
   }

   private void requestToUnfinishedGames() {
      anGraphicsHandler.loadUFGames();
   }
}

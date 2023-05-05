/*
 * @(GP9) PlayerNameScreen.java 1.0 2023/04/27
 *
 * Copyright (c) 2021 Aberystywth University
 * All rights reserved
 */

package uk.ac.aber.cs221.group09.graphics;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PlayerNameScreen - A class for displaying the scene to enter player names before a new game
 * <p>
 * This class is switched to when the user requests a new game.
 * Users will be given the opportunity to enter names for the white and black player before proceeding to a new chessboard.
 *
 * @author Gwion Hughes
 * @version 1.0 (Release)
 * @see PlayScreen
 */
public class PlayerNameScreen {
   private final GraphicsHandler anGraphicsHandler;
   private Scene scene;
   private TextField textFieldWhite, textFieldBlack, textFieldFile;

   /**
    * Simple constructor.
    *
    * @param anGraphicsHandler GraphicsHandler containing the application window for this class' scene.
    */
   public PlayerNameScreen(GraphicsHandler anGraphicsHandler) {
      this.anGraphicsHandler = anGraphicsHandler;
      createScreen();
   }

   public Scene getScene() {
      return scene;
   }

   /**
    * Scene constructor.
    * The layout of the scene has a vertical box as its root with horizontal boxes with labels and textfields for entering the players names.
    * There is also a back and start game button for navigating forwards or backwards through the chess tutour screens.
    */
   private void createScreen() {
      VBox panel = new VBox();
      panel.setAlignment(Pos.CENTER);

      // FX containers for containing the player and file names.
      HBox playerWhite = new HBox();
      playerWhite.getStyleClass().add("hbox");
      HBox playerBlack = new HBox();
      playerBlack.getStyleClass().add("hbox");
      HBox buttons = new HBox();
      buttons.getStyleClass().add("hbox");
      HBox fileName = new HBox(); //jat add
      fileName.getStyleClass().add("hbox");

      // Warning Box. Empty until otherwise.
      HBox warningBox = new HBox();
      warningBox.setAlignment(Pos.CENTER);
      fileName.getStyleClass().add("hbox");
      Text warningText = new Text(" ");
      warningBox.getChildren().add(warningText);

      // Text fields to enter names.
      this.textFieldWhite = new TextField("White Player");
      this.textFieldBlack = new TextField("Black Player");
      this.textFieldFile = new TextField("File Name");

      // Labels to place alongside text fields indicating which colour is being named.
      Label labelWhite = new Label("White:");
      Label labelBlack = new Label("Black:");
      Label labelFile = new Label("Name of File");

      // Create a button that on click calls GraphicsHandler to swap scene to chessboard
      Button startGame = new Button("Start Game");
      startGame.setOnAction(actionEvent -> {
         warningText.setText(" ");
         if (nameIsBlank()) {
            warningText.setText("Names cannot be blank or just whitespace.");
            return;
         } else if (nameCheckSpecChar()) {
            warningText.setText("Names cannot contain special characters.");
            return;
         } else if (isNameTooLong()) {
            warningText.setText("Names cannot exceed 32 character limit.");
            return;
         }

         forwardsToNewGame(textFieldBlack.getText(), textFieldWhite.getText(), textFieldFile.getText());
      });
      Button back = new Button("Back");

      // Create a button that on click calls the GraphicsHandler to backtrack to the start screen
      back.setOnAction(actionEvent -> backToMenu());

      playerWhite.getChildren().addAll(labelWhite, textFieldWhite);
      playerBlack.getChildren().addAll(labelBlack, textFieldBlack);
      fileName.getChildren().addAll(labelFile, textFieldFile);
      buttons.getChildren().addAll(back, startGame);

      panel.getChildren().addAll(playerWhite, playerBlack, fileName, warningBox, buttons);

      Scene scene = new Scene(panel, 1280, 720);
      scene.getStylesheets().add(PlayerNameScreen.class.getResource("/css/PlayerNameScreen.css").toExternalForm());
      this.scene = scene;
   }

   private boolean nameCheckSpecChar() {
      Pattern pattern = Pattern.compile("[^A-Za-z0-9 ]");
      Matcher matchBlack = pattern.matcher(textFieldBlack.getText());

      Matcher matchWhite = pattern.matcher(textFieldWhite.getText());

      Matcher matchFileName = pattern.matcher(textFieldFile.getText());
      return (matchWhite.find() || matchBlack.find() || matchFileName.find());
   }

   private boolean nameIsBlank() {
      boolean fileNameBad = textFieldFile.getText().isEmpty() || textFieldFile.getText().isEmpty();
      boolean whiteNameBad = textFieldWhite.getText().isEmpty() || textFieldWhite.getText().isEmpty();
      boolean blackNameBad = textFieldBlack.getText().isEmpty() || textFieldBlack.getText().isEmpty();
      return (fileNameBad || whiteNameBad || blackNameBad);
   }

   private boolean isNameTooLong() {
      String whiteName = textFieldWhite.getText();
      String blackName = textFieldBlack.getText();
      String fileName = textFieldFile.getText();
      return (whiteName.length() > 32 || blackName.length() > 32 || fileName.length() > 32);
   }

   private void backToMenu() {
      anGraphicsHandler.toMenu();
   }

   private void forwardsToNewGame(String blackN, String whiteN, String fileName) {
      anGraphicsHandler.toNewChessboard(whiteN, blackN, fileName);
   }
}

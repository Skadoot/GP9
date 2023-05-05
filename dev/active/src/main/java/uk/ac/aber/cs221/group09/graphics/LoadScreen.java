/*
 * @(GP9) LoadScreen.java 1.0 2023/04/27
 *
 * Copyright (c) 2021 Aberystywth University
 * All rights reserved
 */

package uk.ac.aber.cs221.group09.graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

/**
 * LoadGameButton - A class for displaying saves in the LoadScreen
 * <p>
 * This class ties a button to a save.
 * It returns an index when the button is selected that is used to load a particular game from the save store.
 *
 * @author Gwion Hughes
 * @version 1.0 (Release)
 * @see LoadScreen
 */
public class LoadScreen {
   private final GraphicsHandler anGraphicsHandler;
   private Scene scene;
   private VBox saveContainer;
   private Label lab;

   /**
    * Constructor for instance of LoadScreen
    *
    * @param anGraphicsHandler The calls containing the primary stage displaying this class' scene.
    */
   public LoadScreen(GraphicsHandler anGraphicsHandler) {
      this.anGraphicsHandler = anGraphicsHandler;
      createScene();
   }

   public Scene getScene() {
      return scene;
   }

   /**
    * Setter for the LoadScreen label
    *
    * @param newLabel Label displaying whether the saves shown are finished or unfinished games.
    */
   public void setLabel(String newLabel) {
      this.lab.setText(newLabel);
   }

   /**
    * Initialises the scene of the class that will be displayed on the primary stage.
    * A GridPane is used to display buttons on a scrollable vertical box that will be mapped to saves in the backend.
    */
   private void createScene() {
      // GridPane. This is the root of the entire LoadScreen scene.
      GridPane layout = new GridPane();
      layout.setAlignment(Pos.CENTER);
      // Setting up column and row width for the layout GridPane. Currently, an 18x12 grid.
      for (int counter = 0; counter < 18; counter++) {
         ColumnConstraints cConstraint = new ColumnConstraints(50);
         layout.getColumnConstraints().add(cConstraint);

         if (counter < 12) {
            RowConstraints rConstraints = new RowConstraints(50);
            layout.getRowConstraints().add(rConstraints);
         }
      }

      // Label at top changes depending on whether you're looking at finished or unfinished games
      Label label = new Label("Label");
      layout.add(label, 1, 0, 4, 1);
      this.lab = label;

      // A scrollable pane that will contain buttons to each game in a vertical box.
      VBox buttonBar = new VBox();
      ScrollPane scrollPane = new ScrollPane(buttonBar);
      this.saveContainer = buttonBar;

      // Add the scrollable pane to the layout of the scene
      layout.add(scrollPane, 1, 1, 4, 9);

      // Add a button to return to the start menu.
      Button backButton = new Button("Back");
      backButton.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent actionEvent) {
            anGraphicsHandler.toMenu();
         }
      });
      layout.add(backButton, 6, 1, 2, 2);

      // Makes the gridlines visible in layout. Useful for debugging
      // layout.setGridLinesVisible(true);

      Scene scene = new Scene(layout, 1280, 720);
      scene.getStylesheets().add(LoadScreen.class.getResource("/css/LoadScreenStyleSheet.css").toExternalForm());


      this.scene = scene;
   }

   /**
    * Populate the ScrollPane containing the save buttons with a button for every save name passed to the function.
    *
    * @param stringNames - String array of save names. Can be empty.
    */
   public void populateButtonBar(String[] stringNames, boolean isFinished) {
      saveContainer.getChildren().clear();
      int length = stringNames.length;
      for (int counter = 0; counter < length; counter++) {
         LoadGameButton button = new LoadGameButton(stringNames[counter], counter, this, isFinished);
         saveContainer.getChildren().add(button.getLoadButton());
      }
   }

   /**
    * Message to the GraphicsHandler the index of the save to send to the backend and load.
    */
   public void requestSave(String fileName, boolean isFinished) {
      anGraphicsHandler.setGameFromSave(fileName, isFinished);
   }
}


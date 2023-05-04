/*
 * @(GP9) LoadGameButton.java 1.0 2023/04/27
 *
 * Copyright (c) 2021 Aberystywth University
 * All rights reserved
 */

package uk.ac.aber.cs221.group09.graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


/**
 * LoadGameButton - A class for displaying saves in the LoadScreen
 * <p>
 * This class ties a button to a save.
 * It returns an index when the button is selected that is used to load a particular game from the save store.
 *
 * @author Gwion Hughes
 * @version 1.0 (Release)
 * @see uk.ac.aber.cs221.group9.graphics.LoadScreen
 */
public class LoadGameButton {
    private Button loadButton;
    private int saveNumber;
    private String saveName;
    private LoadScreen loadScreen;
    private boolean isFinishedGame;

    /**
     * Constructor for a LoadGame Button. Takes the save name as a string to display, the index of the save to return
     * when its button is pressed, and the LoadScreen to message information to.
     * @param saveName String of the save's name. i.e "White v Black[Date]"
     * @param saveNumber The index of the save in the backend
     * @param loadScreen The class containing the screen displaying the LoadGameButton.
     */
    public LoadGameButton(String saveName, int saveNumber, LoadScreen loadScreen, boolean isFinished) {
        this.saveName = saveName;
        this.saveNumber = saveNumber;
        this.loadScreen = loadScreen;
        this.isFinishedGame = isFinished;

      // Create the FX button
      loadButton = new Button(this.saveName);
      loadButton.setPrefSize(150, 50);

      // Set the action of the button
      loadButton.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent actionEvent) {
            // Message to the load screen to pass the index of the save requested to the backend.
            selected();
         }
      });
   }

   public Button getLoadButton() {
      return loadButton;
   }

    private void selected() {
        loadScreen.requestSave(this.saveName, this.isFinishedGame);
    }
}

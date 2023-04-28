/*
 * @(GP9) LoadGameButton.java 0.3 2023/04/27
 *
 * Copyright (c) 2021 Aberystywth University
 * All rights reserved
 *
 */

package uk.ac.aber.cs221.group09.graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


/**
 * LoadGameButton - A class for displaying saves in the LoadScreen
 *
 * This class ties a button to a save. It returns an index when the button is selected that is used to load a particular
 * game from the save store.
 *
 * @author Gwion Hughes
 * @version 0.3 draft
 * @see LoadScreen
 */
public class LoadGameButton {
    private Button loadButton;
    private int saveNumber;
    private String saveName;
    private LoadScreen loadScreen;

    /**
     * Constructor for a LoadGame Button. Takes the save name as a string to display, the index of the save to return
     * when its button is pressed, and the LoadScreen to message information to.
     * @param saveName - String of the save's name. i.e "White v Black[Date]"
     * @param saveNumber - The index of the save in the backend
     * @param loadScreen - The class containing the screen displaying the LoadGameButton.
     */
    public LoadGameButton(String saveName, int saveNumber, LoadScreen loadScreen) {
        this.saveName = saveName;
        this.saveNumber = saveNumber;
        this.loadScreen = loadScreen;

        //Create the FX button
        loadButton = new Button(this.saveName);
        loadButton.setPrefSize(150, 50);

        //Set the action of the button
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Message to the loadscreen to pass the index of the save requested to the backend.
                selected();
            }
        });
    }

    private void selected() {
        loadScreen.requestSave(this.saveNumber);
    }
}

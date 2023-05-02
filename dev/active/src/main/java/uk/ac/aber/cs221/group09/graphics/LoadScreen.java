/*
 * @(GP9) LoadScreen.java 0.5 2023/04/27
 *
 * Copyright (c) 2021 Aberystywth University
 * All rights reserved
 *
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
 *
 * This class ties a button to a save. It returns an index when the button is selected that is used to load a particular
 * game from the save store.
 *
 * @author Gwion Hughes
 * @version 0.5 draft
 * @see LoadScreen
 */
public class LoadScreen {
    private Interface anInterface;
    private Scene scene;
    private VBox saveContainer;
    private Label lab;

    /**
     * Getter for the LoadScreen scene.
     * @return - scene of the LoadScreen containing all the saves
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Setter for the LoadScreen label
     * @param newLabel - Label displaying whether the saves shown are finished or unfinished games.
     */
    public void setLabel(String newLabel) {
        this.lab.setText(newLabel);
    }

    /**
     * Constructor for instance of LoadScreen
     * @param anInterface - The calls containing the primary stage displaying this class' scene.
     */
    public LoadScreen(Interface anInterface) {
        this.anInterface = anInterface;
        createScene();
    }

    /**
     * This function initialises the scene of the class that will be displayed on the primary stage.
     * A gridpane is used to display buttons on a scrollable vertical box that will be mapped to saves in the backend.
     */
    private void createScene() {
        //Gridpane. This is the root of the entire LoadScreen scene.
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        //Setting up column and row width for the layout gridpane. Currently, an 18x12 grid.
        for (int counter = 0; counter < 18; counter++) {
            ColumnConstraints cConstraint = new ColumnConstraints(50);
            layout.getColumnConstraints().add(cConstraint);

            if (counter < 12) {
                RowConstraints rConstraints = new RowConstraints(50);
                layout.getRowConstraints().add(rConstraints);
            }
        }

        //Label at top changes depending on whether your looking at finished or unfinished games
        Label label = new Label("Label");
        layout.add(label, 1, 0, 4, 1);
        this.lab = label;

        //A scrollable pane that will contain buttons to each game in a vertical box.
        VBox buttonBar = new VBox();
        ScrollPane scrollPane = new ScrollPane(buttonBar);
        this.saveContainer = buttonBar;

        //Add the scrollable pane to the layout of the scene
        layout.add(scrollPane, 1, 1, 4, 9);

        //Add a button to return to the start menu.
        Button backButton = new Button("Back");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                anInterface.toMenu();
            }
        });
        layout.add(backButton, 6, 1);


        //Makes the gridlines visible in layout. Useful for debugging
        //layout.setGridLinesVisible(true);

        Scene scene = new Scene(layout, 1280, 720);
        scene.getStylesheets().add(LoadScreen.class.getResource("/css/LoadScreenStyleSheet.css").toExternalForm());


        this.scene = scene;
    }

    /**
     * Populate the scrollpane containing the save buttons with a button for every save name passed to the function.
     * @param stringNames - String array of save names. Can be empty.
     */
    public void populateButtonBar(String[] stringNames) {
        int length = stringNames.length;
        for (int counter = 0; counter < length; counter++) {
            Button save = new Button(stringNames[counter]);
            save.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    //To add function that returns something the backend can use to distinguish a game.

                }
            });
            saveContainer.getChildren().add(save);
        }
    }

    /**
     * Message to the interface the index of the save to send to the backend and load.
     * @param num
     */
    public void requestSave(int num) {
        //My brother in christ, tell the backend of the index of the save to send to the chessboard in the playscene
        //switch to playscene
    }
}


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

public class LoadScreen {
    private Interface anInterface;
    private Scene scene;
    private VBox saveContainer;
    private Label lab;

    public LoadScreen(Interface anInterface) {
        this.anInterface = anInterface;
        createScene();
    }

    private void createScene() {
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

        layout.add(scrollPane, 1, 1, 4, 9);

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

    public void requestSave(int num) {
        //My brother in christ, tell the backend of the index of the save to send to the chessboard in the playscene
        //switch to playscene
    }


    public Scene getScene() {
        return scene;
    }

    public void setLabel(String newLabel) {
        this.lab.setText(newLabel);
    }
}


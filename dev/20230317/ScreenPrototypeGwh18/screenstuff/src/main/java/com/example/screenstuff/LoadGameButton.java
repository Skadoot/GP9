package com.example.screenstuff;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class LoadGameButton {
    private Button loadButton;
    private int saveNumber;
    private String saveName;
    private LoadScreen loadScreen;

    public LoadGameButton(String saveName, int saveNumber, LoadScreen loadScreen) {
        this.saveName = saveName;
        this.saveNumber = saveNumber;
        this.loadScreen = loadScreen;
        loadButton = new Button(this.saveName);
        loadButton.setPrefSize(150, 50);
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Message to the loadscreen to pass the index of the save requested to the backend.
                selected();
            }
        });
    }

    public void selected() {
        loadScreen.requestSave(this.saveNumber);
    }
}

package com.example.screenstuff;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A prototype for the start screen.
 * @author Gwion Hughes
 * @version 0.1
 */
public class StartScreen {
    private final Interface anInterface;
    private Scene playScreen;

    public StartScreen(Interface anInterface) {
        this.anInterface = anInterface;
    }

    /**
     * Creates a scene containing a flow pane and currently three empty buttons. Returns the scene.
     */
    public Scene constructScreen() {
        VBox btnSelection = new VBox();
        Button ngButton = new Button();
        Button conButton = new Button();
        Button vgButton = new Button();

        ngButton.setText("New Game");
        ngButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                anInterface.getStage().setScene(playScreen);
            }
        });
        conButton.setText("Continue");
        vgButton.setText("View Game");

        //btnSelection.setPadding(new Insets(5,5,5,5));
        //btnSelection.setSpacing(12);

        btnSelection.getChildren().add(ngButton);
        btnSelection.getChildren().add(conButton);
        btnSelection.getChildren().add(vgButton);

        //btnSelection.setAlignment(Pos.CENTER);

        Scene menu = new Scene(btnSelection, 1200, 700);
        menu.getStylesheets().add("StartScreenStyleSheet.css");

        return menu;
    }

    public void setPlayScreen(Scene playScreen) {
        this.playScreen = playScreen;
    }
}

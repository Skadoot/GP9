package uk.ac.aber.cs221.group09.graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerNameScreen {
    private Interface anInterface;
    private Scene scene;

    public PlayerNameScreen(Interface anInterface) {
        this.anInterface = anInterface;
        createScreen();
    }

    private void createScreen() {
        VBox panel = new VBox();
        panel.setAlignment(Pos.CENTER);

        HBox playerWhite = new HBox();
        playerWhite.getStyleClass().add("hbox");
        HBox playerBlack = new HBox();
        playerBlack.getStyleClass().add("hbox");
        HBox buttons = new HBox();
        buttons.getStyleClass().add("hbox");

        TextField textFieldWhite = new TextField("Enter your name");
        TextField textFieldBlack = new TextField("Enter your name");

        Label labelWhite = new Label("White:");
        Label labelBlack = new Label("Black:");

        //Create a button that on click calls interface to swap scene to chessboard
        Button startGame = new Button("Start Game");
        startGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                forwardsToNewGame(textFieldBlack.getText(), textFieldWhite.getText());
            }
        });
        Button back = new Button("Back");

        //Create a button that on click calls the interface to backtrack to thee start screen
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                backToMenu();
            }
        });

        playerWhite.getChildren().addAll(labelWhite, textFieldWhite);
        playerBlack.getChildren().addAll(labelBlack, textFieldBlack);
        buttons.getChildren().addAll(back, startGame);

        panel.getChildren().addAll(playerWhite, playerBlack, buttons);

        Scene scene = new Scene(panel, 1280, 720);
        scene.getStylesheets().add(PlayerNameScreen.class.getResource("/css/PlayerNameScreen.css").toExternalForm());
        this.scene = scene;
    }

    public void backToMenu() {
        anInterface.toMenu();
    }

    public void forwardsToNewGame(String blackN, String whiteN) {
        anInterface.toNewChessboard(whiteN, blackN);
    }

    public Scene getScene() {
        return scene;
    }
}

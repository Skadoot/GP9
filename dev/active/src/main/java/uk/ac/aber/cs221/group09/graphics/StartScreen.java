package uk.ac.aber.cs221.group09.graphics;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * A prototype for the start screen.
 *
 * @author Gwion Hughes
 * @version 0.1
 */
public class StartScreen {
    private final Interface anInterface;
    private Scene startScreen;

    public StartScreen(Interface anInterface) {
        this.anInterface = anInterface;
        constructScreen();
    }

    /**
     * Creates a scene containing a flow pane and currently three empty buttons.
     */
    private void constructScreen() {
        VBox btnSelection = new VBox();
        Button ngButton = new Button();
        Button conButton = new Button();
        Button vgButton = new Button();

        ngButton.setText("Start New Game");
        ngButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                requestNewGame();
            }
        });
        conButton.setText("Load Unfinished Game");
        conButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                requestToUnfinishedGames();
            }
        });
        vgButton.setText("View Finished Game");
        vgButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                requestToViewFinishedGames();
            }
        });

        btnSelection.setPadding(new Insets(5, 5, 5, 5));
        btnSelection.setSpacing(12);

        btnSelection.getChildren().add(ngButton);
        btnSelection.getChildren().add(conButton);
        btnSelection.getChildren().add(vgButton);

        btnSelection.setAlignment(Pos.CENTER);

        Scene menu = new Scene(btnSelection, 1280, 720);
        menu.getStylesheets().add(StartScreen.class.getResource("/css/StartScreenStyleSheet.css").toExternalForm());

        this.startScreen = menu;
    }

    public Scene getStartScreen() {
        return startScreen;
    }

    public void requestNewGame() {
        anInterface.toPNScreen();
    }

    public void requestToViewFinishedGames() {
        anInterface.loadFGames();
    }

    public void requestToUnfinishedGames() {
        anInterface.loadUFGames();
    }
}

package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The {@code Client} class is the main entry point for the Tic Tac Toe application.
 * It extends {@link Application} and loads the game's UI from an FXML file.
 *
 * @author Anastasija Canic
 * @version 2.0
 * @since 19/03/2025
 */
public class Client extends Application {

    /**
     * Starts the JavaFX application by setting up the primary stage and loading the game UI.
     *
     * @param primaryStage The main stage for the application.
     * @throws Exception If there is an issue loading the FXML file.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.show();
    }
}
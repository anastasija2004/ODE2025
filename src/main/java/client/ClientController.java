package client;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.*;
import java.net.Socket;

/**
 * The {@code ClientController} class manages the game logic and UI interactions
 * for the Tic Tac Toe client. It handles communication with the server and updates
 * the UI based on game events.
 *
 * @author Anastasija Canic
 * @version 2.0
 * @since 19/03/2025
 */
public class ClientController {

    @FXML private Button button00, button01, button02, button10, button11, button12, button20, button21, button22;
    @FXML private Text statusText;
    @FXML private Button restartButton, quitButton;

    private PrintWriter out;
    private BufferedReader in;
    private boolean isMyTurn = false;
    private String serverAddress;
    private int serverPort;

    private void enableRestartButton() {
        restartButton.setDisable(false);
    }


    /**
     * Initializes the client by establishing a connection to the server
     * and starting a background thread to listen for server messages.
     */
    @FXML
    public void initialize() {
        // Load server configuration from config.txt
        loadServerConfig();
        //print config buffer in terminal to debug
        System.out.println(serverAddress + " " + serverPort);
        try {
            // Connects to the server using the loaded configuration
            Socket socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Thread(this::listenToServer).start();
        } catch (IOException e) {
            statusText.setText("Error: Cannot connect to server.");
            System.err.println("Connection error: " + e.getMessage());
            disableAllButtons();
        }
    }

    /**
     * Loads the server address and port from the config.txt file.
     */
    private void loadServerConfig() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/client/config.txt"))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    serverAddress = parts[0].trim();
                    serverPort = Integer.parseInt(parts[1].trim());
                    System.out.println("Loaded config: " + serverAddress + " " + serverPort);
                } else {
                    statusText.setText("Error: Configuration format is incorrect.");
                    disableAllButtons();
                }
            } else {
                statusText.setText("Error: Configuration file is empty or missing.");
                disableAllButtons();
            }
        } catch (IOException | NumberFormatException e) {
            statusText.setText("Error: Unable to read or parse the configuration file.");
            disableAllButtons();
            System.err.println("Error reading config file: " + e.getMessage());
        }
    }

    /**
     * Listens for messages from the server and processes them accordingly.
     */
    private void listenToServer() {
        try {
            String response;
            while ((response = in.readLine()) != null) {
                String finalResponse = response;
                Platform.runLater(() -> processServerMessage(finalResponse));
            }
        } catch (IOException e) {
            Platform.runLater(() -> statusText.setText("Connection lost."));
        }
    }

    /**
     * Processes messages received from the server and updates the game state.
     *
     * @param message The message received from the server.
     */
    private void processServerMessage(String message) {
        switch (message) {
            case "TURN":
                isMyTurn = true;
                statusText.setText("Your turn!");
                enableAllButtons();
                break;
            case "WIN":
                statusText.setText("You win!");
                disableAllButtons();
                enableRestartButton();
                break;
            case "DRAW":
                statusText.setText("It's a draw!");
                disableAllButtons();
                enableRestartButton();
                break;
            case "LOSE":
                statusText.setText("You lose!");
                disableAllButtons();
                enableRestartButton();
                break;
            case "RESET":
                resetUI();
                break;
            case "QUIT":
                statusText.setText("Opponent quit the game.");
                disableAllButtons();
                enableRestartButton();
                break;
            default:
                updateBoard(message);
        }
    }


    /**
     * Updates the board based on the received move.
     *
     * @param move The move received from the server in the format "row col symbol".
     */

    private void updateBoard(String move) {
        String[] parts = move.split(" ");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);
        String symbol = parts[2];

        Button button = getButton(row, col);
        if (button != null) {
            // Use the fade-in animation instead of directly setting the text
            applyFadeAnimation(button, symbol);  // Display X or O with fade effect
            button.setDisable(true);  // Disable the button after the move
        }

        isMyTurn = false;
        statusText.setText("Waiting for opponent...");
    }


    /**
     * Handles a button click event during the game.
     * Sends the move to the server if it's the player's turn.
     *
     * @param event The button click event.
     */

    @FXML
    private void handleClick(javafx.event.ActionEvent event) {
        if (!isMyTurn) return;  // Prevent clicks when it's not the player's turn.

        Button clickedButton = (Button) event.getSource();
        int row = getRow(clickedButton);
        int col = getCol(clickedButton);

        if (clickedButton.getText().isEmpty()) {
            out.println(row + " " + col);
            // Set X or O here and apply the fade-in effect
            String symbol = isMyTurn ? "X" : "O";
            applyFadeAnimation(clickedButton, symbol);  // Apply fade-in animation
            clickedButton.setDisable(true);  // Disable the button after the move
        }
    }


    /**
     * Handles the restart button click event and sends a reset request to the server.
     */
    @FXML
    private void handleRestart() {
        out.println("RESET");  // Notify server to reset the game
        resetUI();  // Reset the UI on the client side
        restartButton.setDisable(true);  // Disable the restart button until next game ends
    }

    /**
     * Handles the quit button click event and sends a quit request to the server.
     * Also closes the application.
     */
    @FXML
    private void handleQuit() {
        out.println("QUIT");
        Platform.exit();
    }

    /**
     * Resets the UI to start a new game.
     */
    private void resetUI() {
        for (Button btn : new Button[]{button00, button01, button02, button10, button11, button12, button20, button21, button22}) {
            btn.setText("");
            btn.setOpacity(1.0);
            btn.setDisable(false);
        }
        statusText.setText("New game started!");
        restartButton.setDisable(true);  // Disable restart button until game ends
        //Starting connection to the server again and displaying turn signal
        initialize();
        statusText.setText("Waiting for opponent...");

    }


    /**
     * Disables all board buttons, preventing further moves.
     */
    private void disableAllButtons() {
        for (Button btn : new Button[]{button00, button01, button02, button10, button11, button12, button20, button21, button22}) {
            btn.setDisable(true);
        }
    }

    /**
     * Enables all empty board buttons, allowing the player to make a move.
     */
    private void enableAllButtons() {
        for (Button btn : new Button[]{button00, button01, button02, button10, button11, button12, button20, button21, button22}) {
            if (btn.getText().isEmpty()) btn.setDisable(false);
        }
    }

    /**
     * Retrieves the corresponding button based on row and column indices.
     *
     * @param row The row index of the button.
     * @param col The column index of the button.
     * @return The corresponding {@link Button}, or {@code null} if not found.
     */
    private Button getButton(int row, int col) {
        return switch (row * 3 + col) {
            case 0 -> button00;
            case 1 -> button01;
            case 2 -> button02;
            case 3 -> button10;
            case 4 -> button11;
            case 5 -> button12;
            case 6 -> button20;
            case 7 -> button21;
            case 8 -> button22;
            default -> null;
        };
    }

    /**
     * Determines the row index of a given button.
     *
     * @param btn The button to check.
     * @return The row index (0-2).
     */
    private int getRow(Button btn) {
        if (btn == button00 || btn == button01 || btn == button02) return 0;
        if (btn == button10 || btn == button11 || btn == button12) return 1;
        return 2;
    }

    /**
     * Determines the column index of a given button.
     *
     * @param btn The button to check.
     * @return The column index (0-2).
     */
    private int getCol(Button btn) {
        if (btn == button00 || btn == button10 || btn == button20) return 0;
        if (btn == button01 || btn == button11 || btn == button21) return 1;
        return 2;
    }

    /**
     * Applies a fade animation to a button when updating its text.
     *
     * @param button The button to animate.
     * @param text The text to display on the button.
     */
    private void applyFadeAnimation(Button button, String text) {
        button.setText(text);
        button.setOpacity(0); // Start with the button invisible
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), button);
        fadeIn.setFromValue(0); // Start with opacity 0
        fadeIn.setToValue(1); // Fade in to opacity 1
        fadeIn.play();
    }


}

package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.Socket;

public class Client2 {

    @FXML
    private Label statusLabel;

    @FXML
    private GridPane gameBoard;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private boolean playerTurn = false;  // Kontrolle, welcher Spieler dran ist
    private char currentPlayer = 'X';  // Spieler 'X' fängt an

    // Initialisierung des Spielfelds
    @FXML
    private void initialize() {
        createGameBoard();
        connectToServer();
    }

    // Verbindung zum Server herstellen
    private void connectToServer() {
        try {
            socket = new Socket("localhost", 12345);  // Stelle Verbindung zum Server her
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            listenForServerMessages();
        } catch (IOException e) {
            statusLabel.setText("Verbindung zum Server fehlgeschlagen.");
            e.printStackTrace();
        }
    }

    // Nachricht vom Server empfangen und entsprechend verarbeiten
    private void listenForServerMessages() {
        new Thread(() -> {
            try {
                String response;
                while ((response = in.readLine()) != null) {
                    handleServerMessage(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Servernachricht verarbeiten
    private void handleServerMessage(String message) {
        if (message.startsWith("Dein Zug")) {
            // Spieler ist dran
            statusLabel.setText("Dein Zug!");
            playerTurn = true;
        } else if (message.contains("hat gewonnen") || message.contains("Unentschieden")) {
            // Spiel beendet
            statusLabel.setText(message);
            playerTurn = false;
        } else {
            // Spielbrett aktualisieren
            updateBoard(message);
        }
    }

    // Spielfeld generieren
    private void createGameBoard() {
        gameBoard.getChildren().clear(); // Entfernt alte Spielzüge
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                final int finalRow = row;
                final int finalCol = col;
                Button cell = new Button();
                cell.setPrefSize(100, 100);
                cell.setOnAction(event -> handleCellClick(event, finalRow, finalCol)); // Klick-Event setzen
                gameBoard.add(cell, col, row);
            }
        }
    }

    // Behandlung eines Zell-Klicks
    private void handleCellClick(ActionEvent event, int row, int col) {
        Button cell = (Button) event.getSource(); // Holt die geklickte Zelle
        if (cell.getText().isEmpty() && playerTurn) {
            cell.setText(String.valueOf(currentPlayer));  // Setzt X oder O
            statusLabel.setText("Zug wurde gemacht!");

            // Sende Zug an Server
            out.println("MOVE " + row + " " + col + " " + currentPlayer);

            // Wechsel zu nächstem Spieler
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    // Spielfeld nach Server-Nachricht aktualisieren
    private void updateBoard(String boardState) {
        String[] rows = boardState.split("\n");
        for (int row = 0; row < 3; row++) {
            String[] cells = rows[row].split(" ");
            for (int col = 0; col < 3; col++) {
                Button cell = (Button) gameBoard.getChildren().get(row * 3 + col);
                cell.setText(cells[col].trim());
            }
        }
    }

    // Methode, die beim Klick auf den Neustart-Button aufgerufen wird
    @FXML
    public void resetGame() {
        // Alle Felder im Spielbrett zurücksetzen
        currentPlayer = 'X';
        playerTurn = false;
        statusLabel.setText("Spiel zurückgesetzt! Dein Zug.");

        // Sende Reset-Nachricht an Server
        out.println("RESET");

        createGameBoard(); // Spielbrett neu erstellen
    }
}

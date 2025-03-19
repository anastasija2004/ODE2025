package server;

import game.TicTacToe;
import game.Player;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the server class for the TicTacToe game.
 * It manages player connections and handles game logic.
 *
 * @version 1.1
 * @since 2021-03-09
 * @author Anastasija Canic
 */
public class Server {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private boolean running = false;
    private TicTacToe game;
    private Player player1;
    private Player player2;
    private Socket player1Socket;
    private Socket player2Socket;
    private BufferedReader in1, in2;
    private PrintWriter out1, out2;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server gestartet, warte auf Verbindungen...");
            running = true;
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        new Thread(() -> {
            try {
                player1Socket = serverSocket.accept();
                out1 = new PrintWriter(player1Socket.getOutputStream(), true);
                in1 = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
                player1 = new Player("Spieler 1", 'X');
                System.out.println("Spieler 1 verbunden.");
                out1.println("Willkommen Spieler 1. Warte auf Spieler 2...");

                player2Socket = serverSocket.accept();
                out2 = new PrintWriter(player2Socket.getOutputStream(), true);
                in2 = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
                player2 = new Player("Spieler 2", 'O');
                System.out.println("Spieler 2 verbunden.");
                out2.println("Willkommen Spieler 2. Spiel beginnt...");

                game = new TicTacToe(player1, player2);
                runGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void runGame() {
        try {
            PrintWriter currentOut;
            BufferedReader currentIn;
            while (true) {
                sendBoardState();
                currentOut = (game.getCurrentPlayer() == player1) ? out1 : out2;
                currentIn = (game.getCurrentPlayer() == player1) ? in1 : in2;

                currentOut.println("Dein Zug. Gib deine Position (Zeile Spalte) ein:");
                String move = currentIn.readLine();

                if (move == null) {
                    break;
                }

                String[] parts = move.split(" ");
                if (parts.length == 2) {
                    int row = Integer.parseInt(parts[0]);
                    int col = Integer.parseInt(parts[1]);
                    if (game.makeMove(row, col)) {
                        if (game.checkWin()) {
                            sendBoardState();
                            currentOut.println("Du hast gewonnen!");
                            if (currentOut == out1) out2.println("Spieler 1 hat gewonnen!");
                            else out1.println("Spieler 2 hat gewonnen!");
                            break;
                        } else if (game.isBoardFull()) {
                            sendBoardState();
                            out1.println("Unentschieden!");
                            out2.println("Unentschieden!");
                            break;
                        }
                        game.switchPlayer();
                    } else {
                        currentOut.println("Ungültiger Zug. Versuche es erneut.");
                    }
                } else {
                    currentOut.println("Ungültige Eingabe. Bitte Zeile und Spalte mit Leerzeichen getrennt eingeben.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stopServer();
        }
    }

    private void sendBoardState() {
        String board = game.getBoardState();
        out1.println("\n" + board);
        out2.println("\n" + board);
    }

    public void stopServer() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            System.out.println("Server gestoppt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
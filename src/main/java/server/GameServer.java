package server;

// import game.Player;
// import game.TicTacToe;

import java.io.*;
import java.net.*;

/**
 * This is the server class for the TicTacToe game.
 * It is responsible for setting up the server socket and accepting two clients.
 * It then creates a new TicTacToe game and two Player objects to represent the clients.
 * The server then starts the game and sends the game state to the clients after each move.
 *
 * @version 1.0
 * @since 2021-03-09
 * @author Anastasija Canic
 *
 */
public class GameServer {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    // private TicTacToe game;
    // private Player player1;
    // private Player player2;
    // private PrintWriter player1Out;
    // private PrintWriter player2Out;

    public GameServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server gestartet, wartend auf Verbindungen...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        try {
            Socket player1Socket = serverSocket.accept();
            System.out.println("Spieler 1 verbunden");
            Socket player2Socket = serverSocket.accept();
            System.out.println("Spieler 2 verbunden");
            // player1Out = new PrintWriter(player1Socket.getOutputStream(), true);
            // player2Out = new PrintWriter(player2Socket.getOutputStream(), true);
            // player1 = new Player(player1Socket, player1Out);
            // player2 = new Player(player2Socket, player2Out);
            // game = new TicTacToe(player1, player2);
            // game.startGame();

            //timeout
            //serverSocket.setSoTimeout(10000);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        try {
            serverSocket.close();
            System.out.println("Server stopped");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning() {
        return !serverSocket.isClosed();
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.startServer();
    }

}
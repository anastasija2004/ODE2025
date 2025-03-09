package server;

// import game.Player;
// import game.TicTacToe;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
    private volatile boolean running = false; // Add a flag to track server state

    public GameServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started, waiting for connections...");
            running = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        new Thread(() -> {  // Run server in a separate thread
            try {
                while (running) {
                    Socket player1Socket = serverSocket.accept();
                    System.out.println("Player 1 connected");

                    Socket player2Socket = serverSocket.accept();
                    System.out.println("Player 2 connected");
                }
            } catch (IOException e) {
                if (running) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stopServer() {
        running = false; // Set flag to false
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close(); // This will unblock accept()
            }
            System.out.println("Server stopped");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.startServer();
    }
}
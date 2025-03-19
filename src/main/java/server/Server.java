package server;

import java.io.*;
import java.net.*;

/**
 * Server class for the Tic-Tac-Toe game.
 * This server manages two player connections and game logic.
 *
 * @author Anastasija Canic
 * @version 2.0
 * @since 15/03/2025
 */

public class Server {

    private static final int PORT = 12345;
    private static final char EMPTY = ' ';
    private char[][] board = new char[3][3];  // 3x3 Tic-Tac-Toe board
    private PrintWriter out1, out2;
    private boolean player1Turn = true;  // Player 1 starts first

    /**
     * Main method to start the server.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Server().startServer();
    }

    /**
     * Starts the server and waits for two players to connect.
     * Manages the game loop and communication between players.
     */
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started... Waiting for players to connect.");

            Socket player1Socket = serverSocket.accept();
            System.out.println("Player 1 connected.");
            Socket player2Socket = serverSocket.accept();
            System.out.println("Player 2 connected.");

            out1 = new PrintWriter(player1Socket.getOutputStream(), true);
            out2 = new PrintWriter(player2Socket.getOutputStream(), true);

            BufferedReader in1 = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
            BufferedReader in2 = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));

            resetBoard(); // Initialize the game board

            out1.println("TURN"); // Notify Player 1 to start
            out2.println("WAIT");

            // Game loop
            while (true) {
                String move = in1.readLine(); // Read move from Player 1
                if (move == null || move.equals("QUIT")) {
                    out1.println("QUIT");
                    out2.println("QUIT");
                    //cut the connection to the IP
                    player1Socket.close();
                    player2Socket.close();
                    //Make sure to close the server socket
                    serverSocket.close();
                    //going to start back at the beginning of the server entry point
                    startServer();
                }

                if (!processMove(move, 'X')) {
                    out1.println("INVALID");
                    continue;
                }

                out1.println(move + " X");
                out2.println(move + " X");
                printBoard();

                if (checkWin('X')) {
                    out1.println("WIN");
                    out2.println("LOSE");
                    //cut the connection to the IP
                    player1Socket.close();
                    player2Socket.close();
                    //Make sure to close the server socket
                    serverSocket.close();
                    //going to start back at the beginning of the server entry point
                    startServer();
                }

                if (isDraw()) {
                    out1.println("DRAW");
                    out2.println("DRAW");
                    //cut the connection to the IP
                    player1Socket.close();
                    player2Socket.close();
                    //Make sure to close the server socket
                    serverSocket.close();
                    //going to start back at the beginning of the server entry point
                    startServer();
                }

                player1Turn = false;
                out1.println("WAIT");
                out2.println("TURN");

                move = in2.readLine(); // Read move from Player 2
                if (move == null || move.equals("QUIT")) {
                    out1.println("QUIT");
                    out2.println("QUIT");
                    //cut the connection to the IP
                    player1Socket.close();
                    player2Socket.close();
                    //Make sure to close the server socket
                    serverSocket.close();
                    //going to start back at the beginning of the server entry point
                    startServer();
                }

                if (!processMove(move, 'O')) {
                    out2.println("INVALID");
                    //cut the connection to the IP
                    player1Socket.close();
                    player2Socket.close();
                    //Make sure to close the server socket
                    serverSocket.close();
                    //going to start back at the beginning of the server entry point
                    startServer();
                }

                out1.println(move + " O");
                out2.println(move + " O");
                printBoard();

                if (checkWin('O')) {
                    out1.println("LOSE");
                    out2.println("WIN");
                    //cut the connection to the IP
                    player1Socket.close();
                    player2Socket.close();
                    //Make sure to close the server socket
                    serverSocket.close();
                    //going to start back at the beginning of the server entry point
                    startServer();
                }

                if (isDraw()) {
                    out1.println("DRAW");
                    out2.println("DRAW");
                    //cut the connection to the IP
                    player1Socket.close();
                    player2Socket.close();
                    //Make sure to close the server socket
                    serverSocket.close();
                    //going to start back at the beginning of the server entry point
                    startServer();
                }

                player1Turn = true;
                out1.println("TURN");
                out2.println("WAIT");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resets the Tic-Tac-Toe board to an empty state.
     */
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    /**
     * Processes a player's move and updates the board.
     * @param move The move in "row column" format.
     * @param playerSymbol The player's symbol ('X' or 'O').
     * @return True if the move is valid, false otherwise.
     */
    private boolean processMove(String move, char playerSymbol) {
        String[] parts = move.split(" ");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);

        if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != EMPTY) {
            return false; // Invalid move
        }

        board[row][col] = playerSymbol; // Place the player's symbol on the board
        return true;
    }

    /**
     * Prints the current state of the Tic-Tac-Toe board to the console.
     */
    private void printBoard() {
        System.out.println("Current Board:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Checks if a player has won the game.
     * @param playerSymbol The player's symbol ('X' or 'O').
     * @return True if the player has won, false otherwise.
     */
    private boolean checkWin(char playerSymbol) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == playerSymbol && board[i][1] == playerSymbol && board[i][2] == playerSymbol) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == playerSymbol && board[1][i] == playerSymbol && board[2][i] == playerSymbol) {
                return true;
            }
        }
        if (board[0][0] == playerSymbol && board[1][1] == playerSymbol && board[2][2] == playerSymbol) {
            return true;
        }
        if (board[0][2] == playerSymbol && board[1][1] == playerSymbol && board[2][0] == playerSymbol) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the game has ended in a draw.
     * @return True if the board is full with no winner, false otherwise.
     */
    private boolean isDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }


}

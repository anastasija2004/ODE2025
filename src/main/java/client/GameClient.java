package client;

import java.io.IOException;
import java.net.Socket;

/**
 * This is the client class for the TicTacToe game.
 * It is responsible for setting up a connection to the server.
 * It then sends user input to the server and receives the game state after each move.
 * The client then displays the game state to the user.
 *  *
 * @version 1.0
 * @since 2021-03-09
 * @author Anastasija Canic
 *
 */

public class GameClient {

    private static final String SERVER_IP = "localhost";
    private static final int PORT = 12345;



    public GameClient() {
        try {
            Socket socket = new Socket(SERVER_IP, PORT);
            System.out.println("Verbunden mit Server");
            // BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // Scanner scanner = new Scanner(System.in);
            // String input;
            // while ((input = scanner.nextLine()) != null) {
            //     out.println(input);
            //     System.out.println(in.readLine());
            // }
        } catch (IOException e) {
            e.printStackTrace();


        }


    }

    public static void main(String[] args) {
        GameClient client = new GameClient();
    }

}
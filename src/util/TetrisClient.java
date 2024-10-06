/* package util;

import com.google.gson.Gson;
import model.PureGame;
import model.OpMove;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TetrisClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3000;

    public static void main(String[] args) {
        PureGame game = new PureGame(); // Assuming you have filled game state

        // Step 1: Establish a socket connection to the server
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Step 2: Convert PureGame object to JSON
            Gson gson = new Gson();
            String jsonGameState = gson.toJson(game);

            // Step 3: Send the game state to the server
            out.println(jsonGameState);
            System.out.println("Sent game state to server: " + jsonGameState);

            // Step 4: Wait for the server's response (OpMove)
            String response = in.readLine();

            System.out.println("Received response from server: " + response);

            // Step 5: Convert the JSON response to an OpMove object
            OpMove move = gson.fromJson(response, OpMove.class);
            System.out.println("Optimal Move: X=" + move.opX() + ", Rotations=" + move.opRotate());

            // Step 6: Apply the move based on the opX and opRotate values
            if (move.opX() == 0) {
                System.out.println("Place the piece at the left-most position.");
            } else {
                System.out.println("Move the piece to X=" + move.opX());
            }
            if (move.opRotate() == 0) {
                System.out.println("No rotation needed.");
            } else {
                System.out.println("Rotate the piece " + move.opRotate() + " times.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

 */
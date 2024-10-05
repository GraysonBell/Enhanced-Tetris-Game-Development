package util;

import com.google.gson.Gson;
import model.OpMove;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
    private final String host;

    private final int port;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public OpMove sendJsonObject(Object o) {
        Gson gson = new Gson();
        try {
            Socket socket = new Socket(this.host, this.port);
            try {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String jsonMessage = gson.toJson(o);
                out.println(jsonMessage);
                String jsonResponse = in.readLine();
                OpMove opMove = (OpMove) gson.fromJson(jsonResponse, OpMove.class);
                OpMove opMove1 = opMove;
                socket.close();
                return opMove1;
            } catch (Throwable throwable) {
                try {
                    socket.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
                throw throwable;
            }
        } catch (Exception e) {
            System.out.println("Error: You need to start TetrisServer to use external player mode. " + e.getMessage());
            return null;
        }
    }
}

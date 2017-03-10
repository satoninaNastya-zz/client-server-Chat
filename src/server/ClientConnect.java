package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;


public class ClientConnect extends Thread {
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    public ClientConnect(Socket s) throws IOException {
        this.socket = s;
        System.out.println("New user connected");

        output = new PrintWriter(socket.getOutputStream(), true);
        input = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                String msg = input.readLine();

                if (msg != null && !"".equals(msg)) {
                    for (ClientConnect connect : Server.getConnects()) {
                        if (connect != this) {
                            connect.send(msg);
                        }
                    }
                }
            }
        } catch (SocketException se) {
            System.out.println("User disconnected");
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void close() {
        try {
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String msg) throws IOException {
        output.println(msg);
    }
}

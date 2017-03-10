package client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;


public class MessageListener extends Thread {
    private Socket socket;
    private BufferedReader input;
    private boolean run = true;

    public MessageListener(Socket s) {
        this.socket = s;
        try {
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        run = false;
        try {
            input.close();
            socket.close();
            System.out.println("Disconnect");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (run) {
                System.out.println(input.readLine());
            }
        } catch (SocketException se) {
            System.out.println("Server disconnect");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

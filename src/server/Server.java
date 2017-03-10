package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static List<ClientConnect> connects = new ArrayList<>();
    private static ServerSocket socket;

    public static void main(String[] args) {

        try {
            socket = new ServerSocket(4444);
            while (true) {
                ClientConnect connect = new ClientConnect(socket.accept());
                connects.add(connect);
                connect.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            getConnects().forEach(ClientConnect::close);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        }

    protected static List<ClientConnect> getConnects() {
        return connects;
    }

}

package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static PrintWriter out;
    private static Scanner scan;
    private static MessageListener messageListener;

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 4444);
            messageListener = new MessageListener(socket);
            messageListener.start();

            out = new PrintWriter(socket.getOutputStream(), true);
            scan = new Scanner(System.in);

            System.out.println("Connected to chat");

            while (true) {
                String msg = scan.nextLine();
                out.println(msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
            scan.close();
            messageListener.close();
        }
    }


}

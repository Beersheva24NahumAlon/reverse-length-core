package telran.net;

import java.net.*;
import java.io.*;

public class Client {
    private Socket socket;
    private PrintStream writer;
    private BufferedReader reader;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            writer = new PrintStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String sendAndReceive(String string) {
        try {
            writer.println(string);
            return reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void close() {
        try {
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

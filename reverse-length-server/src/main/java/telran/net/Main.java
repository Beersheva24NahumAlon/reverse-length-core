package telran.net;

import java.net.*;
import java.io.*;
import org.json.JSONObject;

public class Main {
    private static final int PORT = 4000;

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            runSession(socket);
        }
    }

    private static void runSession(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintStream writer = new PrintStream(socket.getOutputStream())) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                writer.println(stringProcessing(line));
            }
        } catch (Exception e) {
            System.out.println("client closed connection abnormally");
        }
    }

    private static String stringProcessing(String line) {
        JSONObject jsonObject = new JSONObject(line);
        String type = jsonObject.getString("type");
        String data = jsonObject.getString("data");
        return switch (type) {
            case "reverse" -> new StringBuilder(data).reverse().toString();  
            case "length" -> String.valueOf(data.length());
            default -> "server doesn't understand your command";
        };
    }
}
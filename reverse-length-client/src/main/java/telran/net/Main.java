package telran.net;

import telran.view.*;
import org.json.JSONObject;

public class Main {
    static Client client;

    public static void main(String[] args) {
        Item[] items = {
                Item.of("Start session", Main::startSession),
                Item.of("Exit", Main::exit, true)
        };
        Menu menu = new Menu("Network application", items);
        menu.perform(new StandardInputOutput());
    }

    static void startSession(InputOutput io) {
        String host = io.readString("Enter hostname");
        int port = io.readNumberRange("Enter port", "Wrong port", 3000, 50000).intValue();
        if (client != null) {
            client.close();
        }
        client = new Client(host, port);
        Menu menu = new Menu("Run Session",
                Item.of("Enter command and data", Main::stringProcessing), 
                Item.ofExit());
        menu.perform(io);

    }

    static void stringProcessing(InputOutput io) {
        String type = io.readString("Enter your command");
        String data = io.readString("Enter your data");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("data", data);
        String response = client.sendAndReceive(jsonObject.toString());
        io.writeLine(response);
    }

    static void exit(InputOutput io) {
        if (client != null) {
            client.close();
        }
    }
}
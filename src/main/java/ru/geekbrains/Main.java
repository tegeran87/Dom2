package ru.geekbrains;


import ru.geekbrains.client.ClientGUI;
import ru.geekbrains.server.Server;
import ru.geekbrains.server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow();
        Server server = serverWindow.returnServer();

        new ClientGUI(server);
        new ClientGUI(server);
        new ClientGUI(server);

    }
}

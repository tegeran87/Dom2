package ru.geekbrains.client;

import ru.geekbrains.server.Server;
import ru.geekbrains.server.ServerWindow;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    private String name;
    private ClientView clientView;
    private Server server;
    private boolean connected;

    public Client(ClientView clientView, Server server) {
        this.clientView = clientView;
        this.server = server;
    }

    public boolean connectToServer(String name) {
        this.name = name;
        if (server.connectUser(this)) {
            printText("Вы успешно подключились!\n");
            connected = true;
            String log = server.getHistory();
            if (log != null) {
                printText("История чата:");
                printText(log);
            }
            return true;
        } else {
            printText("Подключение не удалось");
            return false;
        }
    }

    //мы посылаем
    public void sendMessage(String message) {
        if (connected) {
            if (!message.isEmpty()) {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String formattedMessage = name + " [" + timeFormat.format(new Date()) + "] " + ": " + message;
                server.sendMessage(formattedMessage);
            }
        } else {
            printText("Нет подключения к серверу");
        }
    }

    //нам посылают
    public void serverAnswer(String answer) {
        printText(answer);
    }

    public void disconnect() {
        if (connected) {
            connected = false;
            server.disconnectUser(this);
            printText("Вы были отключены от сервера!");
        }
    }

    public void disconnectServerDown() {
        if (connected) {
            connected = false;
            printText("Вы были отключены от сервера!");
        }
    }
    public void disconnectFromServer(){
        clientView.disconnectFromServer();
    }



    public String getName() {
        return name;
    }

    public void printText(String text) {
        clientView.showMessage(text);
    }
}

package ru.geekbrains.server;

import ru.geekbrains.client.Client;
import ru.geekbrains.server.repository.Repository;
import ru.geekbrains.server.repository.Storage;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private ServerView serverView;
    private Repository repository;
    public static final String LOG_PATH = "src/main/java/ru/geekbrains/server/repository/log.txt";

    List<Client> clientList;

    boolean work;

    public Server(ServerView serverView) {
        clientList = new ArrayList<>();
        this.serverView = serverView;
        this.repository = new Storage(LOG_PATH);
    }

    public void startServer() {
        work = true;
    }

    public void stopServer() {
        work = false;
    }

    public boolean getStatusServer() {
        return work;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public boolean connectUser(Client client) {
        if (!work) {
            return false;
        }
        clientList.add(client);
        return true;
    }

    // Метод удаления клиента из списка подключенных клиентов при закрытии окна клиента
    public void disconnectUser(Client client) {
        if (client != null) {
            clientList.remove(client);
        }
    }

    // Метод принудительного отключения всех пользователей при остановке сервера.
    public void serverDown(Client client) {
        if (client != null) {
            client.disconnectFromServer();
        }
    }

    public void sendMessage(String text) {
        if (!work) {
            return;
        }
        serverView.showMessage(text);
        answerAll(text);
        saveInLog(text);
    }


    private void answerAll(String text) {
        for (Client client : clientList) {
            client.serverAnswer(text);
        }
    }

    private void saveInLog(String text) {
        repository.saveInLog(text);
    }

    private String readLog() {
        return repository.readLog();
    }

    public String getHistory() {
        return repository.getHistory();
    }


}

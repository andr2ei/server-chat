package ru.andronov.services;

import lombok.SneakyThrows;
import ru.andronov.Constants;
import ru.andronov.model.Observer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerServiceImpl implements ServerService {

    private final List<Observer> observers = new ArrayList<>();
    @SneakyThrows
    @Override
    public void start() {
        try(ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("LOG: socket accepted");
                new Thread(new ClientServiceImpl(socket, observers)).start();
            }
        }

    }
}

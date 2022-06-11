package ru.andronov.services;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.andronov.model.Observer;
import ru.andronov.model.UserObserver;
import ru.andronov.model.User;
import ru.andronov.services.auth.AuthenticationService;
import ru.andronov.services.auth.AuthenticationServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final Socket socket;
    private final List<Observer> observers;

    @SneakyThrows
    @Override
    public void run() {
        AuthenticationService auth = new AuthenticationServiceImpl(socket);
        User user = auth.authenticate();
        addObserver(new UserObserver(user, socket));

        BufferedReader clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while (true) {
            String msg = clientReader.readLine();
            notifyAll(user.getUsername() + " " + msg);
        }
    }

    @SneakyThrows
    @Override
    public void notifyAll(String msg) {
        synchronized (observers) {
            for (Observer obs : observers) {
                obs.notifyMe(msg);
            }
        }
    }

    @Override
    public void addObserver(Observer observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

}

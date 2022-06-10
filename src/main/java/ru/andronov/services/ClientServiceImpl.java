package ru.andronov.services;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.andronov.services.auth.AuthenticationService;
import ru.andronov.services.auth.AuthenticationServiceImpl;
import ru.andronov.services.utils.ClientMsgSenderService;
import ru.andronov.services.utils.ClientMsgSenderServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final Socket socket;
    private final List<Socket> observers;

    @SneakyThrows
    @Override
    public void run() {
        AuthenticationService auth = new AuthenticationServiceImpl(socket);
        auth.authenticate();

        BufferedReader clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while (true) {
            String msg = clientReader.readLine();
            notifyAll(msg);
        }
    }

    @SneakyThrows
    public void notifyAll(String msg) {
        for (Socket s : observers) {
            ClientMsgSenderService senderService = new ClientMsgSenderServiceImpl(s.getOutputStream());
            senderService.sendMsg(msg);
        }
    }

}

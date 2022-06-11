package ru.andronov.model;

import lombok.Data;
import lombok.SneakyThrows;
import ru.andronov.services.utils.ClientMsgSenderService;
import ru.andronov.services.utils.ClientMsgSenderServiceImpl;

import java.net.Socket;

@Data
public class UserObserver implements Observer {
    private final User user;
    private final ClientMsgSenderService senderService;


    @SneakyThrows
    public UserObserver(User user, Socket socket) {
        this.user = user;
        this.senderService = new ClientMsgSenderServiceImpl(socket.getOutputStream());
    }

    @Override
    public void notifyMe(String msg) {
        senderService.sendMsg(msg);
    }
}

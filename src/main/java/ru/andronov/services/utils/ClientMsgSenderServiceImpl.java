package ru.andronov.services.utils;

import java.io.OutputStream;
import java.io.PrintWriter;

public class ClientMsgSenderServiceImpl implements ClientMsgSenderService {

    private final PrintWriter clientWriter;

    public ClientMsgSenderServiceImpl(OutputStream os) {
        this.clientWriter = new PrintWriter(os);
    }

    @Override
    public void sendMsg(String msg) {
        clientWriter.println(msg);
        clientWriter.flush();
    }
}

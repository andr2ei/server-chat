package ru.andronov.model;

import ru.andronov.services.utils.ClientMsgSenderService;
import ru.andronov.services.utils.ClientMsgSenderServiceImpl;

public interface Observable {
    public void notifyAll(String msg);
    public void addObserver(Observer observer);
}

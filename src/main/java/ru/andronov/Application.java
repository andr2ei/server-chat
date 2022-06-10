package ru.andronov;

import lombok.SneakyThrows;
import ru.andronov.services.ServerService;
import ru.andronov.services.ServerServiceImpl;

public class Application {

    @SneakyThrows
    public static void main(String[] args) {
        ServerService serverService = new ServerServiceImpl();
        serverService.start();
    }
}

package ru.andronov.services.auth;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.andronov.dao.UserDao;
import ru.andronov.dao.UserDaoImpl;
import ru.andronov.model.User;
import ru.andronov.services.utils.ClientMsgSenderService;

import java.io.BufferedReader;

@AllArgsConstructor
public class SignInServiceImpl implements SignInService {
    private final ClientMsgSenderService senderService;
    private final BufferedReader clientReader;

    @SneakyThrows
    @Override
    public User login() {
        while (true) {
            senderService.sendMsg("Enter your username");
            String login = clientReader.readLine();

            senderService.sendMsg("Enter your password");
            String password = clientReader.readLine();

            UserDao userDao = new UserDaoImpl();
            User user = userDao.getUserByLogin(login);
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                senderService.sendMsg("Wrong login or password");
            }
        }
    }
}

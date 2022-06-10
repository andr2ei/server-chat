package ru.andronov.dao;

import ru.andronov.model.User;

public interface UserDao {
    User createUser(String login, String password);
    User getUserByLogin(String login);
}

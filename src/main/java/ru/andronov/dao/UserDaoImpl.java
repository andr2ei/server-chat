package ru.andronov.dao;

import lombok.SneakyThrows;
import ru.andronov.Constants;
import ru.andronov.model.User;

import java.sql.*;

import java.sql.DriverManager;

public class UserDaoImpl implements UserDao {

    @SneakyThrows
    @Override
    public User createUser(String login, String password) {
        String sql = "INSERT INTO chat.user (login, password) VALUES" +
                "(?, ?);";
        try (Connection con = DriverManager
                .getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, login);
            statement.setString(2, password);
            statement.execute();
            return new User(login, password);
        }
    }

    @SneakyThrows
    @Override
    public User getUserByLogin(String login) {
        String sql = "SELECT * FROM chat.user WHERE login = ?";
        try (Connection con = DriverManager
                .getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return new User(
                    login,
                    resultSet.getString("password"));
        }
    }
}

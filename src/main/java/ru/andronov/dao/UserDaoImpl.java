package ru.andronov.dao;

import lombok.SneakyThrows;
import ru.andronov.Constants;
import ru.andronov.model.User;

import java.sql.*;

import java.sql.DriverManager;

public class UserDaoImpl implements UserDao {

    @SneakyThrows
    @Override
    public User createUser(String userName, String password) {
        String sql = "INSERT INTO chat.user (username, password) VALUES" +
                "(?, ?);";
        try (Connection con = DriverManager
                .getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, userName);
            statement.setString(2, password);
            statement.execute();
            return new User(userName, password);
        }
    }

    @SneakyThrows
    @Override
    public User getUserByLogin(String userName) {
        String sql = "SELECT * FROM chat.user WHERE username = ?";
        try (Connection con = DriverManager
                .getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return new User(
                    userName,
                    resultSet.getString("password"));
        }
    }
}

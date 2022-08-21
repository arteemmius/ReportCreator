package ru.vk.competition.minbenchmark.db_listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class DbConfig {
    public static String jdbcURL;

    @Value("${spring.datasource.url}")
    public void setJdbcURL(String jdbcURL) {
        DbConfig.jdbcURL = jdbcURL;
    }

    public static String username;

    @Value("${spring.datasource.username}")
    public void setUsername(String username) {
        DbConfig.username = username;
    }

    public static String password;

    @Value("${spring.datasource.password}")
    public void setPassword(String password) {
        DbConfig.password = password;
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        return connection;
    }
}

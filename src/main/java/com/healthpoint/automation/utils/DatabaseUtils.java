package com.healthpoint.automation.utils;

import com.healthpoint.automation.config.ConfigReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {

    private DatabaseUtils() {
    }

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                ConfigReader.get("db.url"),
                ConfigReader.get("db.user"),
                ConfigReader.get("db.password")
        );
    }
}

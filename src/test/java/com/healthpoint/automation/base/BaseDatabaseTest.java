package com.healthpoint.automation.base;

import com.healthpoint.automation.utils.DatabaseUtils;
import org.testng.annotations.BeforeClass;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatabaseTest {

    @BeforeClass
    public void setUpDatabase() throws SQLException {

        try (
                Connection connection = DatabaseUtils.getConnection();
                Statement statement = connection.createStatement()
        ) {

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS patients (
                        id INT PRIMARY KEY,
                        first_name VARCHAR(100),
                        last_name VARCHAR(100),
                        email VARCHAR(150),
                        active BOOLEAN
                    )
                    """);

            statement.execute("DELETE FROM patients");

            statement.execute("""
                    INSERT INTO patients
                    (id, first_name, last_name, email, active)
                    VALUES
                    (1, 'John', 'Smith', 'john.smith@test.com', TRUE)
                    """);
        }
    }
}

package com.healthpoint.automation.db;

import com.healthpoint.automation.base.BaseDatabaseTest;
import com.healthpoint.automation.utils.DatabaseUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PatientDatabaseTest extends BaseDatabaseTest {

    @Test
    public void shouldReturnPatientFromDatabase() throws SQLException {

        try (
                Connection connection = DatabaseUtils.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT * FROM patients WHERE id = 1"
                )
        ) {

            Assert.assertTrue(resultSet.next());

            Assert.assertEquals(
                    resultSet.getInt("id"),
                    1
            );

            Assert.assertEquals(
                    resultSet.getString("first_name"),
                    "John"
            );

            Assert.assertEquals(
                    resultSet.getString("last_name"),
                    "Smith"
            );

            Assert.assertEquals(
                    resultSet.getString("email"),
                    "john.smith@test.com"
            );

            Assert.assertTrue(
                    resultSet.getBoolean("active")
            );
        }
    }
}


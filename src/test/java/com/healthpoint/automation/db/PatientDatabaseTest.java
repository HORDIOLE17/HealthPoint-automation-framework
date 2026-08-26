package com.healthpoint.automation.db;

import com.healthpoint.automation.base.BaseDatabaseTest;
import com.healthpoint.automation.utils.DatabaseUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Feature("Database Validation")
public class PatientDatabaseTest extends BaseDatabaseTest {

    @Test
    @Story("Patient data validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description(
            "Validates that the patient record stored in the database " +
            "matches the expected seeded test data."
    )
    public void shouldReturnPatientFromDatabase() throws SQLException {

        try (
                Connection connection = DatabaseUtils.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT * FROM patients WHERE id = 1"
                )
        ) {

            Assert.assertTrue(
                    resultSet.next(),
                    "Patient with id=1 should exist in the database"
            );

            Assert.assertEquals(
                    resultSet.getInt("id"),
                    1,
                    "Patient ID should match"
            );

            Assert.assertEquals(
                    resultSet.getString("first_name"),
                    "John",
                    "First name should match"
            );

            Assert.assertEquals(
                    resultSet.getString("last_name"),
                    "Smith",
                    "Last name should match"
            );

            Assert.assertEquals(
                    resultSet.getString("email"),
                    "john.smith@test.com",
                    "Email should match"
            );

            Assert.assertTrue(
                    resultSet.getBoolean("active"),
                    "Patient should be active"
            );
        }
    }
}

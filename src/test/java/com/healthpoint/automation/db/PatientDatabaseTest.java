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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Feature("Database Validation")
public class PatientDatabaseTest extends BaseDatabaseTest {

    @Test(groups = {"regression"})
    @Story("Patient data validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validates that the stored patient matches the expected seeded test data")
    public void shouldReturnPatientFromDatabase() throws SQLException {

        try (
                Connection connection = DatabaseUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM patients WHERE id = ?"
                )
        ) {
            statement.setInt(1, 1);

            try (ResultSet resultSet = statement.executeQuery()) {
                Assert.assertTrue(resultSet.next(), "Patient with id=1 should exist");
                Assert.assertEquals(resultSet.getInt("id"), 1);
                Assert.assertEquals(resultSet.getString("first_name"), "John");
                Assert.assertEquals(resultSet.getString("last_name"), "Smith");
                Assert.assertEquals(resultSet.getString("email"), "john.smith@test.com");
                Assert.assertTrue(resultSet.getBoolean("active"));
            }
        }
    }

    @Test(groups = {"regression"})
    @Story("Patient data validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validates that a nonexistent patient is not returned by the database")
    public void shouldNotReturnUnknownPatient() throws SQLException {

        try (
                Connection connection = DatabaseUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM patients WHERE id = ?"
                )
        ) {
            statement.setInt(1, 999999);

            try (ResultSet resultSet = statement.executeQuery()) {
                Assert.assertFalse(
                        resultSet.next(),
                        "Unknown patient should not exist in the database"
                );
            }
        }
    }
}

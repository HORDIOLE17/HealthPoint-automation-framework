package com.healthpoint.automation.api;

import com.healthpoint.automation.base.BaseApiTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Feature("API Health")
public class HealthCheckApiTest extends BaseApiTest {

    @Test(groups = {"smoke", "regression"})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies that the API is available and responds successfully")
    public void apiShouldBeAvailable() {

        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200);
    }
}
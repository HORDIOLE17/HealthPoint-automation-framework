package com.healthpoint.automation.api;

import com.healthpoint.automation.base.BaseApiTest;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class HealthCheckApiTest extends BaseApiTest {

    @Test(groups = {"smoke", "regression"})
    public void healthCheckTest() {

        RestAssured
                .given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200);
    }

}
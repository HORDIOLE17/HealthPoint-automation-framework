package com.healthpoint.automation.base;

import com.healthpoint.automation.config.ConfigReader;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {

    @BeforeClass
    public void setUpApi() {
        RestAssured.baseURI = ConfigReader.get("base.url");
    }
}
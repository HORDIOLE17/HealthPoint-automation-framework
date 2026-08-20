package com.healthpoint.automation.api;

import com.healthpoint.automation.base.BaseApiTest;
import com.healthpoint.automation.clients.PostClient;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Posts API")
public class NegativeGetPostAPITest extends BaseApiTest {

    private final PostClient postClient = new PostClient();

    @Test(groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that requesting a non-existing post returns HTTP 404")
    public void shouldReturn404WhenPostDoesNotExist() {

        Response response = postClient.getPostResponse(999999);

        Assert.assertEquals(response.statusCode(), 404);
    }
}
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

import java.util.Map;

@Feature("Posts API Contract")
public class PostContractAPITest extends BaseApiTest {

    private final PostClient postClient = new PostClient();

    @Test(groups = {"regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validates the response contract for an existing post")
    public void shouldReturnExpectedPostContract() {

        Response response = postClient.getPostResponse(1);
        Map<String, Object> body = response.jsonPath().getMap("");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(body.containsKey("userId"));
        Assert.assertTrue(body.containsKey("id"));
        Assert.assertTrue(body.containsKey("title"));
        Assert.assertTrue(body.containsKey("body"));
        Assert.assertTrue(body.get("userId") instanceof Number);
        Assert.assertTrue(body.get("id") instanceof Number);
        Assert.assertTrue(body.get("title") instanceof String);
        Assert.assertTrue(body.get("body") instanceof String);
    }

    @Test(groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Description("Validates that an unknown post returns an empty response body")
    public void shouldReturnEmptyBodyForUnknownPost() {

        Response response = postClient.getPostResponse(999999);

        Assert.assertEquals(response.statusCode(), 404);
        Assert.assertEquals(response.jsonPath().getMap("").size(), 0);
    }
}

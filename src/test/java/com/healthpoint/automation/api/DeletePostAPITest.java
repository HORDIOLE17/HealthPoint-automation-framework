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
public class DeletePostAPITest extends BaseApiTest {

    private final PostClient postClient = new PostClient();

    @Test(groups = {"regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that an existing post can be deleted")
    public void shouldDeletePost() {

        Response response = postClient.deletePost(1);

        Assert.assertEquals(response.statusCode(), 200);
    }
}
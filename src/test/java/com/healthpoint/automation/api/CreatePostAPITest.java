package com.healthpoint.automation.api;

import com.healthpoint.automation.base.BaseApiTest;
import com.healthpoint.automation.clients.PostClient;
import com.healthpoint.automation.models.Post;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Posts API")
public class CreatePostAPITest extends BaseApiTest {

    private final PostClient postClient = new PostClient();

    @Test(groups = {"smoke", "regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that a new post can be created with the expected response data")
    public void shouldCreatePost() {

        Post post = new Post(
                1,
                "HealthPoint API Test",
                "Creating a post through REST Assured"
        );

        Response response = postClient.createPost(post);

        Assert.assertEquals(response.statusCode(), 201);

        Assert.assertEquals(
                response.jsonPath().getString("title"),
                post.getTitle()
        );

        Assert.assertEquals(
                response.jsonPath().getString("body"),
                post.getBody()
        );

        Assert.assertEquals(
                response.jsonPath().getInt("userId"),
                post.getUserId()
        );

        Assert.assertTrue(
                response.jsonPath().getInt("id") > 0
        );
    }
}
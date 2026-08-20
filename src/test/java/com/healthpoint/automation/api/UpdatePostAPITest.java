package com.healthpoint.automation.api;

import com.healthpoint.automation.base.BaseApiTest;
import com.healthpoint.automation.clients.PostClient;
import com.healthpoint.automation.models.Post;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UpdatePostAPITest extends BaseApiTest {

    private final PostClient postClient = new PostClient();

    @Test(groups = {"regression"})
    public void shouldUpdatePost() {

        Post post = new Post(
                1,
                "Updated HealthPoint Post",
                "Updated through REST Assured"
        );

        Response response = postClient.updatePost(1, post);

        Assert.assertEquals(response.statusCode(), 200);

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

        Assert.assertEquals(
                response.jsonPath().getInt("id"),
                1
        );
    }
}
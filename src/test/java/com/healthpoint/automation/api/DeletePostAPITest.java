package com.healthpoint.automation.api;

import com.healthpoint.automation.base.BaseApiTest;
import com.healthpoint.automation.clients.PostClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeletePostAPITest extends BaseApiTest {

    private final PostClient postClient = new PostClient();

    @Test(groups = {"regression"})
    public void shouldDeletePost() {

        Response response = postClient.deletePost(1);

        Assert.assertEquals(response.statusCode(), 200);
    }
}
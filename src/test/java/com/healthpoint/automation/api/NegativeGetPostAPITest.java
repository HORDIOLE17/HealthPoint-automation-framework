package com.healthpoint.automation.api;
import  com.healthpoint.automation.base.BaseApiTest;
import com.healthpoint.automation.clients.PostClient;
import io.restassured.response.Response;
import  org.testng.Assert;
import  org.testng.annotations.Test;


public class NegativeGetPostAPITest extends  BaseApiTest{
    private final PostClient postClient = new PostClient();

    @Test(groups = {"regression"})
    public void  shouldReturn404WhenPostDoesNotExist() {
        Response response = postClient.getPostResponse(99999999);
        Assert.assertEquals(response.statusCode(),404);
    }
}

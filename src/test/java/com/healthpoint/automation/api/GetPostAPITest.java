package com.healthpoint.automation.api;

import com.healthpoint.automation.base.BaseApiTest;
import com.healthpoint.automation.clients.PostClient;
import com.healthpoint.automation.models.Post;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetPostAPITest  extends BaseApiTest{
    private final PostClient postClient = new PostClient();

    @Test(groups = {"smoke", "regression"})
    public  void  shouldReturnPostByid() {
        Post post = postClient.getPost(1);

        Assert.assertEquals(post.getId(), 1);
        Assert.assertNotNull(post.getTitle());
        Assert.assertNotNull(post.getBody());
    }

    @Test
    public void shouldReturn404ForUnknowPost() {
        int statusCode = postClient
                .getPostResponse(999999)
                .statusCode();

        Assert.assertEquals(statusCode, 404);
    }
}

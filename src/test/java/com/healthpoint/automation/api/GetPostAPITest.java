package com.healthpoint.automation.api;

import com.healthpoint.automation.base.BaseApiTest;
import com.healthpoint.automation.clients.PostClient;
import com.healthpoint.automation.models.Post;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Posts API")
public class GetPostAPITest extends BaseApiTest {

    private final PostClient postClient = new PostClient();

    @Test(groups = {"smoke", "regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that an existing post can be retrieved by id")
    public void shouldReturnPostById() {

        Post post = postClient.getPost(1);

        Assert.assertEquals(post.getId(), 1);
        Assert.assertEquals(post.getUserId(), 1);
        Assert.assertNotNull(post.getTitle());
        Assert.assertFalse(post.getTitle().isBlank());
        Assert.assertNotNull(post.getBody());
    }
}
package com.healthpoint.automation.clients;
import com.healthpoint.automation.models.Post;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;


public class PostClient {
    public Post getPost(int postId) {
        return given()
                .when()
                .get("/posts/" + postId)
                .then()
                .statusCode(200)
                .extract()
                .as(Post.class);
    }

    public Response getPostResponse ( int postId) {
        return  given()
                .when()
                .get("/posts/" + postId);
    }
}

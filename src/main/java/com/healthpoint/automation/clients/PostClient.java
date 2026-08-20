package com.healthpoint.automation.clients;

import com.healthpoint.automation.models.Post;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

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

    public Response getPostResponse(int postId) {
        return given()
                .when()
                .get("/posts/" + postId);
    }

    public Response createPost(Post post) {
        return given()
                .contentType(ContentType.JSON)
                .body(post)
                .when()
                .post("/posts");
    }

    public Response updatePost(int postId, Post post) {
        return given()
                .contentType(ContentType.JSON)
                .body(post)
                .when()
                .put("/posts/" + postId);
    }

    public Response deletePost(int postId) {
        return given()
                .when()
                .delete("/posts/" + postId);
    }
}
package com.juaracoding.themoviedb;

import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestApiMovieCRUD {
    JSONObject requestBody;
    String authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5YmUxYjViNGVjYThhNTRhMjA4YzkzNTQxMTBjNjlkNSIsInN1YiI6IjY0MDVmNTgwMTM2NTQ1MDA4Y2VhNmYxMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.0urorYmFjebKG2rw_TFNXie1i5ibqDhAZpG_mqprl84";
    String keyApi = "9be1b5b4eca8a54a208c9354110c69d5";

    @BeforeClass
    public void setUp() {
        requestBody = new JSONObject();
    }

    @Test
    public void testPostMovieRating(){

        requestBody.put("value", 8.6);
        System.out.println(requestBody.toJSONString());
        given()
                .headers("A","application/json")
                .contentType(ContentType.JSON)
                .body(requestBody.toJSONString())
                .when()
                .put("https://api.themoviedb.org/3/movie/505642/rating?api_key="+keyApi)
                .then()
                .statusCode(201)
                .body("value",equalTo(8.6))
                .log().all();
    }
}

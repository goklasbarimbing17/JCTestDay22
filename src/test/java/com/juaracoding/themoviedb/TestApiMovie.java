package com.juaracoding.themoviedb;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestApiMovie {
    String keyApi = "9be1b5b4eca8a54a208c9354110c69d5";
    String authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5YmUxYjViNGVjYThhNTRhMjA4YzkzNTQxMTBjNjlkNSIsInN1YiI6IjY0MDVmNTgwMTM2NTQ1MDA4Y2VhNmYxMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.0urorYmFjebKG2rw_TFNXie1i5ibqDhAZpG_mqprl84";
    JSONObject requestBody;

    public String endpoint(String url, String endTheUrl){
        return "https://api.themoviedb.org/3/movie"+url+"api_key="+keyApi+endTheUrl;
    }

    @BeforeClass
    public void setUp() {
        requestBody = new JSONObject();
    }

    @Test
    public void testGetMovieNowPlaying() {
        given()
                .when()
                    .get(endpoint("/now_playing?","&language=en-US&page=1"))
                .then()
                    .statusCode(200)
                    .body("page", equalTo(1))
                    .body("results.id[0]",equalTo(631842));
    }
    @Test
    public void testGetMoviePopular() {
        given()
                .when()
                    .get(endpoint("/popular?","&language=en-US&page=1"))
                .then()
                    .statusCode(200)
                    .body("results.id[1]",equalTo(505642))
                    .body("results.title[1]",equalTo("Black Panther: Wakanda Forever"));
    }

    @Test
    public void testPostMovieRating(){
        postRating(8.8);
        given()
                .header("Authorization",authToken)
                .header("Content-Type","application/json")
                .body(requestBody.toJSONString())
                .when()
                    .post("https://api.themoviedb.org/3/movie/505642/rating?api_key="+keyApi)
                .then()
                    .statusCode(201)
                    .body("success",equalTo(true))
                    .log().all();
    }
    public void postRating(double rating){
        requestBody.put("value", rating);
        System.out.println(requestBody.toJSONString());
    }
}

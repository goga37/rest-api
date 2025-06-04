package lesson15;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class SingleUserTests {
    @Test
    void successfulSingleUserTest() {

        given()
                .header("x-api-key", "reqres-free-v1")
                .contentType(JSON)
                .log().uri()
        .when()
                .get("https://reqres.in/api/users/2")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"))
                .body("data.avatar", is("https://reqres.in/img/faces/2-image.jpg"))
                .body("support.url", is("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral"))
                .body("support.text", is("Tired of writing endless social media content? Let Content Caddy generate it for you."));
    }

    @Test
    void unsuccessfulSingleUserTest() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .contentType(JSON)
                .log().uri()
        .when()
                .get("https://reqres.in/api/users/23")
        .then()
                .log().status()
                .log().body()
                .statusCode(404);

    }
}
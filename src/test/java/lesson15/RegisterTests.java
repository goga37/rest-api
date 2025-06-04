package lesson15;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RegisterTests {
    @Test
    void successfulRegisterTest () {
        String registerData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .header("x-api-key", "reqres-free-v1")
                .body(registerData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unsuccessfulRegistrationTestWithoutLogin () {
        String registerData = "{\"password\": \"pistol\"}";

        given()
                .header("x-api-key", "reqres-free-v1")
                .body(registerData)
                .contentType(JSON)
                .log().uri()
        .when()
                .post("https://reqres.in/api/register")
        .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void unsuccessfulRegistrationTestWithoutHeader () {
        String registerData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .body(registerData)
                .contentType(JSON)
                .log().uri()
        .when()
                .post("https://reqres.in/api/register")
        .then()
                .log().status()
                .log().body()
                .statusCode(401)
                .body("error", is("Missing API key."))
                .body("how_to_get_one", is("https://reqres.in/signup"));
    }
}

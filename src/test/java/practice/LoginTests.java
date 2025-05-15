package practice;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class LoginTests {
//        1. Make request (POST) to https://reqres.in/api/login
//    with body { "email": "eve.holt@reqres.in", "password": "cityslicka" }
//    2. Get response { "token": "QpwL5tke4Pnpja7X4" }
//    3. Check "token" is "QpwL5tke4Pnpja7X4" and status code 200

    @Test
        void successfulLoginTest () {
            String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";

            given()
                    .header("x-api-key", "reqres-free-v1")
                    .body(authData)
                    .contentType(JSON)
                    .log().uri()

                    .when()
                    .post("https://reqres.in/api/login")

                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .body("token", is("QpwL5tke4Pnpja7X4"));
        }
    }


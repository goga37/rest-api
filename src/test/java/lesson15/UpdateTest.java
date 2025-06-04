package lesson15;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UpdateTest {
    @Test
    void unsuccessfulRegistrationTestWithoutLogin () {
        String registerData = "{\"name\": \"morpheus\", \"job\": \"zion resident\"}";

        given()
                .header("x-api-key", "reqres-free-v1")
                .body(registerData)
                .contentType(JSON)
                .log().uri()
        .when()
                .put("https://reqres.in/api/users/301")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"))
                .body("updatedAt", notNullValue());
    }
}

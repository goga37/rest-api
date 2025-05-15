package lesson15;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreateTests {
    @Test
    void successfulCreateTest () {
        String createData = "{\"name\": \"igor\", \"job\": \"qa\"}";

        given()
                .header("x-api-key", "reqres-free-v1")
                .body(createData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("igor"))
                .body("job", is("qa"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());

    }

}

package lesson15;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class DeleteTests {
    @Test
    void unsuccessfulDeleteTest() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .contentType(JSON)
                .log().uri()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);

    }
}

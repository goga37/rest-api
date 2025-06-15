package lesson16;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static lesson16.specs.Specs.requestWithApiKey;
import static io.qameta.allure.Allure.step;

@Tag("demoApi")
public class DeleteTests extends TestBase {

    @Test
    void unsuccessfulDeleteTest() {
        step("Отправка DELETE-запроса на удаление пользователя с id = 2", () ->
                given()
                        .spec(requestWithApiKey)
                        .when()
                        .delete("/users/2")
                        .then()
                        .statusCode(204)
        );
    }
}

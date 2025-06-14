package lesson16;

import lesson16.models.lombok.RessponseSingleUser.SingleUserResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static lesson16.specs.Specs.requestWithApiKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.qameta.allure.Allure.step;

@Tag("demoApi")
public class SingleUserTests {

    @Test
    void successfulSingleUserTest() {
        SingleUserResponse response = step("Отправка GET-запроса на получение пользователя с id = 2", () ->
                given()
                        .spec(requestWithApiKey)
                        .when()
                        .get("/users/2")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .extract().as(SingleUserResponse.class)
        );

        step("Проверка данных пользователя", () -> {
            assertEquals(2, response.getData().getId());
            assertEquals("janet.weaver@reqres.in", response.getData().getEmail());
            assertEquals("Janet", response.getData().getFirst_name());
            assertEquals("Weaver", response.getData().getLast_name());
            assertEquals("https://reqres.in/img/faces/2-image.jpg", response.getData().getAvatar());
        });

        step("Проверка информации из блока support", () -> {
            assertEquals("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral", response.getSupport().getUrl());
            assertEquals("Tired of writing endless social media content? Let Content Caddy generate it for you.", response.getSupport().getText());
        });
    }

    @Test
    void unsuccessfulSingleUserTest() {
        step("Отправка GET-запроса на несуществующего пользователя с id = 23", () ->
                given()
                        .spec(requestWithApiKey)
                        .when()
                        .get("https://reqres.in/api/users/23")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(404)
        );
    }
}

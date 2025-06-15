package lesson16;

import lesson16.models.lombok.CreateBodyLombokModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.qameta.allure.Allure.step;
import static lesson16.specs.Specs.*;

@Tag("demoApi")
public class CreateTests extends TestBase{

    @Test
    void successfulCreateTest() {
        CreateBodyLombokModel createBody = step("Создание тела запроса с name и job", () -> {
            CreateBodyLombokModel body = new CreateBodyLombokModel();
            body.setName("igor");
            body.setJob("qa");
            return body;
        });

        step("Отправка POST-запроса на создание пользователя и проверка ответа", () ->
                given()
                        .spec(requestWithApiKey)
                        .body(createBody)
                        .when()
                        .post("/users")
                        .then()
                        .spec(responseCreateTests(createBody))
        );
    }
}

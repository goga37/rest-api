package lesson16;

import lesson16.models.lombok.CreateBodyLombokModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static lesson16.specs.Specs.requestWithApiKey;
import static lesson16.specs.Specs.responseUpdateTest;
import static io.qameta.allure.Allure.step;

@Tag("demoApi")
public class UpdateTest extends TestBase {

    @Test
    void successfulUpdateUserTest() {
        CreateBodyLombokModel updateBody = step("Создание тела запроса с name и job для обновления", () -> {
            CreateBodyLombokModel body = new CreateBodyLombokModel();
            body.setName("igor");
            body.setJob("qa");
            return body;
        });

        step("Отправка PUT-запроса на обновление пользователя и проверка ответа", () ->
                given()
                        .spec(requestWithApiKey)
                        .body(updateBody)
                        .when()
                        .put("/users/301")
                        .then()
                        .spec(responseUpdateTest(updateBody))
        );
    }
}

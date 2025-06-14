package lesson16;

import lesson16.models.lombok.CreateBodyLombokModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static lesson16.specs.Specs.requestWithApiKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static io.qameta.allure.Allure.step;

@Tag("demoApi")
public class UpdateTest {

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
                        .put("https://reqres.in/api/users/301")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .body("name", is(updateBody.getName()))
                        .body("job", is(updateBody.getJob()))
                        .body("updatedAt", notNullValue())
        );
    }
}

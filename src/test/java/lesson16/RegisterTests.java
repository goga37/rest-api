package lesson16;

import lesson16.models.lombok.RegisterBodyLombokModel;
import lesson16.models.lombok.RegisterResponseLombokModel;
import lesson16.models.lombok.RegisterResponseUnsuccessLombokModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static lesson16.specs.Specs.requestWithApiKey;
import static lesson16.specs.Specs.requestWithoutApiKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.qameta.allure.Allure.step;

@Tag("demoApi")
public class RegisterTests {

    @Test
    void successfulRegisterTest() {
        RegisterBodyLombokModel registerBody = step("Create request body with email and password", () -> {
            RegisterBodyLombokModel body = new RegisterBodyLombokModel();
            body.setEmail("eve.holt@reqres.in");
            body.setPassword("pistol");
            return body;
        });

        RegisterResponseLombokModel response = step("Send POST /register request and get success response", () ->
                given()
                        .spec(requestWithApiKey)
                        .body(registerBody)
                        .when()
                        .post("/register")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .extract().as(RegisterResponseLombokModel.class)
        );

        step("Verify id and token in response", () -> {
            assertEquals(4, response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });
    }

    @Test
    void unsuccessfulRegistrationTestWithoutLogin() {
        RegisterBodyLombokModel registerBody = step("Create request body without email", () -> {
            RegisterBodyLombokModel body = new RegisterBodyLombokModel();
            body.setPassword("pistol");
            return body;
        });

        RegisterResponseUnsuccessLombokModel response = step("Send POST /register request and get error response", () ->
                given()
                        .spec(requestWithApiKey)
                        .body(registerBody)
                        .when()
                        .post("https://reqres.in/api/register")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(400)
                        .extract().as(RegisterResponseUnsuccessLombokModel.class)
        );

        step("Verify error message", () ->
                assertEquals("Missing email or username", response.getError()));
    }

    @Test
    void unsuccessfulRegistrationTestWithoutHeader() {
        RegisterBodyLombokModel registerBody = step("Create valid request body with email and password", () -> {
            RegisterBodyLombokModel body = new RegisterBodyLombokModel();
            body.setEmail("eve.holt@reqres.in");
            body.setPassword("pistol");
            return body;
        });

        RegisterResponseUnsuccessLombokModel response = step("Send POST /register without API key and get error", () ->
                given()
                        .spec(requestWithoutApiKey)
                        .body(registerBody)
                        .when()
                        .post("https://reqres.in/api/register")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(401)
                        .extract().as(RegisterResponseUnsuccessLombokModel.class)
        );

        step("Verify missing API key error and help URL", () -> {
            assertEquals("Missing API key.", response.getError());
            assertEquals("https://reqres.in/signup", response.getHow_to_get_one());
        });
    }
}

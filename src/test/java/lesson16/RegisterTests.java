package lesson16;

import lesson16.models.lombok.RegisterBodyLombokModel;
import lesson16.models.lombok.RegisterResponseLombokModel;
import lesson16.models.lombok.RegisterResponseUnsuccessLombokModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static lesson16.specs.Specs.*;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

@Tag("demoApi")
public class RegisterTests extends TestBase {

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
                        .spec(responseSpec)
                        .extract().as(RegisterResponseLombokModel.class)
        );

        step("Verify id and token in response", () -> {
            assertEquals(4, response.getId());
            assertNotNull(response.getToken());
            assertFalse(response.getToken().isEmpty());
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
                        .post("/register")
                        .then()
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
                        .post("/register")
                        .then()
                        .statusCode(401)
                        .extract().as(RegisterResponseUnsuccessLombokModel.class)
        );

        step("Verify missing API key error and help URL", () -> {
            assertEquals("Missing API key.", response.getError());
            assertEquals("https://reqres.in/signup", response.getHow_to_get_one());
        });
    }
}

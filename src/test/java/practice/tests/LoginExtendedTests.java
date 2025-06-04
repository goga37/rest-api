package practice.tests;

import org.junit.jupiter.api.Test;
import practice.models.lombok.LoginBodyLombokModel;
import practice.models.lombok.LoginResponceLombokModel;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpec.loginRequestSpec;
import static specs.LoginSpec.loginResponseSpec;

public class LoginExtendedTests {


    @Test
    void successfulLoginTest() {
//            String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";
        LoginBodyLombokModel loginBodyModel = new LoginBodyLombokModel();
        loginBodyModel.setEmail("eve.holt@reqres.in");
        loginBodyModel.setPassword("cityslicka");

        LoginResponceLombokModel responceModel = step("Make request", ()->
                 given(loginRequestSpec)
                        .header("x-api-key", "reqres-free-v1")
                        .body(loginBodyModel)

                        .when()
                        .post()

                        .then()
                         .spec(loginResponseSpec)
                        .extract().as(LoginResponceLombokModel.class));
        step("Check response", ()->
        assertEquals("QpwL5tke4Pnpja7X4", responceModel.getToken()));
    }
}


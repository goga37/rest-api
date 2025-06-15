package lesson16.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;

import static io.restassured.http.ContentType.JSON;

public class Specs {
    public static RequestSpecification requestWithApiKey = new RequestSpecBuilder()
            .addFilter(withCustomTemplates())
            .addHeader("x-api-key", "reqres-free-v1")
            .setContentType(JSON)
            .build();

    public static RequestSpecification requestWithoutApiKey = new RequestSpecBuilder()
            .addFilter(withCustomTemplates())
            .setBaseUri("https://reqres.in")
            .setBasePath("/api")
            .setContentType(JSON)
            .build();

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build();

}

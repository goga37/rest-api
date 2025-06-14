package lesson16.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class Specs {
    public static RequestSpecification requestWithApiKey = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in")
            .setBasePath("/api")
            .addHeader("x-api-key", "reqres-free-v1")
            .setContentType(JSON)
            .log(io.restassured.filter.log.LogDetail.URI)
            .build();

    public static RequestSpecification requestWithoutApiKey = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in")
            .setBasePath("/api")
            .setContentType(JSON)
            .log(io.restassured.filter.log.LogDetail.URI)
            .build();
}

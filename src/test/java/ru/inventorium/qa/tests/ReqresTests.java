package ru.inventorium.qa.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.inventorium.qa.lombok.LombokUserData;
import ru.inventorium.qa.models.UserData;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.inventorium.qa.Specs.request;
import static ru.inventorium.qa.Specs.responseSpec;


public class ReqresTests {

    @Test
    void singleUserWithModel() {

        UserData data = given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .log().body()
                .extract().as(UserData.class);

        assertEquals(2, data.getData().getId());
        assertEquals(data.getData().getFirstName(), "Janet");
        assertEquals(data.getData().getLastName(), "Weaver");
        assertEquals(data.getData().getEmail(), "janet.weaver@reqres.in");
    }

    @Test
    void singleUserWithLombokModel() {
        LombokUserData data = given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .log().body()
                .extract().as(LombokUserData.class);

        assertEquals(2, data.getUser().getId());
        assertEquals(data.getUser().getFirstName(), "Janet");
        assertEquals(data.getUser().getLastName(), "Weaver");
        assertEquals(data.getUser().getEmail(), "janet.weaver@reqres.in");
    }


    @Test
    public void checkEmailUsingGroovy() {
        given()
                .spec(request)
                .when()
                .get("/users")
                .then()
                .log().body()
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("eve.holt@reqres.in"));
    }

    @Test
    public void checkLastNameUsingGroovy() {
        given()
                .spec(request)
                .when()
                .get("/users")
                .then()
                .log().body()
                .body("data.findAll{it.last_name}.last_name.flatten()",
                        hasItem("Wong"));
    }

}

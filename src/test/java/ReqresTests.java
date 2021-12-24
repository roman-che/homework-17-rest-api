import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;

public class ReqresTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void registerSuccessfulTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/api/register")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void registrationUnsuccsessfullTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\" }";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/api/register")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void singleResourceNameTest() {
        String response =
                get("/api/unknown/2")
                        .then()
                        .statusCode(200)
                        .extract().path("data.name");

        assertThat(response).isEqualTo("fuchsia rose");
    }
    @Test
    void singleUserResponseTest(){
        Response response = get("/api/users/2")
                .then()
                .statusCode(200)
                .extract().response();

        assertThat(response.path("data.id").toString()).isEqualTo("2");
        assertThat(response.path("data.first_name").toString()).isEqualTo("Janet");
        assertThat(response.path("data.last_name").toString()).isEqualTo("Weaver");
        assertThat(response.path("data.avatar").toString()).isEqualTo("https://reqres.in/img/faces/2-image.jpg");

    }

    @Test
    void deleteTest()
    {
        delete("api/users/2")
                .then()
                .statusCode(204);

    }
}

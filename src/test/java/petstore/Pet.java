package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.given;

public class Pet {
    String url = "https://petstore.swagger.io/v2/pet";


    public String readJson(String pathJson) throws IOException {

        return new String(Files.readAllBytes(Paths.get(pathJson)));
    }

    @Test
    public void createPet() throws IOException {
        String jsonBody = readJson("data/pet1.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(url)
        .then()
                .log().all()
                .statusCode(200)
        ;

    }
}

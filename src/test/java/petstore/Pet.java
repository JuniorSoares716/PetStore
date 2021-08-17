package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class Pet {
    String url = "https://petstore.swagger.io/v2/pet";


    public String readJson(String pathJson) throws IOException {

        return new String(Files.readAllBytes(Paths.get(pathJson)));
    }

    @Test(priority = 1)
    public void createPet() throws IOException {
        String jsonBody = readJson("data/pet1.json");

        given() //dado
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when() //quando
                .post(url)
        .then() //então
                .log().all()
                .statusCode(200)
                .body("name", is("Nico"))
                .body("status", is("available"))
                //.body("category.name",is("cat"))
                .body("tags.name", contains("cats for adoption"))
        ;

    }
    @Test(priority = 2)
    public void queryPet(){
        String petId = "1707199330";

        String token =
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(url + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Nico"))
                .body("category.name", is("XXXXAAAEEE"))
                .body("status", is("available"))
                .body("tags.name", contains("cats for adoption"))
        .extract()
                .path("category.name")
        ;
        System.out.println("O Token é "+ token);
    }
    @Test(priority = 3)
    public void updatePet() throws IOException{
        String jsonBody = readJson("data/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(url)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Nico"))
                .body("status", is("sold"))



        ;
    }
    @Test(priority = 4)
    public void deletePet(){
        String petId = "1707199330";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(url + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))

        ;
    }
}

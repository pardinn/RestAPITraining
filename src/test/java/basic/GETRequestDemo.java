package basic;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.Parameters;

import static io.restassured.RestAssured.given;

public class GETRequestDemo {

    /*
     * Replace this with your own Google API key.
     * You can create a Google API key at the following location:
     * https://developers.google.com/maps/documentation/distance-matrix/start
     */
    String apiKey = Parameters.API_KEY;

    /**
     * Given I have this information
     * When I perform this action
     * Then this should be the output
     */
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://maps.googleapis.com";
        RestAssured.basePath = "/maps/api";
    }

    @Test(enabled = false)
    public void statusCodeVerification() {
        given()
            .param("units", "imperial")
            .param("origins", "Washington,DC")
            .param("destinations", "New+York+City,NY")
            .param("key", apiKey)
        .when()
            .get("/distancematrix/json")
        .then()
            .statusCode(200);
    }

    @Test
    public void getResponseBody() {
        Response res =
        given()
            .param("units", "imperial")
            .param("origins", "Washington,DC")
            .param("destinations", "New+York+City,NY")
            .param("key", apiKey)
        .when()
            .get("/distancematrix/json");

        // You can either use .prettyPrint() or .asString()
        System.out.println(res.body().prettyPrint());
    }
}
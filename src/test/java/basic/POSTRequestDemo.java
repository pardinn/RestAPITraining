package basic;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.Parameters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class POSTRequestDemo {

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

    @Test
    public void statusCodeVerification() {
        given()
            .queryParam("key", apiKey)
            .body("{\n" +
                    "  \"location\": {\n" +
                    "    \"lat\": -33.8669710,\n" +
                    "    \"lng\": 151.1958750\n" +
                    "  },\n" +
                    "  \"accuracy\": 50,\n" +
                    "  \"name\": \"Google Shoes!\",\n" +
                    "  \"phone_number\": \"(02) 9374 4000\",\n" +
                    "  \"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\",\n" +
                    "  \"types\": [\"shoe_store\"],\n" +
                    "  \"website\": \"http://www.google.com.au/\",\n" +
                    "  \"language\": \"en-AU\"\n" +
                    "}")
        .when()
            .post("/place/add/json")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("scope",equalTo("APP"))
            .body("status",equalTo("OK"));
    }

    @Test
    public void printPOSTResponse() {
        Response res = given()
                .queryParam("key", apiKey)
                .body("{\n" +
                        "  \"location\": {\n" +
                        "    \"lat\": -33.8669710,\n" +
                        "    \"lng\": 151.1958750\n" +
                        "  },\n" +
                        "  \"accuracy\": 50,\n" +
                        "  \"name\": \"Google Shoes!\",\n" +
                        "  \"phone_number\": \"(02) 9374 4000\",\n" +
                        "  \"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\",\n" +
                        "  \"types\": [\"shoe_store\"],\n" +
                        "  \"website\": \"http://www.google.com.au/\",\n" +
                        "  \"language\": \"en-AU\"\n" +
                        "}")
            .when()
                .post("/place/add/json");

        System.out.println(res.body().asString());
    }
}

package basic;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.Parameters;
import resources.PropertiesFileHandler;

import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class POSTRequestDemo {

    /**
     * In order for this to work, you need to rename the configTemplate.properties file as config.properties.
     * In that file, you should fill the "google_api_key" with your own Google API key as value.
     * You can create a Google API key at the following location:
     * https://developers.google.com/maps/documentation/distance-matrix/start
     */

    private Properties prop;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://maps.googleapis.com";
        RestAssured.basePath = "/maps/api";
        prop = new Properties();
        PropertiesFileHandler.loadPropertiesFile(prop,"src/test/java/resources/config.properties");
    }

    @Test
    public void statusCodeVerification() {
        given()
            .queryParam("key", prop.getProperty("google_api_key"))
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
                .queryParam("key", prop.getProperty("google_api_key"))
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

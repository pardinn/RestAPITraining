package basic;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.Parameters;
import resources.PropertiesFileHandler;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class GETRequestDemo {

    /**
     * In order for this to work, you need to rename the configTemplate.properties file as config.properties.
     * In that file, you should fill the "google_api_key" with your own Google API key as value.
     * You can create a Google API key at the following location:
     * https://developers.google.com/maps/documentation/distance-matrix/start
     */
    private Properties prop;

    /**
     * Given I have this information
     * When I perform this action
     * Then this should be the output
     */
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://maps.googleapis.com";
        RestAssured.basePath = "/maps/api";
        prop = new Properties();
        PropertiesFileHandler.loadPropertiesFile(prop,"src/test/java/resources/config.properties");
    }

    @Test(enabled = false)
    public void statusCodeVerification() {
        given()
            .param("units", "imperial")
            .param("origins", "Washington,DC")
            .param("destinations", "New+York+City,NY")
            .param("key", prop.getProperty("google_api_key"))
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
            .param("key", prop.getProperty("google_api_key"))
        .when()
            .get("/distancematrix/json");

        // You can either use .prettyPrint() or .asString()
        System.out.println(res.body().prettyPrint());
    }
}

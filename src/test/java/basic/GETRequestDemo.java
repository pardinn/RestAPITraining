package basic;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.PropertiesFileHandler;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class GETRequestDemo {

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

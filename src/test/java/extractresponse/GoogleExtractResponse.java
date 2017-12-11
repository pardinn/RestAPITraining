package extractresponse;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.PropertiesFileHandler;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class GoogleExtractResponse {

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
        Response response =
            given()
                .param("units", "imperial")
                .param("origins", "Washington,DC")
                .param("destinations", "New+York+City,NY")
                .param("key", prop.getProperty("google_api_key"))
            .when()
                .get("/distancematrix/xml")
            .then()
                .statusCode(200).extract().response();

        String responseString = response.asString();
        System.out.println(responseString);

        String value = response.path("distancematrixresponse.row.element.duration.value");
        System.out.println("The duration value is: " + value);

        XmlPath xmlPath = new XmlPath(responseString);
        String text = xmlPath.get("distancematrixresponse.row.element.duration.text");
        System.out.println("The duration text using XML Path is: " + text);

    }
}

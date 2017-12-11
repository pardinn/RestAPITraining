package extractresponse;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.PropertiesFileHandler;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TwitterExtractResponse {

    private Properties prop;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.twitter.com";
        RestAssured.basePath = "/1.1/statuses";
        prop = new Properties();
        PropertiesFileHandler.loadPropertiesFile(prop,"src/test/java/resources/config.properties");
    }

    @Test
    public void extractResponse() {
        Response response =
            given()
                .auth()
                .oauth(prop.getProperty("twitter_api_key"),
                        prop.getProperty("twitter_api_secret"),
                        prop.getProperty("twitter_access_token"),
                        prop.getProperty("twitter_access_token_secret"))
                .queryParam("status", "Tweet generated using RestAssured API with Java")
            .when()
                .post("/update.json")
            .then()
                .statusCode(200)
                .extract().response();

        String id = response.path("id_str");
        System.out.println("The response.path: " + id);

        String responseString = response.asString();
        System.out.println(responseString);

        JsonPath jsPath = new JsonPath(responseString);
        String name = jsPath.get("user.name");
        System.out.println("The username is: " + name);
    }
}

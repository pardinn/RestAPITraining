package specs;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.PropertiesFileHandler;

import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

public class TwitterResponseSpecificationDemo {

    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessSecret;

    private static ResponseSpecification responseSpec;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.twitter.com";
        RestAssured.basePath = "/1.1/statuses";
        Properties prop = new Properties();
        PropertiesFileHandler.loadPropertiesFile(prop, "src/test/java/resources/config.properties");

        consumerKey = prop.getProperty("twitter_api_key");
        consumerSecret = prop.getProperty("twitter_api_secret");
        accessToken = prop.getProperty("twitter_access_token");
        accessSecret = prop.getProperty("twitter_access_token_secret");
        ResponseSpecBuilder responseBuilder = new ResponseSpecBuilder();
        responseBuilder.expectStatusCode(200);
        responseBuilder.expectBody("user.name", hasItem("RestAPI Automation"));
//        responseBuilder.expectBody("user.screen_name", hasItem("apiautomation"));
        responseSpec = responseBuilder.build();
    }

    @Test
    public void readTweet() {
        given()
                .auth()
                .oauth(consumerKey, consumerSecret, accessToken, accessSecret)
                .queryParam("screen_name", "apiautomation")
        .when()
                .get("/user_timeline.json")
        .then()
//                .statusCode(200)
//                .log().body()
//                .body("user.name", hasItem("RestAPI Automation"))
                .spec(responseSpec)
                .body("user.screen_name", hasItem("apiautomation"));
    }
}

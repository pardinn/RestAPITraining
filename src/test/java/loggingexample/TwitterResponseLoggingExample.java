package loggingexample;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.PropertiesFileHandler;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TwitterResponseLoggingExample {

    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessSecret;

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
    }

    @Test
    public void requestLogging() {
        given()
                .auth()
                .oauth(consumerKey, consumerSecret, accessToken, accessSecret)
                .queryParam("status", "Tweet generated using RestAssured API with Java")
        .when()
                .post("/update.json")
        .then()
                .log()
                //.headers()
                //.body()
                //.all()
                .ifError()
                .statusCode(200);
    }

}

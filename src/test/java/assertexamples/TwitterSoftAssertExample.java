package assertexamples;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.PropertiesFileHandler;

import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TwitterSoftAssertExample {

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
    public void readTweet() {
        given()
                .auth()
                .oauth(consumerKey, consumerSecret, accessToken, accessSecret)
                .queryParam("screen_name", "apiautomation")
        .when()
                .get("/user_timeline.json")
        .then()
                .statusCode(200)
                .body("user.name", hasItem("RestAPI Automation"))
                .body("entities.hashtags[0].text",hasItem("multiple"),
                      "entities.hashtags[0].size()",equalTo(2),
                      "entities.hashtags[1].size()",lessThan(2));
    }

}

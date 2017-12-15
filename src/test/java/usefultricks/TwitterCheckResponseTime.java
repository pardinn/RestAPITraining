package usefultricks;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.PropertiesFileHandler;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;

public class TwitterCheckResponseTime {

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
    public void printResponseTime() {
        Long responseTime =
        given()
                .auth()
                .oauth(consumerKey, consumerSecret, accessToken, accessSecret)
                .queryParam("screen_name", "apiautomation")
        .when()
                .get("/user_timeline.json")
//                .time();
                .timeIn(TimeUnit.SECONDS);
        System.out.println("Response time is: " + responseTime);
    }


    @Test
    public void checkResponseTime() {
        given()
                .auth()
                .oauth(consumerKey, consumerSecret, accessToken, accessSecret)
                .queryParam("screen_name", "apiautomation")
        .when()
                .get("/user_timeline.json")
        .then()
                .statusCode(200)
                .time(lessThan(1500L), TimeUnit.MILLISECONDS)
                .log().body()
                .body("user.name", hasItem("RestAPI Automation"))
                .body("user.screen_name",hasItem("apiautomation"))
                .body("entities.hashtags[1].text",hasItem("testing"))
                .body("entities.hashtags[1].size()",equalTo(1));
    }
}

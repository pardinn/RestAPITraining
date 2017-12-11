package twitterapiexample;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.PropertiesFileHandler;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TwitterEndToEndWorkflow {

    private Properties prop;
    private String tweetId = "";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.twitter.com";
        RestAssured.basePath = "/1.1/statuses";
        prop = new Properties();
        PropertiesFileHandler.loadPropertiesFile(prop,"src/test/java/resources/config.properties");
    }

    @Test
    public void postTweet() {
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

        tweetId = response.path("id_str");
        System.out.println("The tweet id is: " + tweetId);

    }

    @Test(dependsOnMethods = {"postTweet"})
    public void readTweet() {
        Response response =
            given()
                .auth()
                .oauth(prop.getProperty("twitter_api_key"),
                        prop.getProperty("twitter_api_secret"),
                        prop.getProperty("twitter_access_token"),
                        prop.getProperty("twitter_access_token_secret"))
                .queryParam("id", tweetId)
            .when()
                .get("/show.json")
            .then()
                .statusCode(200)
                .extract().response();

        String text = response.path("text");
        System.out.println("The tweet text is: " + text);
    }

    @Test(dependsOnMethods = {"readTweet"})
    public void deleteTweet() {
        given()
            .auth()
            .oauth(prop.getProperty("twitter_api_key"),
                    prop.getProperty("twitter_api_secret"),
                    prop.getProperty("twitter_access_token"),
                    prop.getProperty("twitter_access_token_secret"))
            .pathParam("id", tweetId)
        .when()
            .post("/destroy/{id}.json")
        .then()
            .statusCode(200);
    }
}

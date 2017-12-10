package twitterapiexample;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.PropertiesFileHandler;

import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TwitterPOSTRequest {

    /**
     * In order for this to work, you need to rename the configTemplate.properties file as config.properties.
     * In that file, you should fill the twitter key=value pairs.
     * You can create your Twitter key at the following location:
     * https://apps.twitter.com
     * You'll need to create a new app (if you don't have one for your account already)
     * After that, you'll need to generate an Access Token for you.
     * Once you've done all of this, you should have the API Key, API Secret, Access Token and Access Token Secret
     */
    private Properties prop;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.twitter.com";
        RestAssured.basePath = "/1.1/statuses";
        prop = new Properties();
        PropertiesFileHandler.loadPropertiesFile(prop,"src/test/java/resources/config.properties");
    }

    @Test
    public void statusCodeVerification() {
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
            .statusCode(200);
    }
}

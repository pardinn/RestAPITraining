package specs;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.PropertiesFileHandler;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.lessThan;

public class TwitterSpecificationWithTime {

    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;
    private String user_id;

    @BeforeClass
    public void setup() {

        Properties prop = new Properties();
        PropertiesFileHandler.loadPropertiesFile(prop, "src/test/java/resources/config.properties");

        String consumerKey = prop.getProperty("twitter_api_key");
        String consumerSecret = prop.getProperty("twitter_api_secret");
        String accessToken = prop.getProperty("twitter_access_token");
        String accessSecret = prop.getProperty("twitter_access_token_secret");
        user_id = prop.getProperty("twitter_user_id");
        AuthenticationScheme authScheme = RestAssured.oauth(consumerKey, consumerSecret, accessToken, accessSecret);

        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri("https://api.twitter.com");
        requestBuilder.setBasePath("/1.1/statuses");
        requestBuilder.addQueryParam("screen_name", user_id);
        requestBuilder.setAuth(authScheme);
        requestSpec = requestBuilder.build();

        ResponseSpecBuilder responseBuilder = new ResponseSpecBuilder();
        responseBuilder.expectStatusCode(200);
        responseBuilder.expectResponseTime(lessThan(2L), TimeUnit.SECONDS);
        responseBuilder.expectBody("user.name", hasItem("Victor Moraes"));
        responseSpec = responseBuilder.build();
    }

    @Test
    public void readTweet() {
        given()
                .spec(requestSpec)
        .when()
                .get("/user_timeline.json")
        .then()
                .spec(responseSpec)
                .body("user.screen_name", hasItem(user_id));
    }
}

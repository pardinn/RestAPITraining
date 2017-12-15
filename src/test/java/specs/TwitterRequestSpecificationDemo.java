package specs;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.PropertiesFileHandler;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TwitterRequestSpecificationDemo {

    private static RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {
//        RestAssured.baseURI = "https://api.twitter.com";
//        RestAssured.basePath = "/1.1/statuses";
        Properties prop = new Properties();
        PropertiesFileHandler.loadPropertiesFile(prop, "src/test/java/resources/config.properties");

        String consumerKey = prop.getProperty("twitter_api_key");
        String consumerSecret = prop.getProperty("twitter_api_secret");
        String accessToken = prop.getProperty("twitter_access_token");
        String accessSecret = prop.getProperty("twitter_access_token_secret");
        AuthenticationScheme authScheme = RestAssured.oauth(consumerKey, consumerSecret, accessToken, accessSecret);

        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri("https://api.twitter.com");
        requestBuilder.setBasePath("/1.1/statuses");
        requestBuilder.addQueryParam("screen_name", "apiautomation");
        requestBuilder.setAuth(authScheme);
        requestSpec = requestBuilder.build();
    }

    @Test
    public void readTweet() {
        given()
                .spec(requestSpec)
//                .auth()
//                .oauth(consumerKey, consumerSecret, accessToken, accessSecret)
//                .queryParam("screen_name", "apiautomation")
                .when()
                .get("/user_timeline.json")
                .then()
                .statusCode(200);
    }
}

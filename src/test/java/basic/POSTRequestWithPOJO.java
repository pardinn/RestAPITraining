package basic;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.PlacesAddModel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.PropertiesFileHandler;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class POSTRequestWithPOJO {

    private String apiKey;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://maps.googleapis.com";
        RestAssured.basePath = "/maps/api";
        Properties prop = new Properties();
        PropertiesFileHandler.loadPropertiesFile(prop,"src/test/java/resources/config.properties");

        apiKey = prop.getProperty("google_api_key");
    }


    @Test
    public void printPOSTResponse() {

        Response res = given()
                .queryParam("key", apiKey)
                .body(defaultPlaces())
            .when()
                .post("/place/add/json");

        System.out.println(res.body().asString());
    }

    @Test
    public void statusCodeVerification() {
        given()
            .queryParam("key", apiKey)
            .body(defaultPlaces())
        .when()
            .post("/place/add/json")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("scope",equalTo("APP"))
            .body("status",equalTo("OK"));
    }

    private PlacesAddModel defaultPlaces(){
        Map<String,Double> locationMap = new HashMap<>();
        locationMap.put("lat",-33.8669710);
        locationMap.put("lng",151.1958750);

        List<String> types = new ArrayList<>();
        types.add("shoe_store");

        PlacesAddModel places = new PlacesAddModel();
        places.setLocation(locationMap);
        places.setAccuracy(50);
        places.setName("Google Shoes!");
        places.setPhone_number("(02) 9374 4000");
        places.setAddress("48 Pirrama Road, Pyrmont, NSW 2009, Australia");
        places.setTypes(types);
        places.setWebsite("http://www.google.com.au/");
        places.setLanguage("en-AU");

        return places;
    }

}

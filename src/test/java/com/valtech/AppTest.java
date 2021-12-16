package com.valtech;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import io.restassured.path.json.JsonPath;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import static io.restassured.RestAssured.*;

public class AppTest {

    // TODO Move to TestNG instead of JUnit

    @Test
    public void postcodeApiGet() {
        given().header("accept", "*/*").
                when().get("https://api.postcodes.io/postcodes/sw1a1aa").
                then().statusCode(200).
                and().log().body().
                and().body("result.region", equalTo("London")).
                and().body("result.admin_district", equalTo("Westminster"));
    }

    @Test
    public void postcodeApiPost() {
        given().body("{\"postcodes\" : [\"OX49 5NU\", \"M32 0JG\", \"NE30 1DP\"]}").
                and().contentType(ContentType.JSON).
                when().post("https://api.postcodes.io/postcodes").
                then().statusCode(200).
                and().log().body().
                and().body("result[0].result.parish", equalTo("Brightwell Baldwin"));
    }

    @Test
    public void webPageShouldReturn200_WithLogging() {
        given().log().all().
                when().get("http://www.google.com").
                then().log().all().
                and().statusCode(200);
    }

    @Test
    public void testGetSingleUserProgrammatic() {
        io.restassured.specification.RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.body("{\"postcodes\" : [\"OX49 5NU\", \"M32 0JG\", \"NE30 1DP\"]}");
        io.restassured.response.Response res = request.post("https://postcodes.io/postcodes");
        assertEquals(200, res.getStatusCode());
        String json = res.asString();
        JsonPath jp = new JsonPath(json);

        assertEquals("Trafford", jp.get("result[1].result.primary_care_trust"));
        assertEquals((Integer) 3, jp.get("result.size()"));
    }

    @Test
    public void testGraphQLAndValidateSchema() {
        given().body("{\"query\" : \"{  jobs {title company {name websiteUrl} } } \"}").
                and().contentType(ContentType.JSON).
                when().log().all().post("https://api.graphql.jobs/").
                then().log().all().statusCode(200).
                and().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jobsWithCompany.schema.json"));

    }

}
package com.gdavidben.quarkuslab.cache;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class QuarkusCacheResourceTest {

    @Test
    public void shouldReturnEurRate() {
        given()
          .when().get("/bitcoinprice/eur")
          .then()
             .statusCode(200)
             .body(containsString("rate"));
    }

}
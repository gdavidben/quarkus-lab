package com.gdavidben.quarkuslab.cache;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Disabled
@QuarkusTest
@TestHTTPEndpoint(BitcoinPriceResource.class)
public class BitcoinPriceResourceTest {

  @InjectSpy
  BitcoinPriceResource bitcoinPriceResource;

  private ValidatableResponse getBitcoinPrice(final String symbol) {
    return given()
      .when().get("{symbol}", symbol)
      .then()
      .log().ifValidationFails()
      .statusCode(200);
  }

  @Test
  public void shouldReturnBtcPrice() {
    getBitcoinPrice("eur")
      .body("bpi.EUR.code", is("EUR"))
      .body("bpi.EUR.rate", is(notNullValue()));

    getBitcoinPrice("gbp")
      .body("bpi.GBP.code", is("GBP"))
      .body("bpi.GBP.rate", is(notNullValue()));

    getBitcoinPrice("cny")
      .body("bpi.CNY.code", is("CNY"))
      .body("bpi.CNY.rate", is(notNullValue()));
  }

  @Test
  public void shouldInvalidateCache() {
    bitcoinPriceResource.invalidateBitcoinPriceCache();

    getBitcoinPrice("eur")
      .body("bpi.EUR.code", is("EUR"))
      .body("bpi.EUR.rate", is(notNullValue()));

    getBitcoinPrice("eur")
      .body("bpi.EUR.code", is("EUR"))
      .body("bpi.EUR.rate", is(notNullValue()));

    bitcoinPriceResource.invalidateBitcoinPriceCache();

    getBitcoinPrice("eur")
      .body("bpi.EUR.code", is("EUR"))
      .body("bpi.EUR.rate", is(notNullValue()));

    verify(bitcoinPriceResource, times(2)).getPrice("eur");
  }

}

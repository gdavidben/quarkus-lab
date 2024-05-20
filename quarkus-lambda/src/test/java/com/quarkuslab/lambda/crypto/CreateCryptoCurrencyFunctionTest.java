package com.quarkuslab.lambda.crypto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class CreateCryptoCurrencyFunctionTest {

  @Inject
  CreateCryptoCurrencyFunction createCryptoCurrencyFunction;

  /*
  static {
    RestAssured.config =
      RestAssured.config.objectMapperConfig(ObjectMapperConfig.objectMapperConfig().defaultObjectMapperType
      (ObjectMapperType.JSONB));
  }
  */

  @Test
  public void shouldCallLambdaFunction() {
    final CryptoCurrency btc = CryptoCurrency.builder().symbol("BNB").name("Binance Coin").build();
    final CryptoCurrency response = createCryptoCurrencyFunction.handleRequest(btc, null);

    assertEquals(btc, response);
  }

  @Test
  public void shouldThrowSymbolAlreadyExistsException() throws RuntimeException {
    final CryptoCurrency usdt = CryptoCurrency.builder().symbol("USDT").name("Tether").build();

    createCryptoCurrencyFunction.handleRequest(usdt, null);

    Throwable exception = assertThrows(SymbolAlreadyExistsException.class, () -> {
      createCryptoCurrencyFunction.handleRequest(usdt, null);
    });

    assertEquals("Symbol already exists", exception.getMessage());
  }
}

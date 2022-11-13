package com.quarkuslab.lambda.crypto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class CryptoCurrencyRepositoryTest {

  @Inject
  CryptoCurrencyRepository cryptoCurrencyRepository;

  @Test
  public void shouldCreateAndFindCrypto() {
    final CryptoCurrency btc = CryptoCurrency.builder().symbol("BTC").name("Bitcoin").build();
    final CryptoCurrency eth = CryptoCurrency.builder().symbol("ETH").name("Ethereum").build();
    cryptoCurrencyRepository.create(btc);
    cryptoCurrencyRepository.create(eth);

    assertEquals(Optional.of(btc), cryptoCurrencyRepository.find("BTC"));
    assertEquals(Optional.of(eth), cryptoCurrencyRepository.find("ETH"));
    assertEquals(Optional.empty(), cryptoCurrencyRepository.find("SOL"));
  }

}

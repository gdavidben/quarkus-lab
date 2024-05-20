package com.quarkuslab.lambda.crypto;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class CryptoCurrencyRepository {

  private static final List<CryptoCurrency> cryptoCurrencyList = Collections.synchronizedList(new ArrayList<>());

  public List<CryptoCurrency> findAll() {
    return cryptoCurrencyList.stream().collect(toList());
  }

  public Optional<CryptoCurrency> find(final String symbol) {
    return cryptoCurrencyList.stream().filter(cc -> symbol.equals(cc.getSymbol())).findFirst();
  }

  public CryptoCurrency create(final CryptoCurrency cryptoCurrency) {
    find(cryptoCurrency.getSymbol())
      .filter(Objects::nonNull)
      .ifPresent(u -> {
        throw new SymbolAlreadyExistsException();
      });

    return Optional.of(cryptoCurrency)
      .map(cc -> {
        cryptoCurrencyList.add(cc);
        return cc;
      }).orElseThrow(RuntimeException::new);
  }
}

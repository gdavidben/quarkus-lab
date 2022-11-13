package com.quarkuslab.lambda.crypto;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import javax.inject.Inject;
import javax.inject.Named;

@Named("create")
public class CreateCryptoCurrencyFunction implements RequestHandler<CryptoCurrency, CryptoCurrency> {

  @Inject
  CryptoCurrencyRepository cryptoCurrencyRepository;

  @Override
  public CryptoCurrency handleRequest(final CryptoCurrency cryptoCurrency, Context context) {
    return cryptoCurrencyRepository.create(cryptoCurrency);
  }
}

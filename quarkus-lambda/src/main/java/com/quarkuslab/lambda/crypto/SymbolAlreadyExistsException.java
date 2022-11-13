package com.quarkuslab.lambda.crypto;

public class SymbolAlreadyExistsException extends RuntimeException {

  public SymbolAlreadyExistsException() {
    super("Symbol already exists");
  }
}

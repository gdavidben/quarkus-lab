package com.quarkuslab.lambda.crypto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrency {

  @NotBlank(message = "Symbol must not be blank")
  private String symbol;
  @NotBlank(message = "Name must not be blank")
  private String name;
}

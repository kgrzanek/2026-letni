// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.nbp.entity;

import java.math.BigDecimal;
import java.util.Objects;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ExchangeRate(String code, String name, int multiplier, BigDecimal midRate) {

  public ExchangeRate {
    Objects.requireNonNull(code);
    Objects.requireNonNull(name);
    Objects.requireNonNull(midRate);
  }

}

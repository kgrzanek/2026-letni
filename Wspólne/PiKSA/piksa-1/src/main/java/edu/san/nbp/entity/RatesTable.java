// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.nbp.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record RatesTable(String tableNumber, LocalDate publicationDate, List<ExchangeRate> rates) {

  public RatesTable {
    Objects.requireNonNull(tableNumber);
    Objects.requireNonNull(publicationDate);
    rates = List.copyOf(rates);
  }
}

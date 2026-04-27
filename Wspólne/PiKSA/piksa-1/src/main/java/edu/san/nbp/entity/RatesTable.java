// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.nbp.entity;

import java.time.LocalDate;
import java.util.List;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record RatesTable(String tableNumber, LocalDate publicationDate, List<ExchangeRate> rates) {}

// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.nbp.entity;

import java.math.BigDecimal;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ExchangeRate(String code, String name, int multiplier, BigDecimal midRate) {}

// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.sealed;

public sealed interface BinaryOp {

  default double eval(double x, double y) {
    return switch (this) {
      case final Add _ -> x + y;
      case final Sub _ -> x - y;
      case final Mul _ -> x * y;
      case final Div _ -> x / y;
    };
  }

  record Add() implements BinaryOp {}

  record Sub() implements BinaryOp {}

  record Mul() implements BinaryOp {}

  record Div() implements BinaryOp {}
}

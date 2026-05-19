// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.sealed;

public sealed interface BinaryOp {

  default double eval(double x, double y) {
    return switch (this) {
      case Add _ -> x + y;
      case Sub _ -> x - y;
      case Mul _ -> x * y;
      case Div _ -> x / y;
    };
  }

  record Add() implements BinaryOp {}

  record Sub() implements BinaryOp {}

  record Mul() implements BinaryOp {}

  record Div() implements BinaryOp {}
}

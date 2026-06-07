// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.monads;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public sealed interface Either<L, R> {

  public static record Left<L, R>(L left) implements Either<L, R> {
    public Left {
      Objects.requireNonNull(left);
    }
  }

  public static record Right<L, R>(R right) implements Either<L, R> {
    public Right {
      Objects.requireNonNull(right);
    }
  }

  static <L, R> Either<L, R> left(L left) {
    return new Left<>(left);
  }

  static <L, R> Either<L, R> right(R right) {
    return new Right<>(right);
  }

  default Optional<L> maybeLeft() {
    return switch (this) {
      case Either.Left(final L left) -> Optional.of(left);
      case Either.Right(_)           -> Optional.empty();
    };
  }

  default Optional<R> maybeRight() {
    return switch (this) {
      case Either.Left(_)              -> Optional.empty();
      case Either.Right(final R right) -> Optional.of(right);
    };
  }

  default Either<L, R> flatMap(Function<? super R, Either<L, R>> mapper) {
    return maybeRight().map(mapper).orElse(this);
  }

}

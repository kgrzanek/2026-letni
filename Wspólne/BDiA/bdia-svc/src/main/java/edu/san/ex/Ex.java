package edu.san.ex;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public final class Ex {

  public static <V> V evalUnchecked(Callable<V> body) {
    try {
      return body.call();
    } catch (final Exception e) {
      return raise(e);
    }
  }

  public static <V> V raise(Throwable t) {
    return sneakyThrow0(t);
  }

  public static Supplier<RuntimeException> create(String message) {
    return () -> new RuntimeException(message);
  }

  public static Supplier<RuntimeException> create(
      Supplier<String> messageSupplier) {
    Objects.requireNonNull(messageSupplier);
    return () -> new RuntimeException(messageSupplier.get());
  }

  @SuppressWarnings("unchecked")
  private static <V, X extends Throwable> V sneakyThrow0(Throwable t) throws X {
    throw (X) t;
  }

  private Ex() {}

}

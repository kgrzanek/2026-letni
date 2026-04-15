package edu.san.ex;

import java.util.Objects;
import java.util.function.Supplier;

public final class Impossible extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public static Supplier<Impossible> create(String message) {
    return () -> new Impossible(message);
  }

  public static Supplier<Impossible> create(Supplier<String> messageSupplier) {
    Objects.requireNonNull(messageSupplier);
    return () -> new Impossible(messageSupplier.get());
  }

  public Impossible() {}

  public Impossible(String message) {
    super(message);
  }

  public Impossible(Throwable cause) {
    super(cause);
  }

  public Impossible(String message, Throwable cause) {
    super(message, cause);
  }

}

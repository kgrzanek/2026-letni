// © 2025 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.fp;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Beware! Thread unsafe!
 *
 * @param <T>
 */
public final class Delay<T> {

  public static <S> Delay<S> of(Supplier<S> supplier) {
    return new Delay<>(supplier);
  }

  public T value() {
    if (isPending()) {
      value = supplier.get();
      setNonPending();
    }
    return value;
  }

  public boolean isPending() {
    return null != supplier;
  }

  private void setNonPending() {
    supplier = null;
  }

  private Supplier<T> supplier;

  private T value;

  private Delay(Supplier<T> supplier) {
    this.supplier = Objects.requireNonNull(supplier);
  }

}

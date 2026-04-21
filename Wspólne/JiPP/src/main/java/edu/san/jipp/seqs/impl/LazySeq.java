// © 2025 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.seqs.impl;

import java.util.function.Supplier;

import edu.san.jipp.fp.Delay;
import edu.san.jipp.seqs.ISeq;

// Np. (0 1 1 2 3 5 8 13 21 ...)

public final class LazySeq<T> implements ISeq<T> {

  public static <T> LazySeq<T> of(T first, Delay<ISeq<T>> rest) {
    return new LazySeq<>(first, rest);
  }

  public static <T> LazySeq<T> of(T first, Supplier<ISeq<T>> rest) {
    return of(first, Delay.of(rest));
  }

  private final T first;

  private final Delay<ISeq<T>> rest;

  private LazySeq(T first, Delay<ISeq<T>> rest) {
    this.first = first;
    this.rest = rest;
  }

  @Override
  public T first() {
    return first;
  }

  @Override
  public ISeq<T> rest() {
    return rest.value();
  }

  @Override
  public ISeq<T> cons(T obj) {
    return new LinkedSeq<>(obj, this);
  }

  @Override
  public boolean isNil() {
    return false;
  }

}

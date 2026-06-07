// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.colls;

import java.util.Objects;

public class MySet<T> { // T - zmienna "typowa" (ang. type-variable)

  private final T[] elements;

  private int size;

  public MySet(int maxSize) {
    if (maxSize < 1)
      throw new IllegalArgumentException();

    @SuppressWarnings("unchecked")
    final var elems = (T[]) new Object[maxSize];
    elements = elems;
  }

  public boolean add(T e) {
    if (hasElement(e))
      return false;

    if (isFull())
      throw new IllegalStateException();

    elements[size++] = e;
    return true;
  }

  public boolean hasElement(T e) {
    Objects.requireNonNull(e);
    for (var i = 0; i < getSize(); i++) {
      if (elements[i].equals(e))
        return true;
    }

    return false;
  }

  public int getSize() {
    return size;
  }

  public int getMaxSize() {
    return elements.length;
  }

  public boolean isEmpty() {
    return getSize() == 0;
  }

  public boolean isFull() {
    return getSize() == getMaxSize();
  }

  @Override
  public String toString() {
    final var s = new StringBuilder("#{");
    for (int i = 0, n = getSize(); i < n; i++) {
      s.append(elements[i]);
      if (i != n - 1) {
        s.append(",");
      }
    }
    return s.append("}").toString();
  }

}

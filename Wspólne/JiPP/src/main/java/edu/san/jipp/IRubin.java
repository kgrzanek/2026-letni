// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp;

public interface IRubin<T> {

  T adjustVolume(T delta);

  void setChannel(T channel);

  T adjustBrightness(T delta);

  default void foo() {
    IO.println("aaaa" + this);
  }

}

// © 2025 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.fp;

import java.time.Duration;

public class Program07 {

  static int longComputations1(int i) {
    try {
      Thread.sleep(Duration.ofSeconds(2));
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("longComputations1 zwraca wartość");
    return i + 3;
  }

  static int longComputations2(int i, int j) {
    try {
      Thread.sleep(Duration.ofSeconds(3));
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("longComputations2 zwraca wartość");
    return i - 2 * j;
  }

  public static void main(String... args) {
    // computeEagerly();
    computeLazily();
    System.out.println("It's over");
  }

  static void computeEagerly() {
    final var n = 4;
    final var m = longComputations1(n);
    final var k = longComputations2(n, m);
    final var j = k + 3;
    System.out.println(j);
  }

  static void computeLazily() {
    final var n = Delay.of(() -> 4);
    final var m = Delay.of(() -> longComputations1(n.value()));
    final var k = Delay.of(() -> longComputations2(n.value(), m.value()));
    final var j = Delay.of(() -> k.value() + 3);

    System.out.println(j.value());
    System.out.println(k.value());
    System.out.println(m.value());
  }

}

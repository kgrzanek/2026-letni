// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.fp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DelayTest {

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  int longComputations(int m, int n) {
    IO.println("Działa longComputations(" + m + ", " + n + ")");
    sleep(4000);
    var result = m + n;
    IO.println("~");
    return result;
  }

  @Test
  void testOfLongComputations() {
    IO.println("testOfLongComputations");
    var j = longComputations(4, 5);
    IO.println("Wynik: " + j);
  }

  @Test
  void testOfLongComputationsWithDelay() {
    IO.println("testOfLongComputationsWithDelay");
    var j = Delay.of(() -> longComputations(4, 5));
    IO.println("Wynik: " + j.value());
  }

  @Test
  void testOf() {
    var d = Delay.of(() -> 42);
    assertNotNull(d);
    assertTrue(d.isPending());
  }

  @Test
  void testValue() {
    var d = Delay.of(() -> "hello");
    assertEquals("hello", d.value());
  }

  @Test
  void testIsPending() {
    var d = Delay.of(() -> 99);
    assertTrue(d.isPending());
    d.value();
    assertFalse(d.isPending());
  }

  @Test
  void testValueComputedOnce() {
    // provider wywołany dokładnie raz — wynik zapamiętany
    var counter = new int[]{0};
    var d = Delay.of(() -> {
      counter[0]++;
      return counter[0];
    });
    assertEquals(1, d.value());
    assertEquals(1, d.value());
    assertEquals(1, d.value());
    assertEquals(1, counter[0]);
  }

  @Test
  void testValueNull() {
    // Delay może przechowywać null jako wynik
    var d = Delay.<String>of(() -> null);
    assertTrue(d.isPending());
    assertNull(d.value());
    // po pierwszym wywołaniu provider == null => isPending() == false,
    // ale wynik to null — sprawdzamy czy value() nadal zwraca null
    assertNull(d.value());
  }

}

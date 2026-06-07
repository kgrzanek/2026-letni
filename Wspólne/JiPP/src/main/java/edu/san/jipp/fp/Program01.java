// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.fp;

@SuppressWarnings("static-method")
public class Program01 {

  static long factorial1(final int n) {
    final var result = n == 0 ? 1L : n * factorial1(n - 1);
    IO.println(n + ", " + result);
    return result;
  }

  static long factorial2(final int n, final long result) { // n, result -
                                                           // zmienne stanu
                                                           // (ang.
                                                           // state-variables)
    IO.println(n + ", " + result);
    return n == 0 ? result : factorial2(n - 1, n * result);
  }

  static void factorialImpertively() {
    final var n = 4;
    var factorial = 1;
    var i = 1;
    while (i <= n) {
      factorial = factorial * i;
      i = i + 1;
    }
    IO.println(n);
    IO.println(factorial);
  }

  void main() {
    // factorialImpertively();
    IO.println(factorial1(7));
  }

}

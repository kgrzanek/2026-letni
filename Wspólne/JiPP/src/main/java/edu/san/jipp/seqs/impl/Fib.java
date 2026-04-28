// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.seqs.impl;

import edu.san.jipp.fp.functions.Unary;
import edu.san.jipp.seqs.Seqs;

public class Fib {

  void main() {
    record PairOfLongs(long a, long b) {}
    final Unary<PairOfLongs, PairOfLongs> fibGen = pair -> new PairOfLongs(
        pair.b,
        pair.a + pair.b);

    final var fib = Seqs
        .iterate(fibGen, new PairOfLongs(0L, 1L))
        .map(PairOfLongs::a);

    final var fib10 = fib.take(10);
    IO.println(fib10.asString());

    var sum = fib
        .filter(n -> n % 2 == 0)
        .take(10)
        // .reduce((accum, n) -> accum + n, 0L)
        ;

    IO.println(sum.asString());

  }

}

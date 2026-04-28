// © 2025 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.seqs.impl;

import edu.san.jipp.fp.functions.Unary;
import edu.san.jipp.seqs.Seqs;

class Program08 {

  void main() {
    final Unary<Long, Long> inc = n -> n + 1;
    final var naturals = Seqs.iterate(inc, 0L);
    final var ns3 = naturals.take(3);
    final var s3 = ns3.asString();
    IO.println(s3);

//    final var s1 = Seqs.iterate(n -> n + 1, 1)
//        .map(n -> n * n)
//        .take(5)
//        .reduce((i, j) -> i + j, 0L);
//
//    System.out.println(s1);
//
//    // System.out.println(s1.take(10).asString());
//
//    final var result = List.of(1, 2, 3, 4, 5)
//        .stream()
//        .map(n -> n * n)
//        .reduce((i, j) -> i + j);
//    System.out.println(result);
//
//    final Unary<Integer, Integer> inc = n -> n + 1;
//    IO.println(Seqs.iterate(inc, 0).take(10).asString());

  }

}

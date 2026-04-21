// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.seqs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class ISeqTest {

  // Pomocnicza funkcja: buduje sekwencję z varargs (ostatni element = first)
  @SafeVarargs
  private static <T> ISeq<T> seqOf(T... elems) {
    ISeq<T> s = ISeq.nil();
    for (int i = elems.length - 1; i >= 0; i--) {
      s = s.cons(elems[i]);
    }
    return s;
  }

  @Test
  void testCons() {
    var s = ISeq.<Integer>nil().cons(3).cons(2).cons(1);
    IO.println(s.asString());

    assertEquals(1, s.first());
    assertEquals(2, s.rest().first());
    assertEquals(3, s.rest().rest().first());
    assertTrue(s.rest().rest().rest().isNil());
  }

  @Test
  void testIsNil() {
    assertTrue(ISeq.nil().isNil());
    assertFalse(ISeq.<Integer>nil().cons(1).isNil());
  }

  @Test
  void testIterator() {
    var s = seqOf(1, 2, 3);
    var visited = new ArrayList<Integer>();
    var it = s.iterator();
    while (it.hasNext()) {
      visited.add(it.next());
    }
    assertEquals(java.util.List.of(1, 2, 3), visited);
  }

  @Test
  void testIteratorThrowsOnEmpty() {
    var it = ISeq.<Integer>nil().iterator();
    assertFalse(it.hasNext());
    assertThrows(NoSuchElementException.class, it::next);
  }

  @Test
  void testAsString() {
    assertEquals("()", ISeq.nil().asString());
    assertEquals("(1)", seqOf(1).asString());
    assertEquals("(1,2,3)", seqOf(1, 2, 3).asString());
  }

  @Test
  void testFilter() {
    var s = seqOf(1, 2, 3, 4, 5);
    var evens = s.filter(x -> x % 2 == 0);
    assertEquals("(2,4)", evens.asString());

    assertTrue(ISeq.<Integer>nil().filter(_ -> true).isNil());
    assertTrue(seqOf(1, 3, 5).filter(x -> x % 2 == 0).isNil());
  }

  @Test
  void testTake() {
    var s = seqOf(1, 2, 3, 4, 5);
    assertEquals("(1,2,3)", s.take(3).asString());
    assertEquals("()", s.take(0).asString());
    assertEquals("(1,2,3,4,5)", s.take(10).asString());
    assertTrue(ISeq.<Integer>nil().take(3).isNil());
  }

  @Test
  void testReduce() {
    var s = seqOf(1, 2, 3, 4);
    int sum = s.reduce((acc, x) -> acc + x, 0);
    assertEquals(10, sum);

    int product = s.reduce((acc, x) -> acc * x, 1);
    assertEquals(24, product);

    assertEquals(0, ISeq.<Integer>nil().reduce((acc, x) -> acc + x, 0));
  }

  @Test
  void testMap() {
    var s = seqOf(1, 2, 3);
    var doubled = s.map(x -> x * 2);
    assertEquals("(2,4,6)", doubled.asString());

    assertTrue(ISeq.<Integer>nil().map(x -> x * 2).isNil());
  }

  @Test
  void testIterator1() {
    // iterator na pustej sekwencji — hasNext od razu false
    var it = ISeq.<String>nil().iterator();
    assertFalse(it.hasNext());

    // wielokrotne hasNext nie przesuwa kursora
    var s = seqOf("a", "b");
    var it2 = s.iterator();
    assertTrue(it2.hasNext());
    assertTrue(it2.hasNext());
    assertEquals("a", it2.next());
    assertTrue(it2.hasNext());
    assertEquals("b", it2.next());
    assertFalse(it2.hasNext());
  }

  @Test
  void testForEach() {
    var s = seqOf(10, 20, 30);
    var result = new ArrayList<Integer>();
    for (var x : s) {
      result.add(x);
    }
    assertEquals(java.util.List.of(10, 20, 30), result);

    // pusta sekwencja — brak iteracji
    var empty = new ArrayList<Integer>();
    for (var x : ISeq.<Integer>nil()) {
      empty.add(x);
    }
    assertTrue(empty.isEmpty());
  }

}

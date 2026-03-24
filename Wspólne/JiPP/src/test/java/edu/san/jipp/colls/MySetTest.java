// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.colls;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MySetTest {

  @SuppressWarnings("static-method")
  @Test
  void testMySet() {
    var s = new MySet<String>(10);
    assertEquals(10, s.getMaxSize());
    assertEquals(0, s.getSize());
    assertTrue(s.isEmpty());

    assertThrows(IllegalArgumentException.class, () -> new MySet<String>(0));
    assertThrows(IllegalArgumentException.class, () -> new MySet<String>(-1));
  }

  @SuppressWarnings("static-method")
  @Test
  void testAdd() {
    var s = new MySet<String>(3);
    assertTrue(s.add("a"));
    assertTrue(s.add("b"));
    assertEquals(2, s.getSize());

    // duplicates should not be added
    assertFalse(s.add("a"));
    assertEquals(2, s.getSize());

    assertTrue(s.add("c"));
    assertTrue(s.isFull());

    // adding to a full set should throw
    assertThrows(IllegalStateException.class, () -> s.add("d"));
  }

  @SuppressWarnings("static-method")
  @Test
  void testHasElement() {
    var s = new MySet<String>(5);
    s.add("x");
    s.add("y");

    assertTrue(s.hasElement("x"));
    assertTrue(s.hasElement("y"));
    assertFalse(s.hasElement("z"));

    assertThrows(NullPointerException.class, () -> s.hasElement(null));
  }

  @SuppressWarnings("static-method")
  @Test
  void testGetSize() {
    var s = new MySet<String>(5);
    assertEquals(0, s.getSize());
    s.add("a");
    assertEquals(1, s.getSize());
    s.add("b");
    assertEquals(2, s.getSize());
  }

  @SuppressWarnings("static-method")
  @Test
  void testGetMaxSize() {
    assertEquals(1, new MySet<String>(1).getMaxSize());
    assertEquals(100, new MySet<String>(100).getMaxSize());
  }

  @SuppressWarnings("static-method")
  @Test
  void testIsEmpty() {
    var s = new MySet<String>(5);
    assertTrue(s.isEmpty());
    s.add("a");
    assertFalse(s.isEmpty());
  }

  @SuppressWarnings("static-method")
  @Test
  void testIsFull() {
    var s = new MySet<String>(2);
    assertFalse(s.isFull());
    s.add("a");
    assertFalse(s.isFull());
    s.add("b");
    assertTrue(s.isFull());
  }

  @SuppressWarnings("static-method")
  @Test
  void testToString() {
    var s = new MySet<String>(5);
    assertEquals("#{}", s.toString());

    s.add("a");
    assertEquals("#{a}", s.toString());

    s.add("b");
    assertEquals("#{a,b}", s.toString());
  }

  @SuppressWarnings("static-method")
  @Test
  void testAddingDifferentData() {
    var s = new MySet<String>(5);
    s.add("a");
    // s.add(5);     // compilation error - type safety!
    // s.add(3.14);  // compilation error - type safety!

    assertEquals("#{a}", s.toString());

    var s1 = new MySet<Integer>(3);
    s1.add(123);
    assertEquals("#{123}", s1.toString());
  }


}

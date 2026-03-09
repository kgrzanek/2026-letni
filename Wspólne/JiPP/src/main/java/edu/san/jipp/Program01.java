package edu.san.jipp;

public class Program01 {

  public static void main(String[] args) {
    // Typy proste (ang. primitive types)

    // 8b      16b      32b     64b     32b      64b
    // byte <: short <: int  <: long <: float <: double
    //         char  <: int  <: long <: float <: double

    // S <: T - typ S jest podtypem typu T
    // S <  T
    // N : Real := 0.256;

    // boolean {true, false}
    // void

    // X : REAL := 0.356;
    // byte < short

    // var h = 127; // TYPE-INFERENCE, wnioskowanie o typach

    final byte b = 127;

    final byte e1;
    e1 = 5;
    IO.println(e1);

    final int i = b;
    IO.println(i);

    final int e = 130;
    final byte b2 = (byte) e;
    IO.println("b2=" + b2);

    final var n = 1023;
    final var b1 = (byte) n;

    // double y = n;

    final var f = 2.26f;

    final var d1 = 2.26 + 7;
    //       <d>   <d>  + <int> (faktycznie <double>)

    System.out.println(d1);
    System.out.println(f);
    System.out.println("b1=" + b1);
    System.out.println("Hello World!");
  }

}

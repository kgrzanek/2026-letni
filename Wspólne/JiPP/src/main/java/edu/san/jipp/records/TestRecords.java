// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.records;

class TestRecords {

  interface SomeI {

    String name();

    int age();
  }

  SomeI getMy() {
    record TmpRecord (int age, String name) implements SomeI {}
    return new TmpRecord(27, "Tom");
  }

  void main() {
    record TmpRecord (int n, String s) {}
  }

}

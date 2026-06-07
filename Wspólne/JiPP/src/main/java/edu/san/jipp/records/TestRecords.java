// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.records;

class TestRecords {

  interface SomeInterface {

    String name();

    int age();
  }

  SomeInterface getMy() {
    record TmpRecord(int age, String name) implements SomeInterface {}
    return new TmpRecord(27, "Tom");
  }

  void main() {}

}

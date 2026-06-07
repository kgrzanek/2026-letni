// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.records;

public record PointRecord(double x, double y) {

  public PointRecord {
    if (Double.isNaN(x))
      throw new IllegalArgumentException();
  }

}

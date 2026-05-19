// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.records;

public record PointRecord(double x, double y) {

  @Override
  public int hashCode() {
    return 31 * Double.hashCode(x()) + Double.hashCode(y());
  }

}

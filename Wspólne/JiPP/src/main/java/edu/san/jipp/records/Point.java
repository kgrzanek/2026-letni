// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.records;

public final class Point {

  public final double x;

  public final double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public int hashCode() {
    return 31 * Double.hashCode(x) + Double.hashCode(y);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Point other = (Point) obj;
    return Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x)
        && Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y);
  }

  public double x() {
    return x;
  }

  public double y() {
    return y;
  }

}

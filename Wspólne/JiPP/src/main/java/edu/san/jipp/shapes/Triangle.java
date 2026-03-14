// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.shapes;

public class Triangle extends Shape {

  private final double base;

  private final double height;

  public Triangle(double base, double height) {
    this.base = base;
    this.height = height;
  }

  @Override
  public double getArea() {
    return base * height;
  }

  @Override
  public void draw() {
    IO.println("Triangle(" + base + "," + height + ")::draw()");
  }

}
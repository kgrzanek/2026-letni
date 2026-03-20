// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.vehicles;

public class Car {

  private final String color;

  private final int maxSpeed;

  private int speed;

  public Car(String color, int maxSpeed) {
    this.color = color;
    this.maxSpeed = maxSpeed;
  }

  public void speedUp(int delta) {
    if (delta < 0)
      throw new IllegalArgumentException();

    speed += delta;
  }

  public void speedUp(double delta) {
    speedUp(normalizeDelta(delta));
  }

  public void slowDown(int delta) {
    if (delta < 0)
      throw new IllegalArgumentException();

    speed -= delta;
  }

  public void slowDown(double delta) {
    slowDown(normalizeDelta(delta));
  }

  public String getColor() {
    return color;
  }

  public int getMaxSpeed() {
    return maxSpeed;
  }

  public int getSpeed() {
    return speed;
  }

  protected int normalizeDelta(double delta) {
    final var normalized = (int) delta;
    return normalized < 1 ? 1 : normalized;
  }
}

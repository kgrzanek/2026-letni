// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.vehicles;

public class Truck extends Car {

  private final double maxLoad;

  private double cargo;

  public Truck(String color, int maxSpeed, double maxLoad) {
    if (maxLoad <= 0)
      throw new IllegalArgumentException();

    super(color, maxSpeed);
    this.maxLoad = maxLoad;
  }

  public void load(double cargo) {
    if (cargo > getMaxLoad())
      throw new IllegalArgumentException();

    this.cargo = cargo;
  }

  public double getMaxLoad() {
    return maxLoad;
  }

  public double getCargo() {
    return cargo;
  }

  @Override
  public void speedUp(int delta) {
    IO.println("Wait, I'm a really slow Truck");
    super.speedUp(delta * 0.25);
  }

  @Override
  public void slowDown(int delta) {
    IO.println("Wait, I'm a really slow Truck");
    super.slowDown(delta * 0.35);
  }

}

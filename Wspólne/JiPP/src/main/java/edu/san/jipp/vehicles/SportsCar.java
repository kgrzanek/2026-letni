// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.vehicles;

public class SportsCar extends Car {

  private boolean turbo;

  public SportsCar(String color, int maxSpeed) {
    super(color, maxSpeed);
  }

  public void boost(boolean on) {
    turbo = on;
  }

  public boolean isTurbo() {
    return turbo;
  }

}

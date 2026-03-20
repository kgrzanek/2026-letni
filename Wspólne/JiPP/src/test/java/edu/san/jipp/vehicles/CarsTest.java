// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.vehicles;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class CarsTest {

  @Test
  void test() {
    var car = new Car("red", 150);
    var sportsCar = new SportsCar("yellow", 250);
    var truck = new Truck("green", 120, 3_000);

    car.speedUp(50);
    sportsCar.speedUp(150);
    truck.speedUp(80);
  }

}

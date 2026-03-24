// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.vehicles;

public class CarManager {

  static void speedUpCars(Car... cars) { // TA METODA JEST POLIMORFICZNA
    for (final var c : cars) {
      c.speedUp(40);
      // w tym bowiem miejscu może się pojawić obiekt c(ar), którego rzeczywisty
      // typ nie jest znany na etapie kompilacji, i który może różnić
      // się pomiędzy kolejnymi uruchomieniami metody speedUpCars.

      // JEST TO POLIMORFIZM INKLUZYJNY
      // Występują tutaj relacje:
      // Truck <: Car
      // SportsCar <: Car

      // Zgodnie z teorią mnogości, mamy tutaj do czynienia z inkluzją zbiorów.

      // W wywołaniu car.speedUp(...) występuje tzw. późne wiązanie (ang. late
      // binding).
    }
  }

  static void main() {
    final var car = new Car("red", 150);
    final var sportsCar = new SportsCar("yellow", 250);
    final var truck = new Truck("green", 120, 3_000);

    final Car[] cars = { car, sportsCar, truck };
    speedUpCars(cars);

    speedUpCars();
    speedUpCars(car);
    speedUpCars(car, sportsCar, truck);
  }
}

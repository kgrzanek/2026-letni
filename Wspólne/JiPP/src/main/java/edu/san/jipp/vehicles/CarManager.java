// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.vehicles;

public class CarManager {

  static void speedUpCars(Car[] cars) { // TA METODA JEST POLIMORFICZNA
    for (Car car : cars) {
      car.speedUp(40);
      // w tym bowiem miejscu może się pojawić obiekt car, którego rzeczywisty
      // typ nie jest znany na etapie kompilacji, i który może różnić
      // się pomiędzy kolejnymi uruchomieniami metody speedUpCars.

      // JEST TO POLIMORFIZM INKLUZYJNY
      // Występują tutaj relacje:
      // Truck     <: Car
      // SportsCar <: Car

      // Zgodnie z teorią mnogości, mamy tutaj do czynienia z inkluzją zbiorów.

      // W wywołaniu car.speedUp(...) występuje tzw. późne wiązanie (ang. late binding).
    }
  }

  static void main() {
    var car = new Car("red", 150);
    var sportsCar = new SportsCar("yellow", 250);
    var truck = new Truck("green", 120, 3_000);

    Car[] cars = { car, sportsCar, truck };
    speedUpCars(cars);
  }
}

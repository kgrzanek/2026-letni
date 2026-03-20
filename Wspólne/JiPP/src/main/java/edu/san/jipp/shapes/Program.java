// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.shapes;

public class Program {

  static void main() {
    IO.println("Program::main()");

    final var canvas = new Canvas();
    final var rectangle = new Rectangle(5, 4);
    final var triangle = new Triangle(3, 2);

    canvas.draw(rectangle);
    canvas.draw(triangle);

  }

}

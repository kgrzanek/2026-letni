// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.shapes;

public class Program {

  static void main() {
    IO.println("Program::main()");

    var canvas = new Canvas();
    var rectangle  = new Rectangle(5, 4);
    var triangle = new Triangle(3, 2);

    canvas.draw(rectangle);
    canvas.draw(triangle);

  }

}

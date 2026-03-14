// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.jipp.shapes;

public class Canvas {

  public void draw(Shape shape) {
    // TUTAJ WYSTĘPUJE  POLIMORFIZM INKLUZYJNY
    // draw(shape) jest POLIMORFICZNA
    shape.draw();
  }

}

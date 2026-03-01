class Program {

  public static void main(String... args) {
    int i = 0; // Tutaj = oznacza inicjalizację - nadanie wartości początkowej
    while (i < 11) {
      IO.println(i);
      i = i + 1; // i++;, tutaj = oznacza przypisanie, czyli zastąpienie nową wartością
    }
  }

  // Styl imperatywny
  // Java jest językiem imperatywnym, zorientowanym obiektowo

  // W programowaniu funkcyjnym NIE MA operatora przypisania =
  // Dlaczego mamy nie używać operatora = (przypisania)?
  // - Wnioskowanie o poprawności oprogramowania
  // - W szczególności - poprawność programów współbieżnych i rozproszonych
  // - Elegancja, minimalizm znaczeniowy
}

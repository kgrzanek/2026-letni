// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.nbp.control;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import edu.san.nbp.entity.ExchangeRate;
import edu.san.nbp.entity.RatesTable;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NbpFetcher {

  @ConfigProperty(name = "edu.san.nbp.url")
  String url;

  // Kolejność działania (zewnątrz → środek):
  //   Fallback → CircuitBreaker → Retry → Timeout → wywołanie HTTP
  @Timeout(value = 5, unit = ChronoUnit.SECONDS)
  @Retry(maxRetries = 3, delay = 500, delayUnit = ChronoUnit.MILLIS)
  @CircuitBreaker(requestVolumeThreshold = 5, failureRatio = 0.5,
                  delay = 30, delayUnit = ChronoUnit.SECONDS)
  @Fallback(fallbackMethod = "emptyTable")
  public Optional<RatesTable> fetch() throws Exception {
    try (var client = HttpClient.newHttpClient()) {
      final var request = HttpRequest.newBuilder(URI.create(url)).GET().build();
      final var response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
      return Optional.of(parse(response.body()));
    }
  }

  // Fallback: sygnał dla RatesStore żeby zachował stare (stale) dane
  Optional<RatesTable> emptyTable() {
    return Optional.empty();
  }

  private RatesTable parse(byte[] bytes) throws Exception {
    final var factory = DocumentBuilderFactory.newInstance();
    final var builder = factory.newDocumentBuilder();
    // JAXP odczyta encoding="ISO-8859-2" z deklaracji XML automatycznie
    final var doc = builder.parse(new ByteArrayInputStream(bytes));

    final var tableNumber =
        doc.getElementsByTagName("numer_tabeli").item(0).getTextContent();
    final var publicationDate =
        LocalDate.parse(doc.getElementsByTagName("data_publikacji").item(0).getTextContent());

    final var positions = doc.getElementsByTagName("pozycja");
    final var rates = new ArrayList<ExchangeRate>(positions.getLength());
    for (var i = 0; i < positions.getLength(); i++) {
      final var children = positions.item(i).getChildNodes();
      String name = null, code = null, midRateStr = null;
      var multiplier = 1;
      for (var j = 0; j < children.getLength(); j++) {
        final var child = children.item(j);
        switch (child.getNodeName()) {
          case "nazwa_waluty" -> name = child.getTextContent();
          case "kod_waluty"   -> code = child.getTextContent();
          case "przelicznik"  -> multiplier = Integer.parseInt(child.getTextContent().trim());
          case "kurs_sredni"  -> midRateStr = child.getTextContent().replace(',', '.');
        }
      }
      if (code != null && name != null && midRateStr != null) {
        rates.add(new ExchangeRate(code, name, multiplier, new BigDecimal(midRateStr)));
      }
    }
    return new RatesTable(tableNumber, publicationDate, List.copyOf(rates));
  }
}

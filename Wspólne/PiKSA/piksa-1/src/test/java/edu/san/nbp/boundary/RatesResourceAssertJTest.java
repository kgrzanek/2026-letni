package edu.san.nbp.boundary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.san.nbp.control.RatesStore;
import edu.san.nbp.entity.ExchangeRate;
import edu.san.nbp.entity.RatesTable;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RatesResourceAssertJTest {

  @InjectMock
  RatesStore store;

  @TestHTTPResource("/rates")
  URL url;

  private static final List<ExchangeRate> TEST_RATES = List.of(
      new ExchangeRate("EUR", "euro", 1, new BigDecimal("4.2445")),
      new ExchangeRate("USD", "dolar amerykański", 1, new BigDecimal("3.8012")));

  private static final RatesTable TEST_TABLE =
      new RatesTable("080/A/NBP/2026", LocalDate.of(2026, 4, 27), TEST_RATES);

  @BeforeEach
  void setUp() {
    when(store.get()).thenReturn(Optional.of(TEST_TABLE));
    when(store.getByCode(anyString())).thenAnswer(inv ->
        TEST_RATES.stream()
            .filter(r -> r.code().equalsIgnoreCase(inv.getArgument(0)))
            .findFirst());
  }

  @Test
  void getRatesReturns200() throws Exception {
    final var response = get(url.toURI());
    assertThat(response.statusCode()).isEqualTo(200);
  }

  @Test
  void getRatesBodyContainsBothCurrencyCodes() throws Exception {
    final var response = get(url.toURI());
    assertThat(response.body()).contains("\"EUR\"").contains("\"USD\"");
  }

  @Test
  void getEurReturnsRateWithMidRate() throws Exception {
    final var response = get(URI.create(url.toExternalForm() + "/EUR"));
    assertThat(response.statusCode()).isEqualTo(200);
    assertThat(response.body()).contains("\"code\":\"EUR\"").contains("midRate");
  }

  @Test
  void getUnknownCodeReturns404() throws Exception {
    final var response = get(URI.create(url.toExternalForm() + "/XYZ"));
    assertThat(response.statusCode()).isEqualTo(404);
  }

  @Test
  void postRefreshReturns204() throws Exception {
    try (var client = HttpClient.newHttpClient()) {
      final var request = HttpRequest.newBuilder(URI.create(url.toExternalForm() + "/refresh"))
          .POST(HttpRequest.BodyPublishers.noBody())
          .build();
      final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
      assertThat(response.statusCode()).isEqualTo(204);
    }
  }

  @Test
  void getRatesWhenStoreEmptyReturns503() throws Exception {
    when(store.get()).thenReturn(Optional.empty());
    final var response = get(url.toURI());
    assertThat(response.statusCode()).isEqualTo(503);
  }

  private HttpResponse<String> get(URI uri) throws Exception {
    try (var client = HttpClient.newHttpClient()) {
      final var request = HttpRequest.newBuilder(uri).GET().build();
      return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
  }
}

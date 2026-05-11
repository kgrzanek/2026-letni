package edu.san.greeting.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
abstract class GreetingResourceAssertJTest {

  private static final String BASE_URL = "http://localhost:"
      + System.getProperty("http.port", "9080")
      + "/piksa-openliberty/api/hello";

  @Test
  void helloEndpointReturnsStatusOk() throws Exception {
    final var response = get();
    assertThat(response.statusCode()).isEqualTo(200);
  }

  @Test
  void helloBodyMatchesExpectedFormat() throws Exception {
    final var response = get();
    assertThat(response.body())
        .matches(
            "Hello from Open Liberty REST \\(visit #\\d+ in this session\\)");
  }

  // Demonstrates @SessionScoped behaviour: the counter increments within one
  // session and resets when a new session starts (no shared cookies → new
  // session).
  @Test
  void consecutiveCallsIncrementSessionCounter() throws Exception {
    final var cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
    final var request = HttpRequest.newBuilder(URI.create(BASE_URL)).GET()
        .build();

    try (var client = HttpClient.newBuilder().cookieHandler(cookieManager)
        .build()) {
      final var r1 = client.send(request, HttpResponse.BodyHandlers.ofString());
      final var r2 = client.send(request, HttpResponse.BodyHandlers.ofString());
      final var r3 = client.send(request, HttpResponse.BodyHandlers.ofString());

      assertThat(r1.body())
          .isEqualTo("Hello from Open Liberty REST (visit #1 in this session)");
      assertThat(r2.body())
          .isEqualTo("Hello from Open Liberty REST (visit #2 in this session)");
      assertThat(r3.body())
          .isEqualTo("Hello from Open Liberty REST (visit #3 in this session)");
    }
  }

  private HttpResponse<String> get() throws Exception {
    try (var client = HttpClient.newHttpClient()) {
      final var request = HttpRequest.newBuilder(URI.create(BASE_URL)).GET()
          .build();
      return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
  }
}

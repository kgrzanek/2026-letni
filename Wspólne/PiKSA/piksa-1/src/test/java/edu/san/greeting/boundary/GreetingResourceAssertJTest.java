package edu.san.greeting.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class GreetingResourceAssertJTest {

  @TestHTTPResource("/hello")
  URL url;

  @Test
  void helloEndpointReturnsStatusOk() throws Exception {
    final var response = get();
    assertThat(response.statusCode()).isEqualTo(200);
  }

  @Test
  void helloBodyMatchesExpectedFormat() throws Exception {
    final var response = get();
    assertThat(response.body())
        .matches("Hello from Quarkus REST \\(visit #\\d+ in this session\\)");
  }

  // Demonstrates @SessionScoped behaviour: the counter increments within one
  // session
  // and resets when a new session starts (no shared cookies → new session).
  @Test
  void consecutiveCallsIncrementSessionCounter() throws Exception {
    final var cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
    final var request = HttpRequest.newBuilder(url.toURI()).GET().build();

    try (var client = HttpClient.newBuilder().cookieHandler(cookieManager)
        .build()) {
      final var r1 = client.send(request, HttpResponse.BodyHandlers.ofString());
      final var r2 = client.send(request, HttpResponse.BodyHandlers.ofString());
      final var r3 = client.send(request, HttpResponse.BodyHandlers.ofString());

      assertThat(r1.body())
          .isEqualTo("Hello from Quarkus REST (visit #1 in this session)");
      assertThat(r2.body())
          .isEqualTo("Hello from Quarkus REST (visit #2 in this session)");
      assertThat(r3.body())
          .isEqualTo("Hello from Quarkus REST (visit #3 in this session)");
    }
  }

  private HttpResponse<String> get() throws Exception {
    try (var client = HttpClient.newHttpClient()) {
      final var request = HttpRequest.newBuilder(url.toURI()).GET().build();
      return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
  }
}

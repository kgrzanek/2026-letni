package edu.san.item.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ItemResourceAssertJTest {

  @TestHTTPResource("/items")
  URL url;

  // --- happy path ---

  @Test
  void createItemWithValidDataReturns201() throws Exception {
    final var response = post(
        "{\"name\":\"Widget\",\"description\":\"A useful widget\"}");
    assertThat(response.statusCode()).isEqualTo(201);
  }

  @Test
  void createItemResponseContainsItemIdAsUUID() throws Exception {
    final var response = post(
        "{\"name\":\"Widget\",\"description\":\"A useful widget\"}");
    // UUID pattern: 8-4-4-4-12 hex digits
    assertThat(response.body())
        .containsPattern(
            "\"itemId\"\\s*:\\s*\"[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}\"");
  }

  // --- @ValidName: must start uppercase, must not end uppercase ---

  @Test
  void createItemWithNameStartingLowercaseReturns400() throws Exception {
    final var response = post(
        "{\"name\":\"widget\",\"description\":\"starts lowercase\"}");
    assertThat(response.statusCode()).isEqualTo(400);
  }

  @Test
  void createItemWithNameEndingUppercaseReturns400() throws Exception {
    final var response = post(
        "{\"name\":\"WidgeT\",\"description\":\"ends uppercase\"}");
    assertThat(response.statusCode()).isEqualTo(400);
  }

  @Test
  void createItemWithAllUppercaseNameReturns400() throws Exception {
    final var response = post(
        "{\"name\":\"WIDGET\",\"description\":\"all caps\"}");
    assertThat(response.statusCode()).isEqualTo(400);
  }

  @Test
  void createItemWithBlankNameReturns400() throws Exception {
    final var response = post(
        "{\"name\":\"   \",\"description\":\"blank name\"}");
    assertThat(response.statusCode()).isEqualTo(400);
  }

  @Test
  void createItemWithNullNameReturns400() throws Exception {
    final var response = post("{\"name\":null,\"description\":\"null name\"}");
    assertThat(response.statusCode()).isEqualTo(400);
  }

  // --- @NotNull description ---

  @Test
  void createItemWithNullDescriptionReturns400() throws Exception {
    final var response = post("{\"name\":\"Widget\",\"description\":null}");
    assertThat(response.statusCode()).isEqualTo(400);
  }

  private HttpResponse<String> post(String body) throws Exception {
    try (var client = HttpClient.newHttpClient()) {
      final var request = HttpRequest.newBuilder(url.toURI())
          .POST(HttpRequest.BodyPublishers.ofString(body))
          .header("Content-Type", "application/json")
          .build();
      return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
  }
}

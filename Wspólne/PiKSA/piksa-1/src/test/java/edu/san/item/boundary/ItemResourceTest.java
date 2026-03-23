package edu.san.item.boundary;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ItemResourceTest {

  @Test
  void testCreateItem() {
    given()
      .contentType("application/json")
      .body("{\"name\":\"Widget\",\"description\":\"A useful widget\"}")
      .when().post("/items")
      .then()
      .statusCode(201)
      .body("itemId", notNullValue());
  }
}

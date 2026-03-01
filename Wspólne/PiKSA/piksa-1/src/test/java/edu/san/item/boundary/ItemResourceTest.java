package edu.san.item.boundary;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

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
      .body("name", is("Widget"))
      .body("description", is("A useful widget"));
  }
}

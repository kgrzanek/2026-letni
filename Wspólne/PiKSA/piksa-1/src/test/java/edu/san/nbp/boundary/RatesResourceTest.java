package edu.san.nbp.boundary;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.san.nbp.control.RatesStore;
import edu.san.nbp.entity.ExchangeRate;
import edu.san.nbp.entity.RatesTable;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RatesResourceTest {

  @InjectMock
  RatesStore store;

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
  void getRatesReturns200() {
    given().when().get("/rates").then().statusCode(200);
  }

  @Test
  void getRatesBodyContainsBothCodes() {
    given().when().get("/rates").then()
        .statusCode(200)
        .body("code", hasItems("EUR", "USD"));
  }

  @Test
  void getEurReturnsCorrectRate() {
    given().when().get("/rates/EUR").then()
        .statusCode(200)
        .body("code", is("EUR"))
        .body("midRate", notNullValue());
  }

  @Test
  void getUnknownCodeReturns404() {
    given().when().get("/rates/XYZ").then().statusCode(404);
  }

  @Test
  void postRefreshReturns204() {
    given().when().post("/rates/refresh").then().statusCode(204);
  }

  @Test
  void getRatesWhenStoreEmptyReturns503() {
    when(store.get()).thenReturn(Optional.empty());
    given().when().get("/rates").then().statusCode(503);
  }
}
